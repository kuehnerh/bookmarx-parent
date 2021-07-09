export interface JsonTreeFolder {
  id: string;
  name: string;
  children: JsonTreeFolder[];
  assignedBookmarkIds: string[];
}
