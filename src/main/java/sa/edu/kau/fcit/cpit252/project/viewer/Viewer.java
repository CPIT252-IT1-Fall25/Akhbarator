package sa.edu.kau.fcit.cpit252.project.viewer;

import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.util.ArrayList;

public class Viewer {
    public void run(Feed feed) {
        ArrayList<Article> articles = feed.run();
        String userName = System.getProperty("user.name");

        JFrame frame = new JFrame("Akhbarator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        StringBuilder html = new StringBuilder("<html><h2>Welcome " + userName + "!</h2>");
        for (Article a : articles) {
            html.append("<p>• <a href='").append(a.url).append("'>")
                    .append(a.title)
                    .append("</a></p>");
        }
        html.append("</html>");

        JScrollPane scrollPane = getjScrollPane(html);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    private static JScrollPane getjScrollPane(StringBuilder html) {
        JEditorPane editorPane = new JEditorPane("text/html", html.toString());
        editorPane.setEditable(false);
        editorPane.setOpaque(false);
        editorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return scrollPane;
    }
}
