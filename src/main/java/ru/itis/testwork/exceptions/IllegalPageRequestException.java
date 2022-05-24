package ru.itis.testwork.exceptions;

public class IllegalPageRequestException extends IllegalArgumentException {

    public IllegalPageRequestException(String message) {
        super(message);
    }

}
