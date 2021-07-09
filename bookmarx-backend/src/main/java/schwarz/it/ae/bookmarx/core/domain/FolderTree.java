package schwarz.it.ae.bookmarx.core.domain;

import java.util.ArrayList;
import java.util.List;

public class FolderTree {

    private List<TreeFolder> topLevelTreeFolders = new ArrayList<>();


    public List<TreeFolder> getTopLevelFolders() {
        return topLevelTreeFolders;
    }

    public void addFolder(TreeFolder newTreeFolder) {
        assert newTreeFolder.isTopLevel();

        topLevelTreeFolders.add(newTreeFolder);
    }

}
