package org.example.exception;

public class UnkownCustomerException  extends  Exception{

    public UnkownCustomerException(){

    }

    public UnkownCustomerException(String messsage){
        super(messsage);
    }
}
