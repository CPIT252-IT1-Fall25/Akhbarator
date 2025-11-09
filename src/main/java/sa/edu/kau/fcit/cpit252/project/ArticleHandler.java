package sa.edu.kau.fcit.cpit252.project;

abstract class ArticleHandler {
    protected ArticleHandler next;
    protected abstract Article processArticle(Article article);
}
