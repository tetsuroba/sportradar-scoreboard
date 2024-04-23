
import org.junit.jupiter.api.Assertions;
import org.sportradar.scoreboard.domainvalue.ScoreBoard;
import org.junit.jupiter.api.Test;
import org.sportradar.scoreboard.domainvalue.TeamType;
import org.sportradar.scoreboard.exceptions.InvalidScoreException;
import org.sportradar.scoreboard.exceptions.NoTeamFoundException;
import org.sportradar.scoreboard.exceptions.NoTeamNameGivenException;
import org.sportradar.scoreboard.exceptions.TeamAlreadyInMatchException;

import static org.assertj.core.api.Assertions.assertThat;


public class ScoreBoardTest {
    @Test
    public void scoreBoardShouldInitializeEmptyOngoingMatches() {
        ScoreBoard scoreBoard = new ScoreBoard();

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(0);
    }

    @Test
    public void newMatchShouldAddMatchToOngoingMatches() throws NoTeamNameGivenException, TeamAlreadyInMatchException {
        ScoreBoard scoreBoard = new ScoreBoard();

        scoreBoard.newMatch("Germany", "Brazil");

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(1);
    }

    @Test
    public void newMatchTeamsShouldMatchGivenTeams() throws NoTeamNameGivenException, TeamAlreadyInMatchException {
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
    public void newMatchShouldThrowTeamAlreadyInMatchExceptionWhenANewMatchIsCreatedWithTheSameTeam() throws NoTeamNameGivenException, TeamAlreadyInMatchException {
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
    public void updateMatchScoreShouldUpdateGivenOngoingMatchesScore() throws NoTeamNameGivenException, NoTeamFoundException, InvalidScoreException, TeamAlreadyInMatchException {
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
    public void updateMatchScoreShouldThrowInvalidScoreExceptionWhenInvalidScoreIsGiven() throws NoTeamNameGivenException, TeamAlreadyInMatchException {
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
    public void finishMatchShouldRemoveGivenIndexedMatchFromTheScoreboard() throws NoTeamNameGivenException, NoTeamFoundException, TeamAlreadyInMatchException {
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
    public void finishMatchShouldRemoveGivenIndexedMatchAndKeepTheOtherMatch() throws NoTeamNameGivenException, NoTeamFoundException, TeamAlreadyInMatchException {
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
    public void finishMatchShouldThrowNoTeamFoundExceptionWhenTeamIndexDoesNotExist() throws NoTeamNameGivenException, TeamAlreadyInMatchException {
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
}
