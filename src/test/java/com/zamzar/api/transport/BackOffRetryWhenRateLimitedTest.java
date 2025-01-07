package com.zamzar.api.transport;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackOffRetryWhenRateLimitedTest {

    @Test
    public void retriesUntilSuccessfulResponse() throws IOException {
        final BackOffRetryWhenRateLimited interceptor = createInterceptor();
        final okhttp3.Response response = interceptor.intercept(new MockChain(Arrays.asList(
            builder -> builder.code(429).header("Retry-After", "1"),
            builder -> builder.code(429).header("Retry-After", "1"),
            builder -> builder.code(200))
        ));

        assertEquals(200, response.code());
        assertEquals(3, interceptor.getAttempts());
    }

    @Test
    public void immediatelyReturnsSuccessfulResponse() throws IOException {
        final BackOffRetryWhenRateLimited interceptor = createInterceptor();
        final okhttp3.Response response = interceptor.intercept(MockChain.withResponseCode(200));

        assertEquals(200, response.code());
        assertEquals(1, interceptor.getAttempts());
    }

    @Test
    public void doesNotExceedDeadline() throws IOException {
        final Duration timeout = Duration.ofSeconds(5);
        final BackOffRetryWhenRateLimited interceptor = createInterceptor();
        final long start = System.currentTimeMillis();
        final okhttp3.Response response = interceptor.intercept(new MockChain(
            builder -> builder.code(429).header("Retry-After", "1")
        ));
        final long end = System.currentTimeMillis();
        final long elapsed = end - start;

        assertEquals(429, response.code());
        assertEquals(timeout.toMillis(), elapsed, 1500);
    }


    private static BackOffRetryWhenRateLimited createInterceptor() {
        return createInterceptor(Duration.ofSeconds(5));
    }

    private static BackOffRetryWhenRateLimited createInterceptor(Duration timeout) {
        return new BackOffRetryWhenRateLimited(timeout);
    }

}
