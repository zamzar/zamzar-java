package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilesServiceTest extends ZamzarApiTest {
    @Test
    public void find() throws ApiException {
        final FileManager file = zamzar().files().find(FILE_ID);
        assertEquals(FILE_ID, file.getId());
    }

    @Test
    public void list() throws ApiException {
        final Paged<FileManager, Integer> files = zamzar().files().list();
        for (FileManager file : files.getItems()) {
            assertTrue(file.getId() > 0);
        }
    }

    @Test
    public void listAndPageForwards() throws ApiException {
        // Note the zamzar-mock provides at least 7 precanned files
        // See: https://github.com/zamzar/zamzar-mock/blob/main/README.md

        final Paged<FileManager, Integer> firstPage = zamzar().files().list(Anchor.after(6), 2);
        assertEquals(Arrays.asList(5, 4), getIdsOfFilesIn(firstPage));

        final Paged<FileManager, Integer> secondPage = firstPage.nextPage();
        assertEquals(Arrays.asList(3, 2), getIdsOfFilesIn(secondPage));

        final Paged<FileManager, Integer> thirdPage = secondPage.nextPage();
        assertEquals(Collections.singletonList(1), getIdsOfFilesIn(thirdPage));

        final Paged<FileManager, Integer> pastEnd = thirdPage.nextPage();
        assertEquals(0, pastEnd.getItems().size());
    }

    @Test
    public void listAndPageBackwards() throws ApiException {
        // Note the zamzar-mock provides at least 7 precanned files
        // See: https://github.com/zamzar/zamzar-mock/blob/main/README.md

        final Paged<FileManager, Integer> firstPage = zamzar().files().list(Anchor.before(1), 2);
        assertEquals(Arrays.asList(3, 2), getIdsOfFilesIn(firstPage));

        final Paged<FileManager, Integer> secondPage = firstPage.previousPage();
        assertEquals(Arrays.asList(5, 4), getIdsOfFilesIn(secondPage));

        final Paged<FileManager, Integer> thirdPage = secondPage.previousPage();
        assertEquals(Arrays.asList(7, 6), getIdsOfFilesIn(thirdPage));

        final Paged<FileManager, Integer> pastEnd = thirdPage.previousPage();
        assertEquals(0, pastEnd.getItems().size());
    }

    @Test
    public void delete() throws ApiException {
        final FileManager file = zamzar().files().delete(FILE_ID);
        assertEquals(FILE_ID, file.getId());
        assert404s(() -> zamzar().files().find(FILE_ID));
    }

    @Test
    public void deleteNonExistent() {
        assert404s(() -> zamzar().files().delete(999999));
    }

    @Test
    public void upload() throws Exception {
        final Path input = createTempFile("input");

        final FileManager file = zamzar().files().upload(input.toFile());

        assertTrue(file.getId() > 0);
    }

    @Test
    public void download() throws Exception {
        final Path output = createTempFile("output");

        assertEmptyFile(output);

        zamzar().files().download(FILE_ID, output.toFile());

        assertNonEmptyFile(output);
    }

    @Test
    public void downloadToDirectory() throws Exception {
        assertEquals(0, findTempFiles().size(), "Sanity check failed. Expected conversion directory to be empty");

        zamzar().files().download(FILE_ID, TEMP_DIR.toFile());

        assertEquals(1, findTempFiles().size(), "Expected conversion directory to contain 2 files");
    }

    @NotNull
    private static List<Integer> getIdsOfFilesIn(Paged<FileManager, Integer> page) {
        return page.getItems().stream().map(FileManager::getId).collect(Collectors.toList());
    }
}
