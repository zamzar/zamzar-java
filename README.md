# Java file converter library for Zamzar

[![@zamzar on Twitter](https://img.shields.io/badge/twitter-zamzar-blue)](https://twitter.com/zamzar)
![Maven Central Version](https://img.shields.io/maven-central/v/com.zamzar/zamzar-java)
[![GitHub License](https://img.shields.io/github/license/zamzar/zamzar-mock)](https://github.com/zamzar/zamzar-mock/blob/main/LICENSE)

Easy to use Java file conversion API with support for 1,100+ file conversions - convert documents, audio, images, video, eBooks and more. Use `zamzar-java` to convert files between different formats as part of your Java application with the [Zamzar file conversion API](https://developers.zamzar.com). Common use cases include:

- Convert Microsoft Word (DOCX, DOC) to PDF
- Archive email (MSG files) to PDF
- Convert Powerpoint (PPT, PPTX) to JPG
- Extract text from PDF files

This is the official Java SDK for the [Zamzar API](https://developers.zamzar.com).

Jump to:

- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Resources](#resources)

## Requirements

- Before you begin, signup for a Zamzar API Account or retrieve your existing API Key from
  the [Zamzar Developers Homepage](https://developers.zamzar.com/user)
- Java 8 and later.

## Installation

### Maven users

Add this dependency to your project's POM:

```xml

<dependency>
    <groupId>com.zamzar</groupId>
    <artifactId>zamzar-java</artifactId>
    <version>1.0.3</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "com.zamzar:zamzar-java:1.0.3"
```

## Usage

### Getting Started

Please follow the [installation](#installation) instructions and execute the following Java code:

```java
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
```

See the [examples](https://github.com/zamzar/zamzar-java/tree/main/src/test/java/com/zamzar/api/examples) to learn more
about how to use the Zamzar Java library.

### Using the sandbox environment

Whilst developing your application, you can use the Zamzar sandbox environment to test your code without consuming
production credits:

```java
import com.zamzar.api.ZamzarClient;
import com.zamzar.api.ZamzarEnvironment;

ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE", ZamzarEnvironment.SANDBOX);
```

The Zamzar Java library uses the production environment by default, but you can also specify it explicitly:

```java
import com.zamzar.api.ZamzarClient;
import com.zamzar.api.ZamzarEnvironment;

ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE", ZamzarEnvironment.PRODUCTION);
```

### Logging

By default, the Zamzar Java library does not log HTTP requests and responses. To enable logging, configure a
[LoggingInterceptor](https://square.github.io/okhttp/5.x/logging-interceptor/okhttp3.logging/-http-logging-interceptor/index.html);
such as in
[this example](https://github.com/zamzar/zamzar-java/blob/e842490d1e4c2aba7d7e7c49f967c7ecb8a90062/src/test/java/com/zamzar/api/examples/client/Logging.java).

### Configuring timeouts and retries

The Zamzar Java library will automatically:

* time out long-running requests
* retry requests that fail or time out

The default settings are defined in
[ZamzarClient#getDefaultTransportBuilder()](https://github.com/zamzar/zamzar-java/blob/main/src/main/java/com/zamzar/api/ZamzarClient.java).

To override these defaults, configure your
own [okhttp3.OkHttpClient](https://square.github.io/okhttp/5.x/okhttp/okhttp3/-ok-http-client/index.html) and pass it to
the `ZamzarClient` constructor:

```java
OkHttpClient customHttpClient = new OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .writeTimeout(10, TimeUnit.SECONDS)
    .addInterceptor(/* your own retry interceptor */)
    .build();
ZamzarClient zamzar = new ZamzarClient("YOUR_API_KEY_GOES_HERE", customTransport);
```

## Resources

[Code Samples](https://github.com/zamzar/zamzar-java/tree/main/src/test/java/com/zamzar/api/examples) - Copy/Paste from
examples which demonstrate all key areas of functionality.

[Developer Docs](https://developers.zamzar.com/docs) - For more information about API operations, parameters, and
responses. Use this if you need additional context on all areas of functionality.
