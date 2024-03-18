package com.zamzar.api.internal;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Failure;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Awaits the completion of an asynchronous API operation.
 */
public abstract class Awaitable<SELF extends Awaitable<SELF>> {

    /**
     * Default maximum amount of time to wait for completion.
     */
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(20);

    /**
     * Default backoff strategy for polling the API for completion. This is an exponential backoff strategy with a
     * maximum delay of 60 seconds.
     */
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

    /**
     * Waits for the operation to succeed, and throws an exception if it fails.
     */
    public SELF awaitOrThrow() throws ApiException {
        return awaitOrThrow(DEFAULT_TIMEOUT);
    }

    /**
     * Waits for the operation to succeed, and throws an exception if it fails.
     *
     * @param timeout approximate maximum amount of time to wait for completion; may be exceeded by at most the longest
     *                backoff duration
     */
    public SELF awaitOrThrow(Duration timeout) throws ApiException {
        return awaitOrThrow(timeout, DEFAULT_BACKOFF);
    }

    /**
     * Waits for the operation to complete and throws an exception if it fails.
     *
     * @param timeout approximate maximum amount of time to wait for completion; may be exceeded by at most the longest
     *                backoff duration
     * @param backoff backoff strategy for polling the API for completion; use a singleton list for constant backoff or
     *                a list of increasing durations for exponential backoff
     */
    public SELF awaitOrThrow(Duration timeout, List<Duration> backoff) throws ApiException {
        final SELF result = await(timeout, backoff);
        if (hasFailed()) {
            throw new ApiException("Waited for completion but failed");
        }
        return result;
    }

    /**
     * Waits for the operation to succeed or fail. Does not throw an exception if the operation fails.
     */
    public SELF await() throws ApiException {
        return await(DEFAULT_TIMEOUT);
    }

    /**
     * Waits for the operation to succeed or fail. Does not throw an exception if the operation fails.
     *
     * @param timeout approximate maximum amount of time to wait for completion; may be exceeded by at most the longest
     *                backoff duration
     */
    public SELF await(Duration timeout) throws ApiException {
        return await(timeout, DEFAULT_BACKOFF);
    }

    /**
     * Waits for the operation to succeed or fail. Does not throw an exception if the operation fails.
     *
     * @param timeout approximate maximum amount of time to wait for completion; may be exceeded by at most the longest
     *                backoff duration
     * @param backoff backoff strategy for polling the API for completion; use a singleton list for constant backoff or
     *                a list of increasing durations for exponential backoff
     */
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

    /**
     * Returns true if the operation has failed.
     */
    public boolean hasFailed() {
        return hasCompleted() && !hasSucceeded();
    }

    public abstract Failure getFailure();

    @SuppressWarnings("unchecked")
    protected SELF self() {
        return (SELF) this;
    }
}
