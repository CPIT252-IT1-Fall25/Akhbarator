package sa.edu.kau.fcit.cpit252.project.viewer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;

public class Viewer {

    private final Stage stage;
    private final Feed[] feeds;

    public Viewer(Stage stage, Feed[] feeds) {
        this.stage = stage;
        this.feeds = feeds;
    }

    public void run() {
        String userName = System.getProperty("user.name");
        if (userName == null) userName = "";

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label welcome = new Label("Welcome " + userName + "!");
        welcome.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        root.getChildren().add(welcome);

        for (Feed feed : feeds) {

            TitledPane pane = new TitledPane();
            pane.setText(feed.getFeedName());
            pane.setExpanded(false);

            ListView<Article> list = new ListView<>();
            ArrayList<Article> articles = feed.run();
            list.getItems().addAll(articles);

            // clicking an article opens its link
            list.setOnMouseClicked(e -> {
                Article a = list.getSelectionModel().getSelectedItem();
                if (a != null && a.url != null) {
                    try {
                        Desktop.getDesktop().browse(new URI(a.url));
                    } catch (Exception ignored) {}
                }
            });

            pane.setContent(list);
            root.getChildren().add(pane);
        }

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);

        stage.setScene(new Scene(scroll, 1300, 1000));
        stage.setTitle("Akhbarator");
        stage.show();
    }
}
