package schwarz.it.ae.bookmarx.dataproviders.mongodb;


import org.springframework.data.annotation.Id;

import java.util.Set;

public class BookmarkFolderMongoEntity {

    @Id
    private String id;

    private String parentId;
    private String name;
    private Set<String> assignedBookmarkId;

    public BookmarkFolderMongoEntity(String id, String parentId, String name, Set<String> assignedBookmarkId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.assignedBookmarkId = assignedBookmarkId;
    }


    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public Set<String> getAssignedBookmarkIds() {
        return assignedBookmarkId;
    }
}
