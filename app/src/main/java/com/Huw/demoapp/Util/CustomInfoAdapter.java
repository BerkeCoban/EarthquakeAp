package com.Huw.demoapp.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.Huw.demoapp.NavigationLeftActivity;
import com.Huw.demoapp.R;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Marker;

import static androidx.core.content.ContextCompat.getSystemService;

public class CustomInfoAdapter  implements HuaweiMap.InfoWindowAdapter {

    private View mWindow;
    private View mWindow2;
    private View mWindow3;
    private View mWindow4;

    private Context mContext;








    @SuppressLint("InflateParams")
    public CustomInfoAdapter(ViewGroup group,Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.markeroptions ,null);





    }

    private void rendowWindowText(Marker marker, View view) {
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.markertitle);

        if (!title.equals("")) {
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.markertime);

        if (!snippet.equals("")) {
            tvSnippet.setText(snippet);
        }
    }



    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker,mWindow);
        return mWindow;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker,mWindow);
        return mWindow;
    }
}
