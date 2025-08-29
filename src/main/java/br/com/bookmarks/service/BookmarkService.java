package br.com.bookmarks.service;

import br.com.bookmarks.dto.BookmarkCreateDTO;
import br.com.bookmarks.dto.BookmarkResponseDTO;
import br.com.bookmarks.dto.BookmarkUpdateDTO;
import br.com.bookmarks.exception.ResourceNotFoundException;
import br.com.bookmarks.model.entity.Bookmark;
import br.com.bookmarks.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public BookmarkResponseDTO criarBookmark(BookmarkCreateDTO dto) {

        Bookmark newEntity = new Bookmark();
        newEntity.setUrl(dto.url()); // Usamos os getters do record
        newEntity.setTitle(dto.title());
        newEntity.setDescription(dto.description());

        Bookmark SavedEntity = bookmarkRepository.save(newEntity);

        return toResponseDTO(SavedEntity);
    }

    public List<BookmarkResponseDTO> listBookmarks() {

        List<Bookmark> allTheEntities = bookmarkRepository.findAll();

        return allTheEntities.stream()      // <-- Converte a lista para um fluxo de dados
                .map(this::toResponseDTO)   // <-- Aplica a conversão a cada item do fluxo
                .toList();                  // <-- Coleta os itens convertidos em uma nova lista
    }

    public BookmarkResponseDTO findBookmarkById(Long id) {
        return bookmarkRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark não encontrado com o id: " + id));
    }

    public BookmarkResponseDTO findBookmarkByTitle(String title) {
        return bookmarkRepository.findByTitleContainingIgnoreCase(title)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark com o título: '" + title+ "' não encontrado."));
    }

    public BookmarkResponseDTO editBookmark(Long id, BookmarkUpdateDTO dto) {

        Bookmark bookmarkExistente = bookmarkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark não encontrado com o id: " + id));

        bookmarkExistente.setUrl(dto.url());
        bookmarkExistente.setTitle(dto.title());
        bookmarkExistente.setDescription(dto.description());

        Bookmark atualizedEntity = bookmarkRepository.save(bookmarkExistente);

        return toResponseDTO(atualizedEntity);
    }

    public void deleteBookmark(Long id) {
        bookmarkRepository.deleteById(id);
    }

    private BookmarkResponseDTO toResponseDTO(Bookmark bookmark){
        return new BookmarkResponseDTO(
                bookmark.getId(),
                bookmark.getUrl(),
                bookmark.getTitle(),
                bookmark.getDescription()
        );
    }
}