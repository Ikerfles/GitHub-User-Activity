package search;

import model.UsernameActivity;
import service.APIConsumption;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UsernameSearch {
    public static void searchUsername() {
        Scanner scanner = new Scanner(System.in);
        APIConsumption apiConsumption = new APIConsumption();
        System.out.print("Enter your GitHub username: ");

        try {
            var githubUsername = scanner.nextLine();
            List<UsernameActivity> activities = apiConsumption.usernameSearch(githubUsername);

            if (activities.isEmpty()) {
                System.out.println("No recent activity found for user: " + githubUsername);
                return;
            }

            for (UsernameActivity activity : activities) {
                String mess = "";
                String prep = "";
                switch (activity.type()) {
                    case "PushEvent":
                        mess = "- Pushed";
                        prep = "to";
                        break;

                    case "CreateEvent":
                        mess = "- Created";
                        prep = "in";
                        break;

                    case "DeleteEvent":
                        mess = "- Deleted";
                        prep = "from";
                        break;

                    case "ForkEvent":
                        mess = "- Forked";
                        prep = "from";
                        break;

                    case "WatchEvent":
                        mess = "- Started watching";
                        prep = "to";

                        break;

                    case "- IssuesEvent":
                        mess = "Opened an issue";
                        prep = "in";
                        break;
                }
                if (activity.payload().commits().isEmpty()){

                    System.out.println(mess + " " +
                            prep + " " + activity.repo().name());
                }else{

                    System.out.println(mess + " " + activity.payload().commits()
                                    .stream().map(UsernameActivity.Payload.Commits::message)
                                    .collect(Collectors.joining()) + " " +
                            prep + " " + activity.repo().name());
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to fetch data from GitHub API. Please try again later.");
        }
    }
}
