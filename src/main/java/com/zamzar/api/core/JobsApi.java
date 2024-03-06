/*
 * Zamzar API
 * Zamzar provides a simple API for fast, scalable, high-quality file conversion for 100s of formats.
 *
 * The version of the OpenAPI document: 0.0.2
 * Contact: api-sdks@zamzar.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.zamzar.api.core;

import com.zamzar.api.invoker.ApiCallback;
import com.zamzar.api.invoker.ApiClient;
import com.zamzar.api.invoker.ApiException;
import com.zamzar.api.invoker.ApiResponse;
import com.zamzar.api.invoker.Configuration;
import com.zamzar.api.invoker.Pair;
import com.zamzar.api.invoker.ProgressRequestBody;
import com.zamzar.api.invoker.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.zamzar.api.model.Job;
import com.zamzar.api.model.Jobs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public JobsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public JobsApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for cancelJobById
     * @param jobId Numeric id of the job to cancel (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job cancelled successfully </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call cancelJobByIdCall(Integer jobId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/jobs/{jobId}"
            .replace("{" + "jobId" + "}", localVarApiClient.escapeString(jobId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call cancelJobByIdValidateBeforeCall(Integer jobId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new ApiException("Missing the required parameter 'jobId' when calling cancelJobById(Async)");
        }

        return cancelJobByIdCall(jobId, _callback);

    }

    /**
     * Cancel a job
     * Cancel a job
     * @param jobId Numeric id of the job to cancel (required)
     * @return Job
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job cancelled successfully </td><td>  -  </td></tr>
     </table>
     */
    public Job cancelJobById(Integer jobId) throws ApiException {
        ApiResponse<Job> localVarResp = cancelJobByIdWithHttpInfo(jobId);
        return localVarResp.getData();
    }

    /**
     * Cancel a job
     * Cancel a job
     * @param jobId Numeric id of the job to cancel (required)
     * @return ApiResponse&lt;Job&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job cancelled successfully </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Job> cancelJobByIdWithHttpInfo(Integer jobId) throws ApiException {
        okhttp3.Call localVarCall = cancelJobByIdValidateBeforeCall(jobId, null);
        Type localVarReturnType = new TypeToken<Job>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Cancel a job (asynchronously)
     * Cancel a job
     * @param jobId Numeric id of the job to cancel (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job cancelled successfully </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call cancelJobByIdAsync(Integer jobId, final ApiCallback<Job> _callback) throws ApiException {

        okhttp3.Call localVarCall = cancelJobByIdValidateBeforeCall(jobId, _callback);
        Type localVarReturnType = new TypeToken<Job>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getJobById
     * @param jobId Numeric id of the job to get (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Information about a specific job </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getJobByIdCall(Integer jobId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/jobs/{jobId}"
            .replace("{" + "jobId" + "}", localVarApiClient.escapeString(jobId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getJobByIdValidateBeforeCall(Integer jobId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'jobId' is set
        if (jobId == null) {
            throw new ApiException("Missing the required parameter 'jobId' when calling getJobById(Async)");
        }

        return getJobByIdCall(jobId, _callback);

    }

    /**
     * Retrieve a specific job
     * Retrieve a specific job
     * @param jobId Numeric id of the job to get (required)
     * @return Job
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Information about a specific job </td><td>  -  </td></tr>
     </table>
     */
    public Job getJobById(Integer jobId) throws ApiException {
        ApiResponse<Job> localVarResp = getJobByIdWithHttpInfo(jobId);
        return localVarResp.getData();
    }

    /**
     * Retrieve a specific job
     * Retrieve a specific job
     * @param jobId Numeric id of the job to get (required)
     * @return ApiResponse&lt;Job&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Information about a specific job </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Job> getJobByIdWithHttpInfo(Integer jobId) throws ApiException {
        okhttp3.Call localVarCall = getJobByIdValidateBeforeCall(jobId, null);
        Type localVarReturnType = new TypeToken<Job>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Retrieve a specific job (asynchronously)
     * Retrieve a specific job
     * @param jobId Numeric id of the job to get (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Information about a specific job </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getJobByIdAsync(Integer jobId, final ApiCallback<Job> _callback) throws ApiException {

        okhttp3.Call localVarCall = getJobByIdValidateBeforeCall(jobId, _callback);
        Type localVarReturnType = new TypeToken<Job>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listJobs
     * @param limit Limit the number of results (max 50) (optional, default to 50)
     * @param after Retrieve jobs after the specified jobId (optional)
     * @param before Retrieve jobs after the specified jobId (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of all jobs. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listJobsCall(Integer limit, Integer after, Integer before, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/jobs";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (after != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("after", after));
        }

        if (before != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("before", before));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call listJobsValidateBeforeCall(Integer limit, Integer after, Integer before, final ApiCallback _callback) throws ApiException {
        return listJobsCall(limit, after, before, _callback);

    }

    /**
     * Retrieve a list of all jobs
     * Retrieve a list of all jobs
     * @param limit Limit the number of results (max 50) (optional, default to 50)
     * @param after Retrieve jobs after the specified jobId (optional)
     * @param before Retrieve jobs after the specified jobId (optional)
     * @return Jobs
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of all jobs. </td><td>  -  </td></tr>
     </table>
     */
    public Jobs listJobs(Integer limit, Integer after, Integer before) throws ApiException {
        ApiResponse<Jobs> localVarResp = listJobsWithHttpInfo(limit, after, before);
        return localVarResp.getData();
    }

    /**
     * Retrieve a list of all jobs
     * Retrieve a list of all jobs
     * @param limit Limit the number of results (max 50) (optional, default to 50)
     * @param after Retrieve jobs after the specified jobId (optional)
     * @param before Retrieve jobs after the specified jobId (optional)
     * @return ApiResponse&lt;Jobs&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of all jobs. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Jobs> listJobsWithHttpInfo(Integer limit, Integer after, Integer before) throws ApiException {
        okhttp3.Call localVarCall = listJobsValidateBeforeCall(limit, after, before, null);
        Type localVarReturnType = new TypeToken<Jobs>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Retrieve a list of all jobs (asynchronously)
     * Retrieve a list of all jobs
     * @param limit Limit the number of results (max 50) (optional, default to 50)
     * @param after Retrieve jobs after the specified jobId (optional)
     * @param before Retrieve jobs after the specified jobId (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A list of all jobs. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listJobsAsync(Integer limit, Integer after, Integer before, final ApiCallback<Jobs> _callback) throws ApiException {

        okhttp3.Call localVarCall = listJobsValidateBeforeCall(limit, after, before, _callback);
        Type localVarReturnType = new TypeToken<Jobs>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for submitJob
     * @param sourceFile ID of the source file (optional)
     * @param targetFormat The name of the format to convert the file to (optional)
     * @param sourceFormat Optional. The source format if the file does not have an extension or if the actual format is different to the extension (optional)
     * @param exportUrl Optional. The path to a (S)FTP folder or S3 bucket where the converted file will be transferred after completion (optional)
     * @param options Optional. Specify additional options for the conversion (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job submitted successfully </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call submitJobCall(Integer sourceFile, String targetFormat, String sourceFormat, String exportUrl, Map<String, Object> options, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/jobs";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (sourceFile != null) {
            localVarFormParams.put("source_file", sourceFile);
        }

        if (targetFormat != null) {
            localVarFormParams.put("target_format", targetFormat);
        }

        if (sourceFormat != null) {
            localVarFormParams.put("source_format", sourceFormat);
        }

        if (exportUrl != null) {
            localVarFormParams.put("export_url", exportUrl);
        }

        if (options != null) {
            localVarFormParams.put("options", options);
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "multipart/form-data"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call submitJobValidateBeforeCall(Integer sourceFile, String targetFormat, String sourceFormat, String exportUrl, Map<String, Object> options, final ApiCallback _callback) throws ApiException {
        return submitJobCall(sourceFile, targetFormat, sourceFormat, exportUrl, options, _callback);

    }

    /**
     * Submit a conversion job
     * Submit a conversion job for a local file, an uploaded file, a remote URL or S3 bucket.  ### Starting a job for a local file To start a conversion job and upload a source file in a single request, issue a multipart POST request to the jobs endpoint. One part of the request should specify the target format and (optionally) a source format:   - &#x60;source_file&#x60;: The binary data of the source file containing the following headers:    - &#x60;Content-Type&#x60; - an Internet media type that indicates the format of the data in the body of the request    - &#x60;Content-Length&#x60; - the size, in bytes, of the data in the body of the request  - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to  - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for a url To start a conversion job for a source file that resides on another server, issue a POST request to the jobs endpoint with the following parameters:    - &#x60;source_file&#x60;: The url of the file to be converted. Examples:     - &#x60;http://www.example.com/logo.png&#x60;     - &#x60;https://www.example.com/logo.png&#x60;     - &#x60;https://username:password@www.example.com/logo.png&#x60;     - &#x60;ftp://ftp.example.com/logo.png&#x60;     - &#x60;ftp://username:password@ftp.example.com/logo.png&#x60;     - &#x60;sftp://username:password@sftp.example.com/logo.png&#x60;   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to   - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for an Amazon S3 file To start a conversion job for a source file that resides on Amazon&#39;s Simple Storage Service (S3), first determine whether you need to setup any credentials to access the file. If needed you can configure access to Amazon S3 via the [Connected Services](https://developers.zamzar.com/user/services) page.  Next, determine the S3 URL to send to our API. The S3 URL will use one of the styles shown below. For private files, note that CREDENTIAL_NAME must match exactly the credential name you have used on the [Connected Services](https://developers.zamzar.com/user/services) page.    - Public file - &#x60;s3://my-bucket-name/logo.png&#x60;   - Private file - &#x60;s3://CREDENTIAL_NAME@my-bucket-name/logo.png&#x60;  Once you have determined the S3 URL, issue a POST request to the jobs endpoint with the following parameters:    - &#x60;source_file&#x60;: the Amazon S3 url of the file to be converted   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to   - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for a file which has already been uploaded To start a job for a file that you’ve already uploaded to our servers, issue a POST request to the jobs endpoint, with the following arguments:    - &#x60;source_file&#x60;: the unique identifier of the file that will be converted   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to
     * @param sourceFile ID of the source file (optional)
     * @param targetFormat The name of the format to convert the file to (optional)
     * @param sourceFormat Optional. The source format if the file does not have an extension or if the actual format is different to the extension (optional)
     * @param exportUrl Optional. The path to a (S)FTP folder or S3 bucket where the converted file will be transferred after completion (optional)
     * @param options Optional. Specify additional options for the conversion (optional)
     * @return Job
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job submitted successfully </td><td>  -  </td></tr>
     </table>
     */
    public Job submitJob(Integer sourceFile, String targetFormat, String sourceFormat, String exportUrl, Map<String, Object> options) throws ApiException {
        ApiResponse<Job> localVarResp = submitJobWithHttpInfo(sourceFile, targetFormat, sourceFormat, exportUrl, options);
        return localVarResp.getData();
    }

    /**
     * Submit a conversion job
     * Submit a conversion job for a local file, an uploaded file, a remote URL or S3 bucket.  ### Starting a job for a local file To start a conversion job and upload a source file in a single request, issue a multipart POST request to the jobs endpoint. One part of the request should specify the target format and (optionally) a source format:   - &#x60;source_file&#x60;: The binary data of the source file containing the following headers:    - &#x60;Content-Type&#x60; - an Internet media type that indicates the format of the data in the body of the request    - &#x60;Content-Length&#x60; - the size, in bytes, of the data in the body of the request  - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to  - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for a url To start a conversion job for a source file that resides on another server, issue a POST request to the jobs endpoint with the following parameters:    - &#x60;source_file&#x60;: The url of the file to be converted. Examples:     - &#x60;http://www.example.com/logo.png&#x60;     - &#x60;https://www.example.com/logo.png&#x60;     - &#x60;https://username:password@www.example.com/logo.png&#x60;     - &#x60;ftp://ftp.example.com/logo.png&#x60;     - &#x60;ftp://username:password@ftp.example.com/logo.png&#x60;     - &#x60;sftp://username:password@sftp.example.com/logo.png&#x60;   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to   - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for an Amazon S3 file To start a conversion job for a source file that resides on Amazon&#39;s Simple Storage Service (S3), first determine whether you need to setup any credentials to access the file. If needed you can configure access to Amazon S3 via the [Connected Services](https://developers.zamzar.com/user/services) page.  Next, determine the S3 URL to send to our API. The S3 URL will use one of the styles shown below. For private files, note that CREDENTIAL_NAME must match exactly the credential name you have used on the [Connected Services](https://developers.zamzar.com/user/services) page.    - Public file - &#x60;s3://my-bucket-name/logo.png&#x60;   - Private file - &#x60;s3://CREDENTIAL_NAME@my-bucket-name/logo.png&#x60;  Once you have determined the S3 URL, issue a POST request to the jobs endpoint with the following parameters:    - &#x60;source_file&#x60;: the Amazon S3 url of the file to be converted   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to   - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for a file which has already been uploaded To start a job for a file that you’ve already uploaded to our servers, issue a POST request to the jobs endpoint, with the following arguments:    - &#x60;source_file&#x60;: the unique identifier of the file that will be converted   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to
     * @param sourceFile ID of the source file (optional)
     * @param targetFormat The name of the format to convert the file to (optional)
     * @param sourceFormat Optional. The source format if the file does not have an extension or if the actual format is different to the extension (optional)
     * @param exportUrl Optional. The path to a (S)FTP folder or S3 bucket where the converted file will be transferred after completion (optional)
     * @param options Optional. Specify additional options for the conversion (optional)
     * @return ApiResponse&lt;Job&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job submitted successfully </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Job> submitJobWithHttpInfo(Integer sourceFile, String targetFormat, String sourceFormat, String exportUrl, Map<String, Object> options) throws ApiException {
        okhttp3.Call localVarCall = submitJobValidateBeforeCall(sourceFile, targetFormat, sourceFormat, exportUrl, options, null);
        Type localVarReturnType = new TypeToken<Job>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Submit a conversion job (asynchronously)
     * Submit a conversion job for a local file, an uploaded file, a remote URL or S3 bucket.  ### Starting a job for a local file To start a conversion job and upload a source file in a single request, issue a multipart POST request to the jobs endpoint. One part of the request should specify the target format and (optionally) a source format:   - &#x60;source_file&#x60;: The binary data of the source file containing the following headers:    - &#x60;Content-Type&#x60; - an Internet media type that indicates the format of the data in the body of the request    - &#x60;Content-Length&#x60; - the size, in bytes, of the data in the body of the request  - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to  - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for a url To start a conversion job for a source file that resides on another server, issue a POST request to the jobs endpoint with the following parameters:    - &#x60;source_file&#x60;: The url of the file to be converted. Examples:     - &#x60;http://www.example.com/logo.png&#x60;     - &#x60;https://www.example.com/logo.png&#x60;     - &#x60;https://username:password@www.example.com/logo.png&#x60;     - &#x60;ftp://ftp.example.com/logo.png&#x60;     - &#x60;ftp://username:password@ftp.example.com/logo.png&#x60;     - &#x60;sftp://username:password@sftp.example.com/logo.png&#x60;   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to   - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for an Amazon S3 file To start a conversion job for a source file that resides on Amazon&#39;s Simple Storage Service (S3), first determine whether you need to setup any credentials to access the file. If needed you can configure access to Amazon S3 via the [Connected Services](https://developers.zamzar.com/user/services) page.  Next, determine the S3 URL to send to our API. The S3 URL will use one of the styles shown below. For private files, note that CREDENTIAL_NAME must match exactly the credential name you have used on the [Connected Services](https://developers.zamzar.com/user/services) page.    - Public file - &#x60;s3://my-bucket-name/logo.png&#x60;   - Private file - &#x60;s3://CREDENTIAL_NAME@my-bucket-name/logo.png&#x60;  Once you have determined the S3 URL, issue a POST request to the jobs endpoint with the following parameters:    - &#x60;source_file&#x60;: the Amazon S3 url of the file to be converted   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to   - &#x60;source_format&#x60;: optionally the identifying name of the format of the &#x60;source_file&#x60; (and is used to specify a source format that is different to the extension of the &#x60;source_file&#x60;)  ### Starting a job for a file which has already been uploaded To start a job for a file that you’ve already uploaded to our servers, issue a POST request to the jobs endpoint, with the following arguments:    - &#x60;source_file&#x60;: the unique identifier of the file that will be converted   - &#x60;target_format&#x60;: the identifying name of the format that &#x60;source_file&#x60; will be converted to
     * @param sourceFile ID of the source file (optional)
     * @param targetFormat The name of the format to convert the file to (optional)
     * @param sourceFormat Optional. The source format if the file does not have an extension or if the actual format is different to the extension (optional)
     * @param exportUrl Optional. The path to a (S)FTP folder or S3 bucket where the converted file will be transferred after completion (optional)
     * @param options Optional. Specify additional options for the conversion (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Job submitted successfully </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call submitJobAsync(Integer sourceFile, String targetFormat, String sourceFormat, String exportUrl, Map<String, Object> options, final ApiCallback<Job> _callback) throws ApiException {

        okhttp3.Call localVarCall = submitJobValidateBeforeCall(sourceFile, targetFormat, sourceFormat, exportUrl, options, _callback);
        Type localVarReturnType = new TypeToken<Job>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
