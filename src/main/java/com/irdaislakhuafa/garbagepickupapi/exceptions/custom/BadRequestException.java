package com.irdaislakhuafa.garbagepickupapi.exceptions.custom;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String s) {
        super(s);
    }
}