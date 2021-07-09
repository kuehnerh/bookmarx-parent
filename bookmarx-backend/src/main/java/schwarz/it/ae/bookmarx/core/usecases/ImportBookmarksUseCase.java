package schwarz.it.ae.bookmarx.core.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import schwarz.it.ae.bookmarx.core.domain.*;

public class ImportBookmarksUseCase {

    private final BookmarkProvider bookmarkProvider;

    public ImportBookmarksUseCase(BookmarkProvider bookmarkProvider) {
        this.bookmarkProvider = bookmarkProvider;
    }


    public void importFirefoxExport(String firefoxExportAsJsonString) throws Exception {

        // Load Firefox Export Tree from Export-Json File
        ObjectMapper objectMapper = new ObjectMapper();
        FirefoxExportNode ffExport = objectMapper.readValue(firefoxExportAsJsonString, FirefoxExportNode.class);

        // Iterate over all nodes
        FirefoxExportResult importResult = extractBookmarks(ffExport);

        // Persist Folders
        bookmarkProvider.saveFolders(importResult.getFolderList());
    }

    private FirefoxExportResult extractBookmarks(FirefoxExportNode firefoxExportNode) {
        FirefoxExportResult result = new FirefoxExportResult();
        extractBookmarks(firefoxExportNode, result, null);
        return result;
    }

    private void extractBookmarks(FirefoxExportNode firefoxExportNode, FirefoxExportResult importResult, SingleFolder currentParentFolder) {

        // Validate
        if (!FirefoxTypeCode.isValid(firefoxExportNode.typeCode)) {
            throw new IllegalStateException("Found unknown TypeCode '" + firefoxExportNode.typeCode + "' in node:  " + firefoxExportNode.guid);
        }
        if (!FirefoxType.isValid(firefoxExportNode.type)) {
            throw new IllegalStateException("Found unknown Type '" + firefoxExportNode.type + "' in node:  " + firefoxExportNode.guid);
        }

        // Create URL
        if (FirefoxTypeCode.URL.isEqualTo(firefoxExportNode.typeCode)) {
            Bookmark newBookmark = new Bookmark.Builder()
                    .generateId()
                    .title(firefoxExportNode.title)
                    .url(firefoxExportNode.uri)
                    .tags(firefoxExportNode.tags, ",")
                    .build();
            importResult.addBookmark(newBookmark);
        }

        // Create Folder
        if (FirefoxTypeCode.FOLDER.isEqualTo(firefoxExportNode.typeCode)) {
            if (StringUtils.equalsIgnoreCase("root________", firefoxExportNode.guid)) {
                firefoxExportNode.title = "_root";
            }
            SingleFolder newFolder = new SingleFolder(
                    new EntityId(),
                    firefoxExportNode.title,
                    (currentParentFolder == null) ? null : currentParentFolder.getParentId()
            );
            importResult.addFolder(newFolder);
            currentParentFolder = newFolder;
        }

        // Examine Children
        if (firefoxExportNode.children != null) {
            for (FirefoxExportNode childNode : firefoxExportNode.children) {
                extractBookmarks(childNode, importResult, currentParentFolder);
            }
        }
    }




    private String printTree(FirefoxExportNode firefoxExportNode) {
        StringBuilder sb = new StringBuilder();

        sb.append(printNode(firefoxExportNode));

        if (firefoxExportNode.children != null) {
            for (FirefoxExportNode childNode : firefoxExportNode.children) {
                sb.append(printTree(childNode));
            }
        }

        return sb.toString();
    }

    private String printNode(FirefoxExportNode firefoxExportNode) {
        StringBuilder sb = new StringBuilder();
        sb.append("guid:             " + firefoxExportNode.guid + "\n");
        sb.append("children:         " + firefoxExportNode.children + "\n");
        sb.append("children (count): " + ((firefoxExportNode.children == null) ? 0 : firefoxExportNode.children.length) +  "\n");
        return sb.toString();
    }
}
