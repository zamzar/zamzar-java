package com.zamzar.api.internal;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Failure;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public abstract class Awaitable<SELF extends Awaitable<SELF>> {

    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(20);
    public static final List<Duration> DEFAULT_BACKOFF = Arrays.asList(
        Duration.ofMillis(100),
        Duration.ofMillis(100),
        Duration.ofMillis(200),
        Duration.ofMillis(500),
        Duration.ofMillis(1000),
        Duration.ofSeconds(1500),
        Duration.ofSeconds(2),
        Duration.ofSeconds(5),
        Duration.ofSeconds(10),
        Duration.ofSeconds(30),
        Duration.ofSeconds(30),
        Duration.ofSeconds(60)
    );

    public SELF awaitOrThrow() throws ApiException {
        return awaitOrThrow(DEFAULT_TIMEOUT);
    }

    public SELF awaitOrThrow(Duration timeout) throws ApiException {
        return awaitOrThrow(timeout, DEFAULT_BACKOFF);
    }

    public SELF awaitOrThrow(Duration timeout, List<Duration> backoff) throws ApiException {
        final SELF result = await(timeout, backoff);
        if (hasFailed()) {
            throw new ApiException("Waited for completion but failed");
        }
        return result;
    }

    public SELF await() throws ApiException {
        return await(DEFAULT_TIMEOUT);
    }

    public SELF await(Duration timeout) throws ApiException {
        return await(timeout, DEFAULT_BACKOFF);
    }

    public SELF await(Duration timeout, List<Duration> backoff) throws ApiException {
        final LocalDateTime deadline = LocalDateTime.now().plus(timeout);

        SELF current = self();
        int attempt = 1;

        while (!current.hasCompleted()) {
            try {
                long wait = backoff.get(Math.min(attempt, backoff.size() - 1)).toMillis();
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                throw new ApiException(e);
            }

            if (LocalDateTime.now().isAfter(deadline)) {
                throw new ApiException("Timed out waiting for completion");
            }

            current = current.refresh();
        }

        return current;
    }

    public abstract SELF refresh() throws ApiException;

    public abstract boolean hasCompleted();

    public abstract boolean hasSucceeded();

    public boolean hasFailed() {
        return hasCompleted() && !hasSucceeded();
    }

    public abstract Failure getFailure();

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
