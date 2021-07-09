import {InjectionToken, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {ReactiveFormsModule} from "@angular/forms";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './ui/app.component';
import {HeaderComponent} from './ui/header/header.component';
import {BookmarkSearchComponent} from './ui/bookmark-search/bookmark-search.component';
import {BookmarkEditComponent} from './ui/bookmark-edit/bookmark-edit.component';
import {BookmarkListComponent} from './ui/bookmark-list/bookmark-list.component';
import {BookmarkTreeComponent} from './ui/bookmark-tree/bookmark-tree.component';
import {BookmarkTreelistComponent} from './ui/bookmark-treelist/bookmark-treelist.component';

import {BookmarxBackendService} from "./core/usecases/bookmarx-backend-service";
import {BookmarxBackendServiceHttp} from "./provider/services/bookmarx-backend-service-http.service";
import {BookmarxBackendServiceInMemory} from "./provider/services/bookmarx-backend-service-in-memory.service";

import {BookmarxStateService} from "./core/usecases/bookmarx-state-service";
import {BookmarxStateServiceInMemory} from "./provider/state/bookmarx-state-service-in-memory";

import {IsEqualToPipe} from "./ui/shared/pipes/is-equal-to.pipe";
import {TruncatePipe} from "./ui/shared/pipes/truncate.pipe";
import {StartsWithPipe} from "./ui/shared/pipes/starts-with.pipe";

import {SchwarzCoreUIModule} from '@scu/angular';
import {HttpClientModule} from "@angular/common/http";
import {DatePipe} from "@angular/common";

export let BMX_STATE_SERVICE = new InjectionToken<BookmarxStateService>('bmx.state.service');
export let BMX_BACKEND_SERVICE = new InjectionToken<BookmarxBackendService>('bmx.backend.service');


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    IsEqualToPipe,
    StartsWithPipe,
    TruncatePipe,
    BookmarkEditComponent,
    BookmarkListComponent,
    BookmarkSearchComponent,
    BookmarkTreeComponent,
    BookmarkTreelistComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SchwarzCoreUIModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    // Available Implementations: BookmarxBackendServiceHttp, BookmarxBackendServiceInMemory
    {provide: BMX_BACKEND_SERVICE, useClass: BookmarxBackendServiceHttp},
    // Available Implementations: BookmarxStateServiceInMemory
    {provide: BMX_STATE_SERVICE, useClass: BookmarxStateServiceInMemory}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
