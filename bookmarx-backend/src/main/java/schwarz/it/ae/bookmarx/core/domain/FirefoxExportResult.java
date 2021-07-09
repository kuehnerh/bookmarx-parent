package schwarz.it.ae.bookmarx.core.domain;

import java.util.LinkedList;
import java.util.List;

public class FirefoxExportResult {
    private List<Bookmark> bookmarkList = new LinkedList<>();
    private List<SingleFolder> folderList = new LinkedList<>();

    public void addBookmark(Bookmark b) {
        bookmarkList.add(b);
    }

    public void addFolder(SingleFolder f) {
        folderList.add(f);
    }

    public List<Bookmark> getBookmarkList() {
        return bookmarkList;
    }

    public List<SingleFolder> getFolderList() {
        return folderList;
    }
}
