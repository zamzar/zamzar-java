package files;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

import java.io.File;

public class UploadFile {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Upload a file to the Zamzar API
        zamzar.upload(new File("path/to/your/file.docx"));
    }
}
