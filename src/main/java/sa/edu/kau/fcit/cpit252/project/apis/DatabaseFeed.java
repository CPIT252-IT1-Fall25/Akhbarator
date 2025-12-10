package sa.edu.kau.fcit.cpit252.project.apis;

import sa.edu.kau.fcit.cpit252.project.database.Database;
import sa.edu.kau.fcit.cpit252.project.database.Sqlite;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.util.ArrayList;

public class DatabaseFeed extends Feed {
    Database store = new Sqlite();

    @Override
    public String getFeedName() {
        return "Local";
    }

    @Override
    public ArrayList<Article> run() {
        return store.getAll();
    }
}
