package ru.nshi.api.schemas;

public abstract class ErrorSchema {
    protected String errorMessage;

    /*
     *  Empty ctor for json...ofication to work
     */
    public ErrorSchema() {
    }

    public String getErrorMesssage() {
        return errorMessage;
    }
}
