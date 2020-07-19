package com.Huw.demoapp.Api_Models;

import com.Huw.demoapp.NotificationServices.Hms.AccessToken;
import com.Huw.demoapp.NotificationServices.Hms.NotificationBody;
import com.Huw.demoapp.NotificationServices.Hms.NotificationMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {


    String URL_GET="https://api.orhanaydogdu.com.tr/deprem/";
    String ACCESS_TOKEN = "https://login.cloud.huawei.com/oauth2/";
    String PUSH ="https://push-api.cloud.huawei.com/v1/";


//https://api.orhanaydogdu.com.tr/deprem/index.php?date=2020-01-01&limit=100
    //https://api.orhanaydogdu.com.tr/deprem/live.php?limit=100



 //   @GET("live.php?limit=1")
  //  Call<TR_ApiModel> getValues();



    //
     @GET
    Call<TR_ApiModel> getDynamicTr(@Url String url);

     @GET
    Call<ApiModel> getDynamic(@Url String url);

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=UTF-8")
    @POST("v3/token")
    Call<AccessToken> GetAccessToken(
            @Field("grant_type") String grantType,
            @Field("client_secret") String clientSecret,
            @Field("client_id") int clientId);


    @Headers("Content-Type:application/json;")
    @POST("102480429/messages:send")
    Call<NotificationMessage> createNotification(
            @Header("Authorization") String authorization,
            @Body  NotificationBody notificationbody);





    /*
    @GET("{user_id}")
    Call<getApiList> getUser(@Path(value = "user_id", encoded = true) String userId);

    @GET("api/users?page=2")
Call<VeriListem> verilerimilistele();
*/

}

