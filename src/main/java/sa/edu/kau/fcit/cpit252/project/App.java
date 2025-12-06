package sa.edu.kau.fcit.cpit252.project;

import javafx.application.Application;
import javafx.stage.Stage;
import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.apis.RSSFeed;
import sa.edu.kau.fcit.cpit252.project.apis.source.CNNSource;
import sa.edu.kau.fcit.cpit252.project.apis.source.FeedSource;
import sa.edu.kau.fcit.cpit252.project.apis.source.NYTSource;
import sa.edu.kau.fcit.cpit252.project.viewer.Viewer;

import java.net.MalformedURLException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws MalformedURLException {
        FeedSource nyt = new NYTSource();
        FeedSource cnn = new CNNSource();
        Feed NYTfeed = new RSSFeed(nyt);
        Feed CNNfeed = new RSSFeed(cnn);
        Feed[] feeds = new Feed[]{NYTfeed, CNNfeed};

        new Viewer(stage, feeds).run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
