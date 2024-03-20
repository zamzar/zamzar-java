package com.zamzar.api.examples.client;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.ZamzarEnvironment;
import com.zamzar.api.invoker.ApiException;

public class Environments {
    public static void main(String[] args) throws ApiException {
        // By default, a client will use the production environment
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // You can also specify the environment explicitly to switch to the sandbox environment
        ZamzarClient sandboxedZamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE", ZamzarEnvironment.SANDBOX);
    }
}
