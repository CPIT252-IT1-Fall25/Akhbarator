package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;
import java.util.Comparator;
// This class implements the ordering strategy for articles by relevance. For future work now there is no way to order articles by relevance.
public class ByRelevanceOrdering implements OrderingStrategy {
    @Override
    public ArrayList<Article> order(ArrayList<Article> articles) {
        articles.sort(Comparator.comparingDouble(Article::getRelevance).reversed());
        return articles;
    }
}