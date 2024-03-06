package com.zamzar.api.transport;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

class MockChain implements okhttp3.Interceptor.Chain {
    protected List<UnaryOperator<Response.Builder>> responseModifiers;

    protected int proceedCount = 1;

    public static MockChain withResponseCode(int responseCode) {
        return withResponseCodes(Collections.singletonList(responseCode));
    }

    public static MockChain withResponseCodes(List<Integer> responseCodes) {
        return new MockChain(
            responseCodes
                .stream()
                .map(code -> (UnaryOperator<Response.Builder>) builder -> builder.code(code))
                .collect(Collectors.toList())
        );
    }

    public MockChain(UnaryOperator<Response.Builder> responseModifier) {
        this(Collections.singletonList(responseModifier));
    }

    public MockChain(List<UnaryOperator<Response.Builder>> responseModifiers) {
        this.responseModifiers = responseModifiers;
    }

    @Override
    public okhttp3.Request request() {
        return new okhttp3.Request.Builder()
            .url("http://localhost")
            .build();
    }

    @Override
    public okhttp3.Response proceed(okhttp3.Request request) {
        final Response.Builder builder = new Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .message("OK");

        return getResponseModifier(proceedCount++).apply(builder).build();
    }

    protected UnaryOperator<Response.Builder> getResponseModifier(int attempt) {
        return responseModifiers.get(Math.min(attempt - 1, responseModifiers.size() - 1));
    }

    @NotNull
    @Override
    public Call call() {
        return null;
    }

    @Override
    public int connectTimeoutMillis() {
        return 0;
    }

    @Nullable
    @Override
    public Connection connection() {
        return null;
    }

    @Override
    public int readTimeoutMillis() {
        return 0;
    }

    @NotNull
    @Override
    public Interceptor.Chain withConnectTimeout(int i, @NotNull TimeUnit timeUnit) {
        return null;
    }

    @NotNull
    @Override
    public Interceptor.Chain withReadTimeout(int i, @NotNull TimeUnit timeUnit) {
        return null;
    }

    @NotNull
    @Override
    public Interceptor.Chain withWriteTimeout(int i, @NotNull TimeUnit timeUnit) {
        return null;
    }

    @Override
    public int writeTimeoutMillis() {
        return 0;
    }
}
