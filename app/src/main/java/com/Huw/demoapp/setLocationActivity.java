package com.Huw.demoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Huw.demoapp.Api_Models.ApiInterface;
import com.Huw.demoapp.Api_Models.ApiModel_Feature;
import com.Huw.demoapp.AsyncTasks.AsyncWorldMsgBox;
import com.Huw.demoapp.NotificationServices.Hms.AccessToken;
import com.Huw.demoapp.NotificationServices.Hms.NotificationBody;
import com.Huw.demoapp.NotificationServices.Hms.NotificationMessage;
import com.Huw.demoapp.ui.slideshow.SlideshowFragment;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MarkerOptions;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class setLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    HuaweiMap hMap;
    MapView mMapView;
    int barValue;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    SlideshowFragment slideshowFragment= new SlideshowFragment();

    public static String pushToken;
    public static String accessToken;
    private String pusht;

    Retrofit.Builder builderpush = new Retrofit.Builder()
            .baseUrl(ApiInterface.PUSH)
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit2 = builderpush.build();


    ApiInterface sendPush = retrofit2.create(ApiInterface.class);


    ////

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiInterface.ACCESS_TOKEN)
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ApiInterface pushApi = retrofit.create(ApiInterface.class);








    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        mMapView = findViewById(R.id.locationset);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }


        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        pusht = getPushToken(setLocationActivity.this);



    }

    public void notifyMe(final Context context) {


        Call<AccessToken> call = pushApi.GetAccessToken("client_credentials", "666be9b61df3ae31838b96bf01d61a5dd4216f826483fd246c1b51f7bf2f6638", 102480429);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) { // Bu kısımda Bearer koymamızın //nedeni Push Api'nin Bearer+accesstoken şeklinde bir yapı istemesi
                accessToken = "Bearer " + response.body().getAccess_token();

                pusht = getPushToken(context);


                sendNotification(accessToken, pusht);


            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                //  Toast.makeText(context, "api failure", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void sendNotification(final String accesstoken, final String pushToken) {


        //  Intent intent = new Intent(this, MapActivity.class);

        //intent.toString()
        NotificationBody builder = new NotificationBody.Builder("EarthquakeApp", "Your location has been set.", pushToken).build();


        Call<NotificationMessage> callpush = sendPush.createNotification(accesstoken, builder);
        callpush.enqueue(new Callback<NotificationMessage>() {
            @Override
            public void onResponse(Call<NotificationMessage> call, Response<NotificationMessage> response) {


                //  Toast.makeText(getActivity(), accesstoken + "AAAAAAAAAAAA" + pushToken, Toast.LENGTH_LONG).show();

                //  Intent intent = new Intent(getActivity(), NavigationLeftActivity.class);
                //  startActivity(intent);



            }

            @Override
            public void onFailure(Call<NotificationMessage> call, Throwable t) {
                //   Toast.makeText(getActivity(), "fail", Toast.LENGTH_LONG).show();
            }
        });



    }

    private String getPushToken(final Context context) {
        new Thread() {
            @Override
            public void run() {


                try {
                    String appId = AGConnectServicesConfig.fromContext(setLocationActivity.this)
                            .getString("client/app_id");
                    String token = HmsInstanceId.getInstance(context)
                            .getToken(appId, "HCM");

                    pushToken = token;

                    Log.i("11", "token: " + token);


                } catch (ApiException e) {
                    Log.e("12", e.getMessage());
                    pushToken = "catch";

                }
            }
        }.start();


        return pushToken;
    }






    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }


    @Override
    public void onMapReady(HuaweiMap huaweiMap) {

        Toast.makeText(setLocationActivity.this,"click the map to set your location", Toast.LENGTH_LONG).show();

            hMap=huaweiMap;
            hMap.setMarkersClustering(false);



            hMap.setOnMapClickListener(new HuaweiMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    double lati = latLng.latitude;
                    double longi =latLng.longitude;

                    hMap.addMarker(new MarkerOptions().position(new LatLng(lati, longi)));

                    showSeekBar(lati,longi);




                }
            });


        }

    private  void   showSeekBar(final Double lat, final Double lon){

        final AlertDialog.Builder popDialog = new AlertDialog.Builder(setLocationActivity.this);
        final SeekBar seek = new SeekBar(setLocationActivity.this);
        seek.setMax(500);
        seek.setKeyProgressIncrement(1);

        popDialog.setIcon(R.drawable.ic_place);
        popDialog.setTitle("Set your range for the location");
        popDialog.setView(seek);

        popDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SharedPreferences sharedPref =getSharedPreferences("loc",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("range",barValue);
                editor.putString("lat",String.valueOf(lat));
                editor.putString("lon",String.valueOf(lon));
                editor.putBoolean("custom",true);
                editor.commit();

              if (editor.commit()==true) {


                  notifyMe(setLocationActivity.this);

                  updateBox(lat,lon,barValue);

                 Intent intent = new Intent (setLocationActivity.this, NavigationLeftActivity.class);
                  startActivity(intent);




              }

            }
        });

        popDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                hMap.clear();

            }
        });

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            barValue=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(setLocationActivity.this, "your range : "+barValue+ " kilometers", Toast.LENGTH_LONG).show();
            }

        });


        popDialog.show();
    }

    private void  updateBox(final Double lat, final Double lon, final int barValue){
        try {
            new Thread() {
                @Override
                public void run() {

                    Location locationA = new Location("point");
                    locationA.setLatitude(lat);
                    locationA.setLongitude(lon);

                    AsyncWorldMsgBox taskmsg = new AsyncWorldMsgBox(locationA,barValue);


                    List<ApiModel_Feature> aa =  AccountActivity.worldmessageData.getFeatures();

                    taskmsg.execute(aa);


                }
            }.start();
        } catch (Error e) {

            Toast.makeText(setLocationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    }

