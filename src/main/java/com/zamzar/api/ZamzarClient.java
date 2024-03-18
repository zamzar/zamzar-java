package com.zamzar.api;

import com.zamzar.api.invoker.ApiClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.transport.BackOffRetryServerErrors;
import com.zamzar.api.transport.BackOffRetryWhenRateLimited;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;

public class ZamzarClient {
    protected static int HTTP_CALL_TIMEOUT = 60_000;
    protected static int HTTP_CONNECTION_TIMEOUT = 15_000;
    protected static int HTTP_READ_TIMEOUT = 30_000;
    protected static int HTTP_WRITE_TIMEOUT = 30_000;
    protected static final String USER_AGENT = "zamzar-java-v1";

    protected static final String CREDITS_REMAINING_HEADER = "Zamzar-Credits-Remaining";

    protected static final String TEST_CREDITS_REMAINING_HEADER = "Zamzar-Test-Credits-Remaining";

    public static OkHttpClient.Builder getDefaultTransportBuilder() {
        return new OkHttpClient.Builder()
            .callTimeout(HTTP_CALL_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(new BackOffRetryWhenRateLimited())
            .addInterceptor(new BackOffRetryServerErrors());
    }

    protected static OkHttpClient prependInterceptor(OkHttpClient transport, Interceptor interceptor) {
        final OkHttpClient.Builder builder = transport.newBuilder();
        builder.interceptors().add(0, interceptor);
        return builder.build();
    }

    protected final ApiClient client;

    protected final DefaultService _default;
    protected final AccountService account;
    protected final FilesService files;
    protected final FormatsService formats;
    protected final ImportsService imports;
    protected final JobsService jobs;

    protected String lastProductionCreditsRemaining;
    protected String lastSandboxCreditsRemaining;

    public ZamzarClient(String apiKey) {
        this(apiKey, ZamzarEnvironment.PRODUCTION);
    }

    public ZamzarClient(String apiKey, ZamzarEnvironment environment) {
        this(apiKey, environment.getBaseUrl());
    }

    public ZamzarClient(String apiKey, String baseUrl) {
        this(apiKey, baseUrl, getDefaultTransportBuilder().build());
    }

    public ZamzarClient(String apiKey, OkHttpClient transport) {
        this(apiKey, ZamzarEnvironment.PRODUCTION, transport);
    }

    public ZamzarClient(String apiKey, ZamzarEnvironment environment, OkHttpClient transport) {
        this(apiKey, environment.getBaseUrl(), transport);
    }

    public ZamzarClient(String apiKey, String baseUrl, OkHttpClient transport) {
        // Augment the transport to capture the last remaining credits from the response headers
        final OkHttpClient augmentedTransport = prependInterceptor(
            transport,
            chain -> {
                final Response response = chain.proceed(chain.request());
                this.lastProductionCreditsRemaining = response.header(CREDITS_REMAINING_HEADER);
                this.lastSandboxCreditsRemaining = response.header(TEST_CREDITS_REMAINING_HEADER);
                return response;
            }
        );

        // Configure the API client
        this.client = new ApiClient(augmentedTransport);
        this.client.setBasePath(baseUrl);
        this.client.setUserAgent(USER_AGENT);
        this.client.setBearerToken(apiKey);

        // Configure services
        this._default = new DefaultService(this);
        this.account = new AccountService(this);
        this.files = new FilesService(this);
        this.formats = new FormatsService(this);
        this.imports = new ImportsService(this);
        this.jobs = new JobsService(this);
    }

    public JobManager convert(File source, String targetFormat) throws ApiException {
        return jobs().create(source, targetFormat).awaitOrThrow();
    }

    public JobManager convert(File source, String targetFormat, UnaryOperator<JobBuilder> build) throws ApiException {
        return jobs().create(source, targetFormat, build).awaitOrThrow();
    }

    public JobManager convert(Integer sourceId, String targetFormat) throws ApiException {
        return jobs().create(sourceId, targetFormat).awaitOrThrow();
    }

    public JobManager convert(Integer sourceId, String targetFormat, UnaryOperator<JobBuilder> build) throws ApiException {
        return jobs().create(sourceId, targetFormat, build).awaitOrThrow();
    }

    public JobManager convert(String sourceUrl, String targetFormat) throws ApiException {
        return jobs().create(sourceUrl, targetFormat).awaitOrThrow();
    }

    public JobManager convert(String sourceUrl, String targetFormat, UnaryOperator<JobBuilder> build) throws ApiException {
        return jobs().create(sourceUrl, targetFormat, build).awaitOrThrow();
    }

    public JobManager convert(JobBuilder builder) throws ApiException {
        return jobs().create(builder).awaitOrThrow();
    }

    public FileManager upload(File file) throws ApiException {
        return files().upload(file);
    }

    public FileManager upload(File file, String name) throws ApiException {
        return files().upload(file, name);
    }

    public FileManager download(Integer fileId, File destination) throws ApiException {
        return files().download(fileId, destination);
    }

    public String testConnection() throws ApiException {
        return _default.welcome();
    }

    public Integer getLastProductionCreditsRemaining() {
        try {
            return Integer.parseInt(lastProductionCreditsRemaining);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getLastSandboxCreditsRemaining() {
        try {
            return Integer.parseInt(lastSandboxCreditsRemaining);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Integer getProductionCreditsRemaining() throws ApiException {
        return account().get().getCreditsRemaining();
    }

    public Integer getSandboxCreditsRemaining() throws ApiException {
        return account().get().getTestCreditsRemaining();
    }

    public AccountService account() {
        return account;
    }

    public FilesService files() {
        return files;
    }

    public FormatsService formats() {
        return formats;
    }

    public ImportsService imports() {
        return imports;
    }

    public JobsService jobs() {
        return jobs;
    }
}
