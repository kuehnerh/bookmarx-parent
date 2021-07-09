package schwarz.it.ae.bookmarx.entries.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import schwarz.it.ae.bookmarx.core.domain.EntityId;
import schwarz.it.ae.bookmarx.core.domain.SingleFolder;
import schwarz.it.ae.bookmarx.core.domain.FolderTree;
import schwarz.it.ae.bookmarx.core.usecases.CrudFolderUseCase;

import java.util.List;

@RestController
public class FolderRestController {

    private CrudFolderUseCase crudFolderUseCase;

    public FolderRestController(CrudFolderUseCase crudFolderUseCase) {
        this.crudFolderUseCase = crudFolderUseCase;
    }

    @GetMapping(path = "/api/folder-tree")
    public ResponseEntity<JsonTreeOfFoldersResponse> getFolderTree() {
        FolderTree folderTree = crudFolderUseCase.getFolderTree();
        JsonTreeOfFoldersResponse response = JsonTreeOfFoldersResponse.create(folderTree);

        return new ResponseEntity<JsonTreeOfFoldersResponse>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/api/folder-list")
    public ResponseEntity<JsonListOfFoldersResponse> getFolderList() {
        List<SingleFolder> folderList = crudFolderUseCase.getFolderList();
        JsonListOfFoldersResponse response = JsonListOfFoldersResponse.create(folderList);

        return new ResponseEntity<JsonListOfFoldersResponse>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/api/folders/{id}")
    public ResponseEntity<JsonSingleFolderResponse> putFolder(@PathVariable("id")String id) {
        SingleFolder foundFolder = crudFolderUseCase.findById(new EntityId(id));
        if (foundFolder == null) {
            return new ResponseEntity<JsonSingleFolderResponse>(HttpStatus.NOT_FOUND);
        }

        JsonSingleFolderResponse response = JsonSingleFolderResponse.create(foundFolder);
        return new ResponseEntity<JsonSingleFolderResponse>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/api/folders/{id}")
    public ResponseEntity<JsonSingleFolderResponse> putFolder(@PathVariable("id") String pathId, @RequestBody JsonSingleFolder jsonSingleFolder) {
        String folderId = RestControllerUtils.extractId(pathId, jsonSingleFolder.id);
        SingleFolder folderToUpdate = jsonSingleFolder.toDomain(folderId);
        SingleFolder updatedFolder = crudFolderUseCase.saveFolder(folderToUpdate);
        JsonSingleFolderResponse response = JsonSingleFolderResponse.create(updatedFolder);

        return new ResponseEntity<JsonSingleFolderResponse>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/api/folders")
    public ResponseEntity<JsonSingleFolderResponse> postFolder(@RequestBody JsonSingleFolder jsonSingleFolder) {
        SingleFolder folderToCreate = jsonSingleFolder.toDomain();
        SingleFolder createdFolder = crudFolderUseCase.saveFolder(folderToCreate);
        JsonSingleFolderResponse response = JsonSingleFolderResponse.create(createdFolder);

        return new ResponseEntity<JsonSingleFolderResponse>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/folders/{id}")
    public ResponseEntity deleteBookmark(@PathVariable("id") String pathFolderId) {
        EntityId id = new EntityId(pathFolderId);
        crudFolderUseCase.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
