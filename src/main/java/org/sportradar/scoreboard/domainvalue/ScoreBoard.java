package org.sportradar.scoreboard.domainvalue;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ScoreBoard {
    private List<Match> ongoingMatches;

    public ScoreBoard() {
        ongoingMatches = new LinkedList<>();
    }

    public void newMatch(String homeTeamName, String awayTeamName) {
        Team homeTeam = new Team(homeTeamName,0, TeamType.HOME);
        Team awayTeam = new Team(awayTeamName,0, TeamType.AWAY);
        ongoingMatches.add(new Match(homeTeam, awayTeam));
    }
}
