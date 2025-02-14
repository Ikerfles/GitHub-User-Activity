import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class APIConsumption {
    public List<UsernameActivity> usernameSearch(String username) {
        URI uri = URI.create("https://api.github.com/users/" + username + "/events");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Parse response as a List of UsernameActivity
            Type listType = new TypeToken<List<UsernameActivity>>() {}.getType();
            return new Gson().fromJson(response.body(), listType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch data from GitHub API: " + e.getMessage());
        }
    }
}
