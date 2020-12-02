package org.example.exception.country;

public class CountryInUseException extends Exception {

    public CountryInUseException(){}

    public CountryInUseException(String message){super(message);}
}
