package imports;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

public class StartImport {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Start an import, wait for it to complete, or throw an exception if it fails
        zamzar
            .imports()
            .start("https://www.zamzar.com/images/zamzar-logo.png")
            .awaitOrThrow();

        // Check why an import failed
        System.out.println(zamzar.imports().find(123456).getFailure());
    }
}
