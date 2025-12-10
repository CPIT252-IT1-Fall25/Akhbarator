package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;
import java.util.Comparator;

public class ByDateOrdering implements OrderingStrategy {
    @Override
    public ArrayList<Article> order(ArrayList<Article> articles) {
        // Sort by date in ascending order (oldest first)
        articles.sort(Comparator.comparing(Article::getDate,
                Comparator.nullsFirst(Comparator.naturalOrder())));
        return articles;
    }
}
