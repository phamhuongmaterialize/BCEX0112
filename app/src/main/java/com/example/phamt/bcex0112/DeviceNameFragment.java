package com.example.phamt.bcex0112;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by phamt on 2018/01/12.
 */

public class DeviceNameFragment extends Fragment {

    /**
     * Root View
     */
    private View mRootView = null;

    private MainActivity main = null;

    private ImageView mBackImageView = null;
    private TextView mConfirmationTextView = null;
    private EditText mNameEditText = null;

    public static DeviceNameFragment newInstance() {
        DeviceNameFragment mDeviceNameFragment = new DeviceNameFragment();
        return mDeviceNameFragment;
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
        return mRootView = inflater.inflate(R.layout.device_name_change, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        main = (MainActivity) getActivity();

        mBackImageView = (ImageView) mRootView.findViewById(R.id.device_name_back_imageview);
        mConfirmationTextView = (TextView) mRootView.findViewById(R.id.device_name_confimation_textview);
        mNameEditText = (EditText) mRootView.findViewById(R.id.device_name_edittext);

        mBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setViewTab(MainActivity.TabFragment.DEVICE_LIST);
            }
        });

        mConfirmationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), mNameEditText.getText(), Toast.LENGTH_LONG).show();
                main.setViewTab(MainActivity.TabFragment.DEVICE_LIST);
            }
        });

        mNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mNameEditText.setText("");
                } else {
                    mNameEditText.setHint(getResources().getString(R.string.please_enter_name));
                }
                mNameEditText.setTextColor(Color.BLACK);
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
                    Log.v("DeviceNameFragment", "バックボタン押下");

                    return true;
                }
                return false;
            }
        });
    }
}

