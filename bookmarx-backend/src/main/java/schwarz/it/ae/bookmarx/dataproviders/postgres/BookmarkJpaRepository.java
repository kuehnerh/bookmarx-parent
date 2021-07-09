package schwarz.it.ae.bookmarx.dataproviders.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkJpaRepository extends JpaRepository<BookmarkJpaEntity, String> {

}
