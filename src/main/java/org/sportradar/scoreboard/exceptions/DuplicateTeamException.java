package org.sportradar.scoreboard.exceptions;

public class DuplicateTeamException extends Exception {
    public DuplicateTeamException() {
        super("Given teams have the same name");
    }
}
