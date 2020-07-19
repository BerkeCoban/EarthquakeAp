package com.Huw.demoapp.Api_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiModel {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("metadata")
    @Expose
    private ApiModel_Metadata metadata;
    @SerializedName("features")
    @Expose
    private List<ApiModel_Feature> features = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ApiModel_Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ApiModel_Metadata metadata) {
        this.metadata = metadata;
    }

    public List<ApiModel_Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<ApiModel_Feature> features) {
        this.features = features;
    }




}
