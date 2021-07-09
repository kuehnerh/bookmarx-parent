package schwarz.it.ae.bookmarx.entries.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import schwarz.it.ae.bookmarx.core.domain.Bookmark;
import schwarz.it.ae.bookmarx.core.domain.EntityId;
import schwarz.it.ae.bookmarx.core.usecases.CrudBookmarkUseCase;

import java.util.List;
import java.util.Optional;

@RestController
public class BookmarkRestController {

  private final CrudBookmarkUseCase crudBookmarkUseCase;

  public BookmarkRestController(CrudBookmarkUseCase crudBookmarkUseCase) {
    this.crudBookmarkUseCase = crudBookmarkUseCase;
  }

  @GetMapping("/api/bookmarks")
  public JsonListOfBookmarksResponse getAllBookmarks() {
    List<Bookmark> allBookmarks = crudBookmarkUseCase.getAllBookmarks();
    return JsonListOfBookmarksResponse.create(allBookmarks);
  }

  @GetMapping("/api/bookmarks/{bookmarkId}")
  public ResponseEntity<JsonSingleBookmarkResponse> getBookmarById(@PathVariable("bookmarkId") String pathBookmarkId) {
    EntityId bookmarkId = new EntityId(pathBookmarkId);
    Optional<Bookmark> bookmark = crudBookmarkUseCase.findById(bookmarkId);
    if (bookmark.isPresent()) {
      return new ResponseEntity<>(JsonSingleBookmarkResponse.create(bookmark.get()), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/api/bookmarks")
  public JsonSingleBookmarkResponse createBookmark(@RequestBody JsonBookmark jsonBookmark) {
    Bookmark reqBookmark = jsonBookmark.toBookmark();
    Bookmark resBookmark = crudBookmarkUseCase.saveBookmark(reqBookmark);
    JsonSingleBookmarkResponse response = JsonSingleBookmarkResponse.create(resBookmark);
    return response;
  }

  @PutMapping("/api/bookmarks/{bookmarkId}")
  public JsonSingleBookmarkResponse createBookmark(@PathVariable("bookmarkId") String pathBookmarkId, @RequestBody JsonBookmark jsonBookmark) {
    String bookmarkId = RestControllerUtils.extractId(pathBookmarkId, jsonBookmark.getId());
    Bookmark reqBookmark = jsonBookmark.toBookmark(bookmarkId);
    Bookmark resBookmark = crudBookmarkUseCase.saveBookmark(reqBookmark);
    JsonSingleBookmarkResponse response = JsonSingleBookmarkResponse.create(resBookmark);
    return response;
  }

  @DeleteMapping("/api/bookmarks/{bookmarkId}")
  public ResponseEntity deleteBookmark(@PathVariable("bookmarkId") String pathBookmarkId) {
    EntityId bookmarkId = new EntityId(pathBookmarkId);
    crudBookmarkUseCase.deleteBookmark(bookmarkId);
    return new ResponseEntity<>(HttpStatus.OK);
  }


}
