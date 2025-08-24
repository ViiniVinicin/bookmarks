        package br.com.booksmark.booksmark;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookmarkRepository {

    private final List<Bookmark> bookmarks = new ArrayList<>();
    private final AtomicLong counterId = new AtomicLong();

    public Bookmark save(Bookmark bookmark) {
        if (bookmark.getId() == null) {
            // Novo Bookmark
            bookmark.setId(counterId.incrementAndGet());
            bookmarks.add(bookmark);
        } else {
            // Atualização de tarefa existente
            deleteById(bookmark.getId());
            bookmarks.add(bookmark);
        }
        return bookmark;
    }

    public Bookmark findBookmarkById(Long id) {
        return bookmarks.stream()
                .filter(bookmark -> bookmark.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Bookmark findBookmarkByTitle(String name) {
        return bookmarks.stream()
                .filter(bookmark -> bookmark.getTitle().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    // Metodo para listar todos
    public List<Bookmark> findAll() {
        return new ArrayList<>(bookmarks);
    }

    // Metodo para deletar pod ID
    public void deleteById (Long id){
        bookmarks.removeIf(bookmark -> bookmark.getId().equals(id));
    }
}