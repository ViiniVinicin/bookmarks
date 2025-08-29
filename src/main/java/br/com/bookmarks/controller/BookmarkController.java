package br.com.bookmarks.controller;

import br.com.bookmarks.dto.BookmarkCreateDTO;
import br.com.bookmarks.dto.BookmarkResponseDTO;
import br.com.bookmarks.dto.BookmarkUpdateDTO;
import br.com.bookmarks.service.BookmarkService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping
    // O tipo de retorno agora é BookmarkResponseDTO
    public ResponseEntity<BookmarkResponseDTO> createBookmark(@RequestBody @Valid BookmarkCreateDTO dto) {

        // Chame o metodo correto passando o DTO inteiro
        // O tipo da variável de retorno agora é BookmarkResponseDTO
        BookmarkResponseDTO createdBookmark = bookmarkService.criarBookmark(dto);

        // MELHORIA (BOA PRÁTICA): Construir a URI para o header "Location"
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()                   // Pega a URL atual (/bookmarks)
                .path("/{id}")                          // Adiciona o path do ID
                .buildAndExpand(createdBookmark.id())   // Substitui {id} pelo ID do novo bookmark
                .toUri();

        return ResponseEntity.created(location).body(createdBookmark);
    }

    @GetMapping
    public ResponseEntity<List<BookmarkResponseDTO>> listBookmarks() {

        List<BookmarkResponseDTO> bookmarksDTOs = bookmarkService.listBookmarks();

        return ResponseEntity.ok(bookmarksDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<BookmarkResponseDTO> findBookmarkByTitle(@RequestParam("title") String title) {
        // Apenas chama o serviço. Se o serviço lançar a exceção, o @RestControllerAdvice vai pegar.
        return ResponseEntity.ok(bookmarkService.findBookmarkByTitle(title));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookmarkResponseDTO> findBookmarkById(@PathVariable Long id) {
        return ResponseEntity.ok(bookmarkService.findBookmarkById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookmarkResponseDTO> atualizeBookmark(@PathVariable Long id,
                                                                @RequestBody @Valid BookmarkUpdateDTO dto) {

        BookmarkResponseDTO atualizedBookmark = bookmarkService.editBookmark(id, dto);

        return ResponseEntity.ok(atualizedBookmark);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
        return ResponseEntity.noContent().build();
    }
}