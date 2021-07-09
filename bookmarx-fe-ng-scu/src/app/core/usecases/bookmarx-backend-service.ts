import {Observable} from "rxjs";
import {Bookmark} from "../domain/bookmark.model";
import {FolderItemNode} from "../domain/folderItemNode.model";


export interface BookmarxBackendService {

  // Bookmarks
  findBookmarksAll(): Observable<Bookmark[]>;
  findBookmarksBySearchTerm(searchTerm: string): Observable<Bookmark[]>;
  findBookmarkById(id: string): Observable<Bookmark>;
  saveBookmark(newBookmark: Bookmark): Observable<Bookmark>;
  deleteBookmarkById(bookmarkId: string): Observable<any>;

  // Folders
  findFoldersAll(): Observable<FolderItemNode[]>;
  saveFolder(folderToSave: FolderItemNode): Observable<FolderItemNode>;
  deleteFolderById(id: string): Observable<any>;

}
