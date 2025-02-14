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
                        mess = "Pushed ";
                        prep = " to ";
                        break;

                    case "CreateEvent":
                        mess = "Created ";
                        prep = " in ";
                        break;

                    case "DeleteEvent":
                        mess = "Deleted ";
                        prep = " from ";
                        break;

                    case "ForkEvent":
                        mess = "Forked ";
                        prep = " from ";
                        break;

                    case "WatchEvent":
                        mess = "Started watching ";
                        prep = " to ";

                        break;

                    case "IssuesEvent":
                        mess = "Opened an issue";
                        prep = " in ";
                        break;
                }
                    System.out.println(mess +
                            (activity.payload() != null && activity.payload().commits() != null
                                    ? activity.payload().commits().stream()
                                    .map(UsernameActivity.Payload.Commits::message)
                                    .collect(Collectors.joining(", ")) : "No commits") +
                            prep + activity.repo().name());
                    System.out.println("----------------------");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to fetch data from GitHub API. Please try again later.");
        }
    }
}
