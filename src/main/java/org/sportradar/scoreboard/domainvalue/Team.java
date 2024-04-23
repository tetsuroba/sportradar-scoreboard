package org.sportradar.scoreboard.domainvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Team {
    private String name;
    private Integer score;
    private TeamType type;

    @Override
    public String toString() {
        return name + " " + score;
    }
}
