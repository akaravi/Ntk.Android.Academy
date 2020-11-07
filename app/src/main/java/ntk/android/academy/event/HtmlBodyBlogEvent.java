package ntk.android.academy.event;

public class HtmlBodyBlogEvent {

    private final String Html;

    public HtmlBodyBlogEvent(String m) {
        this.Html = m;
    }

    public String GetMessage() {
        return Html;
    }
}
