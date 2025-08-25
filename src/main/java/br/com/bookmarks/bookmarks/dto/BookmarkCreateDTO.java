package br.com.bookmarks.bookmarks.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import jakarta.validation.constraints.Size;

public record BookmarkCreateDTO(
        @NotBlank @URL String url,
        @NotBlank @Size(min = 3) String title,
        String description
) {}