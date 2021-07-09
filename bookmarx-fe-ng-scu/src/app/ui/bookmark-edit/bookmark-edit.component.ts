import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Observable} from "rxjs";
import {Alert, AlertType} from "../../core/domain/alert.model";
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {BookmarxUsecases} from "../../core/usecases/bookmarx-usecases";
import {Bookmark} from "../../core/domain/bookmark.model";
import {ScuInput} from "@scu/angular";
import {DatePipe} from "@angular/common";


@Component({
  selector: 'app-bookmark-edit',
  templateUrl: './bookmark-edit.component.html',
  styleUrls: ['./bookmark-edit.component.css'],
  providers: [DatePipe]
})
export class BookmarkEditComponent implements OnInit, AfterViewInit {


  @ViewChild('titleInput')
  private titleInputElementRef!: ElementRef;

  // View Model
  viewToastArray: ViewToast[] = [];
  isWorking$: Observable<boolean>;
  //alerts$: Observable<Alert[]>
  private currentFavIconPath: string = "";


  bookmarkForm = this.fb.group({
    id: null,
    title: [null, Validators.required],
    url: [null, Validators.required],
    description: [null, Validators.required],
    tags: null,
    createdAt: null,
    modifiedAt: null
  });


  constructor(
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private bookmarkFacade: BookmarxUsecases,
    private datePipe: DatePipe) {


    this.isWorking$ = this.bookmarkFacade.isWorking$();
    //this.alerts$ = this.bookmarkFacade.getAlerts$();

    this.bookmarkFacade.getBookmarkToEdit$().subscribe(
      (bookmark) => this.facadeToView(bookmark)
    );

    this.bookmarkFacade.getAlerts$().subscribe(
      (alertArray) => {
        this.viewToastArray = alertArray.map( alert => {
          setTimeout(() => this.onClickCloseAlert(alert), 3000); // Close toast automatically
          return new ViewToast(alert)
        });
      });
  }

  ngOnInit(): void {
    this.initBookmarkToEdit();
  }

  ngAfterViewInit(): void {
    // Set Focus to Title-Input when entering dialog

    // Material Code
    //this.titleInputElementRef.nativeElement.focus();

    // SCU Code
    (this.titleInputElementRef as unknown as ScuInput).setFocus().then();
  }



  private initBookmarkToEdit() {
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
    this.bookmarkForm.get('tags')!.setValue(bookmark.tags.join(', '));
    this.bookmarkForm.get('createdAt')!.setValue(this.datePipe.transform(bookmark.createdAt, 'yyyy-MM-dd HH:mm:ss'));
    this.bookmarkForm.get('modifiedAt')!.setValue(this.datePipe.transform(bookmark.modifiedAt, 'yyyy-MM-dd HH:mm:ss'));
  }

  private viewToFacade(): Bookmark {
    // MAT Code
    // Tag Model is
    // {
    //   value       -> id
    //   display     -> displayed
    // }
    // convert it to a plain string array
    // let tagArray: string[] = [];
    // for (let tag of this.bookmarkForm.get('tags')!.value) {
    //   tagArray.push(tag.value)
    // }

    // SCU Code
    let tagsAsString = (this.bookmarkForm.get('tags')!.value as string);
    let tagsAsArray: string[] = tagsAsString.split(",").map(function(tag) {
      return tag.trim();
    });

    return new Bookmark(
      this.bookmarkForm.get('id')!.value,
      this.bookmarkForm.get('title')!.value,
      this.bookmarkForm.get('url')!.value,
      this.bookmarkForm.get('description')!.value,
      this.currentFavIconPath,
      tagsAsArray,
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

  onClickCloseAlert(alert: Alert) {
    this.bookmarkFacade.removeAlert(alert);
  }

  onClickCloseToast(toast: ViewToast) {
    this.bookmarkFacade.removeAlert(toast.orgAlert);
  }

  isTitleInvalid(): string {
    return (!this.bookmarkForm.get('title')!.valid && this.bookmarkForm.get('title')!.touched) ? "error" : "";
  }

  getTitleErrorMessage(): string {
    if (this.isTitleInvalid()) {
      return "Title is invalid."
    }
    return "";
  }


}





class ViewToast {
  status: string;
  message: string;
  orgAlert: Alert;


  constructor(alert: Alert) {
    switch (alert.type) {
      case AlertType.SUCCESS:
        this.status = 'success';
        break;
      case AlertType.INFO:
        this.status = 'info';
        break;
      case AlertType.WARNING:
        this.status = 'warning';
        break;
      default:
        this.status = 'error';
        break;
    }
    this.message = alert.message;
    this.orgAlert = alert;
  }
}
