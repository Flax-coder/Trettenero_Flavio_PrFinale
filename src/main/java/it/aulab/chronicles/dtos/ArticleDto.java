package it.aulab.chronicles.dtos;

import java.time.LocalDate;

import it.aulab.chronicles.models.Category;
import it.aulab.chronicles.models.Image;
import it.aulab.chronicles.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String subtitle;
    private String body;
    private LocalDate publishDate;
    private Boolean isAccepted;
    private User user;
    private Category category;
    private Image image;
}
