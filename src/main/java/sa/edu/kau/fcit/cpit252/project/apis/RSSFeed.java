package sa.edu.kau.fcit.cpit252.project.apis;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.net.URL;
import java.util.ArrayList;

public class RSSFeed extends Feed{
    public RSSFeed(URL url) {
        super(url);
    }
    public ArrayList<Article> run() {
        ArrayList<Article> articles = new ArrayList<Article>();

        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(this.url));

            for (SyndEntry entry : feed.getEntries()) {
                Article.Builder articleBuilder = new Article.Builder(entry.getTitle(), entry.getAuthor());
                Article article = articleBuilder.
                        withDate(entry.getPublishedDate())
                        .withURL(entry.getLink())
                        .withPriority(0)
                        .build();
                articles.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
}
