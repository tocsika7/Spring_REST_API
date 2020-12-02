package org.example.exception;

public class CountryInUseException extends Exception {

    public CountryInUseException(){}

    public CountryInUseException(String message){super(message);}
}
