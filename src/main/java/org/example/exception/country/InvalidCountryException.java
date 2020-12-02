package org.example.exception.country;

public class InvalidCountryException extends Exception {

    public InvalidCountryException(){}

    public InvalidCountryException(String message){super(message);}
}
