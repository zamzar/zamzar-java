package com.zamzar.api.internal;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Failure;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AwaitableTest {

    @Test
    public void awaitOrThrowThrowsOnFailure() {
        final ImmediateFail awaitable = new ImmediateFail();
        assertThrows(ApiException.class, awaitable::awaitOrThrow);
    }

    @Test
    public void awaitReturnsOnFailure() throws ApiException {
        final ImmediateFail awaitable = new ImmediateFail();
        assertEquals(awaitable, awaitable.await());
    }

    @Test
    public void awaitThrowsWhenRefreshThrows() {
        final Unrefreshable awaitable = new Unrefreshable();
        assertThrows(ApiException.class, awaitable::await);
    }

    @Test
    public void doesNotExceedDeadline() {
        final Duration timeout = Duration.ofSeconds(5);
        final List<Duration> backoff = Collections.singletonList(Duration.ofMillis(50));
        final NeverCompletes awaitable = new NeverCompletes();
        final long start = System.currentTimeMillis();
        try {
            awaitable.await(timeout, backoff);
        } catch (ApiException e) {
            assertTrue(e.getMessage().contains("Timed out waiting for completion"));
        }
        final long end = System.currentTimeMillis();
        final long elapsed = end - start;

        assertEquals(timeout.toMillis(), elapsed, 100);
    }

    @Test
    public void respectsBackoff() throws ApiException {
        final List<Duration> backoff = Arrays.asList(
            Duration.ofMillis(5),
            Duration.ofMillis(10),
            Duration.ofMillis(20)
        );
        final EventualSuccess awaitable = EventualSuccess.afterAttempts(5);

        final List<Long> actualSleeps = new ArrayList<>();
        final Awaitable.Sleeper sleeper = actualSleeps::add;

        awaitable.await(Duration.ofSeconds(5), backoff, sleeper);

        assertEquals(Arrays.asList(5L, 10L, 20L, 20L, 20L), actualSleeps);
    }

    protected static class ImmediateFail extends Awaitable<ImmediateFail> {

        @Override
        public ImmediateFail refresh() throws ApiException {
            return this;
        }

        @Override
        public boolean hasCompleted() {
            return true;
        }

        @Override
        public boolean hasSucceeded() {
            return false;
        }

        @Override
        public Failure getFailure() {
            return new Failure();
        }
    }

    protected static class Unrefreshable extends Awaitable<Unrefreshable> {

        @Override
        public Unrefreshable refresh() throws ApiException {
            throw new ApiException("Failed to refresh");
        }

        @Override
        public boolean hasCompleted() {
            return false;
        }

        @Override
        public boolean hasSucceeded() {
            return false;
        }

        @Override
        public Failure getFailure() {
            return null;
        }
    }

    protected static class NeverCompletes extends Awaitable<NeverCompletes> {

        @Override
        public NeverCompletes refresh() throws ApiException {
            return this;
        }

        @Override
        public boolean hasCompleted() {
            return false;
        }

        @Override
        public boolean hasSucceeded() {
            return false;
        }

        @Override
        public Failure getFailure() {
            return null;
        }
    }

    protected static class EventualSuccess extends Awaitable<EventualSuccess> {

        protected final int requiredAttempts;
        protected int attempts = 0;

        public static EventualSuccess afterAttempts(int requiredAttempts) {
            return new EventualSuccess(requiredAttempts);
        }

        protected EventualSuccess(int requiredAttempts) {
            this.requiredAttempts = requiredAttempts;
        }

        @Override
        public EventualSuccess refresh() throws ApiException {
            attempts++;
            return this;
        }

        @Override
        public boolean hasCompleted() {
            return attempts >= requiredAttempts;
        }

        @Override
        public boolean hasSucceeded() {
            return hasCompleted();
        }

        @Override
        public Failure getFailure() {
            return null;
        }
    }
}
