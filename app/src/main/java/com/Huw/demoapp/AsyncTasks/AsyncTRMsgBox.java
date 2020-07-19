package com.Huw.demoapp.AsyncTasks;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.Api_Models.TR_ApiModel_Result;
import com.Huw.demoapp.NavigationLeftActivity;
import com.Huw.demoapp.ui.messagebox.MessageFragment;

import java.util.ArrayList;
import java.util.List;

public class AsyncTRMsgBox extends AsyncTask<List<TR_ApiModel_Result>, String, List<String>> {

    Location location;
    List<TR_ApiModel_Result> result;


    public AsyncTRMsgBox(Location loc){
        this.location=loc;
    }


    @Override
    protected List<String> doInBackground(List<TR_ApiModel_Result>... lists) {

        List<TR_ApiModel_Result> weekData;
        ArrayList<String> toPost=new ArrayList<>();

        weekData = lists[0];

       int size = weekData.size();

     //   Double givenLat = location.getLatitude();
     //   Double givenLon = location.getLongitude();


        for (int i = 0; i<size; i++) {



               Double lat = weekData.get(i).getLat();
               Double lon = weekData.get(i).getLng();

               Location locationA = new Location("point A");
               locationA.setLatitude(lat);
               locationA.setLongitude(lon);



               float distance = locationA.distanceTo(location);

               if (distance<300000){
                   toPost.add(weekData.get(i).getTitle());
                   toPost.add(" Time :"+weekData.get(i).getDate());
                   toPost.add(" magnitude : "+weekData.get(i).getMag().toString());
                   toPost.add(weekData.get(i).getLat().toString());
                   toPost.add(weekData.get(i).getLng().toString());


               }


           }




        return toPost;

    }




    @Override
    protected void onPostExecute(List<String> tr_apiModels) {
        super.onPostExecute(tr_apiModels);

        MessageFragment.msgData=tr_apiModels;




    }


}







