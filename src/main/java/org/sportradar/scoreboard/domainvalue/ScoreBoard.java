package org.sportradar.scoreboard.domainvalue;


import lombok.Getter;
import org.sportradar.scoreboard.exceptions.NoTeamNameGivenException;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ScoreBoard {
    private List<Match> ongoingMatches;

    public ScoreBoard() {
        ongoingMatches = new LinkedList<>();
    }

    public void newMatch(String homeTeamName, String awayTeamName) throws NoTeamNameGivenException {
        if(homeTeamName == null || homeTeamName.trim().isEmpty()) {
            throw new NoTeamNameGivenException("homeTeamName is empty or null " + homeTeamName);
        }
        if(awayTeamName == null || awayTeamName.trim().isEmpty()) {
            throw new NoTeamNameGivenException("awayTeamName is empty or null " + awayTeamName);
        }
        Team homeTeam = new Team(homeTeamName,0, TeamType.HOME);
        Team awayTeam = new Team(awayTeamName,0, TeamType.AWAY);
        ongoingMatches.add(new Match(homeTeam, awayTeam));
    }

    public void updateMatchScore(Integer index, Integer homeTeamNewScore, Integer awayTeamNewScore) {

    }

}
