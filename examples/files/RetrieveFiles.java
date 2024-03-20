package files;

import com.zamzar.api.FileManager;
import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.ModelFile;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;

public class RetrieveFiles {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Retrieve information about a single file
        ModelFile file = zamzar.files().find(123456).getModel();
        System.out.println("File ID: " + file.getId() + " was created at " + file.getCreatedAt());

        // List files (returns at most 50 files at a time)
        for (FileManager fileManager : zamzar.files().list().getItems()) {
            System.out.println("File ID: " + fileManager.getModel().getId() + " was created at " + fileManager.getModel().getCreatedAt());
        }

        // To page through all formats, use the nextPage method:
        Paged<FileManager, Integer> currentPage = zamzar.files().list();
        do {
            for (FileManager fileManager : currentPage.getItems()) {
                System.out.println("File ID: " + fileManager.getModel().getId() + " was created at " + fileManager.getModel().getCreatedAt());
            }
            currentPage = currentPage.nextPage();
        } while (!currentPage.getItems().isEmpty());

        // For fine-grained control over pagination, use an anchor and a limit
        // For example, retrieve the 20 files immediately after file ID 123456
        Paged<FileManager, Integer> targetedPage = zamzar.files().list(Anchor.after(123456), 20);
    }
}
