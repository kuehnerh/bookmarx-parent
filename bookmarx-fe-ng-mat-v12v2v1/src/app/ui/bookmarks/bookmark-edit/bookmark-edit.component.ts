import {AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Bookmark} from "../../../core/domain/bookmark.model";
import {ActivatedRoute} from "@angular/router";
import {BookmarxUsecases} from "../../../core/usecases/bookmarx-usecases";
import {Observable} from "rxjs";
import {Alert} from "../../../core/domain/alert.model";
import {MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {DatePipe} from "@angular/common";


export interface BookmarkTag {
  name: string;
}


@Component({
  selector: 'app-bookmark-creation',
  templateUrl: './bookmark-edit.component.html',
  styleUrls: ['./bookmark-edit.component.css'],
  providers: [DatePipe]
})
export class BookmarkEditComponent implements OnInit,AfterViewInit {


  @ViewChild('titleInput')
  private titleInputElementRef!: ElementRef;

  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  // View Model
  isWorking$: Observable<boolean>;
  alerts$: Observable<Alert[]>
  currentTags: BookmarkTag[] = [];

  private currentFavIconPath: string = "";


  bookmarkForm = this.fb.group({
    id: null,
    title: [null, Validators.required],
    url: [null, Validators.required],
    description: [null, Validators.required],
    tags: "",
    createdAt: null,
    modifiedAt: null
  });


  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute,
    private bookmarkFacade: BookmarxUsecases,
    private datePipe: DatePipe) {

    this.bookmarkFacade.getBookmarkToEdit$().subscribe(
      (bookmark) => this.facadeToView(bookmark)
    )
    this.isWorking$ = this.bookmarkFacade.isWorking$();
    this.alerts$ = this.bookmarkFacade.getAlerts$();

  }

  ngOnInit(): void {
    this.initBookmarkToEdit();
  }

  ngAfterViewInit(): void {
    // Set Focus to Title-Input when entering dialog
    this.titleInputElementRef.nativeElement.focus();
    this.cd.detectChanges();
  }






  initBookmarkToEdit() {
    let id = this.activatedRoute.snapshot.params['id'];

    if (id) {
      this.bookmarkFacade.loadBookmarkToEditById(id);
    } else {
      this.bookmarkFacade.loadInitialBookmarkToEdit();
    }
  }



  private facadeToView(bookmark: Bookmark) {
    if (!bookmark) {
      return;
    }
    this.bookmarkForm.get('id')!.setValue(bookmark.id);
    this.bookmarkForm.get('title')!.setValue(bookmark.title);
    this.bookmarkForm.get('url')!.setValue(bookmark.url);
    this.bookmarkForm.get('description')!.setValue(bookmark.description);
    this.currentTags = bookmark.tags.map(item => { return {name: item}});
    this.bookmarkForm.get('tags')!.setValue(this.currentTags);
    this.bookmarkForm.get('createdAt')!.setValue(this.datePipe.transform(bookmark.createdAt, 'yyyy-MM-dd HH:mm:ss'));
    this.bookmarkForm.get('modifiedAt')!.setValue(this.datePipe.transform(bookmark.modifiedAt, 'yyyy-MM-dd HH:mm:ss'));
  }

  private viewToFacade(): Bookmark {
    // Tag Model is
    // {
    //   name       -> name as string
    // }
    // convert it to a plain string array
    let tagArray: string[] = [];
    for (let tag of this.bookmarkForm.get('tags')!.value) {
      tagArray.push(tag.name)
    }

    return new Bookmark(
      this.bookmarkForm.get('id')!.value,
      this.bookmarkForm.get('title')!.value,
      this.bookmarkForm.get('url')!.value,
      this.bookmarkForm.get('description')!.value,
      this.currentFavIconPath,
      tagArray,
      new Date(this.bookmarkForm.get('createdAt')!.value),
      new Date(this.bookmarkForm.get('modifiedAt')!.value)
    );
  }

  onSubmit() {
    let bookmark = this.viewToFacade();
    console.log('Id: ' + bookmark.id);
    console.log('Title: ' + bookmark.title);


    this.bookmarkFacade.saveBookmark(bookmark);
  }

  isTitleInvalid(): boolean {
    return !this.bookmarkForm.get('title')!.valid && this.bookmarkForm.get('title')!.touched;
  }

  close(alert: Alert) {
    this.bookmarkFacade.removeAlert(alert);
  }



  onAddTag(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    // Add our tag
    if (value) {
      this.currentTags.push({name: value});
    }

    // Clear the input value
    event.chipInput!.clear();
  }


  onRemoveTag(tag: BookmarkTag): void {
    const index = this.currentTags.indexOf(tag);

    if (index >= 0) {
      this.currentTags.splice(index, 1);
    }
  }
}
