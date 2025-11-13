package sa.edu.kau.fcit.cpit252.project.viewer;


import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.news.Article;

public class Viewer {
    static Logger logger = LoggerFactory.getLogger("sa.edu.kau.fcit.cpit252.project.viewer");
    private final Feed[] feeds;
    public Viewer(Feed[] feeds){
        this.feeds = feeds;
    }
    public void run() {
        String userName = System.getProperty("user.name");
        if (userName == null)
            userName = "";

        JFrame frame = new JFrame("Akhbarator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 1000);
        frame.setLayout(new BorderLayout());

        StringBuilder html = new StringBuilder(String.format("""
<html>
<head>
<style>
body {
    font-family: 'Segoe UI', 'SansSerif';
    font-size: 14px;
    color: #222;
    background-color: #fafafa;
    line-height: 1.6;
    margin: 10px;
}
h2 {
    color: #1a73e8;
    font-weight: 500;
}
h3 {
    color: #1a73e8;
    font-weight: 400;
    font-size: 30px;
}
a {
    color: #0b57d0;
    text-decoration: none;
}
a:hover {
    text-decoration: underline;
}
</style>
</head>
<body>
<h2>Welcome %s!</h2>
""", userName));

        for (Feed feed: this.feeds) {
            html.append("<h3>").append(feed.getFeedName()).append("</h3>\n");
            ArrayList<Article> articles = feed.run();
            articles.forEach(a -> html.append(a.toString()));
        }
        html.append("</html>");

        JScrollPane scrollPane = createScrollPane(html);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JScrollPane createScrollPane(StringBuilder html) {
        JEditorPane editorPane = new JEditorPane("text/html", html.toString());
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return scrollPane;
    }
}