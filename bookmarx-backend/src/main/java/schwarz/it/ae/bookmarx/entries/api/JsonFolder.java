package schwarz.it.ae.bookmarx.entries.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonFolder {
    public String id;
    public String name;
    public List<JsonFolder> children = new ArrayList<>();
    public Set<String> assignedBookmarkIds = new HashSet<>();

    public JsonFolder(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(JsonFolder newChild) {
        children.add(newChild);
    }

    public void assignBookmarkId(String bookmarkId) {
        assignedBookmarkIds.add(bookmarkId);
    }

}
