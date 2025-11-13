package sa.edu.kau.fcit.cpit252.project.apis;
import sa.edu.kau.fcit.cpit252.project.apis.source.FeedSource;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;

public abstract class Feed {
    protected FeedSource source;
    Feed(FeedSource source) {
        this.source = source;
    }
    public String getFeedName() {return source.getName();}
    abstract public ArrayList<Article> run();
}
