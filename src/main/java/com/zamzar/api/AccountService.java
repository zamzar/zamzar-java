package com.zamzar.api;

import com.zamzar.api.core.AccountApi;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Account;

public class AccountService {

    protected final ZamzarClient zamzar;
    protected final AccountApi api;

    public AccountService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new AccountApi(zamzar.client);
    }

    public Account get() throws ApiException {
        return api.getAccount();
    }
}
