import {Injectable} from '@angular/core';
import {Bookmark} from "../../core/domain/bookmark.model";
import {BookmarxBackendService} from "../../core/usecases/bookmarx-backend-service"
import {Guid} from "../../core/domain/guid.model";
import {EMPTY, Observable, of} from "rxjs";
import {delay} from "rxjs/operators";
import {FolderItemNode} from "../../core/domain/folderItemNode.model";

@Injectable({
  providedIn: 'root'
})
export class BookmarxBackendServiceInMemory implements BookmarxBackendService {

  bookmarks: Bookmark[] = [
    new Bookmark(Guid.newGuid(),"Heise", "https://www.heise.de", "Heise Online", "https://www.heise.de/favicon.ico", ['news','it'], new Date(), new Date()),
    new Bookmark(Guid.newGuid(),"Tagesschau", "https://www.tagesschau.de", "bla bla", "https://www.tagesschau.de/favicon.ico", ['news'], new Date(), new Date()),
    new Bookmark(Guid.newGuid(),"Schwarz IT", "https://it.schwarz", "IT", "https://it.schwarz/favicon.ico", ['it', 'jobs'], new Date(), new Date())
  ];

  folder1_1_1: FolderItemNode = new FolderItemNode(Guid.newGuid(), 'SubFolder1.1.1', "", [], [], false);
  folder1_1: FolderItemNode = new FolderItemNode(Guid.newGuid(), 'SubFolder1.1', "", [this.folder1_1_1], [], false);
  folder1_2: FolderItemNode = new FolderItemNode(Guid.newGuid(), 'SubFolder1.2', "", [], [], false);
  folder1: FolderItemNode = new FolderItemNode(Guid.newGuid(), 'Folder1', "", [this.folder1_1, this.folder1_2], [], false);
  folder2: FolderItemNode = new FolderItemNode(Guid.newGuid(), 'Folder2', "", [], [], false);
  folder3: FolderItemNode = new FolderItemNode(Guid.newGuid(), 'Folder3', "", [], [], true);
  folders: FolderItemNode[] = [this.folder1, this.folder2, this.folder3];


  findBookmarksAll(): Observable<Bookmark[]> {
    return of(this.bookmarks);
  }


  findBookmarkById(id: string): Observable<Bookmark> {
    let foundBookmark = this.getFromArray(id);
    if (foundBookmark) {
      return of(foundBookmark).pipe(delay(3000));
    }
    return of(Bookmark.NULL);
  }

  findBookmarksBySearchTerm(searchTerm:string): Observable<Bookmark[]> {
    return of(this.bookmarks.filter(
      v => -1 < v.title.toLowerCase().indexOf(searchTerm.toLowerCase())));
  }


  saveBookmark(newBookmark: Bookmark): Observable<Bookmark> {
    if (newBookmark.id) {
      // Update
      let itemToUpdate = this.getFromArray(newBookmark.id);
      if (itemToUpdate) {
        let index = this.bookmarks.indexOf(itemToUpdate);
        this.bookmarks[index] = newBookmark;
      }
    } else {
      // Add
      newBookmark.id = Guid.newGuid();
      this.bookmarks.push(newBookmark);
    }

    // Simulation of erroneous Operation
    //const throwingObservable = throwError("SimulatedError while saving bookmark in InMemoryService.");
    //return timer(1000).pipe(mergeMap(e => throwingObservable));

    // Simulation of successful Operation
    return of(newBookmark).pipe(delay(1000));
  }


  deleteBookmarkById(bookmarkId: string): Observable<void>{
    let itemToDelete = this.getFromArray(bookmarkId);
    if (itemToDelete) {
      let index = this.bookmarks.indexOf(itemToDelete);
      if (-1 < index) {
        this.bookmarks.splice(index, 1);
      }
    }
    return EMPTY;
  }


  private getFromArray(id: string): Bookmark | undefined {
    return this.bookmarks.find( bm => bm.id === id);
  }



  findFoldersAll(): Observable<FolderItemNode[]> {
    return of(this.folders);
  }

  saveFolder(folderToSave: FolderItemNode): Observable<FolderItemNode> {
    // TODO Implement
    return of(folderToSave);
  }

  deleteFolderById(id: string): Observable<any> {
    // TODO Implement
    return of(id);
  }








}
