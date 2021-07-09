package schwarz.it.ae.bookmarx.core.domain;

public class FirefoxExportNode {



    public String guid;
    public String title;
    public int index;
    public long dateAdded;
    public long lastModified;
    public int id;
    public int typeCode; // 1 = url, 2 = folder
    public String charset; // UTF-8, windows-1252
    public String tags; // tag1,tag2,...
    public String keyword;
    public String type;
    public String root;
    public String iconuri;
    public String postData; // ???


    // Either "children" oder "uri" is existent
    public FirefoxExportNode[] children;
    public String uri;


}
