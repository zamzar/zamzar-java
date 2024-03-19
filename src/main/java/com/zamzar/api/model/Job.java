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
import com.zamzar.api.model.Export;
import com.zamzar.api.model.Failure;
import com.zamzar.api.model.ModelFile;
import com.zamzar.api.model.ModelImport;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * Represents the process of converting a file to another format.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class Job {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  private String key;

  /**
   * The current status of the job
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    INITIALISING("initialising"),
    
    CONVERTING("converting"),
    
    SUCCESSFUL("successful"),
    
    FAILED("failed"),
    
    CANCELLED("cancelled");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StatusEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      StatusEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private StatusEnum status;

  public static final String SERIALIZED_NAME_FAILURE = "failure";
  @SerializedName(SERIALIZED_NAME_FAILURE)
  private Failure failure;

  public static final String SERIALIZED_NAME_SANDBOX = "sandbox";
  @SerializedName(SERIALIZED_NAME_SANDBOX)
  private Boolean sandbox;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public static final String SERIALIZED_NAME_FINISHED_AT = "finished_at";
  @SerializedName(SERIALIZED_NAME_FINISHED_AT)
  private OffsetDateTime finishedAt;

  public static final String SERIALIZED_NAME_IMPORT = "import";
  @SerializedName(SERIALIZED_NAME_IMPORT)
  private ModelImport _import;

  public static final String SERIALIZED_NAME_SOURCE_FILE = "source_file";
  @SerializedName(SERIALIZED_NAME_SOURCE_FILE)
  private ModelFile sourceFile;

  public static final String SERIALIZED_NAME_TARGET_FILES = "target_files";
  @SerializedName(SERIALIZED_NAME_TARGET_FILES)
  private List<ModelFile> targetFiles;

  public static final String SERIALIZED_NAME_TARGET_FORMAT = "target_format";
  @SerializedName(SERIALIZED_NAME_TARGET_FORMAT)
  private String targetFormat;

  public static final String SERIALIZED_NAME_CREDIT_COST = "credit_cost";
  @SerializedName(SERIALIZED_NAME_CREDIT_COST)
  private Integer creditCost;

  public static final String SERIALIZED_NAME_EXPORT_URL = "export_url";
  @SerializedName(SERIALIZED_NAME_EXPORT_URL)
  private String exportUrl;

  public static final String SERIALIZED_NAME_EXPORTS = "exports";
  @SerializedName(SERIALIZED_NAME_EXPORTS)
  private List<Export> exports;

  public static final String SERIALIZED_NAME_OPTIONS = "options";
  @SerializedName(SERIALIZED_NAME_OPTIONS)
  private Map<String, Object> options = new HashMap<>();

  public Job() {
  }

  public Job id(Integer id) {
    this.id = id;
    return this;
  }

   /**
   * The unique identifier assigned to the job
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public Job key(String key) {
    this.key = key;
    return this;
  }

   /**
   * The API key used to create the job
   * @return key
  **/
  @javax.annotation.Nullable
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }


  public Job status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * The current status of the job
   * @return status
  **/
  @javax.annotation.Nullable
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public Job failure(Failure failure) {
    this.failure = failure;
    return this;
  }

   /**
   * Get failure
   * @return failure
  **/
  @javax.annotation.Nullable
  public Failure getFailure() {
    return failure;
  }

  public void setFailure(Failure failure) {
    this.failure = failure;
  }


  public Job sandbox(Boolean sandbox) {
    this.sandbox = sandbox;
    return this;
  }

   /**
   * Indicates whether or not the job was processed on the developer sandbox (i.e. at no cost)
   * @return sandbox
  **/
  @javax.annotation.Nullable
  public Boolean getSandbox() {
    return sandbox;
  }

  public void setSandbox(Boolean sandbox) {
    this.sandbox = sandbox;
  }


  public Job createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * The time at which the job was created (UTC in [ISO_8601](https://en.wikipedia.org/wiki/ISO_8601))
   * @return createdAt
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public Job finishedAt(OffsetDateTime finishedAt) {
    this.finishedAt = finishedAt;
    return this;
  }

   /**
   * The time at which the job finished if successful, or null otherwise (UTC in [ISO_8601](https://en.wikipedia.org/wiki/ISO_8601))
   * @return finishedAt
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getFinishedAt() {
    return finishedAt;
  }

  public void setFinishedAt(OffsetDateTime finishedAt) {
    this.finishedAt = finishedAt;
  }


  public Job _import(ModelImport _import) {
    this._import = _import;
    return this;
  }

   /**
   * Get _import
   * @return _import
  **/
  @javax.annotation.Nullable
  public ModelImport getImport() {
    return _import;
  }

  public void setImport(ModelImport _import) {
    this._import = _import;
  }


  public Job sourceFile(ModelFile sourceFile) {
    this.sourceFile = sourceFile;
    return this;
  }

   /**
   * Get sourceFile
   * @return sourceFile
  **/
  @javax.annotation.Nullable
  public ModelFile getSourceFile() {
    return sourceFile;
  }

  public void setSourceFile(ModelFile sourceFile) {
    this.sourceFile = sourceFile;
  }


  public Job targetFiles(List<ModelFile> targetFiles) {
    this.targetFiles = targetFiles;
    return this;
  }

  public Job addTargetFilesItem(ModelFile targetFilesItem) {
    if (this.targetFiles == null) {
      this.targetFiles = new ArrayList<>();
    }
    this.targetFiles.add(targetFilesItem);
    return this;
  }

   /**
   * The output from the job
   * @return targetFiles
  **/
  @javax.annotation.Nullable
  public List<ModelFile> getTargetFiles() {
    return targetFiles;
  }

  public void setTargetFiles(List<ModelFile> targetFiles) {
    this.targetFiles = targetFiles;
  }


  public Job targetFormat(String targetFormat) {
    this.targetFormat = targetFormat;
    return this;
  }

   /**
   * The name of the format to which &#x60;source_file&#x60; is being converted
   * @return targetFormat
  **/
  @javax.annotation.Nullable
  public String getTargetFormat() {
    return targetFormat;
  }

  public void setTargetFormat(String targetFormat) {
    this.targetFormat = targetFormat;
  }


  public Job creditCost(Integer creditCost) {
    this.creditCost = creditCost;
    return this;
  }

   /**
   * The cost in conversion credits of the job
   * @return creditCost
  **/
  @javax.annotation.Nullable
  public Integer getCreditCost() {
    return creditCost;
  }

  public void setCreditCost(Integer creditCost) {
    this.creditCost = creditCost;
  }


  public Job exportUrl(String exportUrl) {
    this.exportUrl = exportUrl;
    return this;
  }

   /**
   * The location to which all converted files will be copied
   * @return exportUrl
  **/
  @javax.annotation.Nullable
  public String getExportUrl() {
    return exportUrl;
  }

  public void setExportUrl(String exportUrl) {
    this.exportUrl = exportUrl;
  }


  public Job exports(List<Export> exports) {
    this.exports = exports;
    return this;
  }

  public Job addExportsItem(Export exportsItem) {
    if (this.exports == null) {
      this.exports = new ArrayList<>();
    }
    this.exports.add(exportsItem);
    return this;
  }

   /**
   * An array of objects representing the process of copying converted files to the location specified in the export_url (when submitting a job via the &#x60;/jobs&#x60; endpoint)
   * @return exports
  **/
  @javax.annotation.Nullable
  public List<Export> getExports() {
    return exports;
  }

  public void setExports(List<Export> exports) {
    this.exports = exports;
  }


  public Job options(Map<String, Object> options) {
    this.options = options;
    return this;
  }

  public Job putOptionsItem(String key, Object optionsItem) {
    if (this.options == null) {
      this.options = new HashMap<>();
    }
    this.options.put(key, optionsItem);
    return this;
  }

   /**
   * Additional options for the conversion
   * @return options
  **/
  @javax.annotation.Nullable
  public Map<String, Object> getOptions() {
    return options;
  }

  public void setOptions(Map<String, Object> options) {
    this.options = options;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Job job = (Job) o;
    return Objects.equals(this.id, job.id) &&
        Objects.equals(this.key, job.key) &&
        Objects.equals(this.status, job.status) &&
        Objects.equals(this.failure, job.failure) &&
        Objects.equals(this.sandbox, job.sandbox) &&
        Objects.equals(this.createdAt, job.createdAt) &&
        Objects.equals(this.finishedAt, job.finishedAt) &&
        Objects.equals(this._import, job._import) &&
        Objects.equals(this.sourceFile, job.sourceFile) &&
        Objects.equals(this.targetFiles, job.targetFiles) &&
        Objects.equals(this.targetFormat, job.targetFormat) &&
        Objects.equals(this.creditCost, job.creditCost) &&
        Objects.equals(this.exportUrl, job.exportUrl) &&
        Objects.equals(this.exports, job.exports) &&
        Objects.equals(this.options, job.options);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, status, failure, sandbox, createdAt, finishedAt, _import, sourceFile, targetFiles, targetFormat, creditCost, exportUrl, exports, options);
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
    sb.append("class Job {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    failure: ").append(toIndentedString(failure)).append("\n");
    sb.append("    sandbox: ").append(toIndentedString(sandbox)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    finishedAt: ").append(toIndentedString(finishedAt)).append("\n");
    sb.append("    _import: ").append(toIndentedString(_import)).append("\n");
    sb.append("    sourceFile: ").append(toIndentedString(sourceFile)).append("\n");
    sb.append("    targetFiles: ").append(toIndentedString(targetFiles)).append("\n");
    sb.append("    targetFormat: ").append(toIndentedString(targetFormat)).append("\n");
    sb.append("    creditCost: ").append(toIndentedString(creditCost)).append("\n");
    sb.append("    exportUrl: ").append(toIndentedString(exportUrl)).append("\n");
    sb.append("    exports: ").append(toIndentedString(exports)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("key");
    openapiFields.add("status");
    openapiFields.add("failure");
    openapiFields.add("sandbox");
    openapiFields.add("created_at");
    openapiFields.add("finished_at");
    openapiFields.add("import");
    openapiFields.add("source_file");
    openapiFields.add("target_files");
    openapiFields.add("target_format");
    openapiFields.add("credit_cost");
    openapiFields.add("export_url");
    openapiFields.add("exports");
    openapiFields.add("options");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Job
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Job.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Job is not found in the empty JSON string", Job.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!Job.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Job` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Job.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("key") != null && !jsonObj.get("key").isJsonNull()) && !jsonObj.get("key").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `key` to be a primitive type in the JSON string but got `%s`", jsonObj.get("key").toString()));
      }
      if ((jsonObj.get("status") != null && !jsonObj.get("status").isJsonNull()) && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      // validate the optional field `status`
      if (jsonObj.get("status") != null && !jsonObj.get("status").isJsonNull()) {
        StatusEnum.validateJsonElement(jsonObj.get("status"));
      }
      // validate the optional field `failure`
      if (jsonObj.get("failure") != null && !jsonObj.get("failure").isJsonNull()) {
        Failure.validateJsonElement(jsonObj.get("failure"));
      }
      // validate the optional field `import`
      if (jsonObj.get("import") != null && !jsonObj.get("import").isJsonNull()) {
        ModelImport.validateJsonElement(jsonObj.get("import"));
      }
      // validate the optional field `source_file`
      if (jsonObj.get("source_file") != null && !jsonObj.get("source_file").isJsonNull()) {
        ModelFile.validateJsonElement(jsonObj.get("source_file"));
      }
      if (jsonObj.get("target_files") != null && !jsonObj.get("target_files").isJsonNull()) {
        JsonArray jsonArraytargetFiles = jsonObj.getAsJsonArray("target_files");
        if (jsonArraytargetFiles != null) {
          // ensure the json data is an array
          if (!jsonObj.get("target_files").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `target_files` to be an array in the JSON string but got `%s`", jsonObj.get("target_files").toString()));
          }

          // validate the optional field `target_files` (array)
          for (int i = 0; i < jsonArraytargetFiles.size(); i++) {
            ModelFile.validateJsonElement(jsonArraytargetFiles.get(i));
          };
        }
      }
      if ((jsonObj.get("target_format") != null && !jsonObj.get("target_format").isJsonNull()) && !jsonObj.get("target_format").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `target_format` to be a primitive type in the JSON string but got `%s`", jsonObj.get("target_format").toString()));
      }
      if ((jsonObj.get("export_url") != null && !jsonObj.get("export_url").isJsonNull()) && !jsonObj.get("export_url").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `export_url` to be a primitive type in the JSON string but got `%s`", jsonObj.get("export_url").toString()));
      }
      if (jsonObj.get("exports") != null && !jsonObj.get("exports").isJsonNull()) {
        JsonArray jsonArrayexports = jsonObj.getAsJsonArray("exports");
        if (jsonArrayexports != null) {
          // ensure the json data is an array
          if (!jsonObj.get("exports").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `exports` to be an array in the JSON string but got `%s`", jsonObj.get("exports").toString()));
          }

          // validate the optional field `exports` (array)
          for (int i = 0; i < jsonArrayexports.size(); i++) {
            Export.validateJsonElement(jsonArrayexports.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Job.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Job' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Job> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Job.class));

       return (TypeAdapter<T>) new TypeAdapter<Job>() {
           @Override
           public void write(JsonWriter out, Job value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Job read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Job given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Job
  * @throws IOException if the JSON string is invalid with respect to Job
  */
  public static Job fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Job.class);
  }

 /**
  * Convert an instance of Job to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

