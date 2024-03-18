package com.zamzar.api;

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

    public String getBaseUrl() {
        return url;
    }
}
