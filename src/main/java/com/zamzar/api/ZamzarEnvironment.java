package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * The set of environments available for the Zamzar API.
 *
 * @see ZamzarClient
 */
public enum ZamzarEnvironment {
    /**
     * The production Zamzar API environment; used for production workloads
     */
    PRODUCTION("https://api.zamzar.com/v1"),

    /**
     * The sandbox Zamzar API environment; used for testing and development
     */
    SANDBOX("https://sandbox.zamzar.com/v1");

    private final String url;

    ZamzarEnvironment(String url) {
        this.url = url;
    }

    public URI getBaseUrl() throws ApiException {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new ApiException(e);
        }
    }
}
