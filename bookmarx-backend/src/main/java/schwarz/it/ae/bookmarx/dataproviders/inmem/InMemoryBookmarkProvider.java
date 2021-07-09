package schwarz.it.ae.bookmarx.dataproviders.inmem;

import org.apache.commons.lang3.StringUtils;
import schwarz.it.ae.bookmarx.core.domain.*;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookmarkProvider implements BookmarkProvider {

  private Map<EntityId, Bookmark> bookmarks = new HashMap<>();
  private Map<EntityId, SingleFolder> folderList = new HashMap<>();


  @Override
  public List<Bookmark> findAllBookmarks() {
    return bookmarks
            .values()
            .stream()
            .collect(Collectors.toList());
  }

  @Override
  public Bookmark saveBookmark(Bookmark bookmark) {
    return bookmarks.put(bookmark.getId(), bookmark);
  }

  @Override
  public Optional<Bookmark> findById(EntityId bookmarkId) {
    return Optional.ofNullable(bookmarks.get(bookmarkId));
  }

  @Override
  public void deleteAllBookmarks() {
    bookmarks = new HashMap<>();
  }

  @Override
  public void deleteBookmark(EntityId bookmarkId) {
    bookmarks.remove(bookmarkId);
  }





  @Override
  public FolderTree getFolderTree() {

    List<SingleFolder> bfList = findAllFolders();

    // Create Domain Folders and Build a lookup table for faster access
    HashMap<EntityId, TreeFolder> lookup = new HashMap<>();
    bfList.forEach(fe -> lookup.put(fe.getId(), new TreeFolder(fe.getId(), fe.getName())));

    // Set the Parent / Child relationship
    var ft = new FolderTree();
    for (SingleFolder fe : bfList) {
      TreeFolder currentTreeFolder = lookup.get(fe.getId());

      if (StringUtils.isEmpty(fe.getParentId().asString())) {
        // TopLevel
        // No Parent to set
        ft.addFolder(currentTreeFolder);
      } else {
        // Add as child to the found parent
        TreeFolder parent = lookup.get(fe.getParentId());
        parent.addChild(currentTreeFolder);
      }
    }

    return ft;
  }


  @Override
  public List<SingleFolder> findAllFolders() {
    return folderList
            .values()
            .stream()
            .collect(Collectors.toList());
  }

  @Override
  public SingleFolder findFolderById(EntityId id) {
    return folderList.get(id);
  }

  @Override
  public void saveFolders(List<SingleFolder> folderList) {
    for (SingleFolder f: folderList) {
      saveFolder(f);
    }
  }

  @Override
  public SingleFolder saveFolder(SingleFolder folder) {
    folderList.put(folder.getId(), folder);
    return folder;
  }

  @Override
  public void deleteAllFolders() {
    folderList = new HashMap<>();
  }

  @Override
  public void deleteFolderById(EntityId id) {
    folderList.remove(id);
  }

  @Override
  public void deleteAllAssignments() {
    folderList.values().stream().forEach(f -> f.clearAssignedBookmardIds());
  }
}
