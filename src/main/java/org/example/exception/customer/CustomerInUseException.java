package org.example.exception.customer;

public class CustomerInUseException extends Exception {

    public CustomerInUseException(){}
    public CustomerInUseException(String msg){super(msg);}
}
