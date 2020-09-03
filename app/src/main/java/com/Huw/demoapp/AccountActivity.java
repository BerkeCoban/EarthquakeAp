package com.Huw.demoapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
import com.Huw.demoapp.Api_Models.ApiInterface;
import com.Huw.demoapp.Api_Models.ApiModel;
import com.Huw.demoapp.Api_Models.ApiModel_Feature;
import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.Api_Models.TR_ApiModel_Result;
import com.Huw.demoapp.AsyncTasks.AsyncTRMsgBox;
import com.Huw.demoapp.AsyncTasks.AsyncTRgetLastWeek;
import com.Huw.demoapp.AsyncTasks.AsyncWorldMsgBox;
import com.Huw.demoapp.AsyncTasks.AsyncWorldgetLastWeek;
import com.Huw.demoapp.Util.UrlGenerator;
import com.Huw.demoapp.ui.gallery.GalleryFragment;
import com.huawei.agconnect.remoteconfig.AGConnectConfig;
import com.huawei.agconnect.remoteconfig.ConfigValues;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsResponse;
import com.huawei.hms.location.LocationSettingsStatusCodes;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;






public class AccountActivity extends AppCompatActivity {


    public static ApiModel myData;
    public static ApiModel worldmessageData;
    public static TR_ApiModel myTrData;
    public static TR_ApiModel messageData;
    public static  Integer mapValue;
    public static Context context;


    public  static  List<TR_ApiModel> weekdata;
    public  static  List<ApiModel> worldweekdata;


    private   HuaweiIdAuthParams authParams;
    private   HuaweiIdAuthService service;


    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private SettingsClient settingsClient;
    private LocationCallback mLocationCallback;
    private Location location;
    private int locationController=0;





