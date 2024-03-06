package com.zamzar.api;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SpyingInterceptor implements Interceptor {
    protected Request request;
    protected Response response;

    @Override
    public Response intercept(Chain chain) throws IOException {
        return this.response = chain.proceed(this.request = chain.request());
    }

    public void assertRequestHasPart(String expected) {
        if (this.request.body() instanceof MultipartBody) {
            final MultipartBody body = (MultipartBody) request.body();
            final List<String> headers = body
                .parts()
                .stream()
                .map(part -> part.headers().toString().trim())
                .collect(Collectors.toList());

            assertTrue(
                headers.stream().anyMatch(header -> header.contains(expected)),
                "Expected part with headers containing: " + expected + "\nActual headers: " + headers
            );
        } else {
            fail("Expected request to be multipart");
        }
    }

    public void assertRequestHasHeader(String header, String expected) {
        final String actual = this.request.header(header);
        assertEquals(expected, actual, "Expected header: " + header);
    }
}
