package schwarz.it.ae.bookmarx.dataproviders.postgres;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BMX_FOLDER_BOOKMARK_ASSIGNMENT")
public class FolderBookmarkAssignmentJpaEntity {

    @Id
    private String id;

    private String folderId;
    private String bookmarkId;


    public FolderBookmarkAssignmentJpaEntity() {
        // Needed for JPA
    }

    public FolderBookmarkAssignmentJpaEntity(String id, String folderId, String bookmarkId) {
        this.id = id;
        this.folderId = folderId;
        this.bookmarkId = bookmarkId;
    }

    public String getId() {
        return id;
    }

    public String getFolderId() {
        return folderId;
    }

    public String getBookmarkId() {
        return bookmarkId;
    }
}
