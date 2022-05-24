package ru.itis.testwork.exceptions;

public class PostValidationException extends IllegalArgumentException {

    public PostValidationException(String s) {
        super(s);
    }
}
