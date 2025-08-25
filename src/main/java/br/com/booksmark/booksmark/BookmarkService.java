package br.com.booksmark.booksmark;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public Bookmark createBookmark(String url, String titulo, String descricao) {
        // Validação de URL
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("A URL não pode ser vazia");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("Digite uma URL válida");
        }

        Bookmark newBookmark = new Bookmark(null, url, titulo, descricao);
        return bookmarkRepository.save(newBookmark);
    }

    public List<Bookmark> listBookmarks() {
        return bookmarkRepository.findAll();
    }

    public Bookmark findBookmarkById(Long id) {
        return bookmarkRepository.findBookmarkById(id);
    }

    public Optional<Bookmark> findBookmarkByTitle(String titulo) {
        return bookmarkRepository.findBookmarkByTitle(titulo);
    }

    public void deleteBookmark(Long id) {
        bookmarkRepository.deleteById(id);
    }
}
