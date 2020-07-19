package com.Huw.demoapp.Api_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiModel_Feature {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("properties")
    @Expose
    private ApiModel_Properties properties;
    @SerializedName("geometry")
    @Expose
    private ApiModel_Geometry geometry;
    @SerializedName("id")
    @Expose
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ApiModel_Properties getProperties() {
        return properties;
    }

    public void setProperties(ApiModel_Properties properties) {
        this.properties = properties;
    }

    public ApiModel_Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(ApiModel_Geometry geometry) {
        this.geometry = geometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
