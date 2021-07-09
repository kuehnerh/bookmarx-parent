package schwarz.it.ae.bookmarx.dataproviders.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderJpaRepository extends JpaRepository<FolderJpaEntity, String> {
}
