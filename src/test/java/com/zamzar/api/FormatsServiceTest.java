package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.model.Format;
import com.zamzar.api.pagination.Anchor;
import com.zamzar.api.pagination.Paged;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FormatsServiceTest extends ZamzarApiTest {
    @Test
    public void find() throws ApiException {
        final Format mp3 = zamzar().formats().find("mp3");

        assertFalse(mp3.getTargets().isEmpty(), "Expected at least one target format");
    }

    @Test
    public void list() throws ApiException {
        final Paged<Format, String> formats = zamzar().formats().list();

        for (Format format : formats.getItems()) {
            assertNotNull(format.getName());
        }
    }

    @Test
    public void listAndPageForwards() throws ApiException {
        int numberOfPages = 0;
        Paged<Format, String> current = zamzar().formats().list(50);
        while (!current.getItems().isEmpty()) {
            numberOfPages++;
            assertTrue(current.getItems().size() <= 50);
            current = current.nextPage();
        }
        assertTrue(numberOfPages > 1);
    }

    @Test
    public void listAndPageBackwards() throws ApiException {
        int numberOfPages = 0;
        Paged<Format, String> current = zamzar().formats().list(Anchor.before("zip"), 50);
        while (!current.getItems().isEmpty()) {
            numberOfPages++;
            assertTrue(current.getItems().size() <= 50);
            current = current.previousPage();
        }
        assertTrue(numberOfPages > 1);
    }
}
