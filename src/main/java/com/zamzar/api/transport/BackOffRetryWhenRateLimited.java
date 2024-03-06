package com.zamzar.api.transport;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BackOffRetryWhenRateLimited implements Interceptor {

    protected static Duration DEFAULT_TIMEOUT = Duration.of(5, MINUTES);

    protected static Duration DEFAULT_RETRY_AFTER = Duration.of(1, MINUTES);

    protected final Duration timeout;

    protected int attempts = 1;

    public BackOffRetryWhenRateLimited() {
        this(DEFAULT_TIMEOUT);
    }

    public BackOffRetryWhenRateLimited(Duration timeout) {
        this.timeout = timeout;
    }

    @NotNull
    @Override
    public okhttp3.Response intercept(Chain chain) throws java.io.IOException {
        final okhttp3.Request request = chain.request();
        final LocalDateTime deadline = LocalDateTime.now().plus(timeout);

        okhttp3.Response response = chain.proceed(request);

        do {
            // If the response is not a server error, return it immediately
            if (!isRateLimited(response)) {
                return response;
            }

            // Otherwise, wait for the period indicated by the Retry-After header
            try {
                attempts++;
                Thread.sleep(getRetryAfter(response));
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

    protected long getRetryAfter(Response response) {
        final String retryAfter = response.headers().get("Retry-After");
        return retryAfter == null ? DEFAULT_RETRY_AFTER.toMillis() : Long.parseLong(retryAfter) * 1000;
    }

    protected int getAttempts() {
        return attempts;
    }

    protected boolean isRateLimited(okhttp3.Response response) {
        return response.code() == 429;
    }
}
