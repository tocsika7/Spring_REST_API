package org.example.exception.country;

public class UnknownCountryException extends Exception {

    public UnknownCountryException(){}

    public UnknownCountryException(String message){super(message);}

}
