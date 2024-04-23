package org.sportradar.scoreboard.exceptions;

public class NoTeamFoundException extends Exception{
    public NoTeamFoundException(String errorMessage){
        super(errorMessage);
    }
}
