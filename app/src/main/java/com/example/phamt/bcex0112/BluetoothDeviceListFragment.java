package com.example.phamt.bcex0112;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by phamt on 2018/01/18.
 */

public class BluetoothDeviceListFragment extends Fragment {

    /**
     * Root View
     */
    private View mRootView = null;

    private ListView mBluetoothDeviceListView = null;
    private LinearLayout mBackImageLayout = null;
    private TextView mRefreshListTextView = null;

    private List<BluetoothDevice> mBluetoothDeviceList = null;
    private HashMap<String, String> mBluetoothDeviceHashMap = new HashMap();
    private BluetoothDeviceAdapter mBluetoothDeviceAdapter = null;

    private MainActivity main = null;
    private BluetoothService mBluetoothService = null;
    private IBluetoothCallback mIBluetoothCallback = new IBluetoothCallback() {
        @Override
        public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
            String name = "noname";
            if (bluetoothDevice.getName() != null) {
                name = bluetoothDevice.getName();
            }
            String address = bluetoothDevice.getAddress();
            if (!mBluetoothDeviceHashMap.containsKey(address)) {
//                    if(name == null){
//                        mBluetoothDeviceHashMap.put("noname", bluetoothDevice.getAddress());
//                    } else {
                mBluetoothDeviceHashMap.put(address, name);
//                    }
                mBluetoothDeviceList.add(bluetoothDevice);
                mBluetoothDeviceAdapter.notifyDataSetChanged();
            }

        }
    };

    public static BluetoothDeviceListFragment newInstance() {
        BluetoothDeviceListFragment mBluetoothDeviceListFragment = new BluetoothDeviceListFragment();
        return mBluetoothDeviceListFragment;
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
    public void onStop() {
        super.onStop();
        mBluetoothService.stopFindDevice();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBluetoothService.stopFindDevice();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRootView = inflater.inflate(R.layout.bluetooth_device_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        mBluetoothDeviceListView = (ListView) mRootView.findViewById(R.id.bluetooth_device_listview);

        mBackImageLayout = (LinearLayout) mRootView.findViewById(R.id.bluetooth_device_back_image_layout);
        mBackImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setViewTab(MainActivity.TabFragment.DEVICE_LIST);
            }
        });

        mRefreshListTextView = (TextView) mRootView.findViewById(R.id.bluetooth_device_refresh_textview);
        mRefreshListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBluetoothDeviceListView();
            }
        });


        main = (MainActivity) getActivity();

        mBluetoothService = new BluetoothService(main, mIBluetoothCallback);

        setBluetoothDeviceListView();
    }

    public void setBluetoothDeviceListView() {
        mBluetoothDeviceList = new ArrayList<>();
        mBluetoothDeviceHashMap = new HashMap<>();
        mBluetoothDeviceList = mBluetoothService.getPairedDeivce();
        for (BluetoothDevice bluetoothDevice : mBluetoothDeviceList) {
            if (bluetoothDevice.getName() == null) {
                mBluetoothDeviceHashMap.put(bluetoothDevice.getAddress(), "noname");
            } else {
                mBluetoothDeviceHashMap.put(bluetoothDevice.getName(), bluetoothDevice.getAddress());
            }
        }

        mBluetoothDeviceAdapter = new BluetoothDeviceAdapter(getContext(), mBluetoothDeviceList);
        mBluetoothDeviceListView.setAdapter(mBluetoothDeviceAdapter);

        mBluetoothDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        mBluetoothService.startFindDevice();
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

    private class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {
        private Context mContext = null;
        private LayoutInflater mLayoutInflater = null;
        private List<BluetoothDevice> mHistory = null;

        private BluetoothDeviceAdapter(Context context, List<BluetoothDevice> item) {
            super(context, 0, item);
            mContext = context;
            mHistory = item;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mHistory.size();
        }

        @Override
        public BluetoothDevice getItem(int position) {
            return mHistory.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            BluetoothDevice b = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.bluetooth_device_name_data, parent, false);
                holder = new ViewHolder();

                holder.name = (TextView) convertView.findViewById(R.id.bluetooth_device_listview_data_choice);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (b.getName() == null) {
                holder.name.setText("noname" + "(" + b.getAddress() + ")");
            } else {
                holder.name.setText(b.getName() + "(" + b.getAddress() + ")");
            }


            convertView.setTag(holder);

            return convertView;
        }

        private class ViewHolder {
            TextView name;
        }
    }

//    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
//        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
//
//            if(!mBluetoothService.mBluetoothAdapter.isEnabled()) {
//                Toast.makeText(getContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
////            mBluetoothStatus.setText("Connecting...");
//            // Get the device MAC address, which is the last 17 chars in the View
//            String info = ((TextView) v).getText().toString();
//            final String address = info.substring(info.length() - 17);
//            final String name = info.substring(0,info.length() - 17);
//
//            // Spawn a new thread to avoid blocking the GUI one
//            new Thread()
//            {
//                public void run() {
//                    boolean fail = false;
//
//                    BluetoothDevice device = mBluetoothService.mBluetoothAdapter.getRemoteDevice(address);
//
//                    try {
//                        mBTSocket = createBluetoothSocket(device);
//                    } catch (IOException e) {
//                        fail = true;
//                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
//                    }
//                    // Establish the Bluetooth socket connection.
//                    try {
//                        mBTSocket.connect();
//                    } catch (IOException e) {
//                        try {
//                            fail = true;
//                            mBTSocket.close();
//                            mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
//                                    .sendToTarget();
//                        } catch (IOException e2) {
//                            //insert code to deal with this
//                            Toast.makeText(getContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    if(fail == false) {
//                        mBluetoothService.mConnectedThread = new mBluetoothService.mConnectedThread(mBluetoothService.mConnectedThread.);
//                        mBluetoothService.mConnectedThread.start();
//
//                        mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
//                                .sendToTarget();
//                    }
//                }
//            }.start();
//        }
//    };
}
