package sa.edu.kau.fcit.cpit252.project.apis.source;

import java.net.MalformedURLException;
import java.net.URL;

public class NYTSource implements FeedSource {
    String name = "The New York times";
    URL url = new URL("http://rss.cnn.com/rss/cnn_topstories.rss");

    public NYTSource() throws MalformedURLException {}

    @Override
    public String getName() {return this.name;}
    @Override
    public URL getUrl() {return this.url;}
}
