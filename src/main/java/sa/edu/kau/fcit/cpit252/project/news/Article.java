package sa.edu.kau.fcit.cpit252.project.news;

import java.util.Date;

public class Article {
    public String title;
    public String author;
    public Date date;
    public String url;
    public String content;
    public String description;
    public int priority;

    private Article(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.date = builder.date;
        this.url = builder.url;
        this.content = builder.content;
        this.description = builder.description;
        this.priority = 0;
    }

    @Override
    public String toString() {
        return "• " + title + " (" + author + ")";
    }

    static public class Builder{
        public String title;
        public String author;
        public Date date;
        public String url;
        public String content;
        public String description;
        public int priority;
        public Builder(String title, String author) {
            this.title = title;
            this.author = author;
        }
        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }
        public Builder withURL(String url) {
            this.url = url;
            return this;
        }
        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }
        public Builder withPriority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
