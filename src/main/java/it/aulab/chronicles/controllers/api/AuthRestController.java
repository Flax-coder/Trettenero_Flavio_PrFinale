package it.aulab.chronicles.controllers.api;

import java.security.Principal;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthRestController {

    @GetMapping("/api/auth/me")
    public Map<String, Object> me(Principal principal) {

        if (principal == null) {
            return Map.of("authenticated", false);
        }

        return Map.of(
            "authenticated", true,
            "username", principal.getName()
        );
    }
}
