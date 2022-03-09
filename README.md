# Code Fellowship -- Login, Logout, Post, Error Handling

The Code Fellowship application uses Spring Security to handle user sessions. The app feature a log in/out system with conditional text for the logged in user.

## Instructions

- Build from the command line inside the `codefellowship` directorying with `./gradlew bootRun`
  - Setup a Postgres server with a database named "codefellowship".
  - The `src/main/resources/application.properties` file contains generic user/name password resources change these to match your Postgres server username and password.
  - While the app is running direct your web browser to [localhost:8080](http://localhost:8080). This will load the main login page.

### App Use

Follow the link to create an account and enter a username and password, you'll be redirected to the your profile page and be automatically logged in. On the profile page create new posts that will be publicly viewable. View other member's posts following the `/profile/{id}` path.

## Features

- Added a `/my-profile` route that allows user to create their own posts.
- Added a `/profile/{id}` route that allows anyone to view user's profiles and posts by id.
- Added a post postgres database repository for storing user posts.
- Added a custom error page to replace the white-lable error pages.

