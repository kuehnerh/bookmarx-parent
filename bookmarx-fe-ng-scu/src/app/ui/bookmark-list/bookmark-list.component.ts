import {Component, OnInit} from '@angular/core';
import {Bookmark} from "../../core/domain/bookmark.model";
import {Observable} from "rxjs";
import {BookmarxUsecases} from "../../core/usecases/bookmarx-usecases";
import {NavigationService} from "../shared/navigation.service";

@Component({
  selector: 'app-bookmark-list',
  templateUrl: './bookmark-list.component.html',
  styleUrls: ['./bookmark-list.component.css']
})
export class BookmarkListComponent implements OnInit {

  bookmarks$: Observable<Bookmark[]>;

  bookmarkCount: number = 0;
  bookmarks: Bookmark[] = [];

  constructor(private bookmarkFacade: BookmarxUsecases, private navService: NavigationService) {
    this.bookmarks$ = this.bookmarkFacade.getSelectedBookmarks$();

    this.bookmarks$.subscribe(
      nextBookmarkArray => {
        this.bookmarkCount = nextBookmarkArray.length;
        this.bookmarks = nextBookmarkArray;
      }
    );
  }

  ngOnInit(): void {
    this.bookmarkFacade.loadAllBookmarks();
  }


  onClickDeleteBookmark(event: Event, bookmarkId: string) {
    this.bookmarkFacade.deleteBookmarkById(bookmarkId);
  }

  onClickEditBookmark(event: Event, bookmarkId: string) {
    this.navService.navigateToPath("bookmarks/edit/" + bookmarkId)
  }

  onClickBookmark(event: Event, bookmarkId: string, bookmarkUrl: string) {
    // Navigate to a external URL
    console.log("Click: " + bookmarkId)
    window.location.href = bookmarkUrl;
  }


}
