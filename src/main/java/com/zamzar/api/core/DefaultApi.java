/*
 * Zamzar API
 * ### Introduction   Zamzar provides a simple API for fast, scalable, high-quality file conversion for 100s of formats. Our API can be used to convert files on your machine, or files accessible over HTTP, FTP, SFTP or even Amazon S3.   ### Custom Headers  All requests to Zamzar endpoints include the following headers in the response:  `Zamzar-Credits-Remaining` shows the number of file conversion credits remaining in the current billing period  `Zamzar-Test-Credits-Remaining` shows the number of credits remaining in the current billing period for `sandbox.zamzar.com`  ### Response Codes  Every response contains an appropriate HTTP status code:  ``` Successful requests: 200 OK 201 CREATED - a new resource was created 204 NO CONTENT - the body of the response is empty 307 TEMPORARY REDIRECT - access the resource by following the location header of the response  Errors: 401 FORBIDDEN - the request is not using authentication 402 PAYMENT REQUIRED - the request cannot be completed because this account does not have sufficient credit 404 NOT FOUND - the requested resource does not exist 410 GONE - the requested resource is no longer available 413 PAYLOAD TOO LARGE - the request is too large, typically because a source file exceeds the maximum file size for this account 422 UNPROCESSABLE - the request was malformed (e.g. missing a required parameter) 429 TOO MAY REQUESTS - the request has exceeded a rate limit  500 INTERNAL SERVER ERROR - an unexpected internal error has occurred 503 UNAVAILABLE - the API is temporarily unavailable ```  ### Rate Limits  In order to provide a stable and reliable service for all users, rate limits are applied to different API endpoints: 
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: accounts@zamzar.com
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


import com.zamzar.api.model.Errors;
import com.zamzar.api.model.Welcome200Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public DefaultApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DefaultApi(ApiClient apiClient) {
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
     * Build call for welcome
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Welcome to the Zamzar API </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Forbidden - the request is not using authentication. The response will include an array of &#x60;errors&#x60;. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call welcomeCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/";

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
    private okhttp3.Call welcomeValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return welcomeCall(_callback);

    }

    /**
     * Welcome
     * 
     * @return Welcome200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Welcome to the Zamzar API </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Forbidden - the request is not using authentication. The response will include an array of &#x60;errors&#x60;. </td><td>  -  </td></tr>
     </table>
     */
    public Welcome200Response welcome() throws ApiException {
        ApiResponse<Welcome200Response> localVarResp = welcomeWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Welcome
     * 
     * @return ApiResponse&lt;Welcome200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Welcome to the Zamzar API </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Forbidden - the request is not using authentication. The response will include an array of &#x60;errors&#x60;. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Welcome200Response> welcomeWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = welcomeValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<Welcome200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Welcome (asynchronously)
     * 
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Welcome to the Zamzar API </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Forbidden - the request is not using authentication. The response will include an array of &#x60;errors&#x60;. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call welcomeAsync(final ApiCallback<Welcome200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = welcomeValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<Welcome200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
