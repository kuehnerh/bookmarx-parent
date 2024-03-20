package schwarz.it.ae.bookmarx.dataproviders.postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="BMX_FOLDER")
public class FolderJpaEntity {

    @Id
    private String id;

    private String parentId;
    private String name;

    public FolderJpaEntity() {
        // Needed for JPA
    }

    public FolderJpaEntity(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
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

}
