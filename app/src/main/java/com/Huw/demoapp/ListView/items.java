package com.Huw.demoapp.ListView;

import android.widget.ImageView;

public class items {

String location;
String mag;
String time;
int imageResource;

    public items(String location, String mag, String time) {
        this.location = location;
        this.mag = mag;
        this.time = time;


    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
