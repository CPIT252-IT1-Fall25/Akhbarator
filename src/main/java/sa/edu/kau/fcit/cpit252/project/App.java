package sa.edu.kau.fcit.cpit252.project;

import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.apis.RSSFeed;
import sa.edu.kau.fcit.cpit252.project.viewer.Viewer;

import java.net.MalformedURLException;
import java.net.URL;


public class App {
    public static void main(String[] args) throws MalformedURLException {
        Feed feed = new RSSFeed(new URL("http://rss.cnn.com/rss/cnn_topstories.rss"));
        Viewer viewer = new Viewer();
        viewer.run(feed);
    }
}
