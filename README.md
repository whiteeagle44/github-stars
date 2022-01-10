# Github stars

This repository contains my solution to the task that was given at the 2022 Spring edition of the recruitment for Java backend development at Allegro.

The task was solved using Java and the Spring framework.

## Task

Create endpoints:
* listing repositories created by a given Github user  (the name of the repository and number of stars)
* returning sum of stars of all repositories for a given Github user
* listing most popular languages for a given Github user (name, number of bytes of code)

## Setup

1. Set the `GITHUB_TOKEN` environment variable. You can generate one in Github developer settings of your account.
2. Build and run the application from the IDE. Alternatively in command line with `./gradlew build` and `./gradlew bootRun`
3. The application is now live at http://localhost:8080/

## Comments

### Concurrency
In my first version of the solution I made the requests synchronous in all the subtasks.
However:
* fetching the languages for a user with lots of repos would take a long of time because for each repository a separate request would have to be made to the GitHub API
* counting the number of stars would also take a lot of time, less than in the first case, because we can get the number of stars for 100 repos at the same time, however for a user such as `Microsoft` with 46 pages of repositories the running time would still be significant

Implementing asynchronous request handling, where many requests are sent concurrently allowed to significantly reduce the processing time of the request. 

**Technical details**
* In case of languages, I first gather the links to the languages api (for a given number of repositories) in an array, and then query GitHub using these links asynchronously.
* In case of stargazers, I first send a single request which I process to get the number of pages in total, which can be queried. Then I query GitHub api, for the known range of pages asynchronously.

### Further development
* Additional tests can be added
* Custom exceptions with a centralized exeption handling can be introduced


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
    "stargazersCount": 1680195
    }
    ```

### Languages
Returns languages used by user

http://localhost:8080/api/v1/languages/{username}

* `repos` - (optional) number of repositories to consider (taken alphabetically), 100 by default. ⚠️ If you intend to use large values keep in mind that for repos=`n`, more than `n` requests are sent, which may drain your GitHub quota quickly.

Example:

* Request:
  `http://localhost:8080/api/v1/languages/microsoft?repos=110`

* Response:

    ```json
  {
  "languagesSortedDesc":{"HTML":43993424,"Jupyter Notebook":18652236,"C++":14813723,"C#":13331860,"TypeScript":10895309,"Python":5117802,"PowerShell":3810389,"MATLAB":2889825,"JavaScript":2280998,"Java":1717227,"C":1529813,"Objective-C++":963386,"ABAP":705798,"CSS":590072,"Go":314193,"Shell":312779,"Objective-C":219104,"SCSS":164827,"Julia":161005,"R":139329,"TeX":115205,"EJS":97056,"CMake":65887,"Rich Text Format":47649,"SWIG":46900,"Dockerfile":44633,"Kotlin":39382,"Scala":29552,"Makefile":24957,"F#":23875,"Batchfile":15176,"ASP":13629,"HCL":13314,"Raku":13069,"VBScript":12957,"Mustache":9186,"M":8943,"Roff":8135,"PLpgSQL":5720,"Ruby":5439,"Cuda":3980,"Swift":2923,"TSQL":2854,"ANTLR":2459,"Bicep":2116,"Smarty":949,"Perl":341,"NSIS":165,"Pascal":54}
  }
    ```