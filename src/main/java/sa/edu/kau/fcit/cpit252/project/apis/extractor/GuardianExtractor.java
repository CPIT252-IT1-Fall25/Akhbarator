package sa.edu.kau.fcit.cpit252.project.apis.extractor;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import sa.edu.kau.fcit.cpit252.project.apis.Feed;
import sa.edu.kau.fcit.cpit252.project.news.Article;
import the.guardian.api.client.GuardianApi;
import the.guardian.api.http.content.ContentItem;
import the.guardian.api.http.content.ContentResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class GuardianExtractor extends Feed {
    static String key = "4bc2ba60-9723-494f-9364-26454591d508";
    static String baseUrl = "https://content.guardianapis.com/";
    static GuardianApi guardianApi = new GuardianApi(key);

    @Override
    public String getFeedName() {
        return "The Guardian";
    }

    @Override
    public ArrayList<Article> run()  {
        try {
            return fromQuery("");
        }
        catch (UnirestException | IOException | InterruptedException e) {
            return new ArrayList<>();
        }
    }

    static public ArrayList<Article> fromQuery(String query) throws IOException, InterruptedException, UnirestException {
        ContentResponse contentResp = (ContentResponse) guardianApi.content().setQuery(query).fetch();
        ArrayList<Article> articles = new ArrayList<>();

        for (ContentItem item : contentResp.getResults()) {
            String title = item.getWebTitle();
            String url = item.getWebUrl();
            System.out.println(title);
            String id = item.getId();
            String body = getBodyFromId(id);
            System.out.println(body);
            articles.add(new Article.Builder(title).withBody(body).withURL(url).build());
        }
        return articles;
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
