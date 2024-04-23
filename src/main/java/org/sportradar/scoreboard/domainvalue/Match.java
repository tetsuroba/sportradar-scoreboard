package org.sportradar.scoreboard.domainvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Match {
    private Team homeTeam;
    private Team awayTeam;

    public void updateScore(Integer homeTeamScore, Integer awayTeamScore) {
        homeTeam.setScore(homeTeamScore);
        awayTeam.setScore(awayTeamScore);
    }

    @Override
    public String toString() {
        return homeTeam.toString() + " - " + awayTeam.toString();
    }
}
