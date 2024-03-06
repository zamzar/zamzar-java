package com.zamzar.api.transport;

import okhttp3.Interceptor;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BackOffRetryServerErrors implements Interceptor {

    protected static Duration DEFAULT_TIMEOUT = Duration.of(5, MINUTES);

    protected static List<Duration> DEFAULT_BACKOFF = Stream
        .of(1, 2, 5, 10, 30, 60)
        .map(Duration::ofSeconds)
        .collect(Collectors.toList());

    protected final Duration timeout;
    protected final List<Duration> backoff;

    protected int attempts = 1;

    public BackOffRetryServerErrors() {
        this(DEFAULT_TIMEOUT, DEFAULT_BACKOFF);
    }

    public BackOffRetryServerErrors(Duration timeout, List<Duration> backoff) {
        this.timeout = timeout;
        this.backoff = backoff;
    }

    @NotNull
    @Override
    public okhttp3.Response intercept(Chain chain) throws java.io.IOException {
        final okhttp3.Request request = chain.request();
        final LocalDateTime deadline = LocalDateTime.now().plus(timeout);

        okhttp3.Response response = chain.proceed(request);

        do {
            // If the response is not a server error, return it immediately
            if (!isServerError(response)) {
                return response;
            }

            // Otherwise, wait for the current backoff period
            try {
                long wait = getBackoff(attempts++);

                // Respect the Retry-After header, if present
                if (response.headers().get("Retry-After") != null) {
                    long minimumWait = Long.parseLong(response.headers().get("Retry-After")) * 1000;
                    wait = Math.max(wait, minimumWait);
                }

                Thread.sleep(wait);
            } catch (InterruptedException e) {
                // If the thread is interrupted, return the most recent server error response
                Thread.currentThread().interrupt(); // preserve the interrupted status
                return response;
            }

            // If the deadline has now been exceeded, return the most recent server error response
            if (LocalDateTime.now().isAfter(deadline)) {
                return response;
            }

            // Release resources associated with previous response
            if (response.body() != null) {
                response.close();
            }

            // Retry the request
            response = chain.proceed(request);

        } while (true);
    }

    protected int getAttempts() {
        return attempts;
    }

    protected long getBackoff(int attempt) {
        return backoff.get(Math.min(attempt - 1, backoff.size() - 1)).toMillis();
    }

    protected boolean isServerError(okhttp3.Response response) {
        return response.code() == 502 ||   // Bad Gateway
            response.code() == 503 ||     // Service Unavailable
            response.code() == 504;       // Gateway Timeout
    }
}
