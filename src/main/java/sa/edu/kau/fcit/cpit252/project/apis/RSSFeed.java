package sa.edu.kau.fcit.cpit252.project.apis;

import java.net.URL;
import java.util.ArrayList;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rometools.rome.feed.synd.SyndFeed;


import sa.edu.kau.fcit.cpit252.project.apis.source.FeedSource;
import sa.edu.kau.fcit.cpit252.project.news.Article;


public class RSSFeed extends Feed{
    FeedSource source;
    static Logger logger = LoggerFactory.getLogger("sa.edu.kau.fcit.cpit252.project.apis.RSSFeed");
    public RSSFeed(FeedSource source) {
            this.source = source;

    }
    public String getFeedName() {
        return source.getName();
    }
    public ArrayList<Article> run() {
        ArrayList<Article> articles = new ArrayList<Article>();

        try {
            logger.info("RSSFeed started running...");
            SyndFeedInput input = new SyndFeedInput();
            logger.debug("Feed URL: " + this.source.getUrl());
            SyndFeed feed = input.build(new XmlReader(this.source.getUrl()));
            logger.info("Successfully fetched RSS feed: " + feed.getTitle());
            int articles_received = 0;
            for (SyndEntry entry : feed.getEntries()) {
                logger.debug("Processing entry: " + entry.getTitle());
                Article.Builder articleBuilder = new Article.Builder(entry.getTitle());
                Article article = articleBuilder
                        .withAuthor(entry.getAuthor())
                        .withDate(entry.getPublishedDate())
                        .withURL(entry.getLink())
                        .withDescription(String.valueOf(entry.getDescription()))
                        .withPriority(0)
                        .build();

                articles.add(article);
                articles_received++;
            }
            logger.debug("RSSFEED: successfully received " + articles_received + " articles!");
            System.out.println("RSSFEED: successfully received " + articles_received + " articles!");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("RSSFeed run() completed for source: " + this.source.getUrl());
        return articles;
    }
}
