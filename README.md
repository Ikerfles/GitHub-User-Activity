# GitHub User Activity

GitHub User Activity is a command-line interface (CLI) tool that fetches and displays the recent activity of a GitHub user.
To know more details about the project visit the following link: https://roadmap.sh/projects/github-user-activity

## Features
- Accepts a GitHub username as an argument
- Fetches the recent activity of the specified user using the GitHub API.
- Displays the activity data in a structured format in the terminal.
- Does not use any external libraries or frameworks to interact with the API.

## Installation
1. Clone the repository
2. Compile the project

## Implementation Details
- Uses HTTP requests to fetch data from the GitHub API.
- Parses JSON responses manually without external libraries.
- Extracts and formats recent activity data for display.

## Future Enhancements
- Improve error handling for invalid usernames or API failures.
- Allow filtering of activity types (e.g., only commits, issues, or pull requests).
- Cache results to reduce API requests.
