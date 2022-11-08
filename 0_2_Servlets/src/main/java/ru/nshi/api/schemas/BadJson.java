package ru.nshi.api.schemas;

/*
 *  Such json is returned when a client tries to
 *  feed us with the string that is not actually json or doesn't
 *  correspond to a wanted schema
 *
 *  In other words, a databind error
 */

public class BadJson extends Error {
    /*
     *  It would be actually nice to find out what is the problem exactly.
     *  Is it that we got non-json string or the schema doesn't match.
     *
     *  So I guess we will include here the string, which we tried to databind and
     *  the schema. The name of the class of the schema to be exact.
     */
    public BadJson(String jsonString, String schemaName) {
        this.errorMessage = String.format("Bad json (%s)\nWanted the schema (%s)", jsonString, schemaName);
    }
}
