package schwarz.it.ae.bookmarx.core.domain;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;

class TreeFolderTreeTest {

    @Test
    void testBuildFolderTree() {

        TreeFolder rootTreeFolder1 = new TreeFolder("IT");
        TreeFolder child1_1 = rootTreeFolder1.addChild(new TreeFolder("IT-News"));
        TreeFolder child1_2 = rootTreeFolder1.addChild(new TreeFolder("IT-Agile"));

        TreeFolder rootTreeFolder2 = new TreeFolder("Other");

        FolderTree folderTree = new FolderTree();
        folderTree.addFolder(rootTreeFolder1);
        folderTree.addFolder(rootTreeFolder2);


        List<TreeFolder> topLevelTreeFolders = folderTree.getTopLevelFolders();
        assertThat(topLevelTreeFolders, hasSize(2));
        assertThat(topLevelTreeFolders, containsInAnyOrder(rootTreeFolder1, rootTreeFolder2));
        assertThat(topLevelTreeFolders.stream().map(TreeFolder::getId).collect(Collectors.toList()), containsInAnyOrder(rootTreeFolder1.getId(), rootTreeFolder2.getId()));
        assertThat(topLevelTreeFolders.stream().map(TreeFolder::getName).collect(Collectors.toList()), containsInAnyOrder("IT", "Other"));
        assertThat(topLevelTreeFolders.stream().map(TreeFolder::isTopLevel).collect(Collectors.toList()), contains(true, true));


        for (TreeFolder topLevelTreeFolder : folderTree.getTopLevelFolders()) {
            if (topLevelTreeFolder.hasChildren()) {
                // Child with 2 children
                assertThat(topLevelTreeFolder.getChildren(), hasSize(2));
                assertThat(topLevelTreeFolder.getChildren(), containsInAnyOrder(child1_1, child1_2));
                assertThat(topLevelTreeFolder.getChildren().get(0).getParent(), is(rootTreeFolder1));
                assertThat(topLevelTreeFolder.getChildren().get(1).getParent(), is(rootTreeFolder1));

            } else {
                // Child with 0 children
                assertThat(topLevelTreeFolder.getChildren(), hasSize(0));

            }
        }
    }

}
