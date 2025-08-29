package br.com.bookmarks.bookmarks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record BookmarkUpdateDTO(
        @NotBlank @URL String url,
        @NotBlank @Size(min = 3) String title,
        String description
) {}