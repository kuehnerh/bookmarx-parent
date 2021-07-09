package schwarz.it.ae.bookmarx.core.usecases;

import schwarz.it.ae.bookmarx.core.domain.Bookmark;
import schwarz.it.ae.bookmarx.core.domain.EntityId;
import schwarz.it.ae.bookmarx.core.domain.FolderTree;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CrudBookmarkUseCase {

  private final BookmarkProvider bookmarkProvider;


  public CrudBookmarkUseCase(BookmarkProvider bookmarkProvider) {
    this.bookmarkProvider = bookmarkProvider;
  }

  public List<Bookmark> getAllBookmarks() {
    return this.bookmarkProvider.findAllBookmarks();
  }

  public Optional<Bookmark> findById(EntityId bookmarkId) {
    return this.bookmarkProvider.findById(bookmarkId);
  }

  public Bookmark saveBookmark(Bookmark newBookmark) {
    if (!newBookmark.getId().exists()) {
      // Create
      newBookmark = new Bookmark.Builder().of(newBookmark)
              .generateId()
              .createdAt(Instant.now())
              .modifiedAt(Instant.now())
              .build();
    } else {
      // Update
      newBookmark = new Bookmark.Builder().of(newBookmark)
              .modifiedAt(Instant.now())
              .build();
    }

    return bookmarkProvider.saveBookmark(newBookmark);
  }

  public void deleteBookmark(EntityId bookmarkId) {
    bookmarkProvider.deleteBookmark(bookmarkId);
  }

  public FolderTree getFolderTree() {
    return bookmarkProvider.getFolderTree();
  }
}
