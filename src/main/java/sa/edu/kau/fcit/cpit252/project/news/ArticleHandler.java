package sa.edu.kau.fcit.cpit252.project.news;

abstract class ArticleHandler {
    protected ArticleHandler next;
    protected abstract Article processArticle(Article article);
}
