package com.zamzar.api;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobBuilderTest {

    @ParameterizedTest
    @MethodSource("urlsAndExtensions")
    void remoteFileSetsReasonableFilename(String url, String extension, String expected) throws URISyntaxException {
        assertEquals(
            expected,
            new JobBuilder.RemoteFile(new URI(url)).getFilename(extension)
        );
    }

    private static Stream<Arguments> urlsAndExtensions() {
        return Stream.of(
            // URL / extension / expected filename
            Arguments.of("https://example.com/file.txt", null, "file.txt"),
            Arguments.of("https://example.com/file.txt", "unused", "file.txt"),
            Arguments.of("https://example.com/file", "txt", "file.txt"),
            Arguments.of("https://example.com/file", null, "file"),
            Arguments.of("https://example.com/file.txt?query=param", null, "file.txt"),
            Arguments.of("https://example.com/file.txt?query=param", "unused", "file.txt"),
            Arguments.of("https://example.com/file?query=param", "txt", "file.txt"),
            Arguments.of("https://example.com/file?query=param", null, "file"),
            Arguments.of("https://example.com/", "txt", null),
            Arguments.of("https://example.com/", null, null),
            Arguments.of("https://example.com", "txt", null),
            Arguments.of("https://example.com", null, null)
        );
    }
}
