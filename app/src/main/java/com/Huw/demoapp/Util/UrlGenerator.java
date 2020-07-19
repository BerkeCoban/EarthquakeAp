package com.Huw.demoapp.Util;

public class UrlGenerator {

    private final String undated_root_url="https://api.orhanaydogdu.com.tr/deprem/live.php?limit=";
    private final String dated_root_url="https://api.orhanaydogdu.com.tr/deprem/index.php?date=";
    private final String root_url="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=";

//https://api.orhanaydogdu.com.tr/deprem/index.php?date=2020-01-01&limit=100

    //https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2020-07-04&minmagnitude=5

    //


    public String getUndatedUrl(String limit){

    return undated_root_url+limit;
}

public String getDatedUrl(String date,String limit){

    return  dated_root_url+date+"&limit="+limit;

}


public String getUrl(String date){


return  root_url+date;
}

public String getUrlWmag(String date ,String mag){


    return  root_url+date+"&minmagnitude="+mag;
}

public  String getYandexUrl(String lon,String lat){


     return "https://yandex.com.tr/harita/?ll="+lon+"%2C"+lat+"&mode=whatshere&whatshere%5Bpoint%5D="+lon+"%2C"+lat+"&whatshere%5Bzoom%5D=8&z=8";
}






}
