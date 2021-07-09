import {Inject, Injectable} from "@angular/core";
import {Bookmark} from "../domain/bookmark.model";
import {Observable} from "rxjs";
import {Alert, AlertType} from "../domain/alert.model";
import {FolderItemNode} from "../domain/folderItemNode.model";
import {BookmarxStateService} from "./bookmarx-state-service";
import {BMX_BACKEND_SERVICE, BMX_STATE_SERVICE} from "../../app.module";
import {BookmarxBackendService} from "./bookmarx-backend-service";

@Injectable({
  providedIn: 'root'
})
export class BookmarxUsecases {

  constructor(
    @Inject(BMX_STATE_SERVICE) private bookmarkStateService: BookmarxStateService,
    @Inject(BMX_BACKEND_SERVICE) private httpBookmarkService: BookmarxBackendService) {
  }

  getAllBookmarks$(): Observable<Bookmark[]> {
    return this.bookmarkStateService.getAllBookmarks$();
  }

  getAllFolders$(): Observable<FolderItemNode[]> {
    return this.bookmarkStateService.getAllFolderItemNodes$();
  }

  getBookmarkToEdit$(): Observable<Bookmark> {
    return this.bookmarkStateService.getBookmarkToEdit$();
  }

  getMatchingBookmarks$(): Observable<Bookmark[]> {
    return this.bookmarkStateService.getMatchingBookmarks$();
  }

  getSelectedBookmarks$(): Observable<Bookmark[]> {
    return this.bookmarkStateService.getSelectedBookmarks$();
  }

  isWorking$(): Observable<boolean> {
    return this.bookmarkStateService.isWorking$();
  }

  getAlerts$(): Observable<Alert[]> {
    return this.bookmarkStateService.getAlerts$();
  }


  loadAllBookmarks() {
    this.httpBookmarkService.findBookmarksAll()
      .subscribe(bookmarks => {
        console.log("loadAllBookmarks" + bookmarks);
        this.bookmarkStateService.setAllBookmarks(bookmarks);
        this.bookmarkStateService.setSelectedFolder(FolderItemNode.NULL);
      });
  }


  loadAllFolders() {
    this.httpBookmarkService.findFoldersAll()
      .subscribe(foldersItemNodes => {
        console.log("loadAllFolders" + foldersItemNodes);
        this.bookmarkStateService.setAllFolders(foldersItemNodes)
      });
  }


  addNewFolder(parentFolder: FolderItemNode) {
    this.bookmarkStateService.addNewFolder(parentFolder);
  }


  deleteFolder(folder: FolderItemNode) {
    // Delete Pessimistic
    this.httpBookmarkService.deleteFolderById(folder.uuid)
      .subscribe(
        () => {
          this.bookmarkStateService.deleteFolder(folder);
          this.bookmarkStateService.addAlert(AlertType.SUCCESS, 'Record successfully deleted.');
        },
        (error) => {
          this.bookmarkStateService.addAlert(AlertType.ERROR, 'Could not delete Record.' + error);
          this.bookmarkStateService.setWorkingFinished();
        },
        () => this.bookmarkStateService.setWorkingFinished()
      );
  }


  setFolderToEditMode(folder: FolderItemNode) {
    this.bookmarkStateService.setFolderToEditMode(folder);
  }


  saveFolder(folder: FolderItemNode) {
    this.bookmarkStateService.setWorkingStarts();
    // Optimistic
    if (folder.uuid) {
      // Update
      this.bookmarkStateService.updateFolder(folder)
    } else {
      // Add
      this.bookmarkStateService.addNewFolder(folder);
    }

    this.httpBookmarkService.saveFolder(folder)
      .subscribe(
        savedFolder => {
          this.bookmarkStateService.updateFolderId(folder, savedFolder);
          this.bookmarkStateService.addAlert(AlertType.SUCCESS, 'Saved Record successfully');
        },
        (error) => {
          this.bookmarkStateService.addAlert(AlertType.ERROR, error);
          this.bookmarkStateService.setWorkingFinished();
        },
        () => this.bookmarkStateService.setWorkingFinished()
      );
  }


  loadBookmarkToEditById(id: string) {
    this.bookmarkStateService.setWorkingStarts();

    // 1. Try to load from state
    // 2. Try to load from backend
    const found = this.bookmarkStateService.setBookmarkToEditBy(id);
    if (!found) {

      this.httpBookmarkService.findBookmarkById(id).subscribe(
        (bm) => {
          this.bookmarkStateService.setBookmarkToEdit(bm);
          this.bookmarkStateService.setWorkingFinished()
        },
        () => {
          // Nothing to do here
        },
        () => {
          this.bookmarkStateService.setWorkingFinished();
        }
      );
    } else {
      this.bookmarkStateService.setWorkingFinished();
    }
  }


  loadInitialBookmarkToEdit() {
    this.bookmarkStateService.setBookmarkToEdit(
      new Bookmark("", "", "https://www.example.com", "", "", [], null as any, null as any));
  }


  saveBookmark(bookmarkToSave: Bookmark) {
    this.bookmarkStateService.setWorkingStarts();
    // Optimistic
    if (bookmarkToSave.id) {
      // Update
      this.bookmarkStateService.updateBookmark(bookmarkToSave)
    } else {
      // Add
      this.bookmarkStateService.addBookmark(bookmarkToSave);
    }


    this.httpBookmarkService.saveBookmark(bookmarkToSave)
      .subscribe(
        savedBookmark => {
          this.bookmarkStateService.updateBookmarkId(bookmarkToSave, savedBookmark);
          this.bookmarkStateService.setBookmarkToEdit(savedBookmark);
          this.bookmarkStateService.addAlert(AlertType.SUCCESS, 'Saved Record successfully');
        },
        (error) => {
          this.bookmarkStateService.addAlert(AlertType.ERROR, error);
          this.bookmarkStateService.setWorkingFinished();
        },
        () => this.bookmarkStateService.setWorkingFinished()
      );
  }


  getBookmarkBy(bookmarkId: string): Bookmark {
    return this.bookmarkStateService.findBookmarkById(bookmarkId);
  }


  deleteBookmarkById(id: string) {
    this.bookmarkStateService.deleteBookmarkById(id);
    this.httpBookmarkService.deleteBookmarkById(id).subscribe();
  }


  removeAlert(alert: Alert) {
    this.bookmarkStateService.removeAlert(alert);
  }


  setSearchTerm(searchTerm: string) {
    this.bookmarkStateService.setSearchTerm(searchTerm);
  }


  setSelectedFolder(selectedFolder: FolderItemNode) {
    this.bookmarkStateService.setSelectedFolder(selectedFolder);
  }
}
