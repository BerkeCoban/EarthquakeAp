package com.Huw.demoapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.Huw.demoapp.AccountActivity;
import com.Huw.demoapp.Api_Models.ApiInterface;
import com.Huw.demoapp.Api_Models.ApiModel;
import com.Huw.demoapp.Api_Models.ApiModel_Feature;
import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.Util.UrlGenerator;
import com.Huw.demoapp.ui.gallery.GalleryFragment;
import com.huawei.agconnect.https.OKHttpBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsyncWorldgetLastWeek extends AsyncTask<ArrayList<String>, String, List<ApiModel>> {







    Context con;
    AccountActivity accountActivity =new AccountActivity();

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiInterface.URL_GET)
            .client(new OKHttpBuilder().build())
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    ApiInterface init = retrofit.create(ApiInterface.class);

    UrlGenerator urlGenerator = new UrlGenerator();


    public AsyncWorldgetLastWeek(Context context){
this.con=context;
    }


    @Override
    protected List<ApiModel> doInBackground(ArrayList<String>... arrayLists) {



        ArrayList<String> result = new ArrayList<String>();
        result = arrayLists[0];


        List<ApiModel> toPost=new ArrayList<>();

        try {

            toPost.add(worldEarthquakeData(result.get(0),result.get(1)));
            toPost.add(worldEarthquakeData(result.get(1),result.get(2)));
            toPost.add(worldEarthquakeData(result.get(2),result.get(3)));
            toPost.add(worldEarthquakeData(result.get(3),result.get(4)));
            toPost.add(worldEarthquakeData(result.get(4),result.get(5)));
            toPost.add(worldEarthquakeData(result.get(5),result.get(6)));
            toPost.add(worldLastEarthquakeData(result.get(6)));

        } catch (IOException e) {
            e.printStackTrace();
        }





     return  toPost;

    }

    @Override
    protected void onPostExecute(List<ApiModel> apiModels) {
        super.onPostExecute(apiModels);
        GalleryFragment.worlddata=apiModels;
        accountActivity.login(con);



    }

    public ApiModel worldEarthquakeData(String startData, String endDate) throws IOException {



        Call<ApiModel> callist = init.getDynamic(urlGenerator.getWorldUrl(startData,endDate,"0"));
        Response<ApiModel> aa =   callist.execute();

        return aa.body();
    }

    public ApiModel worldLastEarthquakeData(String date) throws IOException {



        Call<ApiModel> callist = init.getDynamic(urlGenerator.getWorldUrlLast(date,"0"));
        Response<ApiModel> aa =   callist.execute();

        return aa.body();
    }

}
