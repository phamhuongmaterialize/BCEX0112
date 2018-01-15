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
import android.widget.Toast;

/**
 * Created by phamt on 2018/01/15.
 */

public class LoginFragment extends Fragment {

    /** Root View */
    private View mRootView = null;

    private MainActivity main = null;

    private Button mLoginButton = null;
    private EditText mMailEditText = null;
    private EditText mPassEditText = null;

    public static LoginFragment newInstance() {
        LoginFragment mLoginFragment  = new LoginFragment();
        return  mLoginFragment;
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
        return mRootView = inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        main = (MainActivity) getActivity();

        mMailEditText = (EditText) mRootView.findViewById(R.id.login_mail_edittext);
        mMailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    mMailEditText.setText("");
                } else {
                    mMailEditText.setHint(getResources().getString(R.string.please_enter_name));
                }
                mMailEditText.setTextColor(Color.BLACK);
            }
        });

        mPassEditText = (EditText) mRootView.findViewById(R.id.login_password_edittext);
        mPassEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    mPassEditText.setText("");
                } else {
                    mPassEditText.setHint(getResources().getString(R.string.please_enter_name));
                }
                mPassEditText.setTextColor(Color.BLACK);
            }
        });

        mLoginButton = (Button) mRootView.findViewById(R.id.login_button) ;
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"login" , Toast.LENGTH_LONG ).show();
                main.setViewTab(1);
            }
        });


    }

//    //バックボタン押下
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        if(getView() == null){
//            return;
//        }
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//                    main.setViewTab(1);
//                    Log.v("DeviceSwitchTime","バックボタン押下");
//
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
}
