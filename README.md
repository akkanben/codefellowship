# Code Fellowship -- Login

The Code Fellowship application uses Spring Security to handle user sessions. The app feature a log in/out system with conditional text for the logged in user.

## Instructions

- Build from the command line inside the `codefellowship` directorying with `./gradlew bootRun`
  - Setup a Postgres server with a database named "codefellowship".
  - The `src/main/resources/application.properties` file contains generic user/name password resources change these to match your Postgres server username and password.
  - While the app is running direct your web browser to [localhost:8080](http://localhost:8080). This will load the main login page.

### App Use

Follow the link to create an account and enter a username and password, you'll be redirected back to the main page and automatically logged in.

