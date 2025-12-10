package sa.edu.kau.fcit.cpit252.project;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

import sa.edu.kau.fcit.cpit252.project.apis.DatabaseFeed;
import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.apis.RSSFeed;
import sa.edu.kau.fcit.cpit252.project.apis.extractor.GuardianExtractor;
import sa.edu.kau.fcit.cpit252.project.apis.source.CNNSource;
import sa.edu.kau.fcit.cpit252.project.apis.source.NYTSource;
import sa.edu.kau.fcit.cpit252.project.viewer.Viewer;


public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Feed guardianfeed = new GuardianExtractor();
        Feed NYTfeed = new RSSFeed(new NYTSource());
        Feed CNNfeed = new RSSFeed(new CNNSource());
        Feed DBfeed = new DatabaseFeed();
        Feed[] feeds = new Feed[]{guardianfeed, NYTfeed, CNNfeed, DBfeed};

        new Viewer(stage, feeds).run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
