package schwarz.it.ae.bookmarx.core.usecases;

import schwarz.it.ae.bookmarx.core.domain.*;

import java.util.List;
import java.util.Optional;

public interface BookmarkProvider {

  // Bookmarks
  List<Bookmark> findAllBookmarks();
  Optional<Bookmark> findById(EntityId bookmarkId);

  Bookmark saveBookmark(Bookmark bookmark);

  void deleteAllBookmarks();
  void deleteBookmark(EntityId bookmarkId);

  // Folders
  FolderTree getFolderTree();

  List<SingleFolder> findAllFolders();
  SingleFolder findFolderById(EntityId id);
  void saveFolders(List<SingleFolder> folderList);
  SingleFolder saveFolder(SingleFolder folder);

  void deleteAllFolders();
  void deleteFolderById(EntityId id);

  // Assignments
  void deleteAllAssignments();
}
