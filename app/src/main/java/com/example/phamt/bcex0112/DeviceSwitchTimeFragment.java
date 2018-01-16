package com.example.phamt.bcex0112;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by phamt on 2018/01/15.
 */

public class DeviceSwitchTimeFragment extends Fragment {

    /**
     * Root View
     */
    private View mRootView = null;

    private MainActivity main = null;

    private ImageView mBackImageView = null;
    private Button mConfirmationButton = null;
    private NumberPicker mMinutePicker = null;
    private NumberPicker mSecondPicker = null;

    public static DeviceSwitchTimeFragment newInstance() {
        DeviceSwitchTimeFragment mDeviceSwitchTimeFragment = new DeviceSwitchTimeFragment();
        return mDeviceSwitchTimeFragment;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRootView = inflater.inflate(R.layout.device_switching_time, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        main = (MainActivity) getActivity();

        mBackImageView = (ImageView) mRootView.findViewById(R.id.device_switch_time_back_imageview);
        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setViewTab(MainActivity.TabFragment.DEVICE_LIST);
            }
        });

        mMinutePicker = (NumberPicker) mRootView.findViewById(R.id.device_switching_time_minute_picker);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(30);
        mSecondPicker = (NumberPicker) mRootView.findViewById(R.id.device_switching_time_second_picker);
        mSecondPicker.setMinValue(10);
        mSecondPicker.setMaxValue(59);
        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (i1 != 0) {
                    mSecondPicker.setMinValue(0);
                    mSecondPicker.setMaxValue(59);
                } else {
                    mSecondPicker.setMinValue(10);
                    mSecondPicker.setMaxValue(59);
                }
            }
        });

        mConfirmationButton = (Button) mRootView.findViewById(R.id.device_switching_time_confirmation_button);
        mConfirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), mMinutePicker.getValue() + "分" + mSecondPicker.getValue() + "秒", Toast.LENGTH_LONG).show();
                main.setViewTab(MainActivity.TabFragment.DEVICE_LIST);
            }
        });


    }

    //バックボタン押下
    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    main.setViewTab(MainActivity.TabFragment.DEVICE_LIST);
                    Log.v("DeviceSwitchTime", "バックボタン押下");

                    return true;
                }
                return false;
            }
        });
    }
}


