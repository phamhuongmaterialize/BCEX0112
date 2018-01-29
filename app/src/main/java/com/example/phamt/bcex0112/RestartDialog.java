package com.example.phamt.bcex0112;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

/**
 * Created by phamt on 2018/01/29.
 */

public class RestartDialog extends DialogFragment {

    /** タグ */
    private static final String TAG = RestartDialog.class.getSimpleName();

    /** フラグメントのレイアウト */
    private static final int FRGMLAYOUT_XML = R.layout.restart_dialog;

    private Button mYesButton = null;
    private Button mNoButton = null;

    private Dialog dialog = null;public View rootLay = null;

    //======================================================================
    public static RestartDialog newInstance() {
        RestartDialog inst = new RestartDialog();
        return inst;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getContext());

        // ダイアログ外タップで消えないように設定
        dialog.setCanceledOnTouchOutside(false);

        // DialogFragmentをタイトル無しにする
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(FRGMLAYOUT_XML);

        return dialog;
    }

    //======================================================================
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle b) {

        rootLay = i.inflate(FRGMLAYOUT_XML, null);

        mNoButton = (Button) rootLay.findViewById(R.id.restart_device_button_no);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mYesButton = (Button) rootLay.findViewById(R.id.restart_device_button_yes);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootLay;
    }
}
