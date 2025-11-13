package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.Arrays;
import java.util.Comparator;

class ByRelevanceOrdering implements OrderingStrategy {
    @Override
    public Article[] order(Article[] articles) {
      //  Arrays.sort(articles, Comparator.comparingDouble(Article::getRelevance).reversed()); <-- after pattern
        return articles;
    }
}