    UrlGenerator urlGenerator=new UrlGenerator();
    ArrayList<String> dates = new ArrayList<>();
    String currentdate;




    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiInterface.URL_GET)
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ApiInterface userClient = retrofit.create(ApiInterface.class);






  //  AsyncTRgetLastWeek task = new AsyncTRgetLastWeek();
   // AsyncWorldgetLastWeek wtask = new AsyncWorldgetLastWeek();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);



        AGConnectConfig config = AGConnectConfig.getInstance();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        fetchAndApply(config);


        askPermissions();


        HwAds.init(this);

        currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());









        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal =Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,-6);

        for (int i=0 ; i<7 ;i++){

            Date newDate = cal.getTime();
            String day = sdf.format(newDate);
            dates.add(day);


            cal.add(Calendar.DAY_OF_YEAR,1);
        }

        GalleryFragment.days=dates;




    }

    public  void login(final Context context){

        setBoxValues(context);


        authParams = new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setIdToken().createParams();
        service= HuaweiIdAuthManager.getService(context, authParams);
        Task<AuthHuaweiId> task = service.silentSignIn();
        task.addOnSuccessListener(new OnSuccessListener<AuthHuaweiId>() {
            @Override
            public void onSuccess(AuthHuaweiId authHuaweiId) {



                String uri = authHuaweiId.getAvatarUriString();
                String name  = authHuaweiId.getGivenName();
                String familyName = authHuaweiId.getFamilyName();





                NavigationLeftActivity.url=uri;
                NavigationLeftActivity.name=name;
                NavigationLeftActivity.familyname=familyName;



                Intent intent = new Intent (context, NavigationLeftActivity.class);
           //     intent.putExtra("uri",uri);
            //    intent.putExtra("name",name);
            //    intent.putExtra("familyname",familyName);

                context.startActivity(intent);

            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                startActivityForResult(service.getSignInIntent(), 1111);
            }
        });
    }





    private void getLastWeek() {

         if (mapValue==1){
             try {
                 new Thread() {
                     @Override
                     public void run() {


                       //  task.execute(dates);
                         AsyncTRgetLastWeek lastWeek =new AsyncTRgetLastWeek(AccountActivity.this);
                         lastWeek.execute(dates);


                     }
                 }.start();
             }catch (Error e ){

                 Toast.makeText(AccountActivity.this,e.toString(), Toast.LENGTH_LONG).show();
             }
         }else if (mapValue==0){

             try {
                 new Thread() {
                     @Override
                     public void run() {

                         AsyncWorldgetLastWeek lastWeek =new AsyncWorldgetLastWeek(AccountActivity.this);
                         lastWeek.execute(dates);
                       //  wtask.execute(dates);



                     }
                 }.start();
             }catch (Error e ){

                 Toast.makeText(AccountActivity.this,e.toString(), Toast.LENGTH_LONG).show();
             }
         }
    }

    private void fetchAndApply(final AGConnectConfig config) {

    config.fetch(0).addOnSuccessListener(new OnSuccessListener<ConfigValues>() {
        @Override
        public void onSuccess(ConfigValues configValues) {

            config.apply(configValues);

            String text = config.getValueAsString("region");


            //mapValue=Integer.parseInt(text);
            mapValue=0;

       //   Toast.makeText(AccountActivity.this,mapValue.toString(), Toast.LENGTH_LONG).show();

            getLastWeek();
            MessageApi(dates.get(6), "1000");

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(Exception e) {
        //    Toast.makeText(AccountActivity.this,"fetch failed", Toast.LENGTH_LONG).show();
            mapValue=0;
            getLastWeek();
            MessageApi(dates.get(6), "1000");
        }
    });



    }

    private void setLocation(final int range) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        settingsClient = LocationServices.getSettingsClient(context);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000).setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {

                    location = locationResult.getLastLocation();

                    if (locationController==0){
                        setMessageBox(location,range);
                    }








                }
            }
        };
    }

    private void setMessageBox(final Location loc, final int range) {


       if (mapValue==1){
           try {
               new Thread() {
                   @Override
                   public void run() {



                       AsyncTRMsgBox taskmsg = new AsyncTRMsgBox(loc,range);


                       List<TR_ApiModel_Result> aa =  messageData.getResult();

                       taskmsg.execute(aa);


                   }
               }.start();
           } catch (Error e) {

               Toast.makeText(AccountActivity.this, e.toString(), Toast.LENGTH_LONG).show();
           }
       }else if (mapValue==0){

           try {
               new Thread() {
                   @Override
                   public void run() {



                       AsyncWorldMsgBox taskmsg = new AsyncWorldMsgBox(loc,range);


                       List<ApiModel_Feature> aa =  worldmessageData.getFeatures();

                       taskmsg.execute(aa);


                   }
               }.start();
           } catch (Error e) {

               Toast.makeText(AccountActivity.this, e.toString(), Toast.LENGTH_LONG).show();
           }
       }


        locationController++;

    }
    private void checkDeviceSettings() {

        try {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            LocationSettingsRequest locationSettingsRequest = builder.build();
            // check devices settings before request location updates.
            settingsClient.checkLocationSettings(locationSettingsRequest)
                    .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            Log.i("22", "check location settings success");
                            //request location updates
                            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i("22", "requestLocationUpdatesWithCallback onSuccess");
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.e("11",
                                                    "requestLocationUpdatesWithCallback onFailure:" + e.getMessage());
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.e("22", "checkLocationSetting onFailure:" + e.getMessage());
                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        ResolvableApiException rae = (ResolvableApiException) e;
                                        rae.startResolutionForResult(AccountActivity.this, 0);
                                    } catch (IntentSender.SendIntentException sie) {
                                        Log.e("11", "PendingIntent unable to execute request.");
                                    }
                                    break;
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e("11", "requestLocationUpdatesWithCallback exception:" + e.getMessage());
        }






        /*

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
       // mLocationRequest = new LocationRequest();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
// Check the device location settings.
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        // Initiate location requests when the location settings meet the requirements.
                        fusedLocationProviderClient
                                .requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Processing when the API call is successful.
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Device location settings do not meet the requirements.
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    // Call startResolutionForResult to display a pop-up asking the user to enable related permission.
                                    rae.startResolutionForResult(AccountActivity.this, 0);
                                } catch (IntentSender.SendIntentException sie) {
                                    //...
                                }
                                break;
                        }
                    }
                });
*/

    }



    public void MessageApi( String date,  String limit)   {



        if (mapValue==1) {
            Call<TR_ApiModel> callist = userClient.getDynamicTr(urlGenerator.getDatedUrl(date, limit));
            callist.enqueue(new Callback<TR_ApiModel>() {
                @Override
                public void onResponse(Call<TR_ApiModel> call, Response<TR_ApiModel> response) {
                    messageData = response.body();
                    myTrData=response.body();

                }

                @Override
                public void onFailure(Call<TR_ApiModel> call, Throwable t) {
                }
            });
        }else if (mapValue==0){

            Call<ApiModel> callis = userClient.getDynamic("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+currentdate+"&minmagnitude=0");
            callis.enqueue(new Callback<ApiModel>() {
                @Override
                public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {

                    if (response!=null){

                        worldmessageData = response.body();
                        myData=response.body();
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


    }







    public void worldApi(String date, String minMag){
    myData=null;
    Call<ApiModel> callis = userClient.getDynamic("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+date+"&minmagnitude="+minMag);
   callis.enqueue(new Callback<ApiModel>() {
       @Override
       public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {

       if (response!=null){

          myData = response.body();






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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i("11", "onRequestPermissionsResult: apply LOCATION PERMISSION successful");
            } else {
                Log.i("11", "onRequestPermissionsResult: apply LOCATION PERMISSSION  failed");
            }
        }

        if (requestCode == 2) {
            if (grantResults.length > 2 && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i("22", "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful");
            } else {
                Log.i("22", "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION  failed");
            }
        }
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

    private  void askPermissions(){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i("23", "sdk < 28");
            if (ActivityCompat.checkSelfPermission(AccountActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(AccountActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] strings =
                        {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                ActivityCompat.requestPermissions(AccountActivity.this, strings, 1);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(AccountActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(AccountActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(AccountActivity.this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                String[] strings = {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        "android.permission.ACCESS_BACKGROUND_LOCATION"};
                ActivityCompat.requestPermissions(AccountActivity.this, strings, 2);
            }
        }
    }




private void setBoxValues(Context context){

    boolean a =context.getSharedPreferences("loc",MODE_PRIVATE).contains("custom");

    if (a==true){

        SharedPreferences sharedPref=context.getSharedPreferences("loc",MODE_PRIVATE);

        boolean custom =sharedPref.getBoolean("custom", Boolean.parseBoolean(""));

        if (custom){

            String lat = sharedPref.getString("lat",null);
            String lon= sharedPref.getString("lon",null);
            int range =sharedPref.getInt("range", 0);



            Location customLocation = new Location("custom Point");
            customLocation.setLatitude(Double.parseDouble(lat));
            customLocation.setLongitude(Double.parseDouble(lon));


            setMessageBox(customLocation,range);
        }else {

            //users use their  location with range
            int range =sharedPref.getInt("range", 0);
            setLocation(range);
            checkDeviceSettings();
        }
    }
    //default
    else{

        setLocation(100);
        checkDeviceSettings();
    }

}




}