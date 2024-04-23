
import org.junit.jupiter.api.Assertions;
import org.sportradar.scoreboard.domainvalue.Match;
import org.sportradar.scoreboard.domainvalue.ScoreBoard;
import org.junit.jupiter.api.Test;
import org.sportradar.scoreboard.domainvalue.TeamType;
import org.sportradar.scoreboard.exceptions.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ScoreBoardTest {
    @Test
    public void scoreBoardShouldInitializeEmptyOngoingMatches() {
        ScoreBoard scoreBoard = new ScoreBoard();

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(0);
    }

    @Test
    public void newMatchShouldAddMatchToOngoingMatches() throws NoTeamNameGivenException, TeamAlreadyInMatchException, DuplicateTeamException {
        ScoreBoard scoreBoard = new ScoreBoard();

        scoreBoard.newMatch("Germany", "Brazil");

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(1);
    }

    @Test
    public void newMatchTeamsShouldMatchGivenTeams() throws NoTeamNameGivenException, TeamAlreadyInMatchException, DuplicateTeamException {
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";

        scoreBoard.newMatch(homeTeamName, awayTeamName);

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(1);

        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().getFirst().getAwayTeam()).isNotNull();

        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam().getName()).isEqualTo(homeTeamName);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam().getScore()).isEqualTo(0);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam().getType()).isEqualTo(TeamType.HOME);

        assertThat(scoreBoard.getOngoingMatches().getFirst().getAwayTeam().getName()).isEqualTo(awayTeamName);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getAwayTeam().getScore()).isEqualTo(0);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getAwayTeam().getType()).isEqualTo(TeamType.AWAY);
    }

    @Test
    public void newMatchShouldThrowNoTeamNameGivenExceptionWhenTeamNameIsNull() {
        ScoreBoard scoreBoard = new ScoreBoard();

        String nullHomeTeamName = null;
        String nullAwayTeamName = null;
        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";

        Assertions.assertThrows(
            NoTeamNameGivenException.class,
                () -> scoreBoard.newMatch(nullHomeTeamName, nullAwayTeamName)
        );

        Assertions.assertThrows(
                NoTeamNameGivenException.class,
                () -> scoreBoard.newMatch(homeTeamName, nullAwayTeamName)
        );

        Assertions.assertThrows(
                NoTeamNameGivenException.class,
                () -> scoreBoard.newMatch(nullHomeTeamName, awayTeamName)
        );
    }

    @Test
    public void newMatchShouldThrowNoTeamNameGivenExceptionWhenTeamNameIsEmpty() {
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";

        Assertions.assertThrows(
                NoTeamNameGivenException.class,
                () -> scoreBoard.newMatch("", "")
        );
        Assertions.assertThrows(
                NoTeamNameGivenException.class,
                () -> scoreBoard.newMatch(homeTeamName, "")
        );
        Assertions.assertThrows(
                NoTeamNameGivenException.class,
                () -> scoreBoard.newMatch("", awayTeamName)
        );
    }

    @Test
    public void newMatchShouldThrowDuplicateTeamExceptionWhenGivenTeamsAreTheSame() {
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeamName = "Germany";
        String awayTeamName = "Germany";

        Assertions.assertThrows(
                DuplicateTeamException.class,
                () -> scoreBoard.newMatch(homeTeamName, awayTeamName)
        );

        Assertions.assertThrows(
                DuplicateTeamException.class,
                () -> scoreBoard.newMatch(homeTeamName + " ", " " + awayTeamName)
        );
    }

    @Test
    public void newMatchShouldThrowTeamAlreadyInMatchExceptionWhenANewMatchIsCreatedWithTheSameTeam() throws NoTeamNameGivenException, TeamAlreadyInMatchException, DuplicateTeamException {
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";
        String homeTeamName2 = "Uruguay";
        String awayTeamName2 = "Argentina";

        scoreBoard.newMatch(homeTeamName, awayTeamName);

        Assertions.assertThrows(
                TeamAlreadyInMatchException.class,
                () -> scoreBoard.newMatch(homeTeamName, awayTeamName)
        );

        Assertions.assertThrows(
                TeamAlreadyInMatchException.class,
                () -> scoreBoard.newMatch(homeTeamName + " ", awayTeamName + " ")
        );

        Assertions.assertThrows(
                TeamAlreadyInMatchException.class,
                () -> scoreBoard.newMatch(homeTeamName, awayTeamName2)
        );

        Assertions.assertThrows(
                TeamAlreadyInMatchException.class,
                () -> scoreBoard.newMatch(homeTeamName2, awayTeamName)
        );
    }

    @Test
    public void updateMatchScoreShouldUpdateGivenOngoingMatchesScore() throws NoTeamNameGivenException, NoTeamFoundException, InvalidScoreException, TeamAlreadyInMatchException, DuplicateTeamException {
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";

        scoreBoard.newMatch(homeTeamName, awayTeamName);
        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        scoreBoard.updateMatchScore(0, 1, 0);
        scoreBoard.updateMatchScore(0, 1, 1);

        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam().getScore()).isEqualTo(1);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getAwayTeam().getScore()).isEqualTo(1);
    }

    @Test
    public void updateMatchScoreShouldThrowNoTeamFoundExceptionWhenTeamIndexDoesNotExist() {
        ScoreBoard scoreBoard = new ScoreBoard();

        Assertions.assertThrows(
                NoTeamFoundException.class,
                () -> scoreBoard.updateMatchScore(0,1,0)
        );
    }

    @Test
    public void updateMatchScoreShouldThrowInvalidScoreExceptionWhenInvalidScoreIsGiven() throws NoTeamNameGivenException, TeamAlreadyInMatchException, DuplicateTeamException {
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";

        scoreBoard.newMatch(homeTeamName, awayTeamName);
        assertThat(scoreBoard.getOngoingMatches()).isNotNull();

        Assertions.assertThrows(
                InvalidScoreException.class,
                () -> scoreBoard.updateMatchScore(0,-1,0)
        );

        Assertions.assertThrows(
                InvalidScoreException.class,
                () -> scoreBoard.updateMatchScore(0,-1,-1)
        );

        Assertions.assertThrows(
                InvalidScoreException.class,
                () -> scoreBoard.updateMatchScore(0,10,-1)
        );

        Assertions.assertThrows(
                InvalidScoreException.class,
                () -> scoreBoard.updateMatchScore(0,Integer.MAX_VALUE + 1,1)
        );

        Assertions.assertThrows(
                InvalidScoreException.class,
                () -> scoreBoard.updateMatchScore(0,null,1)
        );

        Assertions.assertThrows(
                InvalidScoreException.class,
                () -> scoreBoard.updateMatchScore(0,1,null)
        );

        Assertions.assertThrows(
                NoTeamFoundException.class,
                () -> scoreBoard.updateMatchScore(null,1,1)
        );
    }

    @Test
    public void finishMatchShouldRemoveGivenIndexedMatchFromTheScoreboard() throws NoTeamNameGivenException, NoTeamFoundException, TeamAlreadyInMatchException, DuplicateTeamException {
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";

        scoreBoard.newMatch(homeTeamName, awayTeamName);

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(1);

        scoreBoard.finishMatch(0);

        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(0);
    }

    @Test
    public void finishMatchShouldRemoveGivenIndexedMatchAndKeepTheOtherMatch() throws NoTeamNameGivenException, NoTeamFoundException, TeamAlreadyInMatchException, DuplicateTeamException {
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";

        String homeTeamName2 = "Uruguay";
        String awayTeamName2 = "Argentina";

        scoreBoard.newMatch(homeTeamName, awayTeamName);
        scoreBoard.newMatch(homeTeamName2, awayTeamName2);

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(2);

        scoreBoard.finishMatch(0);
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(1);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam().getName()).isEqualTo(homeTeamName2);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getAwayTeam().getName()).isEqualTo(awayTeamName2);
    }

    @Test
    public void finishMatchShouldThrowNoTeamFoundExceptionWhenTeamIndexDoesNotExist() throws NoTeamNameGivenException, TeamAlreadyInMatchException, DuplicateTeamException {
        ScoreBoard scoreBoard = new ScoreBoard();

        Assertions.assertThrows(
                NoTeamFoundException.class,
                () -> scoreBoard.finishMatch(0)
        );

        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";
        scoreBoard.newMatch(homeTeamName, awayTeamName);
        Assertions.assertThrows(
                NoTeamFoundException.class,
                () -> scoreBoard.finishMatch(1)
        );
        Assertions.assertThrows(
                NoTeamFoundException.class,
                () -> scoreBoard.finishMatch(null)
        );
    }

    @Test
    public void getSummaryShouldReturnMatchesOrderedByTotalScore() throws NoTeamNameGivenException, TeamAlreadyInMatchException, DuplicateTeamException, NoTeamFoundException, InvalidScoreException {
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeamName = "Germany";
        String awayTeamName = "Brazil";
        scoreBoard.newMatch(homeTeamName, awayTeamName);

        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(1);

        String homeTeamName2 = "Uruguay";
        String awayTeamName2 = "Argentina";
        scoreBoard.newMatch(homeTeamName2, awayTeamName2);

        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(2);

        scoreBoard.updateMatchScore(0, 5, 5);
        scoreBoard.updateMatchScore(1, 5, 7);

        List<Match> scoreBoardSummary = scoreBoard.getSummary();
        assertThat(scoreBoardSummary.size()).isEqualTo(2);
        assertThat(scoreBoardSummary.get(0).getHomeTeam().getName()).isEqualTo(homeTeamName2);
        assertThat(scoreBoardSummary.get(1).getHomeTeam().getName()).isEqualTo(homeTeamName);


        scoreBoard.updateMatchScore(0, 10, 5);
        scoreBoardSummary = scoreBoard.getSummary();
        assertThat(scoreBoardSummary.size()).isEqualTo(2);
        assertThat(scoreBoardSummary.get(0).getHomeTeam().getName()).isEqualTo(homeTeamName2);
        assertThat(scoreBoardSummary.get(1).getHomeTeam().getName()).isEqualTo(homeTeamName);
    }

    @Test
    public void getSummaryShouldReturnEarlierMatchFirstInCaseTheyHaveTheSameTotalScore() throws NoTeamNameGivenException, TeamAlreadyInMatchException, DuplicateTeamException, NoTeamFoundException, InvalidScoreException {
        ScoreBoard scoreBoard = new ScoreBoard();


        String homeTeamName = "Mexico";
        String awayTeamName = "Canada";
        scoreBoard.newMatch(homeTeamName, awayTeamName);

        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(1);
        scoreBoard.updateMatchScore(0, 0, 5);

        String homeTeamName2 = "Spain";
        String awayTeamName2 = "Brazil";
        scoreBoard.newMatch(homeTeamName2, awayTeamName2);

        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(2);
        scoreBoard.updateMatchScore(1, 10, 2);

        String homeTeamName3 = "Germany";
        String awayTeamName3 = "France";
        scoreBoard.newMatch(homeTeamName3, awayTeamName3);

        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(3);
        scoreBoard.updateMatchScore(2, 2, 2);

        String homeTeamName4 = "Uruguay";
        String awayTeamName4 = "Italy";
        scoreBoard.newMatch(homeTeamName4, awayTeamName4);

        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(4);
        scoreBoard.updateMatchScore(3, 6, 6);

        String homeTeamName5 = "Argentina";
        String awayTeamName5 = "Australia";
        scoreBoard.newMatch(homeTeamName5, awayTeamName5);

        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(5);
        scoreBoard.updateMatchScore(4, 3, 1);

        List<Match> scoreBoardSummary = scoreBoard.getSummary();

        assertThat(scoreBoardSummary.size()).isEqualTo(5);
        assertThat(scoreBoardSummary.get(0).getHomeTeam().getName()).isEqualTo(homeTeamName2);
        assertThat(scoreBoardSummary.get(1).getHomeTeam().getName()).isEqualTo(homeTeamName4);
        assertThat(scoreBoardSummary.get(2).getHomeTeam().getName()).isEqualTo(homeTeamName5);
        assertThat(scoreBoardSummary.get(3).getHomeTeam().getName()).isEqualTo(homeTeamName);
        assertThat(scoreBoardSummary.get(4).getHomeTeam().getName()).isEqualTo(homeTeamName3);

    }
}
