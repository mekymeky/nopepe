package com.meki.nopepert.inference.exceptions;

public class InferenceException extends Exception {
    public InferenceException(String errorMessage) {
        super(errorMessage);
    }

    public InferenceException(Exception exception) {
        super(exception);
    }
}

