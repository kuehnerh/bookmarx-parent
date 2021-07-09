package schwarz.it.ae.bookmarx.entries.api;

import schwarz.it.ae.bookmarx.core.domain.EntityId;
import schwarz.it.ae.bookmarx.core.domain.FolderTree;
import schwarz.it.ae.bookmarx.core.domain.TreeFolder;

import java.util.ArrayList;
import java.util.List;

public class JsonFolderMapper {
    public static List<JsonFolder> toJson(FolderTree folderTree) {
        if (folderTree.getTopLevelFolders() == null) {
            return null;
        }

        List<JsonFolder> jsonFolders = new ArrayList<>();
        for (TreeFolder f : folderTree.getTopLevelFolders()) {
            jsonFolders.add(toJson(f));
        }
        return jsonFolders;
    }

    public static JsonFolder toJson(TreeFolder treeFolder) {
        JsonFolder jsonFolder = new JsonFolder(
                treeFolder.getId().asString(),
                treeFolder.getName()
        );
        for (TreeFolder childTreeFolder : treeFolder.getChildren()) {
            jsonFolder.addChild(toJson(childTreeFolder));
        }
        for (EntityId bookmarkId : treeFolder.getAssignedBookmarkIds()) {
            jsonFolder.assignBookmarkId(bookmarkId.asString());
        }
        return jsonFolder;
    }

    public static FolderTree toDomain(JsonFolder jsonFolder) {
        return null;
    }
}
