/*
 * Zamzar API
 * Zamzar provides a simple API for fast, scalable, high-quality file conversion for 100s of formats.
 *
 * The version of the OpenAPI document: 0.0.7
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


import com.zamzar.api.model.Format;
import com.zamzar.api.model.Formats;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormatsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public FormatsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public FormatsApi(ApiClient apiClient) {
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
     * Build call for getFormatById
     * @param format The format to retrieve (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A specific source format and valid target formats.  &#x60;&#x60;&#x60;json {   \&quot;name\&quot;: \&quot;doc\&quot;,   \&quot;targets\&quot;: [     {       \&quot;name\&quot;: \&quot;docx\&quot;,       \&quot;credit_cost\&quot;: 1     },     {       \&quot;name\&quot;: \&quot;pdf\&quot;,       \&quot;credit_cost\&quot;: 1     }   ] } &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Unsupported Format. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFormatByIdCall(String format, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/formats/{format}"
            .replace("{" + "format" + "}", localVarApiClient.escapeString(format.toString()));

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
    private okhttp3.Call getFormatByIdValidateBeforeCall(String format, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'format' is set
        if (format == null) {
            throw new ApiException("Missing the required parameter 'format' when calling getFormatById(Async)");
        }

        return getFormatByIdCall(format, _callback);

    }

    /**
     * Retrieve a specific format
     * Retrieve a specific format and the cost of performing a conversion to one or more target formats. 
     * @param format The format to retrieve (required)
     * @return Format
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A specific source format and valid target formats.  &#x60;&#x60;&#x60;json {   \&quot;name\&quot;: \&quot;doc\&quot;,   \&quot;targets\&quot;: [     {       \&quot;name\&quot;: \&quot;docx\&quot;,       \&quot;credit_cost\&quot;: 1     },     {       \&quot;name\&quot;: \&quot;pdf\&quot;,       \&quot;credit_cost\&quot;: 1     }   ] } &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Unsupported Format. </td><td>  -  </td></tr>
     </table>
     */
    public Format getFormatById(String format) throws ApiException {
        ApiResponse<Format> localVarResp = getFormatByIdWithHttpInfo(format);
        return localVarResp.getData();
    }

    /**
     * Retrieve a specific format
     * Retrieve a specific format and the cost of performing a conversion to one or more target formats. 
     * @param format The format to retrieve (required)
     * @return ApiResponse&lt;Format&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A specific source format and valid target formats.  &#x60;&#x60;&#x60;json {   \&quot;name\&quot;: \&quot;doc\&quot;,   \&quot;targets\&quot;: [     {       \&quot;name\&quot;: \&quot;docx\&quot;,       \&quot;credit_cost\&quot;: 1     },     {       \&quot;name\&quot;: \&quot;pdf\&quot;,       \&quot;credit_cost\&quot;: 1     }   ] } &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Unsupported Format. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Format> getFormatByIdWithHttpInfo(String format) throws ApiException {
        okhttp3.Call localVarCall = getFormatByIdValidateBeforeCall(format, null);
        Type localVarReturnType = new TypeToken<Format>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Retrieve a specific format (asynchronously)
     * Retrieve a specific format and the cost of performing a conversion to one or more target formats. 
     * @param format The format to retrieve (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> A specific source format and valid target formats.  &#x60;&#x60;&#x60;json {   \&quot;name\&quot;: \&quot;doc\&quot;,   \&quot;targets\&quot;: [     {       \&quot;name\&quot;: \&quot;docx\&quot;,       \&quot;credit_cost\&quot;: 1     },     {       \&quot;name\&quot;: \&quot;pdf\&quot;,       \&quot;credit_cost\&quot;: 1     }   ] } &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Unsupported Format. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getFormatByIdAsync(String format, final ApiCallback<Format> _callback) throws ApiException {

        okhttp3.Call localVarCall = getFormatByIdValidateBeforeCall(format, _callback);
        Type localVarReturnType = new TypeToken<Format>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listFormats
     * @param limit Limit the number of results (max 50) (optional)
     * @param after Retrieve formats after the specified format (optional)
     * @param before Retrieve formats before the specified format (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Source formats are ordered alphabetically within the &#x60;data&#x60; array of formats and include the target formats and associated cost of conversion.  A &#x60;paging&#x60; object is included within the results, with each page having a limit of 50 records by default. Request the next page by using the &#x60;after&#x60; parameter in reference to the &#x60;last&#x60; property within the paging object of the current response.  For example to retrieve the next page of formats after the &#x60;msg&#x60; format:  &#x60;&#x60;&#x60; https://api.zamzar.com/v1/formats/?after&#x3D;msg &#x60;&#x60;&#x60;  To limit the results to 10 records:  &#x60;&#x60;&#x60; https://api.zamzar.com/v1/formats/?after&#x3D;msg&amp;limit&#x3D;10 &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listFormatsCall(Integer limit, String after, String before, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/formats";

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
    private okhttp3.Call listFormatsValidateBeforeCall(Integer limit, String after, String before, final ApiCallback _callback) throws ApiException {
        return listFormatsCall(limit, after, before, _callback);

    }

    /**
     * List all formats
     * Retrieve a list of all source formats which have one or more target (conversion) formats.
     * @param limit Limit the number of results (max 50) (optional)
     * @param after Retrieve formats after the specified format (optional)
     * @param before Retrieve formats before the specified format (optional)
     * @return Formats
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Source formats are ordered alphabetically within the &#x60;data&#x60; array of formats and include the target formats and associated cost of conversion.  A &#x60;paging&#x60; object is included within the results, with each page having a limit of 50 records by default. Request the next page by using the &#x60;after&#x60; parameter in reference to the &#x60;last&#x60; property within the paging object of the current response.  For example to retrieve the next page of formats after the &#x60;msg&#x60; format:  &#x60;&#x60;&#x60; https://api.zamzar.com/v1/formats/?after&#x3D;msg &#x60;&#x60;&#x60;  To limit the results to 10 records:  &#x60;&#x60;&#x60; https://api.zamzar.com/v1/formats/?after&#x3D;msg&amp;limit&#x3D;10 &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
     </table>
     */
    public Formats listFormats(Integer limit, String after, String before) throws ApiException {
        ApiResponse<Formats> localVarResp = listFormatsWithHttpInfo(limit, after, before);
        return localVarResp.getData();
    }

    /**
     * List all formats
     * Retrieve a list of all source formats which have one or more target (conversion) formats.
     * @param limit Limit the number of results (max 50) (optional)
     * @param after Retrieve formats after the specified format (optional)
     * @param before Retrieve formats before the specified format (optional)
     * @return ApiResponse&lt;Formats&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Source formats are ordered alphabetically within the &#x60;data&#x60; array of formats and include the target formats and associated cost of conversion.  A &#x60;paging&#x60; object is included within the results, with each page having a limit of 50 records by default. Request the next page by using the &#x60;after&#x60; parameter in reference to the &#x60;last&#x60; property within the paging object of the current response.  For example to retrieve the next page of formats after the &#x60;msg&#x60; format:  &#x60;&#x60;&#x60; https://api.zamzar.com/v1/formats/?after&#x3D;msg &#x60;&#x60;&#x60;  To limit the results to 10 records:  &#x60;&#x60;&#x60; https://api.zamzar.com/v1/formats/?after&#x3D;msg&amp;limit&#x3D;10 &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Formats> listFormatsWithHttpInfo(Integer limit, String after, String before) throws ApiException {
        okhttp3.Call localVarCall = listFormatsValidateBeforeCall(limit, after, before, null);
        Type localVarReturnType = new TypeToken<Formats>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List all formats (asynchronously)
     * Retrieve a list of all source formats which have one or more target (conversion) formats.
     * @param limit Limit the number of results (max 50) (optional)
     * @param after Retrieve formats after the specified format (optional)
     * @param before Retrieve formats before the specified format (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Source formats are ordered alphabetically within the &#x60;data&#x60; array of formats and include the target formats and associated cost of conversion.  A &#x60;paging&#x60; object is included within the results, with each page having a limit of 50 records by default. Request the next page by using the &#x60;after&#x60; parameter in reference to the &#x60;last&#x60; property within the paging object of the current response.  For example to retrieve the next page of formats after the &#x60;msg&#x60; format:  &#x60;&#x60;&#x60; https://api.zamzar.com/v1/formats/?after&#x3D;msg &#x60;&#x60;&#x60;  To limit the results to 10 records:  &#x60;&#x60;&#x60; https://api.zamzar.com/v1/formats/?after&#x3D;msg&amp;limit&#x3D;10 &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listFormatsAsync(Integer limit, String after, String before, final ApiCallback<Formats> _callback) throws ApiException {

        okhttp3.Call localVarCall = listFormatsValidateBeforeCall(limit, after, before, _callback);
        Type localVarReturnType = new TypeToken<Formats>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
