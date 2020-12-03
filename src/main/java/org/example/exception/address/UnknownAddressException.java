package org.example.exception.address;

public class UnknownAddressException extends Exception {

    public UnknownAddressException(){}
    public UnknownAddressException(String msg){super(msg);}
}
