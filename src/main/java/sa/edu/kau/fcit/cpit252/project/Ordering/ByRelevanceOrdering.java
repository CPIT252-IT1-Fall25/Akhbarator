package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;

public class ByRelevanceOrdering implements OrderingStrategy {
    @Override
    public ArrayList<Article> order(ArrayList<Article> articles) {
        return articles;
    }
}
