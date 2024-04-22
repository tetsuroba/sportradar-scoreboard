
import org.junit.jupiter.api.Assertions;
import org.sportradar.scoreboard.domainvalue.ScoreBoard;
import org.junit.jupiter.api.Test;
import org.sportradar.scoreboard.domainvalue.TeamType;
import org.sportradar.scoreboard.exceptions.NoTeamNameGivenException;

import static org.assertj.core.api.Assertions.assertThat;


public class ScoreBoardTest {
    @Test
    public void scoreBoardShouldInitializeEmptyOngoingMatches() {
        ScoreBoard scoreBoard = new ScoreBoard();

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(0);
    }

    @Test
    public void newMatchShouldAddMatchToOngoingMatches() {
        ScoreBoard scoreBoard = new ScoreBoard();

        scoreBoard.newMatch("Germany", "Brazil");

        assertThat(scoreBoard.getOngoingMatches()).isNotNull();
        assertThat(scoreBoard.getOngoingMatches().size()).isEqualTo(1);
    }

    @Test
    public void newMatchTeamsShouldMatchGivenTeams() {
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


}
