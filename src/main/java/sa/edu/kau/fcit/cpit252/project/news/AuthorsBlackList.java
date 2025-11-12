package sa.edu.kau.fcit.cpit252.project.news;

import sa.edu.kau.fcit.cpit252.project.news.Article;
import sa.edu.kau.fcit.cpit252.project.news.ArticleHandler;

import java.util.ArrayList;

public class AuthorsBlackList extends ArticleHandler {
    ArrayList<String> blacklist = new ArrayList<String>();
    @Override
    protected Article processArticle(Article article) {
        return article;
    }
}
