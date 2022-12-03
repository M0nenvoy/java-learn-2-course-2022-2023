package ru.nshi.model;

public class Error {
    private String error;

    public Error() {
    }

    public Error(String errorMessage) {
        this.error = errorMessage;
    }

    public String getErrorMessage() {
        return error;
    }

    public void setErrorMessage(String errorMessage) {
        this.error = errorMessage;
    }
}
