import {Observable} from "rxjs";
import {Bookmark} from "../domain/bookmark.model";
import {FolderItemNode} from "../domain/folderItemNode.model";
import {Alert, AlertType} from "../domain/alert.model";


export interface BookmarxStateService {

  getAllBookmarks$(): Observable<Bookmark[]>;

  getAllFolderItemNodes$(): Observable<FolderItemNode[]>;

  getBookmarkToEdit$(): Observable<Bookmark>;

  getMatchingBookmarks$(): Observable<Bookmark[]>;

  getSelectedBookmarks$(): Observable<Bookmark[]>;

  isWorking$(): Observable<boolean>;

  getAlerts$(): Observable<Alert[]>;

  setBookmarkToEdit(bookmarkToEdit: Bookmark): void;

  setBookmarkToEditBy(id: string): boolean;

  setAllBookmarks(bookmarks: Bookmark[]): void;

  setAllFolders(folderItemNodes: FolderItemNode[]): void;

  addBookmark(bookmark: Bookmark): void;

  updateBookmark(updatedBookmark: Bookmark): void;

  updateBookmarkId(bookmarkToUpdate: Bookmark, updatedBookmark: Bookmark): void;

  deleteBookmark(bookmarkToRemove: Bookmark): void;

  deleteBookmarkById(id: string): void;

  findBookmarkIndexById(id: string): number;

  findBookmarkById(id: string): Bookmark;

  setWorkingStarts(): void;

  setWorkingFinished(): void;

  addAlert(type: AlertType, message: string): void;

  removeAlert(alert: Alert): void;

  addNewFolder(parent: FolderItemNode): void;

  deleteFolder(folderToDelete: FolderItemNode): void;

  updateFolder(updatedFolder: FolderItemNode): void;

  updateFolderId(folderToUpdate: FolderItemNode, updatedFolder: FolderItemNode): void;

  setFolderToEditMode(folderToRename: FolderItemNode): void;

  setSearchTerm(searchTerm: string): void;

  setSelectedFolder(selectedFolder: FolderItemNode): void;

}
