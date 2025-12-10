package sa.edu.kau.fcit.cpit252.project.viewer;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import sa.edu.kau.fcit.cpit252.project.Ordering.*;
import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.news.Article;

public class Viewer {
    private final static Map<String, OrderingStrategy> orderingMap = Map.of(
            "Newest first", new DefaultOrdering(),
            "Oldest first", new ByDateOrdering(),
            "By relevance", new ByRelevanceOrdering(),
            "By title", new ByTitleOrdering()
    );
    private final Stage stage;
    private final Feed[] feeds;
    private ArrayList<Article> articles;
    private OrderingStrategy ordering = new DefaultOrdering();

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

        // Search box
        HBox searchBox = new HBox(10);
        Label searchLabel = new Label("Search:");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter keywords");
        searchField.setPrefWidth(250);
        Button searchBtn = new Button("Search");
        Button clearBtn = new Button("Clear");
        searchBox.getChildren().addAll(searchLabel, searchField, searchBtn, clearBtn);

        Label orderLabel = new Label("Order articles by:");
        ComboBox<String> orderCombo = new ComboBox<>();
        orderCombo.getItems().addAll(orderingMap.keySet());
        orderCombo.setValue("Newest first");

        root.getChildren().addAll(orderLabel, orderCombo);

        ListView<Article> list = new ListView<>();
        articles = feed.run();
        reorder();
        list.getItems().addAll(articles);

        list.setOnMouseClicked(e -> {
            Article article = list.getSelectionModel().getSelectedItem();
            if (article != null && article.getBody() != null) {
                showHtmlArticle(article);
            } else if (article != null && article.getUrl() != null) {
                try {
                    Desktop.getDesktop().browse(new URI(article.getUrl()));
                } catch (Exception ignored) {}
            }
        });

        searchBtn.setOnAction(e -> {
            String keywords = searchField.getText().trim();
            ArrayList<Article> filtered = searchArticles(keywords);
            list.getItems().setAll(filtered);
        });

        clearBtn.setOnAction(e -> {
            searchField.clear();
            for (Article a : articles) {
                a.relevance = 0.0;
            }
            list.getItems().setAll(reorder());
        });

        orderCombo.setOnAction(e -> {
            String selectedOrdering = orderCombo.getValue();
            this.ordering = orderingMap.get(selectedOrdering);
            ArrayList<Article> orderedArticles = reorder();
            list.getItems().setAll(orderedArticles);
        });

        root.getChildren().addAll(back, title, searchBox, list);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);

        stage.getScene().setRoot(scroll);
    }

    private ArrayList<Article> searchArticles(String keywords) {
        if (keywords.isEmpty()) {
            for (Article a : articles) {
                a.relevance = 0.0;
            }
            return reorder();
        }

        String[] terms = keywords.toLowerCase().split("\\s+");
        ArrayList<Article> result = new ArrayList<>();

        for (Article article : articles) {
            double score = 0;
            String t = article.title != null ? article.title.toLowerCase() : "";
            String d = article.description != null ? article.description.toLowerCase() : "";

            for (String term : terms) {
                if (t.contains(term)) score += 30;
                if (d.contains(term)) score += 10;
            }

            if (score > 0) {
                article.relevance = score;
                result.add(article);
            }
        }

        articles = result;
        return reorder();
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
            """ + article.getBody() + """
            </body>
            </html>
            """;

        engine.loadContent(html);

        Scene scene = new Scene(view, 900, 700);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/theme.css")).toExternalForm());
        window.setScene(scene);
        window.setTitle(article.getTitle());
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

    public ArrayList<Article> reorder() {
        return ordering.order(articles);
    }
}