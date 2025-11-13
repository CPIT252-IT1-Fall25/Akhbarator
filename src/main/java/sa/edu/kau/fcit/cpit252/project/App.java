package sa.edu.kau.fcit.cpit252.project;

import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.apis.RSSFeed;
import sa.edu.kau.fcit.cpit252.project.apis.source.CNNSource;
import sa.edu.kau.fcit.cpit252.project.apis.source.FeedSource;
import sa.edu.kau.fcit.cpit252.project.apis.source.NYTSource;
import sa.edu.kau.fcit.cpit252.project.viewer.Viewer;


import java.net.MalformedURLException;
import java.net.URL;


public class App {
    public static void main(String[] args) throws MalformedURLException {
        FeedSource nyt = new NYTSource();
        FeedSource cnn = new CNNSource();
        Feed feed = new RSSFeed(nyt);
        Viewer viewer = new Viewer();
        viewer.run(feed);
    }
}
