package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;
import java.util.Comparator;

public class DefaultOrdering implements OrderingStrategy {
    @Override
    public ArrayList<Article> order(ArrayList<Article> articles) {
        // Sort by date in descending order (newest first)
        articles.sort(Comparator.comparing(Article::getDate,
                Comparator.nullsLast(Comparator.reverseOrder())));
        return articles;
    }
}