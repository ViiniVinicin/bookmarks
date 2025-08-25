        package br.com.booksmark.booksmark;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookmarkRepository {

    private final List<Bookmark> bookmarks = new ArrayList<>();
    private final AtomicLong counterId = new AtomicLong();

    public Bookmark save(Bookmark bookmark) {
        if (bookmark.getId() == null) {
            // Lógica de criação (está perfeita)
            bookmark.setId(counterId.incrementAndGet());
            bookmarks.add(bookmark);
            return bookmark;
        } else {
            // Lógica de atualização (edição)
            Optional<Bookmark> optionalExistente = bookmarks.stream()
                    .filter(b -> b.getId().equals(bookmark.getId()))
                    .findFirst();

            if (optionalExistente.isPresent()) {
                Bookmark existente = optionalExistente.get();
                // Atualiza o objeto que já está na lista
                existente.setTitle(bookmark.getTitle());
                existente.setUrl(bookmark.getUrl());
                existente.setDescription(bookmark.getDescription());
                return existente;
            } else {
                // Opcional: Lançar uma exceção se tentar salvar um bookmark com ID que não existe
                throw new IllegalArgumentException("Nenhum bookmark encontrado com o ID: " + bookmark.getId());
            }
        }
    }

    // Metodo para encontrar bookmark pelo id
    public Bookmark findBookmarkById(Long id) {
        return bookmarks.stream()
                .filter(bookmark -> bookmark.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Metodo para encontrar bookmark pelo titulo
    public Optional<Bookmark> findBookmarkByTitle(String titulo) {
        return bookmarks.stream()
                .filter(bookmark -> bookmark.getTitle().equalsIgnoreCase(titulo))
                .findFirst();
    }

    // Metodo para listar todos bookmarks
    public List<Bookmark> findAll() {
        return new ArrayList<>(bookmarks);
    }

    // Metodo para deletar pod ID
    public void deleteById (Long id){
        bookmarks.removeIf(bookmark -> bookmark.getId().equals(id));
    }


}