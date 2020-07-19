package com.Huw.demoapp;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Huw.demoapp.Api_Models.TR_ApiModel;
import com.Huw.demoapp.AsyncTasks.AsyncTRMsgBox;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class NavigationLeftActivity extends AppCompatActivity {


   public static Location msg_location;

  public  static   List<String> msgData;

    private AppBarConfiguration mAppBarConfiguration;

    Snackbar snackbar;
    int snackValue=0;
     int msgValue=0;

     public static String mail;



     String familyname;
    String name;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_left);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                familyname= null;
                name= null;
                url= null;
            } else {
                url= extras.getString("uri");
                name= extras.getString("name");
                familyname= extras.getString("familyname");
            }
        } else {
           // newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
           // newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
           // newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             //  final ProgressDialog progressDialog=new ProgressDialog(NavigationLeftActivity.this);
              //  progressDialog.setMessage("Please wait..");




//           progressDialog.dismiss();


                if (snackValue==0){



                      snackbar=  Snackbar.make(view, "Welcome to Earthquake App.\n"
                              , Snackbar.LENGTH_INDEFINITE)
                              .setAction("Action", null);


                  View snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setMaxLines(20);
                  snackbar.show();
                    snackValue=1;
                }else{
                    snackbar.dismiss();
                    snackValue=0;
                }







            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);




        View  hview=navigationView.getHeaderView(0);


        TextView ma=hview.findViewById(R.id.myname);


        ImageView image=(ImageView)hview.findViewById(R.id.imageView);


       if (name!=null && familyname!=null) {
           ma.setText(name + "  " + familyname);
       }

       if (url!=null) {
           Glide.with(this).load(url).apply(new RequestOptions().override(250, 250)).circleCrop().into(image);
       }



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);







        //  username.setText("berke.coba@gmail.com");



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_left, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}