package schwarz.it.ae.bookmarx.entries.api;

import schwarz.it.ae.bookmarx.core.domain.SingleFolder;

public class JsonSingleFolderResponse {

    public JsonSingleFolder folder;


    public static JsonSingleFolderResponse create(SingleFolder folder) {
        JsonSingleFolderResponse response = new JsonSingleFolderResponse();
        response.folder = JsonSingleFolder.fromDomain(folder);
        return response;
    }
}
