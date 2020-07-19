package com.Huw.demoapp.Api_Models;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TR_ApiModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<TR_ApiModel_Result> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<TR_ApiModel_Result> getResult() {
        return result;
    }

    public void setResult(List<TR_ApiModel_Result> result) {
        this.result = result;
    }




}
