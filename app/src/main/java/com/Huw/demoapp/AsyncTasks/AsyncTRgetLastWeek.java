package com.Huw.demoapp.AsyncTasks;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.Huw.demoapp.AccountActivity;
import com.Huw.demoapp.Api_Models.ApiInterface;
import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.Api_Models.TR_ApiModel_Result;
import com.Huw.demoapp.Util.UrlGenerator;
import com.Huw.demoapp.setLocationActivity;
import com.Huw.demoapp.ui.gallery.GalleryFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

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

public class AsyncTRgetLastWeek extends AsyncTask<ArrayList<String>, String, List<TR_ApiModel>> {




    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiInterface.URL_GET)
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ApiInterface init = retrofit.create(ApiInterface.class);

    UrlGenerator urlGenerator = new UrlGenerator();
    AccountActivity accountActivity=new AccountActivity();
    Context con;





   public AsyncTRgetLastWeek(Context context){
       this.con=context;
   }




     @Override
     protected List<TR_ApiModel> doInBackground(ArrayList<String>... lists) {







         ArrayList<String> result = new ArrayList<String>();
         result = lists[0];


         List<TR_ApiModel> toPost=new ArrayList<>();

         try {
             toPost.add(TRDatedEarthquakeData(result.get(0),"1000"));
             toPost.add(TRDatedEarthquakeData(result.get(1),"1000"));
             toPost.add(TRDatedEarthquakeData(result.get(2),"1000"));
             toPost.add(TRDatedEarthquakeData(result.get(3),"1000"));
             toPost.add(TRDatedEarthquakeData(result.get(4),"1000"));
             toPost.add(TRDatedEarthquakeData(result.get(5),"1000"));
             toPost.add(TRDatedEarthquakeData(result.get(6),"1000"));
         } catch (IOException e) {
             e.printStackTrace();
         }







         return toPost;
     }




     @Override
     protected void onPostExecute(List<TR_ApiModel> tr_apiModels) {
         super.onPostExecute(tr_apiModels);
        GalleryFragment.trdata=tr_apiModels;

         accountActivity.context=con;
        accountActivity.login(con);





        //Intent intent = new Intent (con, setLocationActivity.class);
        // con.startActivity(intent);






     }

     public TR_ApiModel TRDatedEarthquakeData(String date, String limit) throws IOException {



        Call<TR_ApiModel> callist = init.getDynamicTr(urlGenerator.getDatedUrl(date,limit));
        Response<TR_ApiModel> aa =   callist.execute();

  return aa.body();
     }


 }
