package br.com.bookmarks; // Note que o pacote da classe principal também foi atualizado

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// É só isso! Simples, limpo e automático.
@SpringBootApplication
public class BookmarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmarkApplication.class, args);
    }
}