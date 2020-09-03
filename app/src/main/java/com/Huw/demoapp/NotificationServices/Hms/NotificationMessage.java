package com.Huw.demoapp.NotificationServices.Hms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationMessage {

    @SerializedName("code")
    @Expose
    private  Integer code;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("requestId")
    @Expose
    private String requestId;





    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
