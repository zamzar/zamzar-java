package com.zamzar.api.examples.imports;

import com.zamzar.api.ImportManager;
import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.ModelImport;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;

public class RetrieveImports {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Retrieve information about a single import
        ModelImport _import = zamzar.imports().find(123456).getModel();
        System.out.println("Import ID: " + _import.getId() + " was created at " + _import.getCreatedAt());

        // List imports (returns at most 50 imports at a time)
        for (ImportManager importManager : zamzar.imports().list().getItems()) {
            System.out.println("Import ID: " + importManager.getModel().getId() + " was created at " + importManager.getModel().getCreatedAt());
        }

        // To page through all imports, use the nextPage method:
        Paged<ImportManager, Integer> currentPage = zamzar.imports().list();
        do {
            for (ImportManager importManager : currentPage.getItems()) {
                System.out.println("Import ID: " + importManager.getModel().getId() + " was created at " + importManager.getModel().getCreatedAt());
            }
            currentPage = currentPage.nextPage();
        } while (!currentPage.getItems().isEmpty());

        // For fine-grained control over pagination, use an anchor and a limit
        // For example, retrieve the 20 imports immediately after import ID 123456
        Paged<ImportManager, Integer> targetedPage = zamzar.imports().list(Anchor.after(123456), 20);
    }
}
