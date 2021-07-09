package schwarz.it.ae.bookmarx.dataproviders.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookmarkMongoRepository extends MongoRepository<BookmarkMongoEntity, String> {
}
