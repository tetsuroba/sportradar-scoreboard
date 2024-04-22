package org.sportradar.scoreboard.domainvalue;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ScoreBoard {
    private List<Match> ongoingMatches;

    public ScoreBoard() {
        ongoingMatches = new LinkedList<>();
    }

    public void newMatch(String homeTeam, String awayTeam) {
        ongoingMatches.add(new Match());
    }
}
