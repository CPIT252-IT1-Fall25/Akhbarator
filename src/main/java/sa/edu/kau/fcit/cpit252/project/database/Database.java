package sa.edu.kau.fcit.cpit252.project.database;

import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;

public interface Database {
    void save(Article a);
    ArrayList<Article> getAll();
}

