package com.bpointer.rkofficial.Model.Response.GetAppVersionResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
   @SerializedName("app_version_id")
   @Expose
   private Integer appVersionId;
   @SerializedName("app_version")
   @Expose
   private String appVersion;
   @SerializedName("created_at")
   @Expose
   private String createdAt;
   @SerializedName("updated_at")
   @Expose
   private String updatedAt;
   
   public Integer getAppVersionId() {
      return appVersionId;
   }
   
   public void setAppVersionId(Integer appVersionId) {
      this.appVersionId = appVersionId;
   }
   
   public String getAppVersion() {
      return appVersion;
   }
   
   public void setAppVersion(String appVersion) {
      this.appVersion = appVersion;
   }
   
   public String getCreatedAt() {
      return createdAt;
   }
   
   public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
   }
   
   public String getUpdatedAt() {
      return updatedAt;
   }
   
   public void setUpdatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
   }
}
