package com.meki.nopeperuntime.inference.exceptions;

public class ModelLoadFailureException extends Exception {
    public ModelLoadFailureException(String errorMessage) {
        super(errorMessage);
    }

    public ModelLoadFailureException(Exception exception) {
        super(exception);
    }
}
