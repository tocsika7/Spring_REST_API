package org.example.exception.customer;

public class UnkownCustomerException  extends  Exception{

    public UnkownCustomerException(){

    }

    public UnkownCustomerException(String messsage){
        super(messsage);
    }
}
