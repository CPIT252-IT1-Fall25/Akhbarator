package sa.edu.kau.fcit.cpit252.project.apis.source;

import java.net.MalformedURLException;
import java.net.URL;

public class CNNSource implements FeedSource {
    URL url = new URL("http://rss.cnn.com/rss/cnn_topstories.rss");

    public CNNSource() throws MalformedURLException {
    }

    public URL getUrl() {
        return this.url;
    }
}
