package com.zamzar.api.internal;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Failure;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
