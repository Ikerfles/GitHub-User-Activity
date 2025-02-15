package model;

import java.util.List;

public record UsernameActivity(String type,
                               Repo repo,
                               Payload payload) {

    public record Payload(List<Commits> commits) {

        public record Commits(String message) {

        }
    }

    public record Repo(String name) {
    }

}