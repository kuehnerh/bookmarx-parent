package schwarz.it.ae.bookmarx.entries.api;

import schwarz.it.ae.bookmarx.core.domain.EntityId;
import schwarz.it.ae.bookmarx.core.domain.SingleFolder;

import java.util.Set;
import java.util.stream.Collectors;

public class JsonSingleFolder {
    public String id;
    public String name;
    public String parentId;
    public Set<String> assignedBookmarkIds;

    public JsonSingleFolder(String id, String name, String parentId, Set<String> assignedBookmarkIds) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.assignedBookmarkIds = assignedBookmarkIds;
    }


    public SingleFolder toDomain(String id) {
        this.id = id;
        return toDomain();
    }


    public SingleFolder toDomain() {
        return new SingleFolder(
                new EntityId(this.id),
                this.name,
                new EntityId(this.parentId));
    }

    public static JsonSingleFolder fromDomain(SingleFolder folder) {
        return new JsonSingleFolder(
                folder.getId().asString(),
                folder.getName(),
                folder.getParentId().asString(),
                folder.getAssignedBookmarkIds().stream().map(bmId -> bmId.asString()).collect(Collectors.toSet())
        );
    }

}
