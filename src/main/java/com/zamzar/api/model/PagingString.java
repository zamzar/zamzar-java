/*
 * Zamzar API
 * Zamzar provides a simple API for fast, scalable, high-quality file conversion for 100s of formats.
 *
 * The version of the OpenAPI document: 0.0.4
 * Contact: api-sdks@zamzar.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.zamzar.api.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zamzar.api.invoker.JSON;

/**
 * When you request a list of of all resources of any type (files, formats, jobs), you will receive a paged collection as a response. A paged collection allows you to view a subset of the entire collection (limited to 50 elements) and makes it easy to implement pagination in your application. Use the &#x60;limit&#x60; parameter to limit the number of results and the &#x60;after&#x60; parameter to request the next page of results (based on the value of &#x60;last&#x60; within the paging object).
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class PagingString {
  public static final String SERIALIZED_NAME_TOTAL_COUNT = "total_count";
  @SerializedName(SERIALIZED_NAME_TOTAL_COUNT)
  private Integer totalCount;

  public static final String SERIALIZED_NAME_FIRST = "first";
  @SerializedName(SERIALIZED_NAME_FIRST)
  private String first;

  public static final String SERIALIZED_NAME_LAST = "last";
  @SerializedName(SERIALIZED_NAME_LAST)
  private String last;

  public static final String SERIALIZED_NAME_LIMIT = "limit";
  @SerializedName(SERIALIZED_NAME_LIMIT)
  private Integer limit;

  public PagingString() {
  }

  public PagingString totalCount(Integer totalCount) {
    this.totalCount = totalCount;
    return this;
  }

   /**
   * The number of elements in the entire collection
   * @return totalCount
  **/
  @javax.annotation.Nullable
  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }


  public PagingString first(String first) {
    this.first = first;
    return this;
  }

   /**
   * The identifier of the first element in this page of the collection
   * @return first
  **/
  @javax.annotation.Nullable
  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }


  public PagingString last(String last) {
    this.last = last;
    return this;
  }

   /**
   * The identifier of the last element in this page of the collection
   * @return last
  **/
  @javax.annotation.Nullable
  public String getLast() {
    return last;
  }

  public void setLast(String last) {
    this.last = last;
  }


  public PagingString limit(Integer limit) {
    this.limit = limit;
    return this;
  }

   /**
   * The maximum number of elements this page could contain
   * @return limit
  **/
  @javax.annotation.Nullable
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PagingString pagingString = (PagingString) o;
    return Objects.equals(this.totalCount, pagingString.totalCount) &&
        Objects.equals(this.first, pagingString.first) &&
        Objects.equals(this.last, pagingString.last) &&
        Objects.equals(this.limit, pagingString.limit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalCount, first, last, limit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PagingString {\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("    first: ").append(toIndentedString(first)).append("\n");
    sb.append("    last: ").append(toIndentedString(last)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("total_count");
    openapiFields.add("first");
    openapiFields.add("last");
    openapiFields.add("limit");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to PagingString
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!PagingString.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in PagingString is not found in the empty JSON string", PagingString.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!PagingString.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `PagingString` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("first") != null && !jsonObj.get("first").isJsonNull()) && !jsonObj.get("first").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `first` to be a primitive type in the JSON string but got `%s`", jsonObj.get("first").toString()));
      }
      if ((jsonObj.get("last") != null && !jsonObj.get("last").isJsonNull()) && !jsonObj.get("last").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `last` to be a primitive type in the JSON string but got `%s`", jsonObj.get("last").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PagingString.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PagingString' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PagingString> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PagingString.class));

       return (TypeAdapter<T>) new TypeAdapter<PagingString>() {
           @Override
           public void write(JsonWriter out, PagingString value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PagingString read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of PagingString given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of PagingString
  * @throws IOException if the JSON string is invalid with respect to PagingString
  */
  public static PagingString fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PagingString.class);
  }

 /**
  * Convert an instance of PagingString to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

