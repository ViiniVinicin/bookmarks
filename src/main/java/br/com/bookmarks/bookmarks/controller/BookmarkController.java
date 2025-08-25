package br.com.bookmarks.bookmarks.controller;

import br.com.bookmarks.bookmarks.dto.BookmarkCreateDTO;
import br.com.bookmarks.bookmarks.dto.BookmarkResponseDTO;
import br.com.bookmarks.bookmarks.dto.BookmarkUpDateDTO;
import br.com.bookmarks.bookmarks.model.entity.Bookmark;
import br.com.bookmarks.bookmarks.dto.BookmarkDTO;
import br.com.bookmarks.bookmarks.service.BookmarkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    // CORREÇÃO 1: O tipo de retorno agora é BookmarkResponseDTO
    public ResponseEntity<BookmarkResponseDTO> criarBookmark(@RequestBody @Valid BookmarkCreateDTO dto) {

        // CORREÇÃO 2: Chame o metodo correto passando o DTO inteiro
        // CORREÇÃO 3: O tipo da variável de retorno agora é BookmarkResponseDTO
        BookmarkResponseDTO bookmarkCriado = bookmarkService.criarBookmark(dto);

        // MELHORIA (BOA PRÁTICA): Construir a URI para o header "Location"
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // Pega a URL atual (/bookmarks)
                .path("/{id}") // Adiciona o path do ID
                .buildAndExpand(bookmarkCriado.id()) // Substitui {id} pelo ID do novo bookmark
                .toUri();

        // Retorna o status 201 Created com a URI no header e o DTO no corpo
        return ResponseEntity.created(location).body(bookmarkCriado);
    }

    // No BookmarkController.java

    @GetMapping
    // CORREÇÃO 1: O método agora retorna uma lista de DTOs de resposta
    public ResponseEntity<List<BookmarkResponseDTO>> listBookmarks() {

        // CORREÇÃO 2: A variável agora tem o tipo correto para receber o retorno do serviço
        List<BookmarkResponseDTO> bookmarksDTOs = bookmarkService.listBookmarks();

        return ResponseEntity.ok(bookmarksDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<BookmarkResponseDTO> findBookmarkByTitle(@RequestParam("title") String title) {

        return bookmarkService.findBookmarkByTitle(title)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> search(@PathVariable Long id) {
        Bookmark bookmark = bookmarkService.findBookmarkById(id);
        if (bookmark == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookmark);
    }

    @PutMapping("/{id}")
    // CORREÇÃO 1: O metodo agora retorna e recebe os DTOs corretos.
    public ResponseEntity<BookmarkResponseDTO> atualizarBookmark(
            @PathVariable Long id,
            // CORREÇÃO 2: Use o DTO de atualização e ative a validação com @Valid
            @RequestBody @Valid BookmarkUpDateDTO.BookmarkUpdateDTO dto) {

        // CORREÇÃO 3: Chame o método correto do serviço que acabamos de refatorar
        BookmarkResponseDTO bookmarkAtualizado = bookmarkService.editBookmark(id, dto);

        // CORREÇÃO 4: Lógica de retorno simplificada. O caso "não encontrado" será
        // tratado pelo GlobalExceptionHandler que vamos criar.
        return ResponseEntity.ok(bookmarkAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  delete(@PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
        return ResponseEntity.noContent().build();
    }
}
