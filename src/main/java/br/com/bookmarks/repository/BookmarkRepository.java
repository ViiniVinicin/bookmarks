package br.com.bookmarks.repository;

import br.com.bookmarks.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {


    Optional<Bookmark> findByTitleContainingIgnoreCase(String title);
    public Bookmark findBookmarkById(Long id);

}