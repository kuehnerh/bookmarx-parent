package schwarz.it.ae.bookmarx.dataproviders.postgres;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import schwarz.it.ae.bookmarx.MongoTestContainerExtension;
import schwarz.it.ae.bookmarx.PostgresSharedTestContainerExtension;
import schwarz.it.ae.bookmarx.PostgresTestContainerExtension;
import schwarz.it.ae.bookmarx.dataproviders.postgres.BookmarkJpaEntity;
import schwarz.it.ae.bookmarx.dataproviders.postgres.BookmarkJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(PostgresSharedTestContainerExtension.class)
class BookmarkJpaRepositoryIT {

    @Autowired
    private BookmarkJpaRepository bookmarkJpaRepository;

    @Test
    public void testFindAll() {
        BookmarkJpaEntity e1 = new BookmarkJpaEntity.Builder()
                .id(UUID.randomUUID().toString())
                .title("T1")
                .url("https://www.exaple.com")
                .description("D1")
                .tags(new ArrayList<>())
                .build();
        bookmarkJpaRepository.save(e1);

        List<BookmarkJpaEntity> foundBookmarks = bookmarkJpaRepository.findAll();

        assertEquals(1, foundBookmarks.size());
    }

}
