package com.finalproject.cf.exceptions;

import java.io.IOException;

public class ConvertingImgException extends IOException {
    private static final long serialVersionUID = 1L;

    public ConvertingImgException(String message) {
        super(message);
    }
}
