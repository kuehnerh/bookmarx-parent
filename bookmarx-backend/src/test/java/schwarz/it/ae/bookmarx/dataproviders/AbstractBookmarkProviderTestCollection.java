package schwarz.it.ae.bookmarx.dataproviders;

import org.junit.jupiter.api.Test;
import schwarz.it.ae.bookmarx.core.domain.*;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractBookmarkProviderTestCollection {



    private static final String NON_EXISTENT_ID = "a73f51ab-1ca4-4926-a1a5-fae6bc31dcd6";

    protected abstract BookmarkProvider getBookmarkProvider();
    

    @Test
    public void testInsertOneAndFindAll() {
        // Arrange
        Bookmark b1 = this.createBm1();
        getBookmarkProvider().saveBookmark(b1);

        // Act
        List<Bookmark> foundBookmarks = getBookmarkProvider().findAllBookmarks();

        // Assert
        assertEquals(1, foundBookmarks.size());
        Bookmark foundBookmark = foundBookmarks.get(0);
        assertNotNull(foundBookmark.getId().asString());
        assertTrue(foundBookmark.getId().exists());
        assertBookmarkEquals(b1, foundBookmark);
    }

    @Test
    public void testInsertThreeAndFindAll() {
        // Arrange
        Bookmark b1 = this.createBm1();
        Bookmark b2 = this.createBm2();
        Bookmark b3 = this.createBm3();
        getBookmarkProvider().saveBookmark(b1);
        getBookmarkProvider().saveBookmark(b2);
        getBookmarkProvider().saveBookmark(b3);

        // Act
        List<Bookmark> foundBookmarks = getBookmarkProvider().findAllBookmarks();

        // Assert
        assertEquals(3, foundBookmarks.size());
        Bookmark foundBookmark = foundBookmarks.get(0);
        assertNotNull(foundBookmark.getId().asString());
        assertTrue(foundBookmark.getId().exists());
    }


    @Test
    public void testInsertThreeAndFindById() {
        // Arrange
        Bookmark b1 = this.createBm1();
        Bookmark b2 = this.createBm2();
        Bookmark b3 = this.createBm3();
        getBookmarkProvider().saveBookmark(b1);
        getBookmarkProvider().saveBookmark(b2);
        getBookmarkProvider().saveBookmark(b3);

        // Act
        List<Bookmark> foundBookmarks = getBookmarkProvider().findAllBookmarks();

        // Assert
        assertEquals(3, foundBookmarks.size());
        Optional<Bookmark> foundBm1 = getBookmarkProvider().findById(b1.getId());
        assertBookmarkEquals(b1, foundBm1.orElseThrow());
        Optional<Bookmark> foundBm2 = getBookmarkProvider().findById(b2.getId());
        assertBookmarkEquals(b2, foundBm2.orElseThrow());
        Optional<Bookmark> foundBm3 = getBookmarkProvider().findById(b3.getId());
        assertBookmarkEquals(b3, foundBm3.orElseThrow());
        Optional<Bookmark> foundBm4 = getBookmarkProvider().findById(new EntityId(NON_EXISTENT_ID));
        assertTrue(foundBm4.isEmpty());
    }


    @Test
    public void testUpdate() {
        // Arrange
        Bookmark b1 = this.createBm1();
        getBookmarkProvider().saveBookmark(b1);


        // Act
        Bookmark b1Mod = new Bookmark.Builder()
                .of(b1)
                .title("T2")
                .build();
        getBookmarkProvider().saveBookmark(b1Mod);
        List<Bookmark> foundBookmarks = getBookmarkProvider().findAllBookmarks();

        // Assert
        assertEquals(1, foundBookmarks.size());

        Bookmark foundBookmark = foundBookmarks.get(0);
        assertNotNull(foundBookmark.getId().asString());
        assertTrue(foundBookmark.getId().exists());
        assertBookmarkEquals(b1Mod, foundBookmark);
    }


    @Test
    public void testDeleteExisting() {
        // Arrange
        Bookmark b1 = this.createBm1();
        getBookmarkProvider().saveBookmark(b1);
        assertEquals(1, getBookmarkProvider().findAllBookmarks().size());

        // Act
        getBookmarkProvider().deleteBookmark(b1.getId());

        // Assert
        assertEquals(0, getBookmarkProvider().findAllBookmarks().size());
    }


    @Test
    public void testDeleteNonExisting() {
        // Arrange
        Bookmark b1 = this.createBm1();
        getBookmarkProvider().saveBookmark(b1);
        assertEquals(1, getBookmarkProvider().findAllBookmarks().size());

        // Act
        getBookmarkProvider().deleteBookmark(new EntityId());

        // Assert
        assertEquals(1, getBookmarkProvider().findAllBookmarks().size());
    }

    private Bookmark createBm1() {
        return new Bookmark.Builder()
                .generateId()
                .title("BM1")
                .url("https://www.bm1.com")
                .description("D1")
                .tags("tag1")
                .createdAt(Instant.now().minus(12, ChronoUnit.MINUTES))
                .modifiedAt(Instant.now())
                .build();
    }


    private Bookmark createBm2() {
        return new Bookmark.Builder()
                .generateId()
                .title("BM2")
                .url("https://www.bm2.de")
                .description("D2")
                .tags("tag1", "tag2")
                .build();
    }


    private Bookmark createBm3() {
        return new Bookmark.Builder()
                .generateId()
                .title("BM3")
                .url("https://www.bm3.de")
                .description("D3")
                .build();
    }


    private void assertBookmarkEquals(Bookmark expected, Bookmark actual) {
        assertEquals(expected.getId().asString(), actual.getId().asString());
        assertEquals(expected.getTitle().asString(), actual.getTitle().asString());
        assertEquals(expected.getUrl().asString(), actual.getUrl().asString());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getTags().size(), actual.getTags().size());
        assertAlmostEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertAlmostEquals(expected.getModifiedAt(), actual.getModifiedAt());
    }

    private void assertAlmostEquals(Instant expected, Instant actual) {
        if ( expected != null && actual != null) {
            assertEquals(expected.toEpochMilli(), actual.toEpochMilli());
        } else {
            assertEquals(expected, actual);
        }
    }


    @Test
    public void testInsertOneFolder() {
        // Arrange
        SingleFolder f1 = new SingleFolder("F1");
        getBookmarkProvider().saveFolder(f1);

        // Act
        List<SingleFolder> foundFolders = getBookmarkProvider().findAllFolders();

        // Assert
        assertEquals(1, foundFolders.size());
        SingleFolder foundFolder = foundFolders.get(0);
        assertNotNull(foundFolder.getId().asString());
        assertTrue(foundFolder.getId().exists());
        assertFolderEquals(f1, foundFolder);
    }


    @Test
    public void testInsertThreeFoldersOneByOne() {
        // Arrange
        SingleFolder f1 = new SingleFolder("F1");
        getBookmarkProvider().saveFolder(f1);
        SingleFolder f2 = new SingleFolder("F2");
        getBookmarkProvider().saveFolder(f2);
        SingleFolder f3 = new SingleFolder("F3");
        getBookmarkProvider().saveFolder(f3);

        // Act
        List<SingleFolder> foundFolders = getBookmarkProvider().findAllFolders();

        // Assert
        assertEquals(3, foundFolders.size());
        SingleFolder foundFolder = foundFolders.get(0);
        assertNotNull(foundFolder.getId().asString());
        assertTrue(foundFolder.getId().exists());
    }


    @Test
    public void testInsertThreeFoldersAsList() {
        // Arrange
        SingleFolder f1 = new SingleFolder("F1");
        SingleFolder f2 = new SingleFolder("F2");
        SingleFolder f3 = new SingleFolder("F3");

        getBookmarkProvider().saveFolders(List.of(f1, f2, f3));

        // Act
        List<SingleFolder> foundFolders = getBookmarkProvider().findAllFolders();

        // Assert
        assertEquals(3, foundFolders.size());
        SingleFolder foundFolder = foundFolders.get(0);
        assertNotNull(foundFolder.getId().asString());
        assertTrue(foundFolder.getId().exists());
    }

    @Test
    public void testFindFolderById() {
        // Arrange
        SingleFolder f1 = new SingleFolder("F1");
        SingleFolder f1_1 = new SingleFolder("F1_1", f1.getId());
        SingleFolder f2 = new SingleFolder("F2");
        SingleFolder f3 = new SingleFolder("F3");

        getBookmarkProvider().saveFolders(List.of(f1, f2, f3, f1_1));

        // Act
        SingleFolder foundFolder1 = getBookmarkProvider().findFolderById(f1.getId());
        SingleFolder foundFolder1_1 = getBookmarkProvider().findFolderById(f1_1.getId());
        SingleFolder foundFolder2 = getBookmarkProvider().findFolderById(f2.getId());
        SingleFolder foundFolder3 = getBookmarkProvider().findFolderById(f3.getId());

        // Assert
        assertFolderEquals(f1, foundFolder1);
        assertFolderEquals(f1_1, foundFolder1_1);
        assertFolderEquals(f2, foundFolder2);
        assertFolderEquals(f3, foundFolder3);
    }

    @Test
    public void testDeleteFolderByIdExisting() {
        // Arrange
        SingleFolder f1 = new SingleFolder("F1");
        SingleFolder f2 = new SingleFolder("F2");
        SingleFolder f3 = new SingleFolder("F3");
        getBookmarkProvider().saveFolders(List.of(f1, f2, f3));
        assertEquals(3, getBookmarkProvider().findAllFolders().size());

        // Act
        getBookmarkProvider().deleteFolderById(f1.getId());

        // Assert
        assertEquals(2, getBookmarkProvider().findAllFolders().size());
        assertNull(getBookmarkProvider().findFolderById(f1.getId()));
        assertNotNull(getBookmarkProvider().findFolderById(f3.getId()));
        assertNotNull(getBookmarkProvider().findFolderById(f3.getId()));
    }


    @Test
    public void testDeleteFolderByIdNonExisting() {
        // Arrange
        SingleFolder f1 = new SingleFolder("F1");
        SingleFolder f2 = new SingleFolder("F2");
        SingleFolder f3 = new SingleFolder("F3");
        getBookmarkProvider().saveFolders(List.of(f1, f2, f3));
        assertEquals(3, getBookmarkProvider().findAllFolders().size());

        // Act
        getBookmarkProvider().deleteFolderById(new EntityId());

        // Assert
        assertEquals(3, getBookmarkProvider().findAllFolders().size());
        assertNotNull(getBookmarkProvider().findFolderById(f1.getId()));
        assertNotNull(getBookmarkProvider().findFolderById(f3.getId()));
        assertNotNull(getBookmarkProvider().findFolderById(f3.getId()));
    }

    @Test
    public void testDeleteAllFolders() {
        // Arrange
        SingleFolder f1 = new SingleFolder("F1");
        SingleFolder f2 = new SingleFolder("F2");
        SingleFolder f3 = new SingleFolder("F3");
        getBookmarkProvider().saveFolders(List.of(f1, f2, f3));
        assertEquals(3, getBookmarkProvider().findAllFolders().size());

        // Act
        getBookmarkProvider().deleteAllFolders();

        // Assert
        assertEquals(0, getBookmarkProvider().findAllFolders().size());
    }




    @Test
    public void testGetFolderTree() {
        // Arrange
        SingleFolder f1 = new SingleFolder("F1");
        SingleFolder f1_1 = new SingleFolder("F1_1", f1.getId());
        SingleFolder f1_1_1 = new SingleFolder("F1_1_1", f1_1.getId());
        SingleFolder f1_2 = new SingleFolder("F1_2", f1.getId());
        SingleFolder f2 = new SingleFolder("F2");
        SingleFolder f3 = new SingleFolder("F3");
        getBookmarkProvider().saveFolders(List.of(f1, f2, f3, f1_1, f1_2, f1_1_1));
        assertEquals(6, getBookmarkProvider().findAllFolders().size());

        // Act
        FolderTree ft = getBookmarkProvider().getFolderTree();

        // Assert
        List<TreeFolder> topLevelTreeFolders = ft.getTopLevelFolders();
        assertEquals(3, topLevelTreeFolders.size());
    }

    @Test
    public void testFolderBookmarkAssignment() {
        // Arrange
        Bookmark b1 = this.createBm1();
        getBookmarkProvider().saveBookmark(b1);

        SingleFolder f1 = new SingleFolder("F1").assignBookmarkId(b1.getId());
        getBookmarkProvider().saveFolder(f1);

        SingleFolder f2 = new SingleFolder("F2");
        getBookmarkProvider().saveFolder(f2);


        // Act
        SingleFolder found_f1 = getBookmarkProvider().findFolderById(f1.getId());
        SingleFolder found_f2 = getBookmarkProvider().findFolderById(f2.getId());
        List<SingleFolder> allFolders = getBookmarkProvider().findAllFolders();

        // Assert
        assertEquals(1, found_f1.getAssignedBookmarkIds().size());
        assertTrue(found_f1.getAssignedBookmarkIds().contains(b1.getId()));
        assertEquals(0, found_f2.getAssignedBookmarkIds().size());

        assertEquals(2, allFolders.size());
        assertTrue(
                allFolders.stream()
                        .filter(f -> f.getId().equals(f1.getId()))
                        .findFirst()
                        .get().getAssignedBookmarkIds()
                        .contains(b1.getId()));
    }



    @Test
    public void testDeleteAllAssignments() {
        // Arrange
        Bookmark b1 = this.createBm1();
        getBookmarkProvider().saveBookmark(b1);

        SingleFolder f1 = new SingleFolder("F1").assignBookmarkId(b1.getId());
        getBookmarkProvider().saveFolder(f1);

        SingleFolder f2 = new SingleFolder("F2");
        getBookmarkProvider().saveFolder(f2);

        assertEquals(1, getBookmarkProvider().findFolderById(f1.getId()).getAssignedBookmarkIds().size());
        assertEquals(0, getBookmarkProvider().findFolderById(f2.getId()).getAssignedBookmarkIds().size());


        // Act
        getBookmarkProvider().deleteAllAssignments();

        // Assert
        assertEquals(2, getBookmarkProvider().findAllFolders().size());
        assertEquals(0, getBookmarkProvider().findFolderById(f1.getId()).getAssignedBookmarkIds().size());
        assertEquals(0, getBookmarkProvider().findFolderById(f2.getId()).getAssignedBookmarkIds().size());

    }

    @Test
    public void testAllFolders_WithBookmarkAssignment() {
        // Arrange
        Bookmark b1 = this.createBm1();
        getBookmarkProvider().saveBookmark(b1);

        SingleFolder f1 = new SingleFolder("F1").assignBookmarkId(b1.getId());
        getBookmarkProvider().saveFolder(f1);

        SingleFolder f2 = new SingleFolder("F2");
        getBookmarkProvider().saveFolder(f2);


        // Act
        getBookmarkProvider().deleteAllFolders();

        // Assert
        assertEquals(0, getBookmarkProvider().findAllFolders().size());
    }



    private void assertFolderEquals(SingleFolder expected, SingleFolder actual) {
        assertEquals(expected.getId().asString(), actual.getId().asString());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParentId().asString(), actual.getParentId().asString());
    }
}
