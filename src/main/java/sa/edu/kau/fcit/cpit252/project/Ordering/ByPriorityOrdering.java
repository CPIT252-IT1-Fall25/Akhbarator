package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.Arrays;
import java.util.Comparator;

class ByPriorityOrdering implements OrderingStrategy {
    @Override
    public Article[] order(Article[] articles) {
       // Arrays.sort(articles, Comparator.comparingInt(Article::getPriority).reversed());  <---- after fishing the builder pattern
        return articles;
    }
}
