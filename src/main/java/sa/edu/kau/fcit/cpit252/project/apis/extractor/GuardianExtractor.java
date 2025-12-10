package sa.edu.kau.fcit.cpit252.project.apis.extractor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import the.guardian.api.client.GuardianApi;
import the.guardian.api.http.content.ContentItem;
import the.guardian.api.http.content.ContentResponse;
import io.github.cdimascio.dotenv.Dotenv;

import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.news.Article;


public class GuardianExtractor extends Feed {
    static String key = Dotenv.configure().load().get("GUARDIAN_API_KEY");
    static String baseUrl = "https://content.guardianapis.com/";
    static GuardianApi guardianApi = new GuardianApi(key);

    @Override
    public String getFeedName() {
        return "The Guardian";
    }

    @Override
    public ArrayList<Article> run() {
        try {
            System.out.println("GuardianExtractor: Starting to fetch articles...");
            return fromQuery("");
        } catch (UnirestException | IOException | InterruptedException e) {
            System.err.println("GuardianExtractor Error: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    static public ArrayList<Article> fromQuery(String query) throws IOException, InterruptedException, UnirestException {
        System.out.println("GuardianExtractor: Fetching from Guardian API...");
        ContentResponse contentResp = (ContentResponse) guardianApi.content().setQuery(query).fetch();
        ArrayList<Article> articles = new ArrayList<>();

        System.out.println("GuardianExtractor: Received " + contentResp.getTotal() + " articles");

        for (ContentItem item : contentResp.getResults()) {
            String title = item.getWebTitle();
            String url = item.getWebUrl();
            String id = item.getId();
            String dateString = item.getWebPublicationDate();

            // Parse the date from Guardian's format
            Date publicationDate = parseGuardianDate(dateString);

            System.out.println("• " + title + " (Date: " + publicationDate + ")");

            // Build article with all necessary fields for ordering
            Article article = new Article.Builder(title)
                    .withSupplier(() -> {
                        try {
                            return getBodyFromId(id);
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .withURL(url)
                    .withDate(publicationDate)      // ← CRITICAL for date sorting
                    .withPriority(0)                 // ← For priority sorting
                    .withRelevance(0.0)              // ← For relevance sorting
                    .build();

            articles.add(article);
        }

        System.out.println("GuardianExtractor: Successfully processed " + articles.size() + " articles!");
        return articles;
    }

    /**
     * Parse Guardian API date format (ISO 8601)
     * Example: "2024-12-10T14:30:00Z"
     */
    private static Date parseGuardianDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            System.err.println("GuardianExtractor: Date string is null or empty, using current date");
            return new Date();
        }

        try {
            // Guardian uses ISO 8601 format with Z (UTC) timezone
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            format.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            return format.parse(dateString);
        } catch (ParseException e) {
            System.err.println("GuardianExtractor: Error parsing date '" + dateString + "': " + e.getMessage());
            return new Date(); // Fallback to current date
        }
    }

    static private String getBodyFromId(String id) throws IOException, InterruptedException {
        String url = baseUrl + id + "?api-key=" + key + "&show-fields=body";

        String bodyJson = HttpClient.newHttpClient()
                .send(HttpRequest.newBuilder(URI.create(url)).GET().build(),
                        HttpResponse.BodyHandlers.ofString())
                .body();

        JSONObject jsonObject = new JSONObject(bodyJson);

        return jsonObject
                .getJSONObject("response")
                .getJSONObject("content")
                .getJSONObject("fields")
                .getString("body");
    }
}