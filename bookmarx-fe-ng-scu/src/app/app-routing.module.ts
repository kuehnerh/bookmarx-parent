import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BookmarkEditComponent} from "./ui/bookmark-edit/bookmark-edit.component";
import {BookmarkListComponent} from "./ui/bookmark-list/bookmark-list.component";
import {BookmarkSearchComponent} from "./ui/bookmark-search/bookmark-search.component";
import {BookmarkTreeComponent} from "./ui/bookmark-tree/bookmark-tree.component";
import {BookmarkTreelistComponent} from "./ui/bookmark-treelist/bookmark-treelist.component";

const routes: Routes = [
  {path: 'bookmarks/search', component: BookmarkSearchComponent},

  {path: 'bookmarks/list', component: BookmarkListComponent},

  {path: 'bookmarks/edit', component: BookmarkEditComponent},
  {path: 'bookmarks/edit/:id', component: BookmarkEditComponent},

  {path: 'bookmarks/tree', component: BookmarkTreeComponent},

  {path: 'bookmarks/treelist', component: BookmarkTreelistComponent},

  {path: '**', redirectTo: 'bookmarks/search', pathMatch: 'full'}
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
