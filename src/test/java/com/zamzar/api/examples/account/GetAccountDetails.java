package com.zamzar.api.examples.account;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Account;

public class GetAccountDetails {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // The account object is read-only; any changes are not persisted to the Zamzar API
        Account account = zamzar.account().get();

        // Print the direct properties of the account object
        System.out.println(account.getCreditsRemaining());
        System.out.println(account.getTestCreditsRemaining());

        // Print the plan object
        System.out.println(account.getPlan());
    }
}
