package com.example.phamt.bcex0112;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mTabContainer = null;
    private LinearLayout mFragmentContainer = null;

    private RelativeLayout mUploadTitleLayout = null;
    private RelativeLayout mDeviceTitleLayout = null;
    private View mUploadBorder = null;
    private View mDeviceBorder = null;



    public enum TabFragment {
        DEVICE_LIST,
        UPLOAD,
        DEVICE_NAME,
        DEVICE_DETAIL,
        DEVICE_UPLOAD,
        DEVICE_SCHEDULE,
        DEVICE_CONTENT,
        DEVICE_SETTING,
        DEVICE_SWITCH,
        LOGIN,
        IMAGE_DETAIL,
        IMAGE_UPLOAD,
        START

    }


    public Class<?> getmLastclass() {
        return mLastclass;
    }

    public void setmLastclass(Class<?> mLastclass) {
        this.mLastclass = mLastclass;
    }

    private Class<?> mLastclass = null;

    public Fragment mNowFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabContainer = (LinearLayout) findViewById(R.id.tabs_container);
        mFragmentContainer = (LinearLayout) findViewById(R.id.fragment_container);

        mUploadTitleLayout = (RelativeLayout) findViewById(R.id.tab_upload_title_layout);
        mDeviceTitleLayout = (RelativeLayout) findViewById(R.id.tab_device_title_layout);
        mUploadBorder = (View) findViewById(R.id.tab_border_upload);
        mDeviceBorder = (View) findViewById(R.id.tab_border_device);

        mUploadTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewTab(TabFragment.UPLOAD);
                mDeviceBorder.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mUploadBorder.setBackgroundColor(Color.parseColor("#bdbdbd"));
            }
        });

        mDeviceTitleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewTab(TabFragment.DEVICE_LIST);
                mDeviceBorder.setBackgroundColor(Color.parseColor("#bdbdbd"));
                mUploadBorder.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        setViewTab(TabFragment.START);
    }

    public void setView(int i) {
        switch (i){
            case 1:
                Intent intent1 = new Intent(this,SlideEffectActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(this,DisplayTypeClass.class);
                startActivity(intent2);
                break;
        }
    }

    public void setViewTab(TabFragment tab) {
        //
        if (tab == TabFragment.DEVICE_LIST) {
            mTabContainer.setVisibility(View.VISIBLE);
            mFragmentContainer.setVisibility(View.GONE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.tab_content_layout, DeviceListFragment.newInstance());
            transaction.commit();

        } else if (tab == TabFragment.UPLOAD) {
            mTabContainer.setVisibility(View.VISIBLE);
            mFragmentContainer.setVisibility(View.GONE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.tab_content_layout, UploadFragment.newInstance());
            transaction.commit();

            //Change name tab
        } else if (tab == TabFragment.DEVICE_NAME) {
            mTabContainer.setVisibility(View.GONE);
            mFragmentContainer.setVisibility(View.VISIBLE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, DeviceNameFragment.newInstance());
            transaction.commit();

        } else if (tab == TabFragment.DEVICE_SWITCH) {
            mTabContainer.setVisibility(View.GONE);
            mFragmentContainer.setVisibility(View.VISIBLE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, DeviceSwitchTimeFragment.newInstance());
            transaction.commit();
        } else if (tab == TabFragment.LOGIN) {
            mTabContainer.setVisibility(View.GONE);
            mFragmentContainer.setVisibility(View.VISIBLE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, LoginFragment.newInstance());
            transaction.commit();
        } else if (tab == TabFragment.START) {
            mTabContainer.setVisibility(View.GONE);
            mFragmentContainer.setVisibility(View.VISIBLE);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, StartFragment.newInstance());
            transaction.commit();
        } else if (tab == TabFragment.DEVICE_UPLOAD) {
            mTabContainer.setVisibility(View.GONE);
            mFragmentContainer.setVisibility(View.VISIBLE);

            mNowFragment = BluetoothDeviceListFragment.newInstance();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_container, BluetoothDeviceListFragment.newInstance());

            if(!isFinishing()){
                transaction.commitAllowingStateLoss();
            } else {
                transaction.commit();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data){
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                Toast.makeText(getApplicationContext(),"Bluetooth is enabled!", Toast.LENGTH_SHORT).show();
////                if(mNowFragment == BluetoothDeviceListFragment.newInstance()){
//                mNowFragment = BluetoothDeviceListFragment.newInstance();
//                    ((BluetoothDeviceListFragment)mNowFragment).refresh();
////                }
                setViewTab(TabFragment.DEVICE_UPLOAD);
            }
            else
                Toast.makeText(getApplicationContext(),"Bluetooth is disabled!", Toast.LENGTH_SHORT).show();
        }
    }

}
