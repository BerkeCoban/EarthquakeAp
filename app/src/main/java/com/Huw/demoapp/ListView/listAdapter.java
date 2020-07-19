package com.Huw.demoapp.ListView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Huw.demoapp.R;

import java.util.List;

public class listAdapter extends ArrayAdapter<items> {

    Context mContext;
    int mResource;


    public listAdapter(Context context,List<items> objects,int resource) {
        super(context, 0, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



         View listViewItems=convertView;




        if (listViewItems == null) {
            listViewItems = LayoutInflater.from(getContext()).inflate(R.layout.listview_sample,
                    parent,false);

        }


        items myitems=getItem(position);

        TextView title = listViewItems.findViewById(R.id.mytitle);
        title.setText(myitems.getLocation());
        title.setTextColor(Color.BLACK);

        TextView mag = listViewItems.findViewById(R.id.magg);
        mag.setText(myitems.getMag());
        mag.setTextColor(Color.BLACK);

        TextView time = listViewItems.findViewById(R.id.timee);
        time.setText(myitems.getTime());
        time.setTextColor(Color.BLACK);



        ImageView imageView = listViewItems.findViewById(R.id.photoEarth);
       imageView.setImageResource(myitems.getImageResource());




            return listViewItems;

        }

    }

