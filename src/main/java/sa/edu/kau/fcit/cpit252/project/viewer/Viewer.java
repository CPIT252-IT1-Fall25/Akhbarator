package sa.edu.kau.fcit.cpit252.project.viewer;


import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sa.edu.kau.fcit.cpit252.project.apis.Feed;

public class Viewer {
    static Logger logger = LoggerFactory.getLogger("sa.edu.kau.fcit.cpit252.project.viewer");

    public void run(Feed feed) {
        var articles = feed.run();
        var userName = System.getProperty("user.name");

        var frame = new JFrame("Akhbarator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        var html = new StringBuilder(String.format("<html><h2>Welcome %s!</h2>", userName));
        articles.forEach(a -> html.append(a.toString()));
        html.append("</html>");

        var scrollPane = createScrollPane(html);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JScrollPane createScrollPane(StringBuilder html) {
        var editorPane = new JEditorPane("text/html", html.toString());
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

        var scrollPane = new JScrollPane(editorPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return scrollPane;
    }
}