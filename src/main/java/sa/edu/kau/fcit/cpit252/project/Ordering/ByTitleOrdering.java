package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;
import java.util.Comparator;

public class ByTitleOrdering implements OrderingStrategy {
    @Override
    public ArrayList<Article> order(ArrayList<Article> articles) {
        // Sort by title alphabetically
        articles.sort(Comparator.comparing(Article::getTitle,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
        return articles;
    }
}