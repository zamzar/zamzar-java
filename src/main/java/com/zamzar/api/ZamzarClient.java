package com.zamzar.api;

import com.zamzar.api.invoker.ApiClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.transport.BackOffRetryServerErrors;
import com.zamzar.api.transport.BackOffRetryWhenRateLimited;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.File;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * The primary entrypoint for making request against the Zamzar API.
 * <p>
 * The client will automatically retry failed and timed out HTTP requests. You can customise this behaviour by providing
 * your own {@link OkHttpClient} instance when constructing the client.
 * <p>
 * Example usage:
 * <pre>
 *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");
 *
 *     client
 *      .convert(new File("path/to/source.pdf"), "jpg")
 *      .awaitOrThrow()
 *      .download(new File("path/to/destination.jpg"));
 * </pre>
 *
 * @see <a href="https://developers.zamzar.com/docs">Zamzar API Documentation</a>
 */
public class ZamzarClient {
    protected static int HTTP_CALL_TIMEOUT = 60_000;
    protected static int HTTP_CONNECTION_TIMEOUT = 15_000;
    protected static int HTTP_READ_TIMEOUT = 30_000;
    protected static int HTTP_WRITE_TIMEOUT = 30_000;
    protected static final String USER_AGENT = "zamzar-java-v1";

    protected static final String CREDITS_REMAINING_HEADER = "Zamzar-Credits-Remaining";

    protected static final String TEST_CREDITS_REMAINING_HEADER = "Zamzar-Test-Credits-Remaining";

    /**
     * Returns an {@link OkHttpClient.Builder} ready to build a new {@link OkHttpClient} with Zamzar's default
     * timeouts and retry interceptors.
     *
     * @return a new {@link OkHttpClient.Builder} instance
     */
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

    /**
     * Constructs a new client instance with the given Zamzar API key.
     * <p>
     * Create and retrieve your API key from the <a href="https://developers.zamzar.com">Zamzar Developer Portal</a>.
     */
    public ZamzarClient(String apiKey) throws ApiException {
        this(apiKey, ZamzarEnvironment.PRODUCTION);
    }

    /**
     * Constructs a new client instance with the given Zamzar API key.
     * <p>
     * Create and retrieve your API key from the <a href="https://developers.zamzar.com">Zamzar Developer Portal</a>.
     *
     * @param environment the Zamzar API environment to use (production or sandbox)
     */
    public ZamzarClient(String apiKey, ZamzarEnvironment environment) throws ApiException {
        this(apiKey, environment.getBaseUrl());
    }

    /**
     * Constructs a new client instance with the given Zamzar API key and base URL.
     * <p>
     * You should only use this constructor if you need to override the default base URL for the API (e.g., when
     * testing the Zamzar Java library itself).
     */
    public ZamzarClient(String apiKey, URI baseUrl) {
        this(apiKey, baseUrl, getDefaultTransportBuilder().build());
    }

    /**
     * Constructs a new client instance with the given Zamzar API key and transport.
     * <p>
     * Use this constructor if you need to customise the HTTP client used by the client (e.g., to set custom timeouts).
     */
    public ZamzarClient(String apiKey, OkHttpClient transport) throws ApiException {
        this(apiKey, ZamzarEnvironment.PRODUCTION, transport);
    }

    /**
     * Constructs a new client instance with the given Zamzar API key, environment, and transport.
     * <p>
     * Use this constructor if you need to customise the HTTP client used by the client (e.g., to set custom timeouts).
     */
    public ZamzarClient(String apiKey, ZamzarEnvironment environment, OkHttpClient transport) throws ApiException {
        this(apiKey, environment.getBaseUrl(), transport);
    }

    /**
     * Constructs a new client instance with the given Zamzar API key, base URL, and transport.
     * <p>
     * You should only use this constructor if you need to override the default base URL for the API (e.g., when
     * testing the Zamzar Java library itself).
     */
    public ZamzarClient(String apiKey, URI baseUrl, OkHttpClient transport) {
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
        this.client.setBasePath(baseUrl.toString());
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

    /**
     * Converts a local file to the specified format. Call {@link JobManager#awaitOrThrow()} on the result
     * to wait for the conversion to complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");
     *
     *     zamzar
     *       .convert(new File("path/to/source.pdf"), "jpg")
     *       .awaitOrThrow()
     *       .download(new File("path/to/destination.jpg"))
     *       .deleteAllFiles();
     * </pre>
     */
    public JobManager convert(File source, String targetFormat) throws ApiException {
        return jobs().create(source, targetFormat).awaitOrThrow();
    }

    /**
     * Converts a local file to the specified format using a custom job builder. Call {@link JobManager#awaitOrThrow()}
     * on the result to wait for the conversion to complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");
     *
     *     zamzar
     *       .convert(
     *         new File("path/to/source.pdf"),
     *         "jpg",
     *         builder -> builder.exportingTo("s3://my-bucket/path/to/destination.jpg")
     *       )
     *       .awaitOrThrow()
     *       .deleteAllFiles();
     * </pre>
     */
    public JobManager convert(File source, String targetFormat, JobBuilder.Modifier modifier) throws ApiException {
        return jobs().create(source, targetFormat, modifier).awaitOrThrow();
    }

    /**
     * Converts a file already present on Zamzar's API servers to the specified format. Call {@link JobManager#awaitOrThrow()}
     * on the result to wait for the conversion to complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");
     *
     *     zamzar
     *       .convert(123, "jpg")
     *       .awaitOrThrow()
     *       .download(new File("path/to/destination.jpg"))
     *       .deleteTargetFiles();
     * </pre>
     */
    public JobManager convert(Integer sourceId, String targetFormat) throws ApiException {
        return jobs().create(sourceId, targetFormat).awaitOrThrow();
    }

    /**
     * Converts a file already present on Zamzar's API servers to the specified format using a custom job builder.
     * Call {@link JobManager#awaitOrThrow()} on the result to wait for the conversion to complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");
     *
     *     zamzar
     *       .convert(
     *         123,
     *         "jpg",
     *         builder -> builder.exportingTo("s3://my-bucket/path/to/destination.jpg")
     *       )
     *       .awaitOrThrow()
     *       .deleteTargetFiles();
     * </pre>
     */
    public JobManager convert(Integer sourceId, String targetFormat, JobBuilder.Modifier modifier) throws ApiException {
        return jobs().create(sourceId, targetFormat, modifier).awaitOrThrow();
    }

    /**
     * Converts a file at the given URL to the specified format. Call {@link JobManager#awaitOrThrow()} on the result
     * to wait for the conversion to complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");
     *
     *     zamzar
     *       .convert(new URL("https://example.com/source.pdf"), "jpg")
     *       .awaitOrThrow()
     *       .download(new File("path/to/destination.jpg"))
     *       .deleteAllFiles();
     * </pre>
     */
    public JobManager convert(URI source, String targetFormat) throws ApiException {
        return jobs().create(source, targetFormat).awaitOrThrow();
    }

    /**
     * Converts a file at the given URL to the specified format using a custom job builder. Call {@link JobManager#awaitOrThrow()}
     * on the result to wait for the conversion to complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");
     *
     *     zamzar
     *       .convert(
     *         "https://example.com/source.pdf",
     *         "jpg",
     *         builder -> builder.exportingTo("s3://my-bucket/path/to/destination.jpg")
     *       )
     *       .awaitOrThrow()
     *       .deleteAllFiles();
     * </pre>
     */
    public JobManager convert(URI source, String targetFormat, JobBuilder.Modifier modifier) throws ApiException {
        return jobs().create(source, targetFormat, modifier).awaitOrThrow();
    }

    protected JobManager convert(JobBuilder builder) throws ApiException {
        return jobs().create(builder).awaitOrThrow();
    }

    /**
     * Uploads a local file to Zamzar's API servers, blocking until the upload is complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");
     *
     *     int uploadedFileId = zamzar.upload(new File("path/to/source.pdf")).getId();
     */
    public FileManager upload(File file) throws ApiException {
        return files().upload(file);
    }

    /**
     * Uploads a local file to Zamzar's API servers with the given name, blocking until the upload is complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE")
     *
     *     int uploadedFileId = zamzar.upload(new File("path/to/source"), "source.pdf").getId();
     * </pre>
     */
    public FileManager upload(File file, String name) throws ApiException {
        return files().upload(file, name);
    }

    /**
     * Downloads a file from Zamzar's API servers to the given destination, blocking until the download is complete.
     * <p>
     * Example usage:
     * <pre>
     *     ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE)
     *
     *     zamzar.download(123, new File("path/to/destination.jpg"));
     * </pre>
     */
    public FileManager download(Integer fileId, File destination) throws ApiException {
        return files().download(fileId, destination);
    }

    /**
     * Checks whether the client can connect to the Zamzar API; useful for testing your API key.
     */
    public String testConnection() throws ApiException {
        return _default.welcome();
    }

    /**
     * Returns the remaining production credits for the last request made by the client.
     * <p>
     * This method will return {@code null} if no successful requests have been made yet.
     */
    public Integer getLastProductionCreditsRemaining() {
        try {
            return Integer.parseInt(lastProductionCreditsRemaining);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Returns the remaining sandbox credits for the last request made by the client.
     * <p>
     * This method will return {@code null} if no successful requests have been made yet.
     */
    public Integer getLastSandboxCreditsRemaining() {
        try {
            return Integer.parseInt(lastSandboxCreditsRemaining);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Makes a request to the API to retrieve the remaining production credits for the client's API key.
     */
    public Integer getProductionCreditsRemaining() throws ApiException {
        return account().get().getCreditsRemaining();
    }

    /**
     * Makes a request to the API to retrieve the remaining sandbox credits for the client's API key.
     */
    public Integer getSandboxCreditsRemaining() throws ApiException {
        return account().get().getTestCreditsRemaining();
    }

    /**
     * Returns a service for making requests relating to the Zamzar API account associated with the client's API key.
     */
    public AccountService account() {
        return account;
    }

    /**
     * Returns a service for making requests relating to the Zamzar API files.
     */
    public FilesService files() {
        return files;
    }

    /**
     * Returns a service for making requests relating to the Zamzar API formats.
     */
    public FormatsService formats() {
        return formats;
    }

    /**
     * Returns a service for making requests relating to the Zamzar API imports.
     */
    public ImportsService imports() {
        return imports;
    }

    /**
     * Returns a service for making requests relating to the Zamzar API jobs.
     */
    public JobsService jobs() {
        return jobs;
    }
}
