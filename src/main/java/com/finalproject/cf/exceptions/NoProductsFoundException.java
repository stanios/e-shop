package com.finalproject.cf.exceptions;

public class NoProductsFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public NoProductsFoundException() {
        super("No products in the DB");
    }
}
