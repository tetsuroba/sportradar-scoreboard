package org.sportradar.scoreboard.exceptions;

public class NoTeamNameGivenException extends Exception{
    public NoTeamNameGivenException(String errorMessage){
        super(errorMessage);
    }
}
