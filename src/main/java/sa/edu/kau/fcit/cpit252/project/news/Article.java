package sa.edu.kau.fcit.cpit252.project.news;

import java.util.Date;

public class Article {
    public String author;
    public Date date;
    public String content;
    public int priority;

    public Article(String author, Date date, String content, int priority) {
        this.author = author;
        this.date = date;
        this.content = content;
        this.priority = priority;
    }
}
