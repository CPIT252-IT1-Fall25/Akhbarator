package sa.edu.kau.fcit.cpit252.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import sa.edu.kau.fcit.cpit252.project.news.Article;
import sa.edu.kau.fcit.cpit252.project.Ordering.*;
import sa.edu.kau.fcit.cpit252.project.apis.source.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Comprehensive Unit Tests for Akbarator
 */
public class AppTest {

    // ============================================
    // Article Tests
    // ============================================
    @Nested
    @DisplayName("Article Tests")
    class ArticleTests {

        private Date testDate;

        @BeforeEach
        public void setUp() {
            testDate = new Date();
        }

        @Test
        public void testArticleBuilderWithTitleOnly() {
            Article article = new Article.Builder("Test Title").build();

            assertEquals("Test Title", article.getTitle());
            assertNull(article.getDate());
            assertEquals(0.0, article.getRelevance());
        }

        @Test
        public void testArticleBuilderWithAllFields() {
            Article article = new Article.Builder("News Title", "John Doe")
                    .withDate(testDate)
                    .withURL("https://example.com")
                    .withDescription("Test description")
                    .withPriority(5)
                    .withRelevance(10.5)
                    .withBody("<p>Article body</p>")
                    .build();

            assertEquals("News Title", article.getTitle());
            assertEquals("John Doe", article.author);
            assertEquals(testDate, article.getDate());
            assertEquals("https://example.com", article.getUrl());
            assertEquals("Test description", article.description);
            assertEquals(5, article.getPriority());
            assertEquals(10.5, article.getRelevance());
            assertEquals("<p>Article body</p>", article.getBody());
        }

        @Test
        public void testArticleLazyLoading() {
            String[] bodyContent = {"Not loaded"};

            Article article = new Article.Builder("Test")
                    .withSupplier(() -> {
                        bodyContent[0] = "Loaded content";
                        return "Loaded content";
                    })
                    .build();

            assertEquals("Not loaded", bodyContent[0]);
            String body = article.getBody();
            assertEquals("Loaded content", body);
            assertEquals("Loaded content", bodyContent[0]);
        }

    }

    // ============================================
    // Ordering Strategy Tests
    // ============================================
    @Nested
    @DisplayName("Ordering Strategy Tests")
    class OrderingStrategyTests {

        private ArrayList<Article> articles;
        private Date oldDate;
        private Date middleDate;
        private Date newDate;

        @BeforeEach
        public void setUp() {
            oldDate = new Date(1000000000000L);
            middleDate = new Date(1500000000000L);
            newDate = new Date(1700000000000L);

            articles = new ArrayList<>();

            Article article1 = new Article.Builder("Zebra Article", "Author A")
                    .withDate(newDate)
                    .withRelevance(5.0)
                    .build();

            Article article2 = new Article.Builder("Apple Article", "Author B")
                    .withDate(oldDate)
                    .withRelevance(10.0)
                    .build();

            Article article3 = new Article.Builder("Middle Article", "Author C")
                    .withDate(middleDate)
                    .withRelevance(7.5)
                    .build();

            articles.add(article1);
            articles.add(article2);
            articles.add(article3);
        }

        @Test
        public void testDefaultOrdering_NewestFirst() {
            OrderingStrategy strategy = new DefaultOrdering();
            ArrayList<Article> result = strategy.order(articles);

            assertEquals("Zebra Article", result.get(0).getTitle());
            assertEquals("Middle Article", result.get(1).getTitle());
            assertEquals("Apple Article", result.get(2).getTitle());
        }

        @Test
        public void testByDateOrdering_OldestFirst() {
            OrderingStrategy strategy = new ByDateOrdering();
            ArrayList<Article> result = strategy.order(articles);

            assertEquals("Apple Article", result.get(0).getTitle());
            assertEquals("Middle Article", result.get(1).getTitle());
            assertEquals("Zebra Article", result.get(2).getTitle());
        }

        @Test
        public void testByTitleOrdering_Alphabetical() {
            OrderingStrategy strategy = new ByTitleOrdering();
            ArrayList<Article> result = strategy.order(articles);

            assertEquals("Apple Article", result.get(0).getTitle());
            assertEquals("Middle Article", result.get(1).getTitle());
            assertEquals("Zebra Article", result.get(2).getTitle());
        }

        @Test
        public void testByRelevanceOrdering_HighestFirst() {
            OrderingStrategy strategy = new ByRelevanceOrdering();
            ArrayList<Article> result = strategy.order(articles);

            assertEquals(10.0, result.get(0).getRelevance());
            assertEquals(7.5, result.get(1).getRelevance());
            assertEquals(5.0, result.get(2).getRelevance());
        }

        @Test
        public void testOrderingWithNullDates() {
            Article nullDateArticle = new Article.Builder("No Date").build();
            articles.add(nullDateArticle);

            OrderingStrategy strategy = new DefaultOrdering();
            ArrayList<Article> result = strategy.order(articles);

            assertEquals("No Date", result.get(result.size() - 1).getTitle());
        }

        @Test
        public void testOrderingWithNullTitles() {
            Article nullTitleArticle = new Article.Builder(null).build();
            articles.add(nullTitleArticle);

            OrderingStrategy strategy = new ByTitleOrdering();
            ArrayList<Article> result = strategy.order(articles);

            assertNotNull(result);
            assertEquals(4, result.size());
        }
    }

