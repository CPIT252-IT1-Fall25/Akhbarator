package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ByTitleOrdering implements OrderingStrategy {
    @Override
    public ArrayList<Article> order(ArrayList<Article> articles) {
        return articles.stream()
                .sorted(Comparator.comparing(Article::getTitle, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}