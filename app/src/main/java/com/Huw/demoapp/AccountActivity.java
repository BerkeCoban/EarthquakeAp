package com.Huw.demoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.Huw.demoapp.Api_Models.ApiInterface;
import com.Huw.demoapp.Api_Models.ApiModel;
import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.AsyncTasks.AsyncTRMsgBox;
import com.Huw.demoapp.AsyncTasks.AsyncTRgetLastWeek;
import com.Huw.demoapp.BarChart.ChartActivity;
import com.Huw.demoapp.Util.UrlGenerator;
import com.Huw.demoapp.ui.gallery.GalleryFragment;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;


import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import org.xml.sax.Locator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountActivity extends AppCompatActivity {


    public static ApiModel myData;
    public static TR_ApiModel myTrData;
    public static TR_ApiModel TrData;
    public static  Integer mapValue=2;
    public  static  List<TR_ApiModel> weekdata;



    private   HuaweiIdAuthParams authParams;
    private   HuaweiIdAuthService service;



    UrlGenerator urlGenerator=new UrlGenerator();
    ArrayList<String> dates = new ArrayList<>();




    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiInterface.URL_GET)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ApiInterface userClient = retrofit.create(ApiInterface.class);

    AsyncTRgetLastWeek task = new AsyncTRgetLastWeek();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wellcome);


        ImageView signin=findViewById(R.id.signin);
      //  final String current_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());





        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal =Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,-6);

        for (int i=0 ; i<7 ;i++){

            Date newDate = cal.getTime();
            String day = sdf.format(newDate);
            dates.add(day);


            cal.add(Calendar.DAY_OF_YEAR,1);
        }
     //   ChartActivity.days=dates;
        GalleryFragment.days=dates;


      //  Toast.makeText(AccountActivity.this,"please wait...", Toast.LENGTH_LONG).show();
        try {
            new Thread() {
                @Override
                public void run() {


                    task.execute(dates);


                }
            }.start();
        }catch (Error e ){

            Toast.makeText(AccountActivity.this,e.toString(), Toast.LENGTH_LONG).show();
        }

        signin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        if (task.getStatus() == AsyncTask.Status.FINISHED) {
            // Toast.makeText(AccountActivity.this,weekdata.get(3).getResult().get(2).getTitle(), Toast.LENGTH_LONG).show();
            GalleryFragment.mydata = weekdata;
            mapValue = 1;
            TRLiveEarthquakeData("1000");
            Intent intent = new Intent (AccountActivity.this, NavigationLeftActivity.class);
            startActivity(intent);


           /*

            authParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                    .setIdToken().createParams();
            service= HuaweiIdAuthManager.getService(AccountActivity.this, authParams);
            Task<AuthHuaweiId> task = service.silentSignIn();
            task.addOnSuccessListener(new OnSuccessListener<AuthHuaweiId>() {
                @Override
                public void onSuccess(AuthHuaweiId authHuaweiId) {





                    String uri = authHuaweiId.getAvatarUriString();
                   String name  = authHuaweiId.getGivenName();
                   String familyName = authHuaweiId.getFamilyName();





                    Intent intent = new Intent (AccountActivity.this, NavigationLeftActivity.class);
                     intent.putExtra("uri",uri);
                    intent.putExtra("name",name);
                    intent.putExtra("familyname",familyName);




                    startActivity(intent);

                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    startActivityForResult(service.getSignInIntent(), 1111);
                }
            });




        }else
        {
            Toast.makeText(AccountActivity.this,"Please wait..", Toast.LENGTH_LONG).show();
            return;
        }


*/
        }
    }
});
    }




    public TR_ApiModel TRDatedEarthquakeData(String date, String limit){



        Call<TR_ApiModel> callist = userClient.getDynamicTr(urlGenerator.getDatedUrl(date,limit));


        callist.enqueue(new Callback<TR_ApiModel>() {
            @Override
            public void onResponse(Call<TR_ApiModel> call, Response<TR_ApiModel> response) {


                weekdata.add(response.body());



                    Log.d("2143", myTrData.getResult().get(0).getTitle());








            }

            @Override
            public void onFailure(Call<TR_ApiModel> call, Throwable t) {


            }
        });
        return TrData;
    }







    public void worldApi(String date, String minMag){
    myData=null;
    Call<ApiModel> callis = userClient.getDynamic("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+date+"&minmagnitude="+minMag);
   callis.enqueue(new Callback<ApiModel>() {
       @Override
       public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {

       if (response!=null){

          myData = response.body();

           Intent intent = new Intent (AccountActivity.this, NavigationLeftActivity.class);
              startActivity(intent);




       }else{
           Toast.makeText(AccountActivity.this,"else", Toast.LENGTH_LONG).show();

       }

       }

       @Override
       public void onFailure(Call<ApiModel> call, Throwable t) {
           Toast.makeText(AccountActivity.this, t.toString(), Toast.LENGTH_LONG).show();
       }
   });

}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111) {
            Task<AuthHuaweiId> authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data);
            if (authHuaweiIdTask.isSuccessful()) {


                AuthHuaweiId huaweiAccount = authHuaweiIdTask.getResult();

                String uri = huaweiAccount.getAvatarUriString();
                String name  = huaweiAccount.getGivenName();
                String familyName = huaweiAccount.getFamilyName();









                Intent intent = new Intent (this, NavigationLeftActivity.class);
                intent.putExtra("uri",uri);
                intent.putExtra("name",name);
                intent.putExtra("familyname",familyName);

                   startActivity(intent);
            } else {
                Toast.makeText(AccountActivity.this,((ApiException)authHuaweiIdTask.getException()).getStatusCode(), Toast.LENGTH_LONG).show();
                Log.e("23", "sign in failed : " +((ApiException)authHuaweiIdTask.getException()).getStatusCode());
            }
        }
    }



    public void TRLiveEarthquakeData(String limit){

     myTrData=null;

    Call<TR_ApiModel> callist = userClient.getDynamicTr(urlGenerator.getUndatedUrl(limit));


    callist.enqueue(new Callback<TR_ApiModel>() {
        @Override
        public void onResponse(Call<TR_ApiModel> call, Response<TR_ApiModel> response) {

            myTrData  = response.body();


            if (myTrData != null) {


            }



          else {
                Toast.makeText(AccountActivity.this, "data null", Toast.LENGTH_LONG).show();

            }


        }

        @Override
        public void onFailure(Call<TR_ApiModel> call, Throwable t) {
            Toast.makeText(AccountActivity.this, t.toString(), Toast.LENGTH_LONG).show();
        }
    });
    }

public  void tryLogIn(){



}





    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {

            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }

            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}