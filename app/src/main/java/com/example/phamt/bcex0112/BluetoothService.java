package com.example.phamt.bcex0112;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by phamt on 2018/01/18.
 */

public class BluetoothService {

    private static final String TAG = BluetoothService.class.getSimpleName();

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status

    private ConnectedThread mConnectedThread;
    private BluetoothAdapter mBluetoothAdapter;
    public IBluetoothCallback mIBluetoothCallback;

    private Context mContext;
    private Activity mActivity;

    public Activity getmActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public BluetoothService(Activity mActivity, IBluetoothCallback iBluetoothCallback) {
        this.mActivity = mActivity;
        this.mIBluetoothCallback = iBluetoothCallback;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name to the list
                mIBluetoothCallback.setBluetoothDevice(device);
//                Toast.makeText(mActivity.getApplicationContext(), device.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void startFindDevice() {
        if (mBluetoothAdapter.isDiscovering()) {
            mActivity.registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            return;
        } else {
            mBluetoothAdapter.startDiscovery();
            mActivity.registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    public  void stopFindDevice(){
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    public ArrayList<BluetoothDevice> getPairedDeivce() {
        ArrayList<BluetoothDevice> bluetoothDeviceArrayList = new ArrayList<>();
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        Set<BluetoothDevice> bluetoothDeviceSet = mBluetoothAdapter.getBondedDevices();
        if (bluetoothDeviceSet.size() > 0) {
            Iterator iterator = bluetoothDeviceSet.iterator();
            while (iterator.hasNext()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                bluetoothDeviceArrayList.add(bluetoothDevice);
            }
        }
        return bluetoothDeviceArrayList;
    }

    public boolean checkBluetoothStatus() {
        if (mBluetoothAdapter.isEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    public void bluetoothON() {
        if (!checkBluetoothStatus()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
//            Toast.makeText(mActivity.getApplicationContext(), "Bluetooth is already on!", Toast.LENGTH_SHORT).show();
            return;
        }

    }


    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;


        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
    }
}
