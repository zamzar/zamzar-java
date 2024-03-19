/*
 * Zamzar API
 * Zamzar provides a simple API for fast, scalable, high-quality file conversion for 100s of formats.
 *
 * The version of the OpenAPI document: 0.0.5
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


import com.zamzar.api.model.Errors;
import com.zamzar.api.model.Welcome200Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WelcomeApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public WelcomeApi() {
        this(Configuration.getDefaultApiClient());
    }

    public WelcomeApi(ApiClient apiClient) {
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
     * Prints a welcome message; useful for checking connectivity to the API
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
     * Prints a welcome message; useful for checking connectivity to the API
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
     * Prints a welcome message; useful for checking connectivity to the API
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
