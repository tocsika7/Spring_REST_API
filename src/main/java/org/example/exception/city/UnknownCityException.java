package org.example.exception.city;

public class UnknownCityException extends Exception {

    public UnknownCityException(){}

    public UnknownCityException(String msg){super(msg);}
}
