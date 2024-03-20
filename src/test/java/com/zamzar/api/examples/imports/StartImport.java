package com.zamzar.api.examples.imports;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

public class StartImport {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Start an import, wait for it to complete, or throw an exception if it fails
        final int importedFileId = zamzar
            .imports()
            .start("https://www.zamzar.com/images/zamzar-logo.png")
            .awaitOrThrow()
            .getImportedFile()
            .getId();
        System.out.println("Imported file ID: " + importedFileId);

        // Check why an import failed
        System.out.println(zamzar.imports().find(123456).getFailure());
    }
}
