import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NestedTreeControl} from "@angular/cdk/tree";
import {FolderItemNode} from "../../../core/domain/folderItemNode.model";
import {MatTreeNestedDataSource} from "@angular/material/tree";
import {Observable, of} from "rxjs";
import {MatMenuTrigger} from "@angular/material/menu";
import {BookmarxUsecases} from "../../../core/usecases/bookmarx-usecases";
import {Alert} from "../../../core/domain/alert.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MAT_TOOLTIP_DEFAULT_OPTIONS} from "@angular/material/tooltip";
import {myCustomTooltipDefaults} from "../../app.component";

@Component({
  selector: 'app-bookmark-tree',
  templateUrl: './bookmark-tree.component.html',
  styleUrls: ['./bookmark-tree.component.css'],
  providers: [
    {provide: MAT_TOOLTIP_DEFAULT_OPTIONS, useValue: myCustomTooltipDefaults}
  ]
})
export class BookmarkTreeComponent implements OnInit {

  // View Model
  isWorking$: Observable<boolean>;
  alerts$: Observable<Alert[]>


  // Tree
  treeControl: NestedTreeControl<FolderItemNode>;
  dataSource: MatTreeNestedDataSource<FolderItemNode>;

  // Context Menu
  @ViewChild(MatMenuTrigger)
  contextMenu!: MatMenuTrigger;
  contextMenuPosition = { x: '0px', y: '0px'};


  private _getChildren = (node : FolderItemNode) => of(node.children);
  hasNestedChild = (_: number, node: FolderItemNode) => 0 < node.children.length;
  isInEditMode = (_:number, node: FolderItemNode) => node.inEditMode;
  currentFolderName: string = "";


  @ViewChild('newFolderName')
  private theTreeRef!: ElementRef;

  constructor(
    private cd: ChangeDetectorRef,
    private  bf: BookmarxUsecases,
    private snackBar: MatSnackBar) {

    this.isWorking$ = this.bf.isWorking$();
    this.alerts$ = this.bf.getAlerts$();
    this.alerts$.subscribe(
      (nextAlertArray) => {
        for (let alert of nextAlertArray) {
          this.snackBar.open(alert.message, "Close", {duration: 3000});
        }
      }
    );

    this.treeControl = new NestedTreeControl<FolderItemNode>(this._getChildren);
    this.dataSource = new MatTreeNestedDataSource<FolderItemNode>();
    this.bf.getAllFolders$().subscribe(
      folders => (this.dataSource.data = folders)
    )
  }


  ngOnInit(): void {
    this.bf.loadAllFolders();
  }


  onContextMenu(event: MouseEvent, item: FolderItemNode) {
    event.preventDefault();
    this.contextMenuPosition.x = event.clientX + 'px';
    this.contextMenuPosition.y = event.clientY + 'px';
    this.contextMenu.menuData = { 'item': item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }


  onContextMenuNewFolder(parentNode: FolderItemNode) {
    this.bf.addNewFolder(parentNode);
    this.treeControl.expand(parentNode);
    this.refreshTreeData();
  }


  onContextMenuDeleteFolder(node: FolderItemNode) {
    this.bf.deleteFolder(node);
    this.refreshTreeData();
  }


  onContextMenuRenameFolder(node: FolderItemNode) {
    this.currentFolderName = node.name;
    this.bf.setFolderToEditMode(node);
    this.refreshTreeData();
  }


  saveNewFolder(node: FolderItemNode, newFolderName: string) {
    // Empty name is not allowed
    if (newFolderName === '') {
      return;
    }
    node.name = newFolderName;
    this.bf.saveFolder(node);
    node.inEditMode = false;
    this.refreshTreeData()
  }


  refreshTreeData() {
    const data = this.dataSource.data;
    this.dataSource.data = [];
    this.dataSource.data = data;
  }

  onClickTreeNode($event: MouseEvent, node: FolderItemNode) {
    $event.stopPropagation();
    console.log("Clicked: " + node.name, node.assignedBookmarkIds);
    this.bf.setSelectedFolder(node);
  }
}
