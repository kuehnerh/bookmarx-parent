package schwarz.it.ae.bookmarx.dataproviders.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderBookmarkAssignmentJpaRepository extends JpaRepository<FolderBookmarkAssignmentJpaEntity, String> {

    List<FolderBookmarkAssignmentJpaEntity> findByFolderId(String folderId);
    void deleteByFolderId(String s);
}
