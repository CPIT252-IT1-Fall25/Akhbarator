package sa.edu.kau.fcit.cpit252.project.news;

import java.util.Date;

public class Article implements Cloneable {
    public String title;
    public String author;
    public Date date;
    public String url;
    public String content;
    public String description;
    public int priority;
    public double relevance; // Added for relevance ordering
    public String body; // For HTML content
    private final java.util.function.Supplier<String> bodySupplier; // Lazy loading for body

    private Article(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.date = builder.date;
        this.url = builder.url;
        this.content = builder.content;
        this.description = builder.description;
        this.priority = builder.priority;
        this.relevance = builder.relevance;
        this.body = builder.body;
        this.bodySupplier = builder.bodySupplier;
    }

    // Getter methods for ordering
    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

    public double getRelevance() {
        return relevance;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        // Lazy load body if supplier is provided
        if (body == null && bodySupplier != null) {
            body = bodySupplier.get();
        }
        return body;
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

    @Override
    public Article clone() {
        try {
            Article clone = (Article) super.clone();
            clone.date = (Date)this.date.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    static public class Builder {
        public String title;
        public String author;
        public Date date;
        public String url;
        public String content;
        public String description;
        public int priority;
        public double relevance;
        public String body;
        public java.util.function.Supplier<String> bodySupplier;

        // Constructor with both title and author
        public Builder(String title, String author) {
            this.title = title;
            this.author = author;
            this.relevance = 0.0; // Default relevance
        }

        // Constructor with only title (author defaults to empty string)
        public Builder(String title) {
            this(title, "");
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

        public Builder withRelevance(Double relevance) {
            this.relevance = relevance;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withSupplier(java.util.function.Supplier<String> bodySupplier) {
            this.bodySupplier = bodySupplier;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}