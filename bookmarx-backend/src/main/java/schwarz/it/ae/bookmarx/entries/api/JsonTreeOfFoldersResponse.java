package schwarz.it.ae.bookmarx.entries.api;

import schwarz.it.ae.bookmarx.core.domain.Bookmark;
import schwarz.it.ae.bookmarx.core.domain.FolderTree;

import java.util.List;
import java.util.stream.Collectors;

public class JsonTreeOfFoldersResponse {

    public List<JsonFolder> folders;


    public static JsonTreeOfFoldersResponse create(FolderTree folderTree) {
        JsonTreeOfFoldersResponse response = new JsonTreeOfFoldersResponse();
        response.folders = JsonFolderMapper.toJson(folderTree);
        return response;
    }
}
