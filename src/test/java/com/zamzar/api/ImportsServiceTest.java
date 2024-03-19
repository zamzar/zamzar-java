package com.zamzar.api;

import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImportsServiceTest extends ZamzarApiTest {
    @Test
    public void find() throws Exception {
        final ImportManager _import = zamzar().imports().find(SUCCEEDING_IMPORT_ID);

        assertEquals(SUCCEEDING_IMPORT_ID, _import.getId());
    }

    @Test
    public void list() throws Exception {
        final Paged<ImportManager, Integer> imports = zamzar().imports().list();

        for (ImportManager _import : imports.getItems()) {
            assertTrue(_import.getId() > 0);
        }
    }

    @Test
    public void listAndPageForwards() throws Exception {
        // There are at least 3 imports in the mock server
        // We'll therefore navigate 2 items at a time, and can expect at least 2 pages
        int numberOfPages = 0;
        Paged<ImportManager, Integer> current = zamzar().imports().list(2);
        while (!current.getItems().isEmpty()) {
            numberOfPages++;
            assertTrue(current.getItems().size() <= 2);
            current = current.nextPage();
        }
        assertTrue(numberOfPages >= 2);
    }

    @Test
    public void listAndPageBackwards() throws Exception {
        // There are at least 3 imports in the mock server, and using an anchor reduces that to at least 2 imports
        // We'll therefore navigate 1 item at a time, and can expect at least 2 pages
        int numberOfPages = 0;
        Paged<ImportManager, Integer> current = zamzar().imports().list(Anchor.before(1), 1);
        while (!current.getItems().isEmpty()) {
            numberOfPages++;
            assertTrue(current.getItems().size() <= 1);
            current = current.previousPage();
        }
        assertTrue(numberOfPages >= 2);
    }

    @Test
    public void start() throws Exception {
        final Path downloaded = TEMP_DIR.resolve("imported-file.txt");

        zamzar()
            .imports()
            .start("s3://bucket-name/path/to/import")
            .awaitOrThrow()
            .getImportedFile()
            .download(downloaded.toFile());

        // Check that a non-empty file has been downloaded
        assertTrue(Files.size(downloaded) > 0, "Expected non-empty file: " + downloaded);
    }

    @Test
    public void startForUrlWithUnknownFilenameRequiresFilenameParam() {
        assert422s(() -> zamzar().imports().start("s3://bucket-name/path/to/unknown"));
        assert200s(() -> zamzar().imports().start("s3://bucket-name/path/to/unknown", "filename.txt"));
    }
}
