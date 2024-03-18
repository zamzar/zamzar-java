package com.zamzar.api;

import com.zamzar.api.core.DefaultApi;
import com.zamzar.api.invoker.ApiException;

/**
 * Checks connectivity to the Zamzar API.
 */
public class DefaultService {

    protected final ZamzarClient zamzar;
    protected final DefaultApi api;

    protected DefaultService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new DefaultApi(zamzar.client);
    }

    /**
     * Returns a welcome message if the Zamzar API is reachable, available and you are authenticated.
     */
    public String welcome() throws ApiException {
        return api.welcome().getMessage();
    }
}
