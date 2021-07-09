package schwarz.it.ae.bookmarx.dataproviders.postgres;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import schwarz.it.ae.bookmarx.core.domain.*;
import schwarz.it.ae.bookmarx.core.usecases.BookmarkProvider;

import java.util.*;
import java.util.stream.Collectors;

public class PostgresBookmarkProvider implements BookmarkProvider {

    private final BookmarkJpaRepository bookmarkJpaRepository;
    private final FolderJpaRepository folderJpaRepository;
    private final FolderBookmarkAssignmentJpaRepository folderBookmarkAssignmentJpaRepository;


    public PostgresBookmarkProvider(BookmarkJpaRepository bookmarkJpaRepository, FolderJpaRepository folderJpaRepository, FolderBookmarkAssignmentJpaRepository folderBookmarkAssignmentJpaRepository) {
        this.bookmarkJpaRepository = bookmarkJpaRepository;
        this.folderJpaRepository = folderJpaRepository;
        this.folderBookmarkAssignmentJpaRepository = folderBookmarkAssignmentJpaRepository;
    }

    @Override
    public List<Bookmark> findAllBookmarks() {
        List<BookmarkJpaEntity> bookmarkJpaEntities = bookmarkJpaRepository.findAll();
        return bookmarkJpaEntities.stream()
                .map(this::jpaToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Bookmark> findById(EntityId bookmarkId) {
        return bookmarkJpaRepository
                .findById(bookmarkId.asString())
                .map(this::jpaToDomain);
    }

    @Override
    public Bookmark saveBookmark(Bookmark bookmark) {
        BookmarkJpaEntity bookmarkJpaEntityToSave = domainToJpa(bookmark);
        BookmarkJpaEntity bookmarkJpaEntitySaved = bookmarkJpaRepository.save(bookmarkJpaEntityToSave);
        return jpaToDomain(bookmarkJpaEntitySaved);
    }

    @Override
    public void deleteAllBookmarks() {
        bookmarkJpaRepository.deleteAllInBatch();
    }

    @Override
    public void deleteBookmark(EntityId bookmarkId) {
        try {
            bookmarkJpaRepository.deleteById(bookmarkId.asString());
        } catch (EmptyResultDataAccessException ex) {
            // if the id we want to delete is no there we have what we want
            // no Exception should be thrown
        }
    }

    private Bookmark jpaToDomain(BookmarkJpaEntity bookmarkJpaEntity) {
        return new Bookmark.Builder()
                .id(bookmarkJpaEntity.getId())
                .title(bookmarkJpaEntity.getTitle())
                .url(bookmarkJpaEntity.getUrl())
                .description(bookmarkJpaEntity.getDescription())
                .tags(bookmarkJpaEntity.getTags())
                .createdAt(bookmarkJpaEntity.getCreatedAt())
                .modifiedAt(bookmarkJpaEntity.getModifiedAt())
                .build();
    }

    private BookmarkJpaEntity domainToJpa(Bookmark bookmark) {
        return new BookmarkJpaEntity.Builder()
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
        List<FolderJpaEntity> jpaFolderList = folderJpaRepository.findAll();

        // Create Domain Folders and Build a lookup table for faster access
        HashMap<String, TreeFolder> lookup = new HashMap<>();
        jpaFolderList.forEach(fe -> {
            TreeFolder folder = new TreeFolder(new EntityId(fe.getId()), fe.getName());
            List<FolderBookmarkAssignmentJpaEntity> assignedBookmarks = folderBookmarkAssignmentJpaRepository.findByFolderId(fe.getId());
            assignedBookmarks.forEach(a -> folder.assignBookmarkId(new EntityId(a.getBookmarkId())));
            lookup.put(fe.getId(), folder);
        });

        // Set the Parent / Child relationship
        FolderTree ft = new FolderTree();
        for (FolderJpaEntity fe : jpaFolderList) {
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
    public List<SingleFolder> findAllFolders() {
        List<FolderJpaEntity> folderJpaEntityList = folderJpaRepository.findAll();
        return folderJpaEntityList.stream()
                .map(f -> findFolderById(new EntityId(f.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public SingleFolder findFolderById(EntityId id) {
        Optional<FolderJpaEntity> foundFolder = folderJpaRepository.findById(id.asString());

        if (foundFolder.isPresent()) {
            List<FolderBookmarkAssignmentJpaEntity> assignments = folderBookmarkAssignmentJpaRepository.findByFolderId(foundFolder.get().getId());
            return mapJpaToDomain(foundFolder.get(), assignments);
        } else {
            return null;
        }
    }

    @Override
    public void saveFolders(List<SingleFolder> folderList) {
        for (SingleFolder f : folderList) {
            saveFolder(f);
        }
    }

    @Override
    public SingleFolder saveFolder(SingleFolder folder) {
        FolderJpaEntity entityToSave = mapDomainToJpaFolder(folder);
        Set<FolderBookmarkAssignmentJpaEntity> assignmentsToSave = mapDomainToJpaAssignments(folder);

        FolderJpaEntity savedFolder = folderJpaRepository.save(entityToSave);
        folderBookmarkAssignmentJpaRepository.deleteByFolderId(folder.getId().asString());
        List<FolderBookmarkAssignmentJpaEntity> savedAssignments = folderBookmarkAssignmentJpaRepository.saveAll(assignmentsToSave);

        return mapJpaToDomain(savedFolder, savedAssignments);
    }

    @Override
    public void deleteAllFolders() {
        folderBookmarkAssignmentJpaRepository.deleteAllInBatch();
        folderJpaRepository.deleteAllInBatch();
    }

    @Override
    public void deleteFolderById(EntityId id) {
        try {
            folderJpaRepository.deleteById(id.asString());
        } catch (EmptyResultDataAccessException es) {
            // if the id we want to delete is no there we have what we want
            // no Exception should be thrown
        }
    }

    private FolderJpaEntity mapDomainToJpaFolder(SingleFolder folder) {
        return new FolderJpaEntity(
                folder.getId().asString(),
                (folder.getParentId() == null || !folder.getParentId().exists()) ? null : folder.getParentId().asString(),
                folder.getName()
        );
    }


    private Set<FolderBookmarkAssignmentJpaEntity> mapDomainToJpaAssignments(SingleFolder folder) {
        Set<FolderBookmarkAssignmentJpaEntity> result = new HashSet<>();
        result = folder.getAssignedBookmarkIds().stream()
                .map(bookmarkId -> new FolderBookmarkAssignmentJpaEntity(new EntityId().asString(), folder.getId().asString(), bookmarkId.asString()))
                .collect(Collectors.toSet());
        return result;
    }


    private FolderJpaEntity mapDomainToJpa(TreeFolder treeFolder) {
        return mapDomainToJpaFolder(new SingleFolder(treeFolder.getId(), treeFolder.getName(), treeFolder.getParentId()));
    }


    private SingleFolder mapJpaToDomain(FolderJpaEntity folderJpaEntity, List<FolderBookmarkAssignmentJpaEntity> assignmentJpaEntities) {
        SingleFolder result = new SingleFolder(
                new EntityId(folderJpaEntity.getId()),
                folderJpaEntity.getName(),
                new EntityId(folderJpaEntity.getParentId())
        );

        for (FolderBookmarkAssignmentJpaEntity a : assignmentJpaEntities) {
            result.assignBookmarkId(new EntityId(a.getBookmarkId()));
        }

        return result;
    }

    @Override
    public void deleteAllAssignments() {
        folderBookmarkAssignmentJpaRepository.deleteAllInBatch();
    }
}
