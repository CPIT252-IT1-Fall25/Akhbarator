package sa.edu.kau.fcit.cpit252.project.apis.source;

import java.net.MalformedURLException;
import java.net.URL;

public interface FeedSource {
    URL url = null;

    public abstract URL getUrl();
}
