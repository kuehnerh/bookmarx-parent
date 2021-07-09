import {AfterViewInit, ChangeDetectorRef, Component, ElementRef, ViewChild} from '@angular/core';
import {Observable} from "rxjs";
import {debounceTime, distinctUntilChanged, startWith, tap} from "rxjs/operators";
import {Bookmark} from "../../../core/domain/bookmark.model";
import {FormControl} from "@angular/forms";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {BookmarxUsecases} from "../../../core/usecases/bookmarx-usecases";


@Component({
  selector: 'app-bookmark-search',
  templateUrl: './bookmark-search.component.html',
  styleUrls: ['./bookmark-search.component.css']
})
export class BookmarkSearchComponent implements AfterViewInit {

  @ViewChild('searchInputControlRef')
  private searchInputControlRef!: ElementRef;

  selectedString: string = "";
  searchInputControl = new FormControl();

  filteredOptions$: Observable<Bookmark[]>;
  textToHighlight: string = "";


  constructor(
    private cd: ChangeDetectorRef,
    private bookmarkFacade: BookmarxUsecases) {

    bookmarkFacade.loadAllBookmarks();
    this.filteredOptions$ = this.bookmarkFacade.getMatchingBookmarks$();

    this.searchInputControl.valueChanges
      .pipe(
        startWith(''),
        debounceTime(200),
        distinctUntilChanged(),
        tap(term => this.filterBookmarks(term)))
      .subscribe(
        (term) => {
          this.textToHighlight = term
        }
      );
  }


  filterBookmarks(searchTerm: string) {
    console.log("searchTerm: " + searchTerm)
    this.bookmarkFacade.setSearchTerm(searchTerm);
  }


  public displayWithFn(bm: Bookmark): string {
    return bm ? bm.title + ' - ' + bm.url : '';
  }


  ngAfterViewInit(): void {
    this.searchInputControlRef.nativeElement.focus();
    this.cd.detectChanges();
  }


  onEnter(event: Event) {
    console.log("onEnter in SearchInputControl: " + event);
    console.log(this.searchInputControl.value);
  }

  onOptionSelected(event: MatAutocompleteSelectedEvent) {
    console.log("onOptionSelected: " + event);
    console.log("onOptionSelected: " + event.option.value);

    let selectedBookmark = event.option.value as Bookmark;
    window.location.href = selectedBookmark.url;
  }


}
