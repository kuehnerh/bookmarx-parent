import {Injectable} from '@angular/core';
import {Bookmark} from "../../core/domain/bookmark.model";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {BookmarxBackendService} from "../../core/usecases/bookmarx-backend-service";
import {map} from "rxjs/operators";
import {FolderItemNode} from "../../core/domain/folderItemNode.model";
import {JsonListOfBookmarksResponse} from "./JsonListOfBookmarksResponse";
import {JsonSingleBookmarkResponse} from "./JsonSingleBookmarkResponse";
import {JsonEmptyResponse} from "./JsonEmptyResponse";
import {JsonBookmark} from "./JsonBookmark";
import {JsonTreeOfFoldersResponse} from "./JsonTreeOfFoldersResponse";
import {JsonTreeFolder} from "./JsonTreeFolder";
import {JsonSingleFolderResponse} from "./JsonSingleFolderResponse";
import {JsonSingleFolder} from "./JsonSingleFolder";

@Injectable({
  providedIn: 'root'
})
export class BookmarxBackendServiceHttp implements BookmarxBackendService {

  constructor(private http: HttpClient) { }


  findBookmarksAll(): Observable<Bookmark[]> {
    return this.http
      .get<JsonListOfBookmarksResponse>('/api/bookmarks')
      .pipe(
        map(response => this.mapJsonListOfBookmarksResponse(response))
      );
  }



  findBookmarksBySearchTerm(searchTerm: string): Observable<Bookmark[]> {
    return new BehaviorSubject<Bookmark[]>([]);
  }


  findBookmarkById(id: string): Observable<Bookmark> {
    return this.http
      .get<JsonSingleBookmarkResponse>('/api/bookmarks/' + id )
      .pipe(
        map(response => this.mapJsonSingleBookmarkResponse(response))
      );
  }



  saveBookmark(newBookmark: Bookmark): Observable<Bookmark> {
    if (newBookmark.id) {
      return this.http
        .put<JsonSingleBookmarkResponse>('/api/bookmarks/' + newBookmark.id, newBookmark)
        .pipe(
          map(response => this.mapJsonSingleBookmarkResponse(response))
        );
    } else {
      return this.http
        .post<JsonSingleBookmarkResponse>('/api/bookmarks', newBookmark)
        .pipe(
          map(response => this.mapJsonSingleBookmarkResponse(response))
        );
    }
  }





  deleteBookmarkById(bookmarkId: string): Observable<any> {
      return this.http
        .delete<JsonEmptyResponse>('/api/bookmarks/' + bookmarkId);
  }




  private mapJsonListOfBookmarksResponse(json: JsonListOfBookmarksResponse): Bookmark[] {
    let bookmarks: Bookmark[] = [];
    for (let jsonBookmark of json.bookmarks) {
      bookmarks.push(this.mapJsonBookmark(jsonBookmark));
    }
    return bookmarks;
  }

  private mapJsonSingleBookmarkResponse(json: JsonSingleBookmarkResponse): Bookmark {
    return this.mapJsonBookmark(json.bookmark);
  }

  private mapJsonBookmark(jsonBookmark: JsonBookmark): Bookmark {
    return new Bookmark(
      jsonBookmark.id,
      jsonBookmark.title,
      jsonBookmark.url,
      jsonBookmark.description,
      jsonBookmark.favIconPath,
      jsonBookmark.tags,
      new Date(jsonBookmark.createdAt),
      new Date(jsonBookmark.modifiedAt)
    )
  }


  findFoldersAll(): Observable<FolderItemNode[]> {
    return this.http
      .get<JsonTreeOfFoldersResponse>('/api/folder-tree')
      .pipe(
        map(response => this.mapJsonTreeOfFoldersResponse(response))
      );
  }

  saveFolder(folder: FolderItemNode): Observable<FolderItemNode> {
    if (folder.uuid) {
      return this.http
        .put<JsonSingleFolderResponse>('/api/folders/' + folder.uuid, folder)
        .pipe(
          map(response => this.mapJsonSingleFolderResponse(response))
        );
    } else {
      return this.http
        .post<JsonSingleFolderResponse>('/api/bookmarks', folder)
        .pipe(
          map(response => this.mapJsonSingleFolderResponse(response))
        );
    }
  }

  deleteFolderById(id: string): Observable<any> {
    return this.http
      .delete<JsonEmptyResponse>('/api/folders/' + id);
  }




  private mapJsonTreeOfFoldersResponse(json: JsonTreeOfFoldersResponse): FolderItemNode[] {
    return this.mapJsonTreeOfFolders(json.folders, "");
  }

  private mapJsonSingleFolderResponse(json: JsonSingleFolderResponse): FolderItemNode {
    return this.mapJsonSingleFolder(json.folder);
  }

  private mapJsonTreeOfFolders(json: JsonTreeFolder[], parentId: string): FolderItemNode[] {
    let folders: FolderItemNode[] = [];
    for (let jsonFolder of json) {
      let folder = this.mapJsonTreeFolder(jsonFolder, parentId);
      folders.push(folder);

      if (jsonFolder.children) {
        let childFolders = this.mapJsonTreeOfFolders(jsonFolder.children, jsonFolder.id);
        folder.children = childFolders;
      }
    }
    return folders;
  }

  private mapJsonTreeFolder(jsonTreeFolder: JsonTreeFolder, parentId: string): FolderItemNode {
    return new FolderItemNode(
      jsonTreeFolder.id,
      jsonTreeFolder.name,
      parentId,
      [],
      jsonTreeFolder.assignedBookmarkIds,
      false);
  }

  private mapJsonSingleFolder(jsonSingleFolder: JsonSingleFolder): FolderItemNode {
    return new FolderItemNode(
      jsonSingleFolder.id,
      jsonSingleFolder.name,
      jsonSingleFolder.parentId,
      [],
      jsonSingleFolder.assignedBookmarkIds,
      false);
  }
}




