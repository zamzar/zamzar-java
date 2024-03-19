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
import org.openapitools.jackson.nullable.JsonNullable;

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
 * AccountPlan
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class AccountPlan {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_PRICE_PER_MONTH = "price_per_month";
  @SerializedName(SERIALIZED_NAME_PRICE_PER_MONTH)
  private Float pricePerMonth;

  public static final String SERIALIZED_NAME_CONVERSIONS_PER_MONTH = "conversions_per_month";
  @SerializedName(SERIALIZED_NAME_CONVERSIONS_PER_MONTH)
  private Integer conversionsPerMonth;

  public static final String SERIALIZED_NAME_MAXIMUM_FILE_SIZE = "maximum_file_size";
  @SerializedName(SERIALIZED_NAME_MAXIMUM_FILE_SIZE)
  private Long maximumFileSize;

  public AccountPlan() {
  }

  public AccountPlan name(String name) {
    this.name = name;
    return this;
  }

   /**
   * The name used to describe this plan
   * @return name
  **/
  @javax.annotation.Nullable
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public AccountPlan pricePerMonth(Float pricePerMonth) {
    this.pricePerMonth = pricePerMonth;
    return this;
  }

   /**
   * The monthly subscription cost for this plan
   * @return pricePerMonth
  **/
  @javax.annotation.Nullable
  public Float getPricePerMonth() {
    return pricePerMonth;
  }

  public void setPricePerMonth(Float pricePerMonth) {
    this.pricePerMonth = pricePerMonth;
  }


  public AccountPlan conversionsPerMonth(Integer conversionsPerMonth) {
    this.conversionsPerMonth = conversionsPerMonth;
    return this;
  }

   /**
   * The number of conversion credits included in this plan per month
   * @return conversionsPerMonth
  **/
  @javax.annotation.Nullable
  public Integer getConversionsPerMonth() {
    return conversionsPerMonth;
  }

  public void setConversionsPerMonth(Integer conversionsPerMonth) {
    this.conversionsPerMonth = conversionsPerMonth;
  }


  public AccountPlan maximumFileSize(Long maximumFileSize) {
    this.maximumFileSize = maximumFileSize;
    return this;
  }

   /**
   * The maximum size (in bytes) of files that can be uploaded with this plan; or &#x60;null&#x60; if this plan has no such cap.
   * @return maximumFileSize
  **/
  @javax.annotation.Nullable
  public Long getMaximumFileSize() {
    return maximumFileSize;
  }

  public void setMaximumFileSize(Long maximumFileSize) {
    this.maximumFileSize = maximumFileSize;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountPlan accountPlan = (AccountPlan) o;
    return Objects.equals(this.name, accountPlan.name) &&
        Objects.equals(this.pricePerMonth, accountPlan.pricePerMonth) &&
        Objects.equals(this.conversionsPerMonth, accountPlan.conversionsPerMonth) &&
        Objects.equals(this.maximumFileSize, accountPlan.maximumFileSize);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, pricePerMonth, conversionsPerMonth, maximumFileSize);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountPlan {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    pricePerMonth: ").append(toIndentedString(pricePerMonth)).append("\n");
    sb.append("    conversionsPerMonth: ").append(toIndentedString(conversionsPerMonth)).append("\n");
    sb.append("    maximumFileSize: ").append(toIndentedString(maximumFileSize)).append("\n");
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
    openapiFields.add("name");
    openapiFields.add("price_per_month");
    openapiFields.add("conversions_per_month");
    openapiFields.add("maximum_file_size");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AccountPlan
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AccountPlan.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AccountPlan is not found in the empty JSON string", AccountPlan.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!AccountPlan.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AccountPlan` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AccountPlan.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AccountPlan' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AccountPlan> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AccountPlan.class));

       return (TypeAdapter<T>) new TypeAdapter<AccountPlan>() {
           @Override
           public void write(JsonWriter out, AccountPlan value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AccountPlan read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AccountPlan given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AccountPlan
  * @throws IOException if the JSON string is invalid with respect to AccountPlan
  */
  public static AccountPlan fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AccountPlan.class);
  }

 /**
  * Convert an instance of AccountPlan to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

