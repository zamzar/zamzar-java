/*
 * Zamzar API
 * Zamzar provides a simple API for fast, scalable, high-quality file conversion for 100s of formats.
 *
 * The version of the OpenAPI document: 0.0.3
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
 * PagingNumeric
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class PagingNumeric {
  public static final String SERIALIZED_NAME_TOTAL_COUNT = "total_count";
  @SerializedName(SERIALIZED_NAME_TOTAL_COUNT)
  private Integer totalCount;

  public static final String SERIALIZED_NAME_FIRST = "first";
  @SerializedName(SERIALIZED_NAME_FIRST)
  private Integer first;

  public static final String SERIALIZED_NAME_LAST = "last";
  @SerializedName(SERIALIZED_NAME_LAST)
  private Integer last;

  public static final String SERIALIZED_NAME_LIMIT = "limit";
  @SerializedName(SERIALIZED_NAME_LIMIT)
  private Integer limit;

  public PagingNumeric() {
  }

  public PagingNumeric totalCount(Integer totalCount) {
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


  public PagingNumeric first(Integer first) {
    this.first = first;
    return this;
  }

   /**
   * The identifier of the first element in this page of the collection
   * @return first
  **/
  @javax.annotation.Nullable
  public Integer getFirst() {
    return first;
  }

  public void setFirst(Integer first) {
    this.first = first;
  }


  public PagingNumeric last(Integer last) {
    this.last = last;
    return this;
  }

   /**
   * The identifier of the last element in this page of the collection
   * @return last
  **/
  @javax.annotation.Nullable
  public Integer getLast() {
    return last;
  }

  public void setLast(Integer last) {
    this.last = last;
  }


  public PagingNumeric limit(Integer limit) {
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
    PagingNumeric pagingNumeric = (PagingNumeric) o;
    return Objects.equals(this.totalCount, pagingNumeric.totalCount) &&
        Objects.equals(this.first, pagingNumeric.first) &&
        Objects.equals(this.last, pagingNumeric.last) &&
        Objects.equals(this.limit, pagingNumeric.limit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalCount, first, last, limit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PagingNumeric {\n");
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
  * @throws IOException if the JSON Element is invalid with respect to PagingNumeric
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!PagingNumeric.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in PagingNumeric is not found in the empty JSON string", PagingNumeric.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!PagingNumeric.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `PagingNumeric` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!PagingNumeric.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'PagingNumeric' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<PagingNumeric> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(PagingNumeric.class));

       return (TypeAdapter<T>) new TypeAdapter<PagingNumeric>() {
           @Override
           public void write(JsonWriter out, PagingNumeric value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public PagingNumeric read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of PagingNumeric given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of PagingNumeric
  * @throws IOException if the JSON string is invalid with respect to PagingNumeric
  */
  public static PagingNumeric fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, PagingNumeric.class);
  }

 /**
  * Convert an instance of PagingNumeric to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

