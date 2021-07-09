import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ScuSearch} from "@scu/angular";
import {Bookmark} from "../../core/domain/bookmark.model";
import {FormControl} from "@angular/forms";
import {BookmarxUsecases} from "../../core/usecases/bookmarx-usecases";

@Component({
  selector: 'app-bookmark-search',
  templateUrl: './bookmark-search.component.html',
  styleUrls: ['./bookmark-search.component.css']
})
export class BookmarkSearchComponent implements OnInit, AfterViewInit {

  @ViewChild('searchInput')
  private searchDropDownElementRef!: ElementRef;


  scuSearchControl = new FormControl();
  scuSearchLiveSearchData: string[] = [];

  constructor(
    private bookmarkFacade: BookmarxUsecases) {

    // Bind ScuSearchData -> AllBookmarkObservable
    this.bookmarkFacade.getAllBookmarks$().subscribe(
      (bookmarks: Bookmark[]) => {
        this.scuSearchLiveSearchData = [];
        bookmarks.forEach( b => this.scuSearchLiveSearchData.push(b.title + ' - ' + b.url))
      }
    )
  }


  ngOnInit(): void {
    this.bookmarkFacade.loadAllBookmarks();
  }

  ngAfterViewInit(): void {
    // Set Focus to Title-Input when entering dialog
    setTimeout(() => {this.setFocusToInput()}, 100);
  }

  setFocusToInput() {
    // Material Code
    //this.titleInputElementRef.nativeElement.focus();

    // SCU Code
    // noinspection JSIgnoredPromiseFromCall
    (this.searchDropDownElementRef as unknown as ScuSearch).setFocus();
  }

  onClickSearch() {
    console.log("Search")
  }


  onScuSearchSubmit() {
    let selectedValue: string = this.scuSearchControl.value;
    let index = selectedValue.indexOf(" - ");
    let targetUrl = selectedValue.slice(index + 3);

    console.log("onScuSearchSubmit:" + this.scuSearchControl.value)
    console.log("targetUrl: >" + targetUrl + "<");
    window.location.href = targetUrl;
  }

}
