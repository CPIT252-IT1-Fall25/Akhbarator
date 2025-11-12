package sa.edu.kau.fcit.cpit252.project.apis;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.net.URL;
import java.util.ArrayList;

public abstract class Feed {
    protected URL url;
    Feed(URL url) {
        this.url = url;
    }
    abstract public ArrayList<Article> run();
}
