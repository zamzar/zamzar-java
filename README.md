# zamzar-java

Zamzar API
- API version: 0.0.2

Zamzar provides a simple API for fast, scalable, high-quality file conversion for 100s of formats.


*Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech)*


## Requirements

Building the API client library requires:
1. Java 1.8+
2. Maven (3.8.3+)/Gradle (7.2+)

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.zamzar</groupId>
  <artifactId>zamzar-java</artifactId>
  <version>0.0.4</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
  repositories {
    mavenCentral()     // Needed if the 'zamzar-java' jar has been published to maven central.
    mavenLocal()       // Needed if the 'zamzar-java' jar has been published to the local maven repo.
  }

  dependencies {
     implementation "com.zamzar:zamzar-java:0.0.4"
  }
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/zamzar-java-0.0.4.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

// Import classes:
import com.zamzar.api.invoker.ApiClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.invoker.Configuration;
import com.zamzar.api.invoker.auth.*;
import com.zamzar.api.invoker.models.*;
import com.zamzar.api.core.AccountApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.zamzar.com/v1");
    
    // Configure HTTP bearer authorization: ApiKeyAuth
    HttpBearerAuth ApiKeyAuth = (HttpBearerAuth) defaultClient.getAuthentication("ApiKeyAuth");
    ApiKeyAuth.setBearerToken("BEARER TOKEN");

    AccountApi apiInstance = new AccountApi(defaultClient);
    try {
      Account result = apiInstance.getAccount();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AccountApi#getAccount");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://api.zamzar.com/v1*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AccountApi* | [**getAccount**](docs/AccountApi.md#getAccount) | **GET** /account | Retrieve account information
*FilesApi* | [**deleteFileById**](docs/FilesApi.md#deleteFileById) | **DELETE** /files/{fileId} | Delete a file
*FilesApi* | [**getFileById**](docs/FilesApi.md#getFileById) | **GET** /files/{fileId} | Retrieve metadata for a specific file
*FilesApi* | [**getFileContentById**](docs/FilesApi.md#getFileContentById) | **GET** /files/{fileId}/content | Retrieve the content of a file
*FilesApi* | [**listFiles**](docs/FilesApi.md#listFiles) | **GET** /files | Retrieve a list of files
*FilesApi* | [**uploadFile**](docs/FilesApi.md#uploadFile) | **POST** /files | Upload a file
*FormatsApi* | [**getFormatById**](docs/FormatsApi.md#getFormatById) | **GET** /formats/{format} | Retrieve a specific format
*FormatsApi* | [**listFormats**](docs/FormatsApi.md#listFormats) | **GET** /formats | List all formats
*ImportsApi* | [**getImportById**](docs/ImportsApi.md#getImportById) | **GET** /imports/{importId} | Retrieve a specific import
*ImportsApi* | [**listImports**](docs/ImportsApi.md#listImports) | **GET** /imports | Retrieve a list of all imports
*ImportsApi* | [**startImport**](docs/ImportsApi.md#startImport) | **POST** /imports | Start an Import
*JobsApi* | [**cancelJobById**](docs/JobsApi.md#cancelJobById) | **DELETE** /jobs/{jobId} | Cancel a job
*JobsApi* | [**getJobById**](docs/JobsApi.md#getJobById) | **GET** /jobs/{jobId} | Retrieve a specific job
*JobsApi* | [**listJobs**](docs/JobsApi.md#listJobs) | **GET** /jobs | Retrieve a list of all jobs
*JobsApi* | [**submitJob**](docs/JobsApi.md#submitJob) | **POST** /jobs | Submit a conversion job
*WelcomeApi* | [**welcome**](docs/WelcomeApi.md#welcome) | **GET** / | Welcome


## Documentation for Models

 - [Account](docs/Account.md)
 - [AccountPlan](docs/AccountPlan.md)
 - [Error](docs/Error.md)
 - [ErrorContext](docs/ErrorContext.md)
 - [Errors](docs/Errors.md)
 - [Export](docs/Export.md)
 - [Failure](docs/Failure.md)
 - [Files](docs/Files.md)
 - [Format](docs/Format.md)
 - [FormatTargetsInner](docs/FormatTargetsInner.md)
 - [Formats](docs/Formats.md)
 - [Imports](docs/Imports.md)
 - [Job](docs/Job.md)
 - [Jobs](docs/Jobs.md)
 - [ModelFile](docs/ModelFile.md)
 - [ModelImport](docs/ModelImport.md)
 - [PagingNumeric](docs/PagingNumeric.md)
 - [PagingString](docs/PagingString.md)
 - [Welcome200Response](docs/Welcome200Response.md)


<a id="documentation-for-authorization"></a>
## Documentation for Authorization


Authentication schemes defined for the API:
<a id="ApiKeyAuth"></a>
### ApiKeyAuth

- **Type**: HTTP Bearer Token authentication


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

api-sdks@zamzar.com

