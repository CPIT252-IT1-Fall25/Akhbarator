package sa.edu.kau.fcit.cpit252.project.apis.source;

import java.net.MalformedURLException;
import java.net.URL;

public class CNNSource implements FeedSource {
    String name = "CNN";
    URL url = new URL("http://rss.cnn.com/rss/cnn_topstories.rss");

    public CNNSource() throws MalformedURLException {}

    @Override
    public String getName() {return this.name;}
    @Override
    public URL getUrl() {return this.url;}
}

