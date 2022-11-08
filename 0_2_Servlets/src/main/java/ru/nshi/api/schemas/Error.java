package ru.nshi.api.schemas;

public abstract class Error {
    protected String errorMessage;

    /*
     *  Empty ctor for json...ofication to work
     */
    public Error() {
    }

    public String getErrorMesssage() {
        return errorMessage;
    }
}
