package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;

public class DefaultOrdering implements OrderingStrategy {
    @Override
    public ArrayList<Article> order(ArrayList<Article> articles) {
        // Arrays.sort(articles, Comparator.comparing(Article::     (COMPARE WITH DATA HERE)    ));
        return articles;
    }
}
