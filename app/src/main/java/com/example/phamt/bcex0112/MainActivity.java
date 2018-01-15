package com.example.phamt.bcex0112;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    private LinearLayout mTabContainer = null;
    private LinearLayout mFragmentContainer = null;



    public Class<?> getmLastclass() {
        return mLastclass;
    }

    public void setmLastclass(Class<?> mLastclass) {
        this.mLastclass = mLastclass;
    }

    private Class<?> mLastclass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabContainer = (LinearLayout) findViewById(R.id.tab_container);
        mFragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);

        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("device").setIndicator("Device"),
                DeviceListFragment.newInstance().getClass(), null);
        mTabHost.addTab(mTabHost.newTabSpec("upload").setIndicator("Upload"),
                UploadFragment.newInstance().getClass(), null);

    }

    public  void setViewTab(int tab){
        //Main tab
        if (tab == 1) {
            mTabContainer.setVisibility(View.VISIBLE);
            mFragmentContainer.setVisibility(View.GONE);

        //Change name tab
        } else if (tab == 2){
            mTabContainer.setVisibility(View.GONE);
            mFragmentContainer.setVisibility(View.VISIBLE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, DeviceNameFragment.newInstance());
            transaction.commit();

//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction transaction = fm.beginTransaction();
//            transaction.replace(R.id.fragment_container, DeviceListFragment.newInstance());
//            transaction.commit();
        } else if (tab == 5){
            mTabContainer.setVisibility(View.GONE);
            mFragmentContainer.setVisibility(View.VISIBLE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, DeviceSwitchTimeFragment.newInstance());
            transaction.commit();
        }
    }

}
