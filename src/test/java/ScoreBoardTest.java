
import org.sportradar.scoreboard.domainvalue.ScoreBoard;
import org.junit.jupiter.api.Test;

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
}
