package com.zamzar.api.examples.files;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

import java.io.File;

public class DeleteFile {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Delete the file with the specified ID from the Zamzar API
        zamzar.files().delete(123456);

        // Or if you need to download and then delete:
        zamzar.download(123456, new File("path/to/your/file.docx")).delete();
    }
}
