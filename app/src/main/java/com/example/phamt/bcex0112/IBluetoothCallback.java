package com.example.phamt.bcex0112;

import android.bluetooth.BluetoothDevice;

/**
 * Created by phamt on 2018/01/18.
 */

public abstract interface IBluetoothCallback {
    public abstract void setBluetoothDeviceName(String paramString);

    public abstract void setBluetoothDevice(BluetoothDevice bluetoothDevice);


}
