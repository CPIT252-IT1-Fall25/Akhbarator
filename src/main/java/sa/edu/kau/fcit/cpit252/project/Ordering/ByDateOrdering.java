package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.Arrays;
import java.util.Comparator;

class ByDateOrdering implements OrderingStrategy {
    @Override
    public Article[] order(Article[] articles) {
        // Arrays.sort(articles, Comparator.comparing(Article::     (COMPARE WITH DATA HERE)    ));
        return articles;
    }
}
