package com.zamzar.api;

import com.zamzar.api.invoker.ApiException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JobBuilderTest {

    @ParameterizedTest
    @MethodSource("filenameCanBeInferred")
    void remoteFileSetsReasonableFilename(String url, String extension, String expected) throws Exception {
        assertEquals(
            expected,
            new JobBuilder.RemoteFile(new URI(url)).getFilename(extension)
        );
    }

    @ParameterizedTest
    @MethodSource("filenameCannotBeInferred")
    void remoteFileThrowsWhenFilenameCannotBeInferred(String url, String extension) {
        assertThrows(
            ApiException.class,
            () -> new JobBuilder.RemoteFile(new URI(url)).getFilename(extension)
        );
    }

    private static Stream<Arguments> filenameCanBeInferred() {
        return Stream.of(
            // URL / extension / expected filename
            Arguments.of("https://example.com/file.txt", null, "file.txt"),
            Arguments.of("https://example.com/file.txt", "unused", "file.txt"),
            Arguments.of("https://example.com/file", "txt", "file.txt"),
            Arguments.of("https://example.com/file.txt?query=param", null, "file.txt"),
            Arguments.of("https://example.com/file.txt?query=param", "unused", "file.txt"),
            Arguments.of("https://example.com/file?query=param", "txt", "file.txt")
        );
    }

    private static Stream<Arguments> filenameCannotBeInferred() {
        return Stream.of(
            // URL / extension
            Arguments.of("https://example.com/file", null),
            Arguments.of("https://example.com/file?query=param", null, "file"),
            Arguments.of("https://example.com/", "txt", null),
            Arguments.of("https://example.com/", null, null),
            Arguments.of("https://example.com", "txt", null),
            Arguments.of("https://example.com", null, null)
        );
    }
}
