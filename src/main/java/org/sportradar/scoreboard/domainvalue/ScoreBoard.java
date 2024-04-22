package org.sportradar.scoreboard.domainvalue;

import lombok.Getter;

import java.util.List;

@Getter
public class ScoreBoard {
    private List<Match> ongoingMatches;

    public void newMatch() {

    }
}
