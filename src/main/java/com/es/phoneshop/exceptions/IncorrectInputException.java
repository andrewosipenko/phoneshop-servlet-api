package com.es.phoneshop.exceptions;

public class IncorrectInputException extends Exception{
    String errorMessage;

    public IncorrectInputException(){
        errorMessage = "";
    }

    public IncorrectInputException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
