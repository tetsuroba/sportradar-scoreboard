package org.sportradar.scoreboard.domainvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Team {
    private String name;
    private Integer score;
    private TeamType type;
}
