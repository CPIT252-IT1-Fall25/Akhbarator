package sa.edu.kau.fcit.cpit252.project.apis;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;

public abstract class Feed {
    abstract public String getFeedName();
    abstract public ArrayList<Article> run();
}
