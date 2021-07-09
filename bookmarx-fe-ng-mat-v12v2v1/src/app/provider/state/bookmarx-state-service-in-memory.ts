import {Injectable} from "@angular/core";
import {Bookmark} from "../../core/domain/bookmark.model";
import {BehaviorSubject, Observable} from "rxjs";
import {Alert, AlertType} from "../../core/domain/alert.model";
import {FolderItemNode} from "../../core/domain/folderItemNode.model";
import {Guid} from "../../core/domain/guid.model";
import {BookmarxStateService} from "../../core/usecases/bookmarx-state-service";


@Injectable({
  providedIn: 'root'
})
export class BookmarxStateServiceInMemory implements BookmarxStateService {

  private allBookmarks$ = new BehaviorSubject<Bookmark[]>([]);
  private allFolders$ = new BehaviorSubject<FolderItemNode[]>([]);

  private bookmarkToEdit$ = new BehaviorSubject<Bookmark>(Bookmark.NULL);
  private matchingBookmarks$ = new BehaviorSubject<Bookmark[]>([]); // By Search
  private selectedBookmarks$ = new BehaviorSubject<Bookmark[]>([]); // By Folder Selection


  private working$ = new BehaviorSubject<boolean>(false);
  private alerts$ = new BehaviorSubject<Alert[]>([])


  getAllBookmarks$(): Observable<Bookmark[]> {
    return this.allBookmarks$.asObservable();
  }

  getAllFolderItemNodes$(): Observable<FolderItemNode[]> {
    return this.allFolders$.asObservable();
  }

  getBookmarkToEdit$(): Observable<Bookmark> {
    return this.bookmarkToEdit$.asObservable();
  }

  getMatchingBookmarks$(): Observable<Bookmark[]> {
    return this.matchingBookmarks$.asObservable();
  }


  getSelectedBookmarks$(): Observable<Bookmark[]> {
    return this.selectedBookmarks$.asObservable();
  }

  isWorking$(): Observable<boolean> {
    return this.working$.asObservable();
  }

  getAlerts$(): Observable<Alert[]> {
    return this.alerts$.asObservable();
  }

  setBookmarkToEdit(bookmarkToEdit: Bookmark) {
    this.bookmarkToEdit$.next(bookmarkToEdit);
  }

  setBookmarkToEditBy(id: string): boolean {
    const currentBookmarks = this.allBookmarks$.getValue();
    if (currentBookmarks) {
      const indexToReturn = currentBookmarks.findIndex(bm => bm.id === id);
      this.bookmarkToEdit$.next(currentBookmarks[indexToReturn])
      return true;
    }
    return false;
  }

  setAllBookmarks(bookmarks: Bookmark[]) {
    this.allBookmarks$.next(bookmarks);
  }

  setAllFolders(folderItemNodes: FolderItemNode[]) {
    this.allFolders$.next(folderItemNodes);
  }

  addBookmark(bookmark: Bookmark) {
    const currentBookmarks = this.allBookmarks$.getValue();
    this.allBookmarks$.next([...currentBookmarks, bookmark]);
  }

  updateBookmark(updatedBookmark: Bookmark) {
    const currentBookmarks = this.allBookmarks$.getValue();
    const indexToUpdate = currentBookmarks.findIndex(bm => bm.id === updatedBookmark.id);
    currentBookmarks[indexToUpdate] = updatedBookmark;
    this.allBookmarks$.next([...currentBookmarks]);
  }

  updateBookmarkId(bookmarkToUpdate: Bookmark, updatedBookmark: Bookmark) {
    const currentBookmarks = this.allBookmarks$.getValue();
    const indexToUpdate = currentBookmarks.findIndex(bm => bm === bookmarkToUpdate);
    currentBookmarks[indexToUpdate] = updatedBookmark;
    this.allBookmarks$.next([...currentBookmarks]);
  }

  deleteBookmark(bookmarkToRemove: Bookmark) {
    const currentValue = this.allBookmarks$.getValue();
    this.allBookmarks$.next(currentValue.filter(bookmark => bookmark !== bookmarkToRemove));
  }

