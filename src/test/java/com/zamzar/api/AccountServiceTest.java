package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountServiceTest extends ZamzarApiTest {

    @Test
    public void account() throws ApiException {
        final Account account = zamzar().account().get();

        assertNotNull(account.getPlan());
        assertNotNull(account.getCreditsRemaining());
        assertNotNull(account.getTestCreditsRemaining());
    }
}
