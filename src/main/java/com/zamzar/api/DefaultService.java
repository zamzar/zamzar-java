package com.zamzar.api;

import com.zamzar.api.core.DefaultApi;
import com.zamzar.api.invoker.ApiException;

public class DefaultService {

    protected final ZamzarClient zamzar;
    protected final DefaultApi api;

    public DefaultService(ZamzarClient zamzar) {
        this.zamzar = zamzar;
        this.api = new DefaultApi(zamzar.client);
    }

    public String welcome() throws ApiException {
        return api.welcome().getMessage();
    }
}
