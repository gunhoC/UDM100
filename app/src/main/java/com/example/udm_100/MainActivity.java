package com.example.udm_100;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements ServerResponse {


    private static  android.graphics.Typeface Typeface;
    public static FragmentManager fm;
    public static FragmentTransaction ft;
    public static Fragment frame;
    public static TextView Title;
    public static int redline,blueline;
    public static MainActivity mainActivity;

    public static Calendar now = Calendar.getInstance();
    public static int currentYear;
    public static int currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Title=findViewById(R.id.TITLE);

        android.graphics.Typeface typeface = Typeface.createFromAsset(getAssets(), "font/BMDOHYEON_ttf.ttf");
        Title.setTypeface(typeface);
        blueline=100;
        redline=80;

        currentYear = now.get(Calendar.YEAR);
        currentMonth = now.get(Calendar.MONTH) + 1;

        fm=getFragmentManager();
        frame = fm.findFragmentById(R.id.frame);
        ft=fm.beginTransaction();

        ft.replace(R.id.frame,new Realtime());
        ft.addToBackStack(null);
        ft.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu1:
                    Title.setText("오늘의 생산효율");
                    ft=fm.beginTransaction();
                    ft.replace(R.id.frame,new Realtime());
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
                case R.id.menu2:
                    Title.setText("월별 생산효율");
                    ft=fm.beginTransaction();
                    ft.replace(R.id.frame,new Monthly());
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
                case R.id.menu3:
                    return true;
                case R.id.menu4:
                    return true;
            }
            return false;
        }
    };

    public interface onKeyBackPressedListener {
        public void onBack();
    }
    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }
    @Override
    public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener.onBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void processFinish(String output) {
    }
}