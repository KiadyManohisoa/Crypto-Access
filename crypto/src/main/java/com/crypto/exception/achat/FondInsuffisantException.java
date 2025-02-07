package com.crypto.exception.achat;

public class FondInsuffisantException  extends Exception{
    public FondInsuffisantException(){
        super();
    }

    public FondInsuffisantException(String message){
        super(message);
    }

    public FondInsuffisantException(String message, Throwable throwable){
        super(message, throwable);
    }

}
