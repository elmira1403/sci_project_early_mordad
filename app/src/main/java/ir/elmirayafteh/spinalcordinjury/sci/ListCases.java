package ir.elmirayafteh.spinalcordinjury.sci;


public class ListCases {
    public String title;
    public String poster;
    public String file;
    public String text;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getFile() { return file; }

    public String getImage() {
        return poster;
    }


    public ListCases(String title, String text, String poster, String file) {
        this.title = title;
        this.poster = poster;
        this.file = file;
        this.text = text;
    }
}
