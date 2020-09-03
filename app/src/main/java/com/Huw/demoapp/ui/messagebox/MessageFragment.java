package com.Huw.demoapp.ui.messagebox;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.Huw.demoapp.AccountActivity;
import com.Huw.demoapp.ListView.items;
import com.Huw.demoapp.ListView.listAdapter;
import com.Huw.demoapp.NavigationLeftActivity;
import com.Huw.demoapp.R;
import com.Huw.demoapp.Util.UrlGenerator;
import com.Huw.demoapp.ui.home.HomeFragment;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

public class MessageFragment extends Fragment {



    public  static List<String> msgData;
    ArrayList<items> msg =new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View rootview=inflater.inflate(R.layout.fragment_message,container,false);




   if (msgData.size()>0) {


         //check async task for here
       for (int i = 0; i < msgData.size(); i += 5) {
           msg.add(new items(msgData.get(i), msgData.get(i + 1), msgData.get(i + 2)));

       }
   }else {

       msg.add(new items("Uygun veri bulunamadi.", "", ""));
   }



         listAdapter adapter = new listAdapter(getActivity(),msg,R.color.colorcc);
        SwipeMenuListView listView = rootview.findViewById(R.id.listviewmsg);


        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xff, 0x00,
                        0x00)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Show");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

            }
        };

// set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                     int value=3;


                     for (int i = 0; i<msgData.size(); i++) {

                    if (position == i) {
                            HomeFragment.isFromMessage = true;

                           if (position==0) {
                               HomeFragment.lat = Double.parseDouble(msgData.get(3));
                               HomeFragment.lon = Double.parseDouble(msgData.get(4));
                           }else{
                               HomeFragment.lat = Double.parseDouble(msgData.get(i*5+value));
                               HomeFragment.lon = Double.parseDouble(msgData.get(i*5+value+1));

                           }

                            Intent intent = new Intent(getActivity(), NavigationLeftActivity.class);
                            startActivity(intent);

                        }


                }



                return false;
            }
        });











//<View
//        android:layout_width="match_parent"
//        android:layout_height="1dp"
//        android:background="#e7e7e7" />
       /*
        ListView listView=(ListView) view.findViewById(R.id.listview);

        String[] items={"berkeberkebe\nrkeberkeberkeberk\neberkeberkeberkeb","cobanberkeberkeberkeberkeberke"};

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1
                ,items
        );

        listView.setAdapter(listViewAdapter);

*/


        return  rootview;
    }
}
