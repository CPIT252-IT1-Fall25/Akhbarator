package sa.edu.kau.fcit.cpit252.project.news;

import sa.edu.kau.fcit.cpit252.project.news.Article;
import sa.edu.kau.fcit.cpit252.project.news.ArticleHandler;

public class AuthorsBlackList extends ArticleHandler {
    @Override
    protected Article processArticle(Article article) {
        return article;
    }
}
