package files;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

import java.io.File;

public class ConvertExistingFile {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Converts a file that is already present on Zamzar's servers to PDF, waits for completion, and downloads PDF
        zamzar
            .convert(123456, "pdf")
            .awaitOrThrow()
            .store(new File("path/to/your/file.pdf"));
    }
}
