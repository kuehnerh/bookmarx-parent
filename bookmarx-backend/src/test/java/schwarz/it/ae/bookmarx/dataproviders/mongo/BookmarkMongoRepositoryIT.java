package schwarz.it.ae.bookmarx.dataproviders.mongo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import schwarz.it.ae.bookmarx.MongoTestContainerExtension;
import schwarz.it.ae.bookmarx.dataproviders.mongodb.BookmarkMongoEntity;
import schwarz.it.ae.bookmarx.dataproviders.mongodb.BookmarkMongoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(MongoTestContainerExtension.class)
class BookmarkMongoRepositoryIT {

    @Autowired
    private BookmarkMongoRepository bookmarkMongoRepository;


    @Test
    public void testFindAll() {
        BookmarkMongoEntity e1 = new BookmarkMongoEntity.Builder()
                .id(UUID.randomUUID().toString())
                .title("T1")
                .url("https://www.exaple.com")
                .description("D1")
                .tags(new ArrayList<>())
                .build();
        bookmarkMongoRepository.save(e1);

        List<BookmarkMongoEntity> foundBookmarks = bookmarkMongoRepository.findAll();

        assertEquals(1, foundBookmarks.size());
    }

}
