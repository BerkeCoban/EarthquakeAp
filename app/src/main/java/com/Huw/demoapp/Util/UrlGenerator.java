package com.Huw.demoapp.Util;

public class UrlGenerator {

    private final String undated_root_url="https://api.orhanaydogdu.com.tr/deprem/live.php?limit=";
    private final String dated_root_url="https://api.orhanaydogdu.com.tr/deprem/index.php?date=";


    private final String root_url="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=";




    public String getUndatedUrl(String limit){

    return undated_root_url+limit;
}

public String getDatedUrl(String date,String limit){

    return  dated_root_url+date+"&limit="+limit;

}


public String getWorldUrl(String startDate,String endDate,String minmag){


return  root_url+startDate+"&endtime="+endDate+"&minmagnitude="+minmag;
}

public String getWorldUrlLast(String date ,String mag){


    return  root_url+date+"&minmagnitude="+mag;
}



    //https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2020-07-28&minmagnitude=0


//https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2020-07-27&endtime=2020-07-28
// &minmagnitude=0



}
