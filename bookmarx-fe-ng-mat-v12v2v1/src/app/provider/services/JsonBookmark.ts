export interface JsonBookmark {
  id: string;
  title: string;
  url: string;
  description: string;
  favIconPath: string;
  tags: string[];
  createdAt: Date;
  modifiedAt: Date;
}
