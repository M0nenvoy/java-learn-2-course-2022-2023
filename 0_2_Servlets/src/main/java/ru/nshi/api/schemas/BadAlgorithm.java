package ru.nshi.api.schemas;

/*
 *  Such json is returned when client asks us to sort with
 *  an unknown algorithm.
 */
public class BadAlgorithm extends ErrorSchema {
    public BadAlgorithm(String algorithm) {
        this.errorMessage = String.format("Algorith unknown (%s)", algorithm);
    }
}
