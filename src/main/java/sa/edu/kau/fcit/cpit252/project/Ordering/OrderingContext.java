package sa.edu.kau.fcit.cpit252.project.Ordering;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;

public class OrderingContext {
    private OrderingStrategy strategy;

    public OrderingContext(OrderingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(OrderingStrategy strategy) {
        this.strategy = strategy;
    }

    public ArrayList<Article> executeStrategy(ArrayList<Article> articles) {
        return strategy.order(articles);
    }
}