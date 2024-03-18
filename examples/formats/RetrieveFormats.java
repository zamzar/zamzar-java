package formats;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Format;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;

import java.util.List;

public class RetrieveFormats {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Retrieve information about a single format
        Format jpg = zamzar.formats().find("jpg");
        System.out.println(jpg);

        // Viewing the target conversion options for a format
        jpg
            .getTargets()
            .forEach(t -> System.out.println(t.getName() + " (" + t.getCreditCost() + " credits)"));

        // List all formats
        List<Format> formats = zamzar.formats().list().getItems();

        // List will return at most 50 formats at a time; to paginate:
        Paged<Format, String> currentPage = zamzar.formats().list();
        do {
            for (Format format : currentPage.getItems()) {
                System.out.println(format);
            }
            currentPage = currentPage.nextPage();
        } while (!currentPage.getItems().isEmpty());

        // For fine-grained control over pagination, use an anchor and a limit
        // For example, retrieve the 20 formats immediately after the "jpg" format
        Paged<Format, String> targetedPage = zamzar.formats().list(Anchor.after("jpg"), 20);
    }
}
