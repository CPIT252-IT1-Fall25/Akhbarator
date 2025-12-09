package sa.edu.kau.fcit.cpit252.project.viewer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

public class Viewer {

    private final Stage stage;
    private final Feed[] feeds;

    public Viewer(Stage stage, Feed[] feeds) {
        this.stage = stage;
        this.feeds = feeds;
    }

    public void run() {
        showMainPage();
    }

    private void showMainPage() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        String userName = System.getProperty("user.name");
        if (userName == null) userName = "";

        Label welcome = new Label("Welcome " + userName + "!");
        welcome.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        root.getChildren().add(welcome);

        for (Feed feed : feeds) {
            Button btn = new Button(feed.getFeedName());
            btn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            btn.setOnAction(e -> showArticlesPage(feed));
            root.getChildren().add(btn);
        }

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);

        Scene scene = new Scene(scroll, 1300, 1000);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/theme.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Akhbarator");
        stage.show();
    }

    private void showArticlesPage(Feed feed) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label title = new Label(feed.getFeedName());
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Button back = new Button("← Back");
        back.setStyle("-fx-font-size: 16px;");
        back.setOnAction(e -> showMainPage());

        ListView<Article> list = new ListView<>();
        ArrayList<Article> articles = feed.run();
        list.getItems().addAll(articles);

        list.setOnMouseClicked(e -> {
            Article article = list.getSelectionModel().getSelectedItem();
            if (article.body != null) {
                showHtmlArticle(article);
            } else if (article.url != null) {
                try {
                    Desktop.getDesktop().browse(new URI(article.url));
                } catch (Exception ignored) {}
            }
        });

        root.getChildren().addAll(back, title, list);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);

        stage.getScene().setRoot(scroll);
    }

    private void showHtmlArticle(Article article) {
        Stage window = new Stage();

        WebView view = new WebView();
        var engine = view.getEngine();

        String css = loadResourceText("/webview_theme.css");
        String html =
                """
                <html>
                <head>
                    <style>
                """ + css + """
                </style>
            </head>
            <body>
            """ + article.body + """
            </body>
            </html>
            """;

        engine.loadContent(html);

        Scene scene = new Scene(view, 900, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/theme.css")).toExternalForm());
        window.setScene(scene);
        window.setTitle(article.title);
        window.show();
    }

    private String loadResourceText(String path) {
        try (var stream = getClass().getResourceAsStream(path)) {
            assert stream != null;
            return new String(stream.readAllBytes());
        } catch (Exception e) {
            return "";
        }
    }
}