package com.example.matchcontroller.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by UnVrai on 2015/7/18.
 */
public class BluetoothService {

    static BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();;
    static Map<String, BluetoothDevice> deviceMap = new HashMap<>();
    static BluetoothDevice device = null;
    static LinkThread linkThread;
    //��ȡ����Ե������豸
    public static String[] getDevicesName() {
        Set<BluetoothDevice> device= adapter.getBondedDevices();
        if(device.size()>0) {
            List<String> devicesName = new ArrayList<>();
            for (Iterator iterator = device.iterator(); iterator.hasNext(); ) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                String deviceName = bluetoothDevice.getName();
                deviceMap.put(deviceName, bluetoothDevice);
                devicesName.add(deviceName);
            }
            return devicesName.toArray(new String[0]);
        }
        return null;
    }
    //�����û�ѡ��������豸Ϊ�����豸
    public static void setBluetoothDevice(String deviceName) {
        BluetoothService.device = deviceMap.get(deviceName);
    }
    //���������豸���߳�
    public static void link() throws Exception {
        linkThread = new LinkThread();
    }
    //���������߳���
    private static class LinkThread extends Thread {
        private Handler mHandler;
        private Looper mLooper;
        private BluetoothSocket socket = null;
        //����ʱ�����߳�
        LinkThread() {
            start();
        }

        public void run() {
            try {
                LinkService.setDevice(device.getName(), LinkService.BLUETOOTH);
                Looper.prepare();
                mLooper = Looper.myLooper();
                mHandler = new Handler(mLooper, new Handler.Callback() {
                    public boolean handleMessage(Message msg) {
                        try {
                            LinkService.sendData(new DataOutputStream(socket.getOutputStream()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                });
                Looper.loop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (socket != null) {
                try {
                    socket.close();
                    LinkService.setDevice(null, LinkService.NO_DEVICE);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }

        public void sendData() {
            if (mLooper == null || mHandler == null) {
                return;
            }
            Message msg = Message.obtain();
            mHandler.sendMessage(msg);
        }
    }

    static void sendData() {
        linkThread.sendData();
    }

    public static BluetoothAdapter getAdapter() {
        return adapter;
    }

}
