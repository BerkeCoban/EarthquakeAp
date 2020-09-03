package com.Huw.demoapp.ui.home;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.Huw.demoapp.AccountActivity;
import com.Huw.demoapp.Api_Models.ApiInterface;
import com.Huw.demoapp.Api_Models.ApiModel;
import com.Huw.demoapp.Api_Models.ApiModel_Feature;
import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.Api_Models.TR_ApiModel_Result;
import com.Huw.demoapp.AsyncTasks.AsyncTRMsgBox;
import com.Huw.demoapp.NavigationLeftActivity;
import com.Huw.demoapp.R;
import com.Huw.demoapp.Util.CustomInfoAdapter;
import com.Huw.demoapp.Util.UrlGenerator;
import com.Huw.demoapp.setLocationActivity;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.agconnect.crash.AGConnectCrash;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;
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
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;
    HuaweiMap hMap;
    MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private TextView datetext;
    private int mYear, mMonth, mDay;
    Date date;
    String api_date;
    SeekBar seekBar;
    String monthValue, dayValue, yearValue;
    String startDate, endDate;
    UrlGenerator urlGenerator = new UrlGenerator();




    private int seekBarValue;

    CustomInfoAdapter customInfoAdapter;















    private String pusht;
    public static String pushToken;
    public static String accessToken;
    int appID = 102480429;




    public static boolean isFromMessage=false;



    public  static double lat;
    public  static double lon;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private SettingsClient settingsClient;
    private LocationCallback mLocationCallback;
    private Location mylocation;
    ViewGroup groupcontext;

    HiAnalyticsInstance instance;





    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiInterface.ACCESS_TOKEN)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ApiInterface pushApi = retrofit.create(ApiInterface.class);


    ///

    Retrofit.Builder builderpush = new Retrofit.Builder()
            .baseUrl(ApiInterface.PUSH)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create());



    Retrofit retrofit2 = builderpush.build();


    ApiInterface sendPush = retrofit2.create(ApiInterface.class);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mMapView = root.findViewById(R.id.mapp);

         groupcontext = container;

       //  setLocation();
       //  checkDeviceSettings();







        final Button search = (Button) root.findViewById(R.id.search);
        datetext = (TextView) root.findViewById(R.id.date);
         seekBar=(SeekBar) root.findViewById(R.id.seekbar);

         HiAnalyticsTools.enableLog();
         instance= HiAnalytics.getInstance(getActivity());

         instance.setAnalyticsEnabled(true);



        // instance.setAutoCollectionEnabled(true);
        // instance.regHmsSvcEvent();













        pusht = getPushToken(getActivity());










        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateMapWithDate(api_date, startDate, endDate);

                Bundle bundle=new Bundle();
                bundle.putString("date",api_date);
                instance.onEvent("dates",bundle);



            }
        });


       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //   Toast.makeText(getActivity(),"Minumum Magnitude is : "+progress/10, Toast.LENGTH_SHORT).show();
               seekBarValue=progress/10;
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {
               Toast.makeText(getActivity(),"Minumum Magnitude is : "+seekBarValue, Toast.LENGTH_SHORT).show();
           }
       });



        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                monthOfYear = monthOfYear + 1;
                                String dateInString = dayOfMonth + "-" + monthOfYear + "-" + year;

                                startDate = year + "-" + monthOfYear + "-" + dayOfMonth;


                                try {

                                    date = formatter.parse(dateInString);


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
                                String day = (String) DateFormat.format("dd", date); // 20
                                String monthString = (String) DateFormat.format("MMM", date); // Jun

                                datetext.setText(dayOfTheWeek.substring(0, 3) + ", " + day + " " + monthString);


                                if (monthOfYear < 10) {
                                    monthValue = String.format("%02d", monthOfYear);

                                } else {
                                    monthValue = String.valueOf(monthOfYear);
                                }

                                if (dayOfMonth < 10) {
                                    dayValue = String.format("%02d", dayOfMonth);

                                } else {
                                    dayValue = String.valueOf(dayOfMonth);
                                }
                                yearValue = String.valueOf(year);


                                api_date = yearValue + "-" + monthValue + "-" + dayValue;


                                Calendar calendar = new GregorianCalendar();
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.YEAR, year);
                                calendar.add(Calendar.DATE, 1);

                                int a = calendar.get(Calendar.YEAR);
                                int b = calendar.get(Calendar.MONTH);
                                int c = calendar.get(Calendar.DAY_OF_MONTH);


                                endDate = a + "-" + b + "-" + c;





                                //  Toast.makeText(getApplicationContext(),endDay,Toast.LENGTH_LONG).show();


                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });








        return root;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);


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


    public void TRDatedEarthquakeData(String date, String limit){


        // myTrData=null;
        Call<TR_ApiModel> callist = sendPush.getDynamicTr(urlGenerator.getDatedUrl(date,limit));






        callist.enqueue(new Callback<TR_ApiModel>() {
            @Override
            public void onResponse(Call<TR_ApiModel> call, Response<TR_ApiModel> response) {


                TR_ApiModel myTrData=response.body();
                hMap.clear();
                hMap.setMarkersClustering(true);
               // hMap.setMyLocationEnabled(true);
                List<TR_ApiModel_Result> markerData = myTrData.getResult();

                Bitmap icon1 = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.icons8r);

                Bitmap icon2 = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.icons8y);

                for (TR_ApiModel_Result x : markerData) {

                    MarkerOptions options=new MarkerOptions();
                    options.title("Magnitude : "+x.getMag().toString());
                    options.snippet(" Time : " +x.getDate().substring(10));
                    options.position(new LatLng(x.getLat(),x.getLng()));
                    options.draggable(false);
                    options.clusterable(true);




                    if (x.getMag()>=seekBarValue) {

                        if (x.getMag()<4){
                            hMap.addMarker(options).setIcon(BitmapDescriptorFactory.fromBitmap(icon2));
                            //  hMap.addMarker(new MarkerOptions().position(new LatLng(x.getLat(), x.getLng())).snippet(x.getTitle()).title(" Magnitude : " + x.getMag().toString()).draggable(false).clusterable(true).icon(BitmapDescriptorFactory.fromBitmap(icon2)));
                        }
                        else  if (x.getMag()>=4) {
                            hMap.addMarker(options).setIcon(BitmapDescriptorFactory.fromBitmap(icon1));
                            //   hMap.addMarker(new MarkerOptions().position(new LatLng(x.getLat(), x.getLng())).snippet(x.getTitle()).title(" Magnitude : " + x.getMag().toString()).draggable(false).clusterable(true).icon(BitmapDescriptorFactory.fromBitmap(icon1)));
                        }

                    }
                    }

                hMap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {


                        CustomInfoAdapter customInfoAdapter=new CustomInfoAdapter(groupcontext,getActivity());
                        hMap.setInfoWindowAdapter(customInfoAdapter);

                        return false;
                    }
                });

            }

            @Override
            public void onFailure(Call<TR_ApiModel> call, Throwable t) {

                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });



    }



    public void worldApi(String startDate,String endDate, int minMag){

        Call<ApiModel> callis = sendPush.getDynamic("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime="+startDate+"&endtime="+endDate+"&minmagnitude="+minMag);
        callis.enqueue(new Callback<ApiModel>() {
            @Override
            public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {

                if (response!=null){

                    ApiModel myData = response.body();
                    hMap.clear();
                    hMap.setMarkersClustering(true);
                //    hMap.setMyLocationEnabled(true);
                    List<ApiModel_Feature> markerData =myData.getFeatures();

                   Bitmap icon1 = BitmapFactory.decodeResource(getActivity().getResources(),
                            R.drawable.icons8r);

                    Bitmap icon2 = BitmapFactory.decodeResource(getActivity().getResources(),
                            R.drawable.icons8y);

                    for (ApiModel_Feature x : markerData) {
                        MarkerOptions options = new MarkerOptions();
                        options.title("Magnitude : " + x.getProperties().getMag());
                        options.snippet(" Time :"+ getDate(x.getProperties().getTime()).substring(10));
                        options.position(new LatLng(x.getGeometry().getCoordinates().get(1), x.getGeometry().getCoordinates().get(0)));
                        options.draggable(false);
                        options.clusterable(true);


                        //  hMap.addMarker(new MarkerOptions().position(new LatLng(x.getGeometry().getCoordinates().get(1), x.getGeometry().getCoordinates().get(0))).title(x.getProperties().getTitle()).draggable(false).clusterable(true)
                        //         .icon(BitmapDescriptorFactory.fromBitmap(icon1)));

                        if (x.getProperties().getMag() >= seekBarValue) {
                            if (x.getProperties().getMag() < 4) {
                                hMap.addMarker(options).setIcon(BitmapDescriptorFactory.fromBitmap(icon2));

                            } else if (x.getProperties().getMag() >= 4) {
                                hMap.addMarker(options).setIcon(BitmapDescriptorFactory.fromBitmap(icon1));

                            }

                            //    hMap.addMarker(new MarkerOptions().position(new LatLng(x.getGeometry().getCoordinates().get(1), x.getGeometry().getCoordinates().get(0))).title(x.getProperties().getTitle()).draggable(false).icon(BitmapDescriptorFactory.fromBitmap(icon1)));
                        }
                    }
                    hMap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {


                            CustomInfoAdapter customInfoAdapter=new CustomInfoAdapter(groupcontext,getActivity());
                            hMap.setInfoWindowAdapter(customInfoAdapter);

                            return false;
                        }
                    });


                }else{
                    Toast.makeText(getActivity(),"else", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ApiModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private  void   updateMapWithDate(String api_date,String startDate,String endDate) {


        // hMap.clear();

        if (AccountActivity.mapValue == 0) {

            worldApi(startDate,endDate,seekBarValue);







        }


        if (AccountActivity.mapValue == 1) {


            TRDatedEarthquakeData(api_date, "1000");

            //  ProgressDialog pd = new ProgressDialog(MapActivity.this);
            //  pd.setMessage("loading");
            //  pd.show();


            // if (data==null){

            //     Toast.makeText(MapActivity.this,"please select date again.", Toast.LENGTH_LONG).show();
            //     return;
            //  }


            //    hMap.clear();
            //      hMap.setMarkersClustering(true);
            //   List<TR_ApiModel_Result> markerData = data.getResult();

            //      for (TR_ApiModel_Result x : markerData) {

            //           hMap.addMarker(new MarkerOptions().position(new LatLng(x.getLat(), x.getLng())).title(x.getMag().toString()).draggable(false).clusterable(true));
            //      }


            // }
        }

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
                                        rae.startResolutionForResult(getActivity(), 0);
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
      //  mLocationRequest = new LocationRequest();
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
                                    rae.startResolutionForResult(getActivity(), 0);
                                } catch (IntentSender.SendIntentException sie) {
                                    //...
                                }
                                break;
                        }
                    }
                });
*/

    }




    private void setLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        settingsClient = LocationServices.getSettingsClient(getActivity());
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {

                    mylocation = locationResult.getLastLocation();
                }
            }
        };
    }




    @Override
    public void onMapReady(HuaweiMap huaweiMap) {

        hMap = huaweiMap;

        //hMap.setMapType(1);
     //   hMap.setMyLocationEnabled(true);




        Bitmap icon1 = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.icons8r);

        Bitmap icon2 = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.icons8y);
      //  customInfoAdapter = new CustomInfoAdapter(getActivity());


        //if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

   //         hMap.setMyLocationEnabled(true);
   //     }else{
  //          hMap.setMyLocationEnabled(true);
  //      }


if (isFromMessage==false){
    hMap.setMarkersClustering(true);

        if (AccountActivity.mapValue == 0) {
            List<ApiModel_Feature> markerData = AccountActivity.myData.getFeatures();



            for (ApiModel_Feature x : markerData) {

                MarkerOptions options=new MarkerOptions();
                options.title("Magnitude : "+x.getProperties().getMag());
                options.snippet(" Time :"+getDate(x.getProperties().getTime()).substring(10));
                options.position(new LatLng(x.getGeometry().getCoordinates().get(1),x.getGeometry().getCoordinates().get(0)));
                options.draggable(false);
                options.clusterable(true);


              //  hMap.addMarker(new MarkerOptions().position(new LatLng(x.getGeometry().getCoordinates().get(1), x.getGeometry().getCoordinates().get(0))).title(x.getProperties().getTitle()).draggable(false).clusterable(true)
               //         .icon(BitmapDescriptorFactory.fromBitmap(icon1)));

                if (x.getProperties().getMag()>=seekBarValue){
                    if (x.getProperties().getMag() < 4) {
                        hMap.addMarker(options).setIcon(BitmapDescriptorFactory.fromBitmap(icon2));

                }else  if (x.getProperties().getMag()>=4){
                        hMap.addMarker(options).setIcon(BitmapDescriptorFactory.fromBitmap(icon1));

                    }
                }

            }

            hMap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {


                    CustomInfoAdapter customInfoAdapter=new CustomInfoAdapter(groupcontext,getActivity());
                    hMap.setInfoWindowAdapter(customInfoAdapter);

                    return false;
                }
            });



        }
        if (AccountActivity.mapValue == 1) {
            List<TR_ApiModel_Result> markerData = AccountActivity.myTrData.getResult();


            for (TR_ApiModel_Result x : markerData) {

          MarkerOptions options=new MarkerOptions();
          options.title("Magnitude : "+x.getMag().toString());
          options.snippet(" Time : " +x.getDate().substring(10));
          options.position(new LatLng(x.getLat(),x.getLng()));
          options.draggable(false);
          options.clusterable(true);



                if (x.getMag() >= seekBarValue) {

                    if (x.getMag() < 4) {

                       //   hMap.addMarker(new MarkerOptions().position(new LatLng(x.getLat(), x.getLng())).snippet(x.getTitle()).title(" Magnitude : " + x.getMag().toString()).draggable(false).icon(BitmapDescriptorFactory.fromBitmap(icon2)).clusterable(true));
                        hMap.addMarker(options).setIcon(BitmapDescriptorFactory.fromBitmap(icon2));
                    } else if (x.getMag() >= 4) {
                        hMap.addMarker(options).setIcon(BitmapDescriptorFactory.fromBitmap(icon1));
                       // hMap.addMarker(new MarkerOptions().position(new LatLng(x.getLat(), x.getLng())).snippet(x.getTitle()).title(" Magnitude : " + x.getMag().toString()).draggable(false).icon(BitmapDescriptorFactory.fromBitmap(icon1)).clusterable(true));
                    }
                }


            }





            hMap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {


                    CustomInfoAdapter customInfoAdapter=new CustomInfoAdapter(groupcontext,getActivity());
                    hMap.setInfoWindowAdapter(customInfoAdapter);

                    return false;
                }
            });

        }
    }

   else if (isFromMessage==true){

    hMap.clear();
       hMap.setMarkersClustering(false);





       hMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).icon(BitmapDescriptorFactory.fromBitmap(icon1)));

       LatLng latLng=new LatLng(lat,lon);
       hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));

       isFromMessage=false;

   }





    }

    private String getPushToken(final Context context) {
        new Thread() {
            @Override
            public void run() {


                try {
                    String appId = AGConnectServicesConfig.fromContext(getActivity())
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

    private String getDate(String time) {


        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd '  ' HH:mm:ss z");
        Date date = new Date(Long.parseLong(time));
        return sf.format(date);
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
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