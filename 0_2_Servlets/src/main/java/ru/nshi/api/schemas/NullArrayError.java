package ru.nshi.api.schemas;

/*
 *  Such json is returned when a client sends
 *  us null array for some reason (whatever shall I do).
 */

public class NullArrayError extends ErrorSchema {
    public NullArrayError() {
        this.errorMessage = "Array is null";
    }
}
