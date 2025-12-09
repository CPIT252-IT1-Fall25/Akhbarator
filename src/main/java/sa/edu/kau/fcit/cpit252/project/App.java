package sa.edu.kau.fcit.cpit252.project;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.stage.Stage;
import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.apis.RSSFeed;
import sa.edu.kau.fcit.cpit252.project.apis.extractor.GuardianExtractor;
import sa.edu.kau.fcit.cpit252.project.apis.source.CNNSource;
import sa.edu.kau.fcit.cpit252.project.apis.source.NYTSource;
import sa.edu.kau.fcit.cpit252.project.viewer.Viewer;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, UnirestException, InterruptedException {

        Feed guardianfeed = new GuardianExtractor();
        Feed NYTfeed = new RSSFeed(new NYTSource());
        Feed CNNfeed = new RSSFeed(new CNNSource());
        Feed[] feeds = new Feed[]{guardianfeed, NYTfeed, CNNfeed};

        new Viewer(stage, feeds).run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
