package files;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

import java.io.File;

public class DownloadFile {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Download the file with the specified ID from the Zamzar API
        zamzar.download(123456, new File("path/to/your/file.docx"));
    }
}
