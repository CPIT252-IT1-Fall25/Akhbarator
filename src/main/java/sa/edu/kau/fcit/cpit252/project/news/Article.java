package sa.edu.kau.fcit.cpit252.project.news;

import java.util.Date;
import java.util.Locale;

public class Article {
    public String title;
    public String author;
    public Date date;
    public String url;
    public String body;
    public String description;
    public int priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    private Article(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.date = builder.date;
        this.url = builder.url;
        this.body = builder.body;
        this.description = builder.description;
        this.priority = 0;
    }

    @Override
    public String toString() {
        String result = title;
        if (this.author != null) {
            title = title + " by author";
            if (this.author.toLowerCase().contains("and"))
                title = title + "s";
            title = title + " ";
        }
        return result;
    }

    static public class Builder{
        public String title;
        public String author;
        public Date date;
        public String url;
        public String body;
        public String description;
        public int priority;
        public Builder(String title) {
            this.title = title;
        }

        public Builder withAuthor(String author) {
            this.author = author;
            return this;
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

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
