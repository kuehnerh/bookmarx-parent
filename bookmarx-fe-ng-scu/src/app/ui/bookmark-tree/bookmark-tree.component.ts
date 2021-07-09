import { Component, OnInit } from '@angular/core';
import {FolderItemNode} from "../../core/domain/folderItemNode.model";
import {BookmarxUsecases} from "../../core/usecases/bookmarx-usecases";

@Component({
  selector: 'app-bookmark-tree',
  templateUrl: './bookmark-tree.component.html',
  styleUrls: ['./bookmark-tree.component.css']
})
export class BookmarkTreeComponent implements OnInit {

  folderTree: FolderItemNode[] = [];

  constructor(private bmxUseCases: BookmarxUsecases) {
    this.bmxUseCases.getAllFolders$().subscribe(
      (folders) => { this.folderTree = folders; }
    );
  }

  ngOnInit(): void {
    this.bmxUseCases.loadAllFolders();
  }

  onClickTreeNode($event: MouseEvent, node: FolderItemNode) {
    $event.stopPropagation();
    console.log("Clicked: " + node.name, node.assignedBookmarkIds);
    this.bmxUseCases.setSelectedFolder(node);
  }

}
