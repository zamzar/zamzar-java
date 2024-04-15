package com.zamzar.api.examples;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

import java.io.File;

public class GettingStarted {
    public static void main(String[] args) throws ApiException {
        // Signup for a Zamzar API Account or retrieve your existing API Key from https://developers.zamzar.com
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Converts /tmp/example.docx to /tmp/example.pdf
        zamzar
            .convert(new File("/tmp/example.docx"), "pdf") // uploads and converts your file
            .store(new File("/tmp/")) // downloads the converted file
            .deleteAllFiles(); // removes your files (example.docx and example.pdf) from Zamzar's servers
    }
}
