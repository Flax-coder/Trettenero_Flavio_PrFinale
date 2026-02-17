package it.aulab.chronicles.services;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.aulab.chronicles.models.Article;
import it.aulab.chronicles.models.Image;
import it.aulab.chronicles.repositories.ImageRepository;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import jakarta.transaction.Transactional;

@Service
public class MinioImageServiceImpl implements ImageService {

  @Autowired
  private ImageRepository imageRepository;

  private final S3Client s3;

  @Value("${storage.s3.bucket}")
  private String bucket;

  @Value("${storage.s3.public-base-url}")
  private String publicBaseUrl;

  private static final Set<String> ALLOWED = Set.of("image/jpeg", "image/png", "image/webp");

  public MinioImageServiceImpl(S3Client s3) {
    this.s3 = s3;
  }

  public void saveImageOnDB(String url, Article article) {
    imageRepository.save(Image.builder().path(url).article(article).build());
  }

  @Async
  public CompletableFuture<String> saveImageOnCloud(MultipartFile file) throws Exception {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("File is empty");
    }

    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED.contains(contentType)) {
      throw new IllegalArgumentException("Formato non supportato: " + contentType);
    }

    String ext = switch (contentType) {
      case "image/jpeg" -> "jpg";
      case "image/png" -> "png";
      case "image/webp" -> "webp";
      default -> "bin";
    };

    String key = "articles/" + UUID.randomUUID() + "." + ext;

    try {
      PutObjectRequest req = PutObjectRequest.builder()
          .bucket(bucket)
          .key(key)
          .contentType(contentType)
          .build();

      s3.putObject(req, RequestBody.fromBytes(file.getBytes()));

      String url = publicBaseUrl + "/" + key;
      return CompletableFuture.completedFuture(url);

    } catch (Exception e) {
      e.printStackTrace();
      return CompletableFuture.failedFuture(e);
    }
  }

  @Async
  @Transactional
  public void deleteImage(String imagePath) throws IOException {
    // imagePath = public URL, ricaviamo la key
    if (imagePath == null || imagePath.isBlank()) return;

    imageRepository.deleteByPath(imagePath);

    String prefix = publicBaseUrl.endsWith("/") ? publicBaseUrl : publicBaseUrl + "/";
    String key = imagePath.startsWith(prefix) ? imagePath.substring(prefix.length()) : imagePath;

    DeleteObjectRequest del = DeleteObjectRequest.builder()
        .bucket(bucket)
        .key(key)
        .build();

    s3.deleteObject(del);
  }
}
