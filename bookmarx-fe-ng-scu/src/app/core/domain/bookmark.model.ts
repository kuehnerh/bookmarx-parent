export class Bookmark {
  public id: string;
  public title: string;
  public url: string;
  public description: string;
  public favIconPath: string;
  public tags: string[];
  public createdAt: Date;
  public modifiedAt: Date;


  public static readonly NULL: Bookmark = new Bookmark("", "", "", "", "", [], new Date(), new Date());


  constructor(id: string, title: string, url: string, description: string, favIconPath: string, tags: string[], createdAt: Date, modifiedAt: Date) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.description = description;
    this.favIconPath =  favIconPath;
    this.tags = tags;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }



}
