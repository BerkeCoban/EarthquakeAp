package com.Huw.demoapp.BarChart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;




import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

//com.github.mikephil.charting.charts.BarChart barChart;
    BarChart barChart;

ArrayList<BarEntry> barEntryArrayList;
ArrayList<String> labelnames;
ArrayList<Data> data = new ArrayList<>();


    public  static List<TR_ApiModel> mydata;
    public  static List<String> days;





    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

           // barChart =(com.github.mikephil.charting.charts.BarChart) findViewById(R.id.bargraph);
        barChart =findViewById(R.id.bargraph);



        barEntryArrayList=new ArrayList<>();
        labelnames =new ArrayList<>();
        add();

       // barEntryArrayList.clear();
       // labelnames.clear();
        for (int i=0 ;i<data.size();i++){

         String day=data.get(i).getDays();
        int acc=data.get(i).getAccurance();
        barEntryArrayList.add(new BarEntry(i,acc));
        labelnames.add(day);
        }

        BarDataSet barDataSet=new BarDataSet(barEntryArrayList,"Occurrence");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description=new Description();
        description.setText("Days");
        barChart.setDescription(description);
        BarData barData =new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis=barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelnames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelnames.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();









    }

    private void add(){
data.clear();
data.add(new Data(days.get(0),mydata.get(0).getResult().size()));
data.add(new Data(days.get(1),mydata.get(1).getResult().size()));
data.add(new Data(days.get(2),mydata.get(2).getResult().size()));
data.add(new Data(days.get(3),mydata.get(3).getResult().size()));
data.add(new Data(days.get(4),mydata.get(4).getResult().size()));
data.add(new Data(days.get(5),mydata.get(5).getResult().size()));
data.add(new Data(days.get(6),mydata.get(6).getResult().size()));

}


}