  deleteBookmarkById(id: string) {
    this.deleteBookmark(this.findBookmarkById(id));
  }

  findBookmarkIndexById(id: string): number {
    const currentBookmarks = this.allBookmarks$.getValue();
    if (currentBookmarks) {
      return currentBookmarks.findIndex(bm => bm.id === id);
    }
    return -1;
  }

  findBookmarkById(id: string): Bookmark {
    const currentBookmarks = this.allBookmarks$.getValue();
    if (currentBookmarks) {
      return currentBookmarks[this.findBookmarkIndexById(id)];
    }
    return Bookmark.NULL;
  }


  setWorkingStarts() {
    this.working$.next(true);
  }

  setWorkingFinished() {
    this.working$.next(false);
  }


  addAlert(type: AlertType, message: string) {
    let currentAlerts = this.alerts$.getValue();
    if (!currentAlerts) {
      currentAlerts = [];
    }
    currentAlerts.push({type: type, message: message});
    this.alerts$.next([...currentAlerts]);
  }

  removeAlert(alert: Alert) {
    const currentAlerts = this.alerts$.getValue();
    currentAlerts.splice(currentAlerts.indexOf(alert), 1);
    this.alerts$.next([...currentAlerts]);
  }


  addNewFolder(parent: FolderItemNode) {
    const currentFolders = this.allFolders$.getValue();
    parent.children.push(new FolderItemNode(
      Guid.newGuid(),
      '',
      parent.uuid,
      [],
      [],
      true));
    this.allFolders$.next(currentFolders);
  }


  deleteFolder(folderToDelete: FolderItemNode) {
    const currentFolders = this.allFolders$.getValue();
    this._delete(currentFolders, folderToDelete)
    this.allFolders$.next(currentFolders);
  }

  private _delete(folders: FolderItemNode[], folderToDelete: FolderItemNode) {
    const indexToDelete = folders.indexOf(folderToDelete);
    if (0 <= indexToDelete) {
      folders.splice(indexToDelete, 1);
    }
    folders.forEach(f => this._delete(f.children, folderToDelete));
  }


  updateFolder(updatedFolder: FolderItemNode) {
    const currentFolders = this.allFolders$.getValue();
    const indexToUpdate = currentFolders.findIndex(f => f.uuid === updatedFolder.uuid);
    currentFolders[indexToUpdate] = updatedFolder;
    this.allFolders$.next([...currentFolders]);
  }


  updateFolderId(folderToUpdate: FolderItemNode, updatedFolder: FolderItemNode) {
    const currentFolders = this.allFolders$.getValue();
    const indexToUpdate = currentFolders.findIndex(bm => bm === folderToUpdate);
    currentFolders[indexToUpdate] = updatedFolder;
    this.allFolders$.next([...currentFolders]);
  }

  setFolderToEditMode(folderToRename: FolderItemNode) {
    folderToRename.inEditMode = true;
  }

  setSearchTerm(searchTerm: string) {
    if (this.isString(searchTerm) && (0 < searchTerm.length)) {
      const currentBookmarks = this.allBookmarks$.getValue();
      this.matchingBookmarks$.next(
        currentBookmarks.filter(
          v => -1 < v.title.toLowerCase().indexOf(searchTerm.toLowerCase()))
      );
    } else {
      this.matchingBookmarks$.next([]);
    }
  }

  setSelectedFolder(selectedFolder: FolderItemNode) {
    if (selectedFolder === FolderItemNode.NULL) {
      const currentBookmarks = this.allBookmarks$.getValue();
      this.selectedBookmarks$.next(currentBookmarks);
    } else {
      if (selectedFolder.assignedBookmarkIds) {
        const currentBookmarks = this.allBookmarks$.getValue();
        let selectedBookmarks = currentBookmarks.filter(
          bm => selectedFolder.assignedBookmarkIds.includes(bm.id));

        this.selectedBookmarks$.next(selectedBookmarks);
      }
    }
  }


  private isString(value: any): boolean {
    return typeof value === 'string' || value instanceof String;
  }

}
