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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    private List<BluetoothDevice> mBluetoothDeviceList = null;
    private Set<BluetoothDevice> mBluetoothDeviceSet = null;
    private BluetoothDeviceAdapter mBluetoothDeviceAdapter = null;

    private MainActivity main = null;
    private BluetoothService mBluetoothService = null;
    private IBluetoothCallback mIBluetoothCallback = null;

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
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mRootView = inflater.inflate(R.layout.bluetooth_device_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        mBluetoothDeviceListView = (ListView) mRootView.findViewById(R.id.bluetooth_device_listview);

        main = (MainActivity) getActivity();

        mIBluetoothCallback = new IBluetoothCallback() {
            @Override
            public void setBluetoothDeviceName(String paramString) {

            }

            @Override
            public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
//                mBluetoothDeviceList.add(bluetoothDevice);
                mBluetoothDeviceSet.add(bluetoothDevice);
                mBluetoothDeviceAdapter.notifyDataSetChanged();
            }
        };

        mBluetoothService = new BluetoothService(main,mIBluetoothCallback);

        if (mBluetoothService.checkBluetoothStatus()) {
            setBluetoothDeviceListView();
        }

        mBluetoothService.startFindDevice();


    }

    public void setBluetoothDeviceListView() {
        mBluetoothDeviceList = new ArrayList<>();
        mBluetoothDeviceList = mBluetoothService.getPairingDeivce();

        mBluetoothDeviceAdapter = new BluetoothDeviceAdapter(getContext(), mBluetoothDeviceList);
        mBluetoothDeviceListView.setAdapter(mBluetoothDeviceAdapter);

        mBluetoothDeviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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

            holder.name.setText(b.getName());


            convertView.setTag(holder);

            return convertView;
        }

        private class ViewHolder {
            TextView name;
        }
    }
}
