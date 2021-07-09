import {BrowserModule} from '@angular/platform-browser';
import {InjectionToken, NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";

import {AppComponent} from './ui/app.component';
import {HeaderComponent} from "./ui/header/header.component";
import {BookmarksComponent} from './ui/bookmarks/bookmarks.component';
import {BookmarkListComponent} from './ui/bookmarks/bookmark-list/bookmark-list.component';
import {BookmarkEditComponent} from './ui/bookmarks/bookmark-edit/bookmark-edit.component';
import {BookmarkSearchComponent} from './ui/bookmarks/bookmark-search/bookmark-search.component';
import {BookmarkTreeComponent} from './ui/bookmarks/bookmark-tree/bookmark-tree.component';
import {BookmarkTreelistComponent} from './ui/bookmarks/bookmark-treelist/bookmark-treelist.component';

import {BookmarxBackendServiceHttp} from "./provider/services/bookmarx-backend-service-http.service";
import {TruncatePipe} from "./ui/shared/pipes/truncate.pipe";
import {StartsWithPipe} from "./ui/shared/pipes/starts-with.pipe";
import {IsEqualToPipe} from "./ui/shared/pipes/is-equal-to.pipe";
import {HighlightPipe} from './ui/bookmarks/bookmark-search/highlight.pipe';
import {SpyDirective} from "./ui/bookmarks/bookmark-tree/spy.directive";

import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {FlexLayoutModule} from "@angular/flex-layout";

import {MatSliderModule} from "@angular/material/slider";
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatRadioModule} from '@angular/material/radio';
import {MatCardModule} from '@angular/material/card';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatTreeModule} from "@angular/material/tree";
import {MatTableModule} from "@angular/material/table";
import {MatChipsModule} from "@angular/material/chips";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatTooltipModule} from "@angular/material/tooltip";
import {BookmarxBackendService} from "./core/usecases/bookmarx-backend-service";
import {BookmarxStateService} from "./core/usecases/bookmarx-state-service";
import {BookmarxStateServiceInMemory} from "./provider/state/bookmarx-state-service-in-memory";

const appRoutes: Routes = [
  {path: 'bookmarks/search', component: BookmarkSearchComponent},

  {path: 'bookmarks/list', component: BookmarksComponent},

  {path: 'bookmarks/edit', component: BookmarkEditComponent},
  {path: 'bookmarks/edit/:id', component: BookmarkEditComponent},

  {path: 'bookmarks/tree', component: BookmarkTreeComponent},

  {path: 'bookmarks/treelist', component: BookmarkTreelistComponent},

  {path: '**', redirectTo: 'bookmarks/search', pathMatch: 'full'}
]

export let BMX_STATE_SERVICE = new InjectionToken<BookmarxStateService>('bmx.state.service');
export let BMX_BACKEND_SERVICE = new InjectionToken<BookmarxBackendService>('bmx.backend.service');


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BookmarksComponent,
    BookmarkListComponent,
    BookmarkEditComponent,
    BookmarkSearchComponent,
    HighlightPipe,
    BookmarkTreeComponent,
    SpyDirective,
    IsEqualToPipe,
    StartsWithPipe,
    TruncatePipe,
    BookmarkTreelistComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(appRoutes, {relativeLinkResolution: 'legacy'}),
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatSliderModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatRadioModule,
    MatCardModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatAutocompleteModule,
    MatTreeModule,
    FlexLayoutModule,
    MatTableModule,
    MatChipsModule,
    MatSnackBarModule,
    MatTooltipModule
  ],
  providers: [
    // Available Implementations: BookmarxBackendServiceHttp, BookmarxBackendServiceInMemory
    {provide: BMX_BACKEND_SERVICE, useClass: BookmarxBackendServiceHttp},
    // Available Implementations: BookmarxStateServiceInMemory
    {provide: BMX_STATE_SERVICE, useClass: BookmarxStateServiceInMemory}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
