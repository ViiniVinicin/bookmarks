package br.com.bookmarks.dto;

import lombok.Data;

@Data
public class BookmarkDTO {
    private String url;
    private String titulo;
    private String descricao;
}