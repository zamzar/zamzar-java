package client;

import com.zamzar.api.ZamzarClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class Logging {
    public static void main(String[] args) {
        // Create an OkHttp logging interceptor
        // see: https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        logging.redactHeader("Authorization");

        // Create an OkHttp Client that uses the logging interceptor
        OkHttpClient transport = ZamzarClient.getDefaultTransportBuilder()
            .addInterceptor(logging)
            .build();

        // Create a ZamzarClient that uses the custom OkHttp Client
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE", transport);
    }
}
