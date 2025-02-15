package service;

import model.UsernameActivity;
import model.UsernameActivity.Repo;
import model.UsernameActivity.Payload;
import model.UsernameActivity.Payload.Commits;

import java.util.List;
import java.util.ArrayList;
import java.net.http.*;
import java.net.URI;
import java.io.IOException;

public class APIConsumption {
    public List<UsernameActivity> usernameSearch(String username) {
        URI uri = URI.create("https://api.github.com/users/" + username + "/events");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.
                newBuilder().
                uri(uri).
                GET().
                build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return parseResponse(response.body());
            } else {
                System.out.println("Error: " + response.statusCode());
                return List.of();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private List<UsernameActivity> parseResponse(String json) {
        List<UsernameActivity> activities = new ArrayList<>();

        json = json.trim();
        if (json.startsWith("[")) {
            json = json.substring(1, json.length() - 1);
        }

        String[] objects = json.split("\\},\\{");

        for (String obj : objects) {
            obj = obj.replace("{", "").replace("}", "");

            String type = extractField(obj, "\"type\":\"");
            String repoName = extractField(obj, "\"name\":\"");
            String commitMessage = extractField(obj, "\"message\":\"");

            Repo repo = new Repo(repoName);
            List<Commits> commitsList = commitMessage.isEmpty() ? List.of() : List.of(new Commits(commitMessage));
            Payload payload = new Payload(commitsList);

            activities.add(new UsernameActivity(type, repo, payload));
        }
        return activities;
    }

    private String extractField(String json, String field) {
        int startIndex = json.indexOf(field);
        if (startIndex == -1) {

            return "";
        }
        startIndex += field.length();
        int endIndex = json.indexOf("\"", startIndex);
        return (endIndex != -1) ? json.substring(startIndex, endIndex) : "";
    }
}
