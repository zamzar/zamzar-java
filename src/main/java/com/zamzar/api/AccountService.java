package com.zamzar.api;

import com.zamzar.api.core.AccountApi;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Account;

/**
 * Retrieves account information.
 */
public class AccountService {

    protected final ZamzarClient zamzar;
    protected final AccountApi api;

    protected AccountService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new AccountApi(zamzar.client);
    }

    /**
     * Retrieves the current status of your Zamzar API account, including your available conversion credits and current
     * plan.
     */
    public Account get() throws ApiException {
        return api.getAccount();
    }
}
