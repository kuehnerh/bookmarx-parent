package schwarz.it.ae.bookmarx.core.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeFolder {

    private EntityId id;
    private String name;
    private TreeFolder parent;
    private List<TreeFolder> children = new ArrayList<>();
    private Set<EntityId> assignedBookmarkIds = new HashSet<>();

    public TreeFolder(EntityId id, String name, TreeFolder parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public TreeFolder(EntityId id, String name) {
        this(id, name, null);
    }

    public TreeFolder(String name) {
        this(new EntityId(), name, null);
    }

    public TreeFolder(String name, TreeFolder parent) {
        this(new EntityId(), name, parent);
    }

    public EntityId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EntityId getParentId() {
        if (parent != null) {
            return parent.getId();
        }
        return null;
    }

    public TreeFolder getParent() {
        return parent;
    }

    public List<TreeFolder> getChildren() {
        return children;
    }

    public boolean isTopLevel() {
        return parent == null;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }


    public void setParent(TreeFolder parent) {
        this.parent = parent;

        // TODO: Modify Child List of Parent ???
    }

    public TreeFolder addChild(TreeFolder child) {
        children.add(child);
        child.setParent(this);
        return child;
    }

    public void assignBookmarkId(EntityId bookmarkId) {
        assignedBookmarkIds.add(bookmarkId);
    }

    public Set<EntityId> getAssignedBookmarkIds() {
        return assignedBookmarkIds;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
