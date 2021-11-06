# Github stars

This repository contains my solution to the task that was originally given at the 2021 recrutation for Java backend developer at Allegro.

## Task

Create endpoints:
* listing repositories created by a given Github user  (the name of the repository and number of stars)
* returning sum of stars of all repositories for a given Github user

## Setup

1. Set the `GITHUB_TOKEN` environment variable. You can generate one in Github developer settings of your account.
2. Build and run the application from the IDE. Alternatively in command line with `./gradlew build` and `./gradlew bootRun`
3. The application is now live at http://localhost:8080/

## API

### Repositories

Lists repos for a given users

http://localhost:8080/api/v1/repos/{username}?page=1&per_page=30

* `page` - (optional) number of page dispayed, 1 by default
* `per_page` - (optional) number of entries per page, 30 by default

Example:

* Request:
  `http://localhost:8080/api/v1/repos/microsoft?per_page=3`

* Response:

    ```json
    {
    "repos": [
        {
        "name": ".github",
        "stargazers_count": 23
        },
        {
        "name": ".Net-Interactive-Kernels-ADS",
        "stargazers_count": 3
        },
        {
        "name": ".NET-Modernization-In-a-Day",
        "stargazers_count": 39
        }
    ],
    "pagination": {
        "prevPage": null,
        "nextPage": "https://api.github.com/user/6154722/repos?per_page=3&page=2",
        "firstPage": null,
        "lastPage": "https://api.github.com/user/6154722/repos?per_page=3&page=1482"
    }
    }
    ```


### Stargazers
Returns sum of stars of all user repositories

http://localhost:8080/api/v1/stargazers/{username}

Example: 

* Request:
  `http://localhost:8080/api/v1/stargazers/microsoft`

* Response:

    ```json
    {
    "stargazersCount": 1621692
    }
    ```