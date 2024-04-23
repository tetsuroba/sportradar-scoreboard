# SportRadar ScoreBoard implementation

### Prerequisites

1. Java 21
2. Maven

### Setting up for local project usage

1. Clone with git
2. Navigate to project folder root
3. Run `mvn clean package` to create the jar file under `target` directory.
4. To include it in a maven project add under dependencies like so: 
```
<dependencies>
    <dependency>
        <groupId>org.sportradar.scoreboard</groupId>
        <artifactId>scoreboard</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/src/main/resources/scoreboard-1.0-SNAPSHOT.jar</systemPath>
    </dependency>
</dependencies>
```

### Objects

|           ScoreBoard           |      Match      |      Team       | TeamType |
|:------------------------------:|:---------------:|:---------------:|:--------:|
| ongoingMatches : List \<Match> | HomeTeam : Team |  Name : String  |   HOME   |
|               -                | AwayTeam : Team | Score : Integer |   AWAY   |
|               -                |        -        | Type : TeamType |    -     |

### Functions

#### Scoreboard

| ScoreBoard |       Name       |                               Parameters                                |        Returns         | Example                                     | Description                                                                                          |
|:----------:|:----------------:|:-----------------------------------------------------------------------:|:----------------------:|:--------------------------------------------|------------------------------------------------------------------------------------------------------|
|     -      |     newMatch     |              homeTeamName : String, awayTeamName : String               |           -            | `scoreBoard.newMatch("Germany", "Brazil");` | Adds a new match to the ongoingMatches, creates a home and away team with 0 - 0 points respectively. |
|     -      | updateMatchScore | index : Integer, homeTeamNewScore : Integer, awayTeamNewScore : Integer |           -            | `scoreBoard.updateMatchScore(0, 5, 6);`     | Updates the given match score to the provided values.                                                |
|     -      |   finishMatch    |                             index : Integer                             |           -            | `scoreBoard.finishMatch(0);`                | Finishes the match on the given index, removing it from ongoingMatches.                              |
|     -      |    getSummary    |                                    -                                    |     List \<Match>      | `scoreBoard.getSummary();`                  | Returns a list of Matches, ordered by their total score.                                             |

#### Match

| Match |    Name     |                   Parameters                   |        Returns        |          Example          | Description                                                  |
|:-----:|:-----------:|:----------------------------------------------:|:---------------------:|:-------------------------:|--------------------------------------------------------------|
|   -   | updateScore | homeTeamScore : Integer, awayTeamScore : Integer |     -                 | `match.updateScore(5, 6)` | Updates the participating team's scores to the given scores. |



