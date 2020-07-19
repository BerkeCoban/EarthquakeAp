package com.Huw.demoapp.Api_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiModel_Metadata {
    @SerializedName("generated")
    @Expose
    private String generated;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("api")
    @Expose
    private String api;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getGenerated() {
        return generated;
    }

    public void setGenerated(String generated) {
        this.generated = generated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}
