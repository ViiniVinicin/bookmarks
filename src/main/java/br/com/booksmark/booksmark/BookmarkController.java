package br.com.booksmark.booksmark;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping ("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping
    public ResponseEntity<Bookmark> createBookmark(@RequestBody BookmarkDTO bookmarkDTO) {
        Bookmark novoBookmark = bookmarkService.createBookmark(
                bookmarkDTO.getUrl(),
                bookmarkDTO.getTitulo(),
                bookmarkDTO.getDescricao()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(novoBookmark);
    }

    @GetMapping
    public ResponseEntity<List<Bookmark>> listBookmarks() {
        List<Bookmark> bookmarks = bookmarkService.listBookmarks();
        return  ResponseEntity.ok(bookmarks);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> search(@PathVariable Long id) {
        Bookmark bookmark = bookmarkService.findBookmarkById(id);
        if (bookmark == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookmark);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  delete(@PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
        return ResponseEntity.noContent().build();
    }
}
