package org.sportradar.scoreboard.exceptions;

public class TeamAlreadyInMatchException extends Exception{
    public TeamAlreadyInMatchException(String errorMessage){
        super(errorMessage);
    }
}
