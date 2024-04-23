package org.sportradar.scoreboard.domainvalue;


import lombok.Getter;
import org.sportradar.scoreboard.exceptions.InvalidScoreException;
import org.sportradar.scoreboard.exceptions.NoTeamFoundException;
import org.sportradar.scoreboard.exceptions.NoTeamNameGivenException;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ScoreBoard {
    private final List<Match> ongoingMatches;

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

    public void updateMatchScore(Integer index, Integer homeTeamNewScore, Integer awayTeamNewScore) throws NoTeamFoundException, InvalidScoreException {
        if(index == null || ongoingMatches.size() <= index || index < 0) {
            throw new NoTeamFoundException("No team found with given index " + index);
        }
        if(homeTeamNewScore == null || homeTeamNewScore < 0) {
            throw new InvalidScoreException("Invalid home team score given " + homeTeamNewScore);
        }
        if(awayTeamNewScore == null || awayTeamNewScore < 0) {
            throw new InvalidScoreException("Invalid away team score given " + awayTeamNewScore);
        }
        ongoingMatches.get(index).updateScore(homeTeamNewScore, awayTeamNewScore);
    }

    public void finishMatch(Integer index) {
        if(index == 0){
            ongoingMatches.removeFirst();
        } else {
            ongoingMatches.remove(ongoingMatches.get(index));
        }
    }
}
