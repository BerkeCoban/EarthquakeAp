package com.Huw.demoapp.BarChart;

public class Data {
String days;
int accurance;

    public Data(String days, int accurance) {
        this.days = days;
        this.accurance = accurance;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getAccurance() {
        return accurance;
    }

    public void setAccurance(int accurance) {
        this.accurance = accurance;
    }
}
