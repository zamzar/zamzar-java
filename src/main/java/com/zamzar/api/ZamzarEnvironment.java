package com.zamzar.api;

public enum ZamzarEnvironment {
    PRODUCTION("https://api.zamzar.com/v1"),
    SANDBOX("https://sandbox.zamzar.com/v1");

    private final String url;

    ZamzarEnvironment(String url) {
        this.url = url;
    }

    public String getBaseUrl() {
        return url;
    }
}
