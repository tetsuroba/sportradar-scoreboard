
import org.sportradar.scoreboard.domainvalue.ScoreBoard;
import org.junit.jupiter.api.Test;
import org.sportradar.scoreboard.domainvalue.TeamType;

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

        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam().getName()).isEqualTo(awayTeamName);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam().getScore()).isEqualTo(0);
        assertThat(scoreBoard.getOngoingMatches().getFirst().getHomeTeam().getType()).isEqualTo(TeamType.AWAY);
    }
}
