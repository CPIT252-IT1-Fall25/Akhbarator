package sa.edu.kau.fcit.cpit252.project.database;
import sa.edu.kau.fcit.cpit252.project.news.Article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class Sqlite implements Database {

    private final String url;
    static Logger logger = LoggerFactory.getLogger("sa.edu.kau.fcit.cpit252.project.database.Sqlite");

    public Sqlite() {
        String path = "articles.sqlite";
        this.url = "jdbc:sqlite:" + path;
        createTable();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS articles (
                title TEXT NOT NULL,
                url TEXT NOT NULL UNIQUE,
                date DATE,
                body TEXT
            );
        """;

        try (Connection c = connect(); Statement s = c.createStatement()) {
            s.execute(sql);
        } catch (Exception ignored) {}
    }

    // SAVE
    @Override
    public void save(Article a) {
        String sql = """
            INSERT OR REPLACE INTO articles
            (title, url, date, body)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection c = connect();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, a.getTitle());
            st.setString(2, a.getUrl());

            if (a.getDate() != null)
                st.setDate(3, new Date(a.getDate().getTime()));
            else
                st.setNull(3, Types.DATE);

            st.setString(4, a.getBody());

            st.executeUpdate();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    // GET ALL ARTICLES
    @Override
    public ArrayList<Article> getAll() {
        ArrayList<Article> list = new ArrayList<>();

        String sql = "SELECT * FROM articles";

        try (Connection c = connect();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Article a = buildArticleFromResult(rs);
                list.add(a);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return list;
    }

    // INTERNAL BUILDER
    private Article buildArticleFromResult(ResultSet rs) throws Exception {
        String title = rs.getString("title");
        String url   = rs.getString("url");
        Date date  = rs.getDate("date");
        String body  = rs.getString("body");

        java.util.Date utilDate = null;
        if (date != null) {
            utilDate = new java.util.Date(date.getTime());
        }

        return new Article.Builder(title)
                .withURL(url)
                .withDate(utilDate)
                .withBody(body)
                .build();
    }
}
