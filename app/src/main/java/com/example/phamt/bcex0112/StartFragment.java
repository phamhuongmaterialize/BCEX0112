package com.example.phamt.bcex0112;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by phamt on 2018/01/16.
 */


public class StartFragment extends Fragment {

    /**
     * Root View
     */
    private View mRootView = null;

    private MainActivity main = null;

    private Button mLoginButton = null;
    private Button mRegisterButton = null;

    public static StartFragment newInstance() {
        StartFragment mStartFragment = new StartFragment();
        return mStartFragment;
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
        return mRootView = inflater.inflate(R.layout.start, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        main = (MainActivity) getActivity();

        mRegisterButton = (Button) mRootView.findViewById(R.id.start_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mLoginButton = (Button) mRootView.findViewById(R.id.start_login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                main.setViewTab(MainActivity.TabFragment.LOGIN);
            }
        });


    }
}
