package com.example.phamt.bcex0112;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by phamt on 2018/01/11.
 */

public class DeviceSettingDialog extends DialogFragment {
    /**
     * タグ
     */
    private static final String TAG = DeviceSettingDialog.class.getSimpleName();

    /**
     * フラグメントのレイアウト
     */
    private static final int FRGMLAYOUT_XML = R.layout.device_setting_detail;

    public static DeviceSettingDialog newInstance() {
        return new DeviceSettingDialog();
    }

    private RelativeLayout mSpaceLayout = null;

    private MainActivity main = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログを生成
        Dialog dialog = new Dialog(getActivity());

        // ダイアログ外タップで消えないように設定
        dialog.setCanceledOnTouchOutside(false);

        // DialogFragmentをタイトル無しにする
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(FRGMLAYOUT_XML);
        // 背景を透明に
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 画面全体に広げる
        dialog.getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater i, ViewGroup c, Bundle b) {
        View rootView = i.inflate(FRGMLAYOUT_XML, null);

        mSpaceLayout = (RelativeLayout) rootView.findViewById(R.id.spaceLay);
        mSpaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        main = (MainActivity) getActivity();

        // 「名前の変更」を押下
        rootView.findViewById(R.id.btnLay1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                main.setViewTab(MainActivity.TabFragment.DEVICE_NAME);

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                main.setViewTab(1);

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                main.setViewTab(1);

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                main.setViewTab(MainActivity.TabFragment.DEVICE_SWITCH);

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                main.setView(1);

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("")
//                        .setMessage("再起動しますか。")
//                        .setPositiveButton("はい", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Yes
//
//                            }
//                        })
//                        .setNegativeButton("いいえ", null)
//                        .setCancelable(false)
//                        .show();

                RestartDialog restartDialog = RestartDialog.newInstance();
                restartDialog.show(getFragmentManager(), "");

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("")
//                        .setMessage("初期化しますか。")
//                        .setPositiveButton("はい", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Yes
//
//                            }
//                        })
//                        .setNegativeButton("いいえ", null)
//                        .setCancelable(false)
//                        .show();

                InitialDialog initialDialog = InitialDialog.newInstance();
                initialDialog

                        .show(getFragmentManager(), "");

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                main.setViewTab(1);

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                main.setViewTab(1);
                main.setView(2);

                // ダイアログを非表示
                dismiss();
            }
        });

        //
        rootView.findViewById(R.id.btnLay10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
//                main.setViewTab(1);

                // ダイアログを非表示
                dismiss();
            }
        });


        return rootView;
    }

}
