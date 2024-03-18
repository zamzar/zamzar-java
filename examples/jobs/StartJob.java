package jobs;

import com.zamzar.api.ZamzarClient;
import com.zamzar.api.invoker.ApiException;

import java.io.File;

public class StartJob {
    public static void main(String[] args) throws ApiException {
        ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE");

        // Convert a local file, wait for it to complete (or throw an exception if it fails), and download the result
        zamzar
            .convert(new File("path/to/your/file.docx"), "pdf")
            .awaitOrThrow()
            .store(new File("path/to/your/file.pdf"));

        // Convert a remote file, wait for it to complete (or throw an exception if it fails), and download the result
        zamzar
            .convert("https://www.zamzar.com/images/zamzar-logo.png", "jpg")
            .awaitOrThrow()
            .store(new File("path/to/your/file.jpg"));

        // Convert a file in an S3 bucket, wait for it to complete (or throw an exception if it fails), and upload the result to S3
        // (requires Connected Services to be configured in the developer dashboard at https://developers.zamzar.com/)
        zamzar
            .convert(
                "s3://CREDENTIAL_NAME@your-bucket/your-file.docx",
                "pdf",
                job -> job.exportingTo("s3://CREDENTIAL_NAME@your-bucket/your-file.pdf")
            )
            .awaitOrThrow();

        // Override the source format (if it's not correctly detected from the URL / filename)
        zamzar
            .convert("https://www.zamzar.com/images/zamzar-logo", "jpg", job -> job.from("png"))
            .awaitOrThrow()
            .store(new File("path/to/your/file.jpg"));

        // Specify conversion options
        zamzar
            .convert(new File("path/to/your/file.txt"), "mp3", job -> job.option("voice", "en.female"))
            .awaitOrThrow()
            .store(new File("path/to/your/file.mp3"));

        // Check why a job failed
        System.out.println(zamzar.jobs().find(123456).getFailure());
    }
}