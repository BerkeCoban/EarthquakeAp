package com.Huw.demoapp.ui.slideshow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.Huw.demoapp.AccountActivity;
import com.Huw.demoapp.Api_Models.ApiInterface;
import com.Huw.demoapp.Api_Models.ApiModel_Feature;
import com.Huw.demoapp.AsyncTasks.AsyncWorldMsgBox;
import com.Huw.demoapp.NavigationLeftActivity;
import com.Huw.demoapp.NotificationServices.Hms.AccessToken;
import com.Huw.demoapp.NotificationServices.Hms.NotificationBody;
import com.Huw.demoapp.NotificationServices.Hms.NotificationMessage;
import com.Huw.demoapp.R;
import com.Huw.demoapp.setLocationActivity;
import com.Huw.demoapp.ui.home.HomeFragment;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.agconnect.crash.AGConnectCrash;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.common.ApiException;

import java.util.ArrayList;
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

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public static String pushToken;
    public static String accessToken;
    int appID = 102480429;
    private String pusht;

    int barValue=50;


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




    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

   //      Button pushButton = (Button) root.findViewById(R.id.push);
     //   Button setloc = (Button) root.findViewById(R.id.setlocation);
        pusht = getPushToken(getActivity());

        BannerView bottomBannerView = root.findViewById(R.id.hw_banner_view);
        AdParam adParam = new AdParam.Builder().build();
        bottomBannerView.loadAd(adParam);


/*
pushButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
           notifyMe();

    }
});

 */


final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Message box Location");

        final List<String> lables = new ArrayList<>();

        lables.add("Use my location");
        lables.add("Custom location");




        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, lables);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                if (which==0){
                  dialog.dismiss();


                    final AlertDialog.Builder popDialog = new AlertDialog.Builder(getActivity());
                    final SeekBar seek = new SeekBar(getActivity());
                    seek.setMax(500);
                    seek.setKeyProgressIncrement(1);

                    popDialog.setIcon(R.drawable.ic_place);
                    popDialog.setTitle("Set your range for the location");
                    popDialog.setView(seek);

                    popDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            SharedPreferences sharedPref =  getActivity().getSharedPreferences("loc",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt("range",barValue);
                            editor.putBoolean("custom",false);


                            editor.commit();

                            if (editor.commit()==true) {
                                notifyMe(getActivity());


                                Intent intent = new Intent(getActivity(), NavigationLeftActivity.class);
                                startActivity(intent);

                            }

                        }
                    });

                    popDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();

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
                            Toast.makeText(getActivity(), "your range : "+barValue+ " kilometers", Toast.LENGTH_LONG).show();
                        }

                    });


                    popDialog.show();
                }


                if (which==1) {
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), setLocationActivity.class);
                    startActivity(intent);
                }


            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();








        return root;
    }
    public void notifyMe(final Context context) {


        Call<AccessToken> call = pushApi.GetAccessToken("client_credentials",
                "666be9b61df3ae31838b96bf01d61a5dd4216f826483fd246c1b51f7bf2f6638", 102480429);
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
        
        NotificationBody builder = new NotificationBody.Builder
                ("EarthquakeApp", "Your location has been set.", pushToken).build();


        Call<NotificationMessage> callpush = sendPush.createNotification(accesstoken, builder);
        callpush.enqueue(new Callback<NotificationMessage>() {
            @Override
            public void onResponse(Call<NotificationMessage> call, Response<NotificationMessage> response) {

            }

            @Override
            public void onFailure(Call<NotificationMessage> call, Throwable t) {
                Toast.makeText(getActivity(), "fail", Toast.LENGTH_LONG).show();
            }
        });



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


                }
            }
        }.start();
        return pushToken;
    }





/*
      private  void  customAlert(){
          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
          //you should edit this to fit your needs
          builder.setTitle("Double Edit Text");

          final EditText one = new EditText(getActivity());
          one.setHint("Latitude");
          final EditText two = new EditText(getActivity());
          two.setHint("Longitude");



          one.setInputType(InputType.TYPE_CLASS_TEXT);
          two.setInputType(InputType.TYPE_CLASS_TEXT);

          LinearLayout lay = new LinearLayout(getActivity());
          lay.setOrientation(LinearLayout.VERTICAL);
          lay.addView(one);
          lay.addView(two);
          builder.setView(lay);

          // Set up the buttons
          builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                  //get the two inputs
                  int i = Integer.parseInt(one.getText().toString());
                  int j = Integer.parseInt(two.getText().toString());
              }
          });

          builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                  dialog.cancel();
              }
          });
          builder.show();
      }
*/

}