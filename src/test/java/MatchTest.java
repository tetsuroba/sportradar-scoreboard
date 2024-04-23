import org.junit.jupiter.api.Test;
import org.sportradar.scoreboard.domainvalue.Match;
import org.sportradar.scoreboard.domainvalue.Team;
import org.sportradar.scoreboard.domainvalue.TeamType;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchTest {
    @Test
    public void updateScoreShouldChangeTeamScoresToGivenScores() {
        Team homeTeam = new Team("Germany", 7, TeamType.HOME);
        Team awayTeam = new Team("Brazil", 1, TeamType.AWAY);
        Match match = new Match(homeTeam, awayTeam);

        match.updateScore(8, 1);
        assertThat(match.getHomeTeam().getScore()).isEqualTo(8);
        assertThat(match.getAwayTeam().getScore()).isEqualTo(1);

        match.updateScore(8, 2);
        assertThat(match.getHomeTeam().getScore()).isEqualTo(8);
        assertThat(match.getAwayTeam().getScore()).isEqualTo(2);

        match.updateScore(10, 10);
        assertThat(match.getHomeTeam().getScore()).isEqualTo(10);
        assertThat(match.getAwayTeam().getScore()).isEqualTo(10);
    }
}
