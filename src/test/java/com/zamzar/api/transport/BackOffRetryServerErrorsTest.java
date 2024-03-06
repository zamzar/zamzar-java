package com.zamzar.api.transport;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackOffRetryServerErrorsTest {

    @Test
    public void retriesUntilSuccessfulResponse() throws IOException {
        final BackOffRetryServerErrors interceptor = createInterceptor();
        final okhttp3.Response response = interceptor.intercept(MockChain.withResponseCodes(Arrays.asList(503, 503, 200)));

        assertEquals(200, response.code());
        assertEquals(3, interceptor.getAttempts());
    }

    @Test
    public void immediatelyReturnsSuccessfulResponse() throws IOException {
        final BackOffRetryServerErrors interceptor = createInterceptor();
        final okhttp3.Response response = interceptor.intercept(MockChain.withResponseCode(200));

        assertEquals(200, response.code());
        assertEquals(1, interceptor.getAttempts());
    }

    @Test
    public void immediatelyReturnsClientError() throws IOException {
        final BackOffRetryServerErrors interceptor = createInterceptor();
        final okhttp3.Response response = interceptor.intercept(MockChain.withResponseCode(422));

        assertEquals(422, response.code());
        assertEquals(1, interceptor.getAttempts());
    }

    @Test
    public void immediatelyReturnsInternalServerError() throws IOException {
        final BackOffRetryServerErrors interceptor = createInterceptor();
        final okhttp3.Response response = interceptor.intercept(MockChain.withResponseCode(500));

        assertEquals(500, response.code());
        assertEquals(1, interceptor.getAttempts());
    }

    @Test
    public void doesNotExceedDeadline() throws IOException {
        final Duration timeout = Duration.ofMillis(500);
        final BackOffRetryServerErrors interceptor = createInterceptor(timeout);
        final long start = System.currentTimeMillis();
        final okhttp3.Response response = interceptor.intercept(MockChain.withResponseCode(503));
        final long end = System.currentTimeMillis();
        final long elapsed = end - start;

        assertEquals(503, response.code());
        assertEquals(timeout.toMillis(), elapsed, 100);
    }

    @Test
    public void respectsRetryAfterHeader() throws IOException {
        // we'd like to only wait for 10ms
        final BackOffRetryServerErrors interceptor = createInterceptor(
            Duration.ofMillis(500),
            Arrays.asList(Duration.ofMillis(10))
        );

        // but the server asks us to wait for 1 second
        final MockChain chain = new MockChain(builder -> builder.code(503).header("Retry-After", "1"));

        final okhttp3.Response response = interceptor.intercept(chain);

        assertEquals(503, response.code());
        assertEquals(2, interceptor.getAttempts()); // if Retry-After is ignored, more attempts will be made
    }

    @Test
    public void computesBackoffAccurately() {
        final BackOffRetryServerErrors interceptor = createInterceptor(Arrays.asList(
            Duration.ofSeconds(1),
            Duration.ofSeconds(2),
            Duration.ofSeconds(4)
        ));

        assertEquals(1000, interceptor.getBackoff(1));
        assertEquals(2000, interceptor.getBackoff(2));
        assertEquals(4000, interceptor.getBackoff(3));
        assertEquals(4000, interceptor.getBackoff(4));
        assertEquals(4000, interceptor.getBackoff(5));
    }

    private static BackOffRetryServerErrors createInterceptor() {
        return createInterceptor(Duration.ofMillis(500));
    }

    private static BackOffRetryServerErrors createInterceptor(Duration timeout) {
        return createInterceptor(timeout, Collections.singletonList(Duration.ofMillis(25)));
    }

    private static BackOffRetryServerErrors createInterceptor(List<Duration> backoff) {
        return createInterceptor(Duration.ofMillis(500), backoff);
    }

    private static BackOffRetryServerErrors createInterceptor(Duration timeout, List<Duration> backoff) {
        return new BackOffRetryServerErrors(timeout, backoff);
    }
}