    // ============================================
    // OrderingContext Tests
    // ============================================
    @Nested
    @DisplayName("OrderingContext Tests")
    class OrderingContextTests {

        private OrderingContext context;
        private ArrayList<Article> articles;

        @BeforeEach
        public void setUp() {
            articles = new ArrayList<>();

            Article article1 = new Article.Builder("Zebra")
                    .withDate(new Date(1700000000000L))
                    .build();

            Article article2 = new Article.Builder("Apple")
                    .withDate(new Date(1000000000000L))
                    .build();

            articles.add(article1);
            articles.add(article2);
        }

        @Test
        public void testContextWithDefaultStrategy() {
            context = new OrderingContext(new DefaultOrdering());
            ArrayList<Article> result = context.executeStrategy(articles);

            assertEquals("Zebra", result.get(0).getTitle());
        }

        @Test
        public void testContextStrategySwitch() {
            context = new OrderingContext(new DefaultOrdering());
            ArrayList<Article> result1 = context.executeStrategy(articles);
            assertEquals("Zebra", result1.get(0).getTitle());

            context.setStrategy(new ByTitleOrdering());
            ArrayList<Article> result2 = context.executeStrategy(articles);
            assertEquals("Apple", result2.get(0).getTitle());
        }
    }

    // ============================================
    // FeedSource Tests
    // ============================================
    @Nested
    @DisplayName("FeedSource Tests")
    class FeedSourceTests {

        @Test
        public void testCNNSource() throws MalformedURLException {
            FeedSource cnn = new CNNSource();

            assertEquals("CNN", cnn.getName());
            assertNotNull(cnn.getUrl());
            assertTrue(cnn.getUrl().toString().contains("cnn"));
        }

        @Test
        public void testNYTSource() throws MalformedURLException {
            FeedSource nyt = new NYTSource();

            assertEquals("The New York times", nyt.getName());
            assertNotNull(nyt.getUrl());
            assertTrue(nyt.getUrl().toString().contains("nytimes"));
        }

        @Test
        public void testSourceURLsAreValid() throws MalformedURLException {
            FeedSource cnn = new CNNSource();
            FeedSource nyt = new NYTSource();

            assertTrue(cnn.getUrl().getProtocol().startsWith("http"));
            assertTrue(nyt.getUrl().getProtocol().startsWith("http"));
        }
    }

    // ============================================
    // Search Algorithm Tests
    // ============================================
    @Nested
    @DisplayName("Search Algorithm Tests")
    class SearchAlgorithmTests {

        private ArrayList<Article> articles;

        @BeforeEach
        public void setUp() {
            articles = new ArrayList<>();

            Article article1 = new Article.Builder("Bitcoin Price Surges")
                    .withDescription("Cryptocurrency market sees growth")
                    .build();

            Article article2 = new Article.Builder("Stock Market Update")
                    .withDescription("Wall Street performance")
                    .build();

            Article article3 = new Article.Builder("Technology News")
                    .withDescription("Bitcoin technology advances")
                    .build();

            articles.add(article1);
            articles.add(article2);
            articles.add(article3);
        }

        @Test
        public void testSearchByTitle() {
            String keyword = "bitcoin";
            ArrayList<Article> results = searchArticles(keyword.toLowerCase());

            assertEquals(2, results.size());
            assertTrue(results.get(0).getRelevance() > 0);
        }

        @Test
        public void testSearchByDescription() {
            String keyword = "wall street";
            ArrayList<Article> results = searchArticles(keyword.toLowerCase());

            assertEquals(1, results.size());
            assertEquals("Stock Market Update", results.get(0).getTitle());
        }

        @Test
        public void testSearchRelevanceScoring() {
            String keyword = "bitcoin";
            ArrayList<Article> results = searchArticles(keyword.toLowerCase());

            Article titleMatch = results.stream()
                    .filter(a -> a.getTitle().toLowerCase().contains("bitcoin"))
                    .findFirst()
                    .orElse(null);

            Article descMatch = results.stream()
                    .filter(a -> !a.getTitle().toLowerCase().contains("bitcoin"))
                    .findFirst()
                    .orElse(null);

            if (titleMatch != null && descMatch != null) {
                assertTrue(titleMatch.getRelevance() > descMatch.getRelevance());
            }
        }

        @Test
        public void testEmptySearchReturnsAll() {
            ArrayList<Article> results = searchArticles("");
            assertEquals(3, results.size());
        }

        @Test
        public void testMultipleKeywords() {
            String keywords = "bitcoin technology";
            ArrayList<Article> results = searchArticles(keywords.toLowerCase());

            assertTrue(results.size() > 0);
        }

        private ArrayList<Article> searchArticles(String keywords) {
            if (keywords.isEmpty()) {
                return new ArrayList<>(articles);
            }

            String[] terms = keywords.toLowerCase().split("\\s+");
            ArrayList<Article> result = new ArrayList<>();

            for (Article article : articles) {
                double score = 0;
                String t = article.title != null ? article.title.toLowerCase() : "";
                String d = article.description != null ? article.description.toLowerCase() : "";

                for (String term : terms) {
                    if (t.contains(term)) score += 30;
                    if (d.contains(term)) score += 10;
                }

                if (score > 0) {
                    article.relevance = score;
                    result.add(article);
                }
            }

            return result;
        }
    }
}