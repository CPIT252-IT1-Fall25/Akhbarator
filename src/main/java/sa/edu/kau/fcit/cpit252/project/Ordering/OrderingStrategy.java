package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;

public interface OrderingStrategy {
    ArrayList<Article> order(ArrayList<Article> articles);

}


