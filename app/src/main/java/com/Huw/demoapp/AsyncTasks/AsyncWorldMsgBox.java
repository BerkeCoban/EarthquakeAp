package com.Huw.demoapp.AsyncTasks;

import android.location.Location;
import android.os.AsyncTask;
import android.text.format.DateFormat;

import com.Huw.demoapp.Api_Models.ApiModel_Feature;
import com.Huw.demoapp.Api_Models.TR_ApiModel_Result;
import com.Huw.demoapp.ui.messagebox.MessageFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AsyncWorldMsgBox extends AsyncTask<List<ApiModel_Feature>, String, List<String>> {


    Location location;
    int range;

    public AsyncWorldMsgBox(Location loc,int ran){
        this.location=loc;
        this.range=ran*1000;
  }




    @Override
    protected List<String> doInBackground(List<ApiModel_Feature>... lists) {


        List<ApiModel_Feature> weekData;
        ArrayList<String> toPost=new ArrayList<>();
        weekData = lists[0];

        int size = weekData.size();


        for (int i = 0; i<size; i++) {



            Double lat = weekData.get(i).getGeometry().getCoordinates().get(1);
            Double lon = weekData.get(i).getGeometry().getCoordinates().get(0);

            Location locationA = new Location("point A");
            locationA.setLatitude(lat);
            locationA.setLongitude(lon);



            float distance = locationA.distanceTo(location);

            if (distance<range){
                toPost.add(weekData.get(i).getProperties().getTitle());
                toPost.add(" Time :"+ getDate( weekData.get(i).getProperties().getTime() ));
                toPost.add(" magnitude : "+weekData.get(i).getProperties().getMag());
                toPost.add(lat.toString());
                toPost.add(lon.toString());


            }


        }


    return toPost;

    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        MessageFragment.msgData=strings;
    }

    private String getDate(String time) {


        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd '  ' HH:mm:ss z");
        Date date = new Date(Long.parseLong(time));
        return sf.format(date);
    }



}
