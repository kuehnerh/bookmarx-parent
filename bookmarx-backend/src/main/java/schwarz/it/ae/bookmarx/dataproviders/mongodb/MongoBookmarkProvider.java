package schwarz.it.ae.bookmarx.dataproviders.mongodb;

import org.apache.commons.lang3.StringUtils;
import schwarz.it.ae.bookmarx.core.domain.*;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;

import java.util.*;
import java.util.stream.Collectors;

public class MongoBookmarkProvider implements BookmarkProvider {

    private final BookmarkMongoRepository bookmarkMongoRepository;
    private final BookmarkFolderMongoRepository bookmarkFolderMongoRepository;


    public MongoBookmarkProvider(BookmarkMongoRepository bookmarkMongoRepository, BookmarkFolderMongoRepository bookmarkFolderMongoRepository) {
        this.bookmarkMongoRepository = bookmarkMongoRepository;
        this.bookmarkFolderMongoRepository = bookmarkFolderMongoRepository;
    }

    @Override
    public List<Bookmark> findAllBookmarks() {
        List<schwarz.it.ae.bookmarx.dataproviders.mongodb.BookmarkMongoEntity> bookmarkJpaEntities = bookmarkMongoRepository.findAll();
        return bookmarkJpaEntities.stream()
                .map(this::mongoToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Bookmark> findById(EntityId bookmarkId) {
        return bookmarkMongoRepository
                .findById(bookmarkId.asString())
                .map(this::mongoToDomain);
    }

    @Override
    public Bookmark saveBookmark(Bookmark bookmark) {
        BookmarkMongoEntity bookmarkMongoEntityToSave = domainToMongo(bookmark);
        BookmarkMongoEntity bookmarkMongoEntitySaved = bookmarkMongoRepository.save(bookmarkMongoEntityToSave);
        return this.mongoToDomain(bookmarkMongoEntitySaved);
    }

    @Override
    public void deleteAllBookmarks() {
        bookmarkMongoRepository.deleteAll();
    }

    @Override
    public void deleteBookmark(EntityId bookmarkId) {
        bookmarkMongoRepository.deleteById(bookmarkId.asString());
    }

    private Bookmark mongoToDomain(BookmarkMongoEntity bookmarkMongoEntity) {
        return new Bookmark.Builder()
                .id(bookmarkMongoEntity.getId())
                .title(bookmarkMongoEntity.getTitle())
                .url(bookmarkMongoEntity.getUrl())
                .description(bookmarkMongoEntity.getDescription())
                .tags(bookmarkMongoEntity.getTags())
                .createdAt(bookmarkMongoEntity.getCreatedAt())
                .modifiedAt(bookmarkMongoEntity.getModifiedAt())
                .build();
    }

    private BookmarkMongoEntity domainToMongo(Bookmark bookmark) {
        return new BookmarkMongoEntity.Builder()
                .id(bookmark.getId().asString())
                .title(bookmark.getTitle().asString())
                .url(bookmark.getUrl().asString())
                .description(bookmark.getDescription())
                .tags(bookmark.getTags().stream().map(BookmarkTag::asString).collect(Collectors.toList()))
                .createdAt(bookmark.getCreatedAt())
                .modifiedAt(bookmark.getModifiedAt())
                .build();
    }


    @Override
    public FolderTree getFolderTree() {

        // Get all Entities from DB
        List<BookmarkFolderMongoEntity> bfList = bookmarkFolderMongoRepository.findAll();

        // Create Domain Folders and Build a lookup table for faster access
        HashMap<String, TreeFolder> lookup = new HashMap<>();
        bfList.forEach(fe -> lookup.put(fe.getId(), new TreeFolder(new EntityId(fe.getId()), fe.getName())));

        // Set the Parent / Child relationship
        var ft = new FolderTree();
        for (BookmarkFolderMongoEntity fe : bfList) {
            TreeFolder currentTreeFolder = lookup.get(fe.getId());

            if (StringUtils.isEmpty(fe.getParentId())) {
                // TopLevel
                // No Parent to set
                ft.addFolder(currentTreeFolder);
            } else {
                // Add as child to the found parent
                TreeFolder parent = lookup.get(fe.getParentId());
                parent.addChild(currentTreeFolder);
            }
        }

        return ft;
    }

    @Override
    public void saveFolders(List<SingleFolder> folderList) {
        List<BookmarkFolderMongoEntity> mongoEntityList = folderList.stream()
                .map(this::mapDomainToMongo)
                .collect(Collectors.toList());
        bookmarkFolderMongoRepository.saveAll(mongoEntityList);
    }

    @Override
    public SingleFolder saveFolder(SingleFolder folder) {
        BookmarkFolderMongoEntity savedEntity = bookmarkFolderMongoRepository.save(mapDomainToMongo(folder));
        return mapMongoToDomain(savedEntity);
    }

    @Override
    public List<SingleFolder> findAllFolders() {
        return bookmarkFolderMongoRepository.findAll().stream()
                .map(this::mapMongoToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public SingleFolder findFolderById(EntityId id) {
        Optional<BookmarkFolderMongoEntity> foundEntity = bookmarkFolderMongoRepository.findById(id.asString());
        return foundEntity.map(this::mapMongoToDomain).orElse(null);
    }

    @Override
    public void deleteAllFolders() {
        bookmarkFolderMongoRepository.deleteAll();
    }

    @Override
    public void deleteFolderById(EntityId id) {
        bookmarkFolderMongoRepository.deleteById(id.asString());
    }



    private BookmarkFolderMongoEntity mapDomainToMongo(SingleFolder folder) {
        Set<String> assignedBookmarkIds = folder.getAssignedBookmarkIds().stream()
                .map(EntityId::asString)
                .collect(Collectors.toSet());

        return new BookmarkFolderMongoEntity(
                folder.getId().asString(),
                (folder.getParentId() == null || !folder.getParentId().exists()) ? null : folder.getParentId().asString(),
                folder.getName(),
                assignedBookmarkIds
        );
    }


    private SingleFolder mapMongoToDomain(BookmarkFolderMongoEntity entity) {
        SingleFolder singleFolder = new SingleFolder(
                new EntityId(entity.getId()),
                entity.getName(),
                new EntityId(entity.getParentId())
        );

        for (String bookmarkId : entity.getAssignedBookmarkIds()) {
            singleFolder.assignBookmarkId(new EntityId(bookmarkId));
        }

        return singleFolder;
    }

    @Override
    public void deleteAllAssignments() {
        // Remove Assigments from all folders
        List<BookmarkFolderMongoEntity> allFolders = bookmarkFolderMongoRepository.findAll();
        allFolders.stream()
                .map(f -> new BookmarkFolderMongoEntity(
                    f.getId(),
                    f.getParentId(),
                    f.getName(),
                    new HashSet<>()))
                .forEach(bookmarkFolderMongoRepository::save);
    }


}
