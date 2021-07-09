export class FolderItemNode {
  public uuid: string;
  public name: string;
  public parentId: string;
  public children: FolderItemNode[];
  public assignedBookmarkIds: string[];

  public inEditMode: boolean = false;

  public static readonly NULL = new FolderItemNode("", "", "", [], [], false);

  constructor(uuid: string, name: string, parentId: string, children: FolderItemNode[], assignedBookmarkIds: string[], inEditMode: boolean) {
    this.uuid = uuid;
    this.name = name;
    this.parentId = parentId;
    this.children = children;
    this.assignedBookmarkIds = assignedBookmarkIds;
    this.inEditMode = inEditMode;
  }


  hasChildren(): boolean {
    return (0 < this.children.length);
  }
}
