package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImportManagerTest extends ZamzarApiTest {
    @Test
    public void await() throws ApiException {
        assertTrue(zamzar().imports().find(SUCCEEDING_IMPORT_ID).await().hasSucceeded());
        assertNull(zamzar().imports().find(SUCCEEDING_IMPORT_ID).await().getFailure());
        assertTrue(zamzar().imports().find(SUCCEEDING_IMPORT_ID).awaitOrThrow().hasSucceeded());
    }

    @Test
    public void awaitFailing() throws ApiException {
        assertTrue(zamzar().imports().find(FAILING_IMPORT_ID).await().hasFailed());
        assertNotNull(zamzar().imports().find(FAILING_IMPORT_ID).await().getFailure().getCode());
        assertNotNull(zamzar().imports().find(FAILING_IMPORT_ID).await().getFailure().getMessage());
        assertThrows(ApiException.class, () -> zamzar().imports().find(FAILING_IMPORT_ID).awaitOrThrow());
    }

    @Test
    public void throwsWhenAwaitedNotFound() throws Exception {
        final ImportManager _import = zamzar().imports().find(SUCCEEDING_IMPORT_ID);
        destroyInMock("/imports/" + SUCCEEDING_IMPORT_ID);
        assertThrows(ApiException.class, _import::await);
    }
}
