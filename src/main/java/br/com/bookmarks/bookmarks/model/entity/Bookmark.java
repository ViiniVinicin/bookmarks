package br.com.booksmark.booksmark;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Anotação do Lombok: Cria getters, setters, toString, equals, hashCode
@AllArgsConstructor // Anotação do Lombok: Cria um construtor com todos os argumentos
@NoArgsConstructor // Anotação do Lombok: Cria um construtor vazio
public class Bookmark {

    private Long id;
    private String url;
    private String description;
    private String title;
}
