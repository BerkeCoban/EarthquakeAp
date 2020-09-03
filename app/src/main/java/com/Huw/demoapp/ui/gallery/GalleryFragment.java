package com.Huw.demoapp.ui.gallery;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.Huw.demoapp.Api_Models.ApiModel;
import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.BarChart.Data;
import com.Huw.demoapp.ListView.listAdapter;
import com.Huw.demoapp.ListView.items;
import com.Huw.demoapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.xmlpull.v1.XmlPullParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    BarChart barChart;

    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelnames;

    ArrayList<Data> data = new ArrayList<>();


    public  static List<TR_ApiModel> trdata;
    public  static List<ApiModel> worlddata;
    public  static List<String> days;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        final View root = inflater.inflate(R.layout.activity_chart, container, false);


        barChart =root.findViewById(R.id.bargraph);

     //   @SuppressLint("WrongViewCast") final ListView mlistview =(ListView) root.findViewById(R.id.vvv);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);



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


        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                float x = e.getX();
                int d= (int) x;


               if (trdata!=null) {

                   if (d == 0) {
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < trdata.get(0).getResult().size(); i++) {

                           bb.add(trdata.get(0).getResult().get(i).getTitle());
                           bb.add("Time: " + trdata.get(0).getResult().get(i).getDate());
                           bb.add("Magnitude: " + trdata.get(0).getResult().get(i).getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + trdata.get(0).getResult().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   } else if (d == 1) {
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < trdata.get(1).getResult().size(); i++) {

                           bb.add(trdata.get(1).getResult().get(i).getTitle());
                           bb.add("Time: " + trdata.get(1).getResult().get(i).getDate());
                           bb.add("Magnitude: " + trdata.get(1).getResult().get(i).getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + trdata.get(1).getResult().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   } else if (d == 2) {
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < trdata.get(2).getResult().size(); i++) {

                           bb.add(trdata.get(2).getResult().get(i).getTitle());
                           bb.add("Time: " + trdata.get(2).getResult().get(i).getDate());
                           bb.add("Magnitude: " + trdata.get(2).getResult().get(i).getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + trdata.get(2).getResult().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   } else if (d == 3) {
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < trdata.get(3).getResult().size(); i++) {

                           bb.add(trdata.get(3).getResult().get(i).getTitle());
                           bb.add("Time: " + trdata.get(3).getResult().get(i).getDate());
                           bb.add("Magnitude: " + trdata.get(3).getResult().get(i).getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + trdata.get(3).getResult().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   } else if (d == 4) {
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < trdata.get(4).getResult().size(); i++) {

                           bb.add(trdata.get(4).getResult().get(i).getTitle());
                           bb.add("Time: " + trdata.get(4).getResult().get(i).getDate());
                           bb.add("Magnitude: " + trdata.get(4).getResult().get(i).getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + trdata.get(4).getResult().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   } else if (d == 5) {
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < trdata.get(5).getResult().size(); i++) {

                           bb.add(trdata.get(5).getResult().get(i).getTitle());
                           bb.add("Time: " + trdata.get(5).getResult().get(i).getDate());
                           bb.add("Magnitude: " + trdata.get(5).getResult().get(i).getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + trdata.get(5).getResult().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   } else if (d == 6) {
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < trdata.get(6).getResult().size(); i++) {

                           bb.add(trdata.get(6).getResult().get(i).getTitle());
                           bb.add("Time: " + trdata.get(6).getResult().get(i).getDate());
                           bb.add("Magnitude: " + trdata.get(6).getResult().get(i).getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + trdata.get(6).getResult().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   }

               } else if (worlddata!=null){


                   if (d == 0) {
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < worlddata.get(0).getFeatures().size(); i++) {

                           bb.add(worlddata.get(0).getFeatures().get(i).getProperties().getTitle());
                           bb.add("Time: " + getDate( worlddata.get(0).getFeatures().get(i).getProperties().getTime() ));
                           bb.add("Magnitude: " + worlddata.get(0).getFeatures().get(i).getProperties().getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + worlddata.get(0).getFeatures().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   }else if (d==1){
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < worlddata.get(1).getFeatures().size(); i++) {

                           bb.add(worlddata.get(1).getFeatures().get(i).getProperties().getTitle());
                           bb.add("Time: " + getDate( worlddata.get(1).getFeatures().get(i).getProperties().getTime() ));
                           bb.add("Magnitude: " + worlddata.get(1).getFeatures().get(i).getProperties().getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + worlddata.get(1).getFeatures().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   } else if (d==2){
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < worlddata.get(2).getFeatures().size(); i++) {

                           bb.add(worlddata.get(2).getFeatures().get(i).getProperties().getTitle());
                           bb.add("Time: " + getDate( worlddata.get(2).getFeatures().get(i).getProperties().getTime() ));
                           bb.add("Magnitude: " + worlddata.get(2).getFeatures().get(i).getProperties().getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + worlddata.get(2).getFeatures().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   }else if (d==3){
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < worlddata.get(3).getFeatures().size(); i++) {

                           bb.add(worlddata.get(3).getFeatures().get(i).getProperties().getTitle());
                           bb.add("Time: " + getDate( worlddata.get(3).getFeatures().get(i).getProperties().getTime() ));
                           bb.add("Magnitude: " + worlddata.get(3).getFeatures().get(i).getProperties().getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + worlddata.get(3).getFeatures().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   }else if (d==4){
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < worlddata.get(4).getFeatures().size(); i++) {

                           bb.add(worlddata.get(4).getFeatures().get(i).getProperties().getTitle());
                           bb.add("Time: " + getDate( worlddata.get(4).getFeatures().get(i).getProperties().getTime() ));
                           bb.add("Magnitude: " + worlddata.get(4).getFeatures().get(i).getProperties().getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + worlddata.get(4).getFeatures().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   } else  if (d==5){
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < worlddata.get(5).getFeatures().size(); i++) {

                           bb.add(worlddata.get(5).getFeatures().get(i).getProperties().getTitle());
                           bb.add("Time: " + getDate( worlddata.get(5).getFeatures().get(i).getProperties().getTime() ));
                           bb.add("Magnitude: " + worlddata.get(5).getFeatures().get(i).getProperties().getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + worlddata.get(5).getFeatures().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   }else if (d==6){
                       List<String> bb = new ArrayList<>();

                       for (int i = 0; i < worlddata.get(6).getFeatures().size(); i++) {

                           bb.add(worlddata.get(6).getFeatures().get(i).getProperties().getTitle());
                           bb.add("Time: " + getDate( worlddata.get(6).getFeatures().get(i).getProperties().getTime() ));
                           bb.add("Magnitude: " + worlddata.get(6).getFeatures().get(i).getProperties().getMag());
                           bb.add("");
                       }
                       String[] aa = new String[bb.size()];
                       bb.toArray(aa);
                       final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                       builder.setTitle("Occurrence : " + worlddata.get(6).getFeatures().size());
                       builder.setCancelable(true).setPositiveButton("close", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       });
                       builder.setItems(aa, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
                       AlertDialog alert = builder.create();
                       alert.show();
                   }

               }





            }

            @Override
            public void onNothingSelected() {

            }
        });











        return root;
    }
    private void add(){
        data.clear();

        if (trdata!=null) {
            data.add(new Data(days.get(0), trdata.get(0).getResult().size()));
            data.add(new Data(days.get(1), trdata.get(1).getResult().size()));
            data.add(new Data(days.get(2), trdata.get(2).getResult().size()));
            data.add(new Data(days.get(3), trdata.get(3).getResult().size()));
            data.add(new Data(days.get(4), trdata.get(4).getResult().size()));
            data.add(new Data(days.get(5), trdata.get(5).getResult().size()));
            data.add(new Data(days.get(6), trdata.get(6).getResult().size()));
        }
else if (worlddata!=null){
            data.add(new Data(days.get(0), worlddata.get(0).getFeatures().size() ));
            data.add(new Data(days.get(1), worlddata.get(1).getFeatures().size() ));
            data.add(new Data(days.get(2), worlddata.get(2).getFeatures().size() ));
            data.add(new Data(days.get(3), worlddata.get(3).getFeatures().size() ));
            data.add(new Data(days.get(4), worlddata.get(4).getFeatures().size() ));
            data.add(new Data(days.get(5), worlddata.get(5).getFeatures().size() ));
            data.add(new Data(days.get(6), worlddata.get(6).getFeatures().size() ));


        }




    }
    private String getDate(String time) {


        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd '  ' HH:mm:ss z");
        Date date = new Date(Long.parseLong(time));
        return sf.format(date);
    }

}