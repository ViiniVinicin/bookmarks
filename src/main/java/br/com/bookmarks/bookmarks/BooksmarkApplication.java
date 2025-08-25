package br.com.bookmarks.bookmarks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "br.com.bookmarks.bookmarks",   // Onde está a classe principal
                "br.com.bookmarks.controller",  // Onde está o Controller
                "br.com.bookmarks.service",     // Onde está o Service
                "br.com.bookmarks.repository",  // Onde está o Repository
                "br.com.bookmarks.model.entity",// Onde está a Entidade
                "br.com.bookmarks.dto"          // Onde estão os DTOs
        }
)
public class BooksmarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksmarkApplication.class, args);
	}

}
