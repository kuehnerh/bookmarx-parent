import {Component, OnInit} from '@angular/core';
import {Bookmark} from "../../../core/domain/bookmark.model";
import {BookmarxUsecases} from "../../../core/usecases/bookmarx-usecases";
import {NavigationService} from "../../shared/navigation.service";
import {MAT_TOOLTIP_DEFAULT_OPTIONS} from "@angular/material/tooltip";
import {myCustomTooltipDefaults} from "../../app.component";


@Component({
  selector: 'app-bookmark-list',
  templateUrl: './bookmark-list.component.html',
  styleUrls: ['./bookmark-list.component.css'],
  providers: [
    {provide: MAT_TOOLTIP_DEFAULT_OPTIONS, useValue: myCustomTooltipDefaults}
  ]
})
export class BookmarkListComponent implements OnInit {

  bookmarkArray: Bookmark[] = [];
  displayedColumns: string[] = ['id', 'title', 'url', 'description', 'tags', 'createdAt', 'modifiedAt', 'actions'];

  constructor(private bookmarkFacade: BookmarxUsecases, private navService: NavigationService) {
    this.bookmarkFacade.getSelectedBookmarks$().subscribe(
      (b) => {
        this.bookmarkArray = b;
      }
    );
  }

  ngOnInit(): void {
    this.bookmarkFacade.loadAllBookmarks();
  }


  onClickEditBookmark(event: Event, bookmarkId: string) {
    this.navService.navigateToPath("bookmarks/edit/" + bookmarkId).then();
    console.log("Edit Bookmark: " + bookmarkId)
  }


  onClickDeleteBookmark(event: Event, bookmarkId: string) {
    this.bookmarkFacade.deleteBookmarkById(bookmarkId);
  }


  onClickNavigateToBookmark(event: Event, bookmarkId: string, bookmarkUrl: string) {
    // Navigate to a external URL
    console.log("Click: " + bookmarkId)
    window.location.href = bookmarkUrl;
  }


}
