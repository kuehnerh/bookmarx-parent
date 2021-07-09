package schwarz.it.ae.bookmarx.core.usecases;

import schwarz.it.ae.bookmarx.core.domain.EntityId;
import schwarz.it.ae.bookmarx.core.domain.SingleFolder;
import schwarz.it.ae.bookmarx.core.domain.FolderTree;

import java.util.List;

public class CrudFolderUseCase {

    private final BookmarkProvider bookmarkProvider;

    public CrudFolderUseCase(BookmarkProvider bookmarkProvider) {
        this.bookmarkProvider = bookmarkProvider;
    }

    public FolderTree getFolderTree() {
        return bookmarkProvider.getFolderTree();
    }

    public List<SingleFolder> getFolderList() { return bookmarkProvider.findAllFolders(); }

    public SingleFolder saveFolder(SingleFolder folderToSave) {
        if (!folderToSave.getId().exists()) {
            folderToSave = new SingleFolder(
                    new EntityId(),
                    folderToSave.getName(),
                    folderToSave.getParentId());
        }
        return bookmarkProvider.saveFolder(folderToSave);
    }

    public SingleFolder findById(EntityId id) {
        return bookmarkProvider.findFolderById(id);
    }

    public void deleteById(EntityId id) {
        bookmarkProvider.deleteFolderById(id);
    }
}
