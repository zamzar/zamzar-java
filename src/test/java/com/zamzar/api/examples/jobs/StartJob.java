package com.zamzar.api.examples.jobs;

import com.zamzar.api.ZamzarClient;

import java.io.File;
import java.net.URI;

public class StartJob {
    public static void main(String[] args) throws Exception {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Convert a local file, wait for it to complete (or throw an exception if it fails), and download the result
        zamzar
            .convert(new File("path/to/your/file.docx"), "pdf")
            .store(new File("path/to/your/file.pdf"));

        // Convert a local file that produces multiple output files (e.g. a multi-page PDF to PNGs)
        zamzar
            .convert(new File("path/to/your/multipage.pdf"), "png")
            .store(new File("path/to/output/directory"));

        // Convert a local file that produces multiple output files and download them as a ZIP file
        zamzar
            .convert(new File("path/to/your/multipage.pdf"), "png")
            .store(new File("path/to/output/directory"), false);

        // Convert a remote file, wait for it to complete (or throw an exception if it fails), and download the result
        zamzar
            .convert(new URI("https://www.zamzar.com/images/zamzar-logo.png"), "jpg")
            .store(new File("path/to/your/file.jpg"));

        // Convert a file in an S3 bucket, wait for it to complete (or throw an exception if it fails), and upload the result to S3
        // (requires Connected Services to be configured in the developer dashboard at https://developers.zamzar.com/)
        zamzar
            .convert(
                new URI("s3://CREDENTIAL_NAME@your-bucket/your-file.docx"),
                "pdf",
                job -> job.exportingTo("s3://CREDENTIAL_NAME@your-bucket/your-file.pdf")
            );

        // Override the source format (if it's not correctly detected from the URL / filename)
        zamzar
            .convert(new URI("https://www.example.com/example-logo"), "jpg", job -> job.from("png"))
            .store(new File("path/to/your/file.jpg"));

        // Specify conversion options
        zamzar
            .convert(new File("path/to/your/file.txt"), "mp3", job -> job.option("voice", "en.female"))
            .store(new File("path/to/your/file.mp3"));

        // Check why a job failed
        System.out.println(zamzar.jobs().find(123456).getFailure());
    }
}
