package schwarz.it.ae.bookmarx.core.domain;

import java.util.HashSet;
import java.util.Set;

public class SingleFolder {

    private final EntityId id;
    private final String name;
    private final EntityId parentId;

    private final Set<EntityId> assignedBookmarkIds = new HashSet<>();

    public SingleFolder(EntityId id, String name, EntityId parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public SingleFolder(EntityId id, String name) {
        this(id, name, null);
    }

    public SingleFolder(String name, EntityId parentId) {
        this(new EntityId(), name, parentId);
    }

    public SingleFolder(String name) {
        this(new EntityId(), name, new EntityId(null));
    }


    public EntityId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EntityId getParentId() {
        return parentId;
    }

    public boolean isTopLevel() {
        return parentId == null;
    }

    public Set<EntityId> getAssignedBookmarkIds() {
        return assignedBookmarkIds;
    }

    public SingleFolder assignBookmarkId(EntityId bookmarkId) {
        this.assignedBookmarkIds.add(bookmarkId);
        return this;
    }

    public void clearAssignedBookmardIds() {
        assignedBookmarkIds.clear();
    }





    @Override
    public String toString() {
        return "Folder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
