package ru.nshi.api.schemas;

/*
 *  Such json is returned when a client
 *  requests a content type that we don't support
 */

public class BadContentTypeError extends Error {

    /*
     *  For the debug purposes I think it would be nice
     *  to report what kind of content we wanted and what we got.
     */
    public BadContentTypeError(String wanted, String got) {
        this.errorMessage = String.format("Content-Type is not supported. Wanted: %s; Got: %s", wanted, got);
    }
}
