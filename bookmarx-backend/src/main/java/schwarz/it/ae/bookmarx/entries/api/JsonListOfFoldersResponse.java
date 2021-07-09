package schwarz.it.ae.bookmarx.entries.api;

import schwarz.it.ae.bookmarx.core.domain.SingleFolder;

import java.util.List;
import java.util.stream.Collectors;

public class JsonListOfFoldersResponse {

    public List<JsonSingleFolder> folders;


    public static JsonListOfFoldersResponse create(List<SingleFolder> folders) {
        JsonListOfFoldersResponse response = new JsonListOfFoldersResponse();
        response.folders = folders.stream()
                .map(folder -> JsonSingleFolder.fromDomain(folder))
                .collect(Collectors.toList());
        return response;
    }
}
