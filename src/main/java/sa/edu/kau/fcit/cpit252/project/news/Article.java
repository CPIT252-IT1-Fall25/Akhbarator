package sa.edu.kau.fcit.cpit252.project.news;

import java.util.Date;
import java.util.Locale;
import java.util.function.Supplier;

public class Article {
    private String title;
    private final String author;
    private final Date date;
    private final String url;
    private String body;
    private final Supplier<String> bodySupplier;
    public String description;
    public int priority;


    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        boolean canSupplies = body == null && bodySupplier != null;
        if (canSupplies) {
            body = bodySupplier.get(); // fetch lazily
        }
        return body;
    }

    private Article(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.date = builder.date;
        this.url = builder.url;
        this.body = builder.body;
        this.bodySupplier = builder.bodySupplier;
        this.description = builder.description;
        this.priority = 0;
    }

    @Override
    public String toString() {
        if (author == null || author.isBlank()) {
            return title;
        }

        StringBuilder result = new StringBuilder(title);
        result.append(" by ").append(author);

        if (author.toLowerCase().contains("and")) {
            result.append("s");
        }

        return result.toString();
    }

    static public class Builder{
        public String title;
        public String author;
        public Date date;
        public String url;
        public String body;
        public Supplier<String> bodySupplier;
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

        public Builder withSupplier(Supplier<String> supplier) {
            this.bodySupplier = supplier;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
