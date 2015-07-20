package com.example.matchcontroller.services;

import java.io.DataOutputStream;

/**
 * Created by UnVrai on 2015/7/19.
 */
public class LinkService {
    public final static byte NO_DEVICE = 0;
    public final static byte BLUETOOTH = 1;
    public final static byte WIFI = 2;
    private static byte deviceType = NO_DEVICE;
    private static String deviceName = null;

    //���õ�ǰ�����豸
    public static void setDevice(String deviceName, byte deviceType) {
        LinkService.deviceName = deviceName;
        LinkService.deviceType = deviceType;
    }

    //ʹ���豸�ж�
    public static boolean sendData() {
        if (deviceType == BLUETOOTH) {
            BluetoothService.sendData();
            return true;
        }
        if (deviceType == WIFI) {

        }
        return false;
    }

    public static void sendData(DataOutputStream output) throws Exception{
        String data = DataService.getScoreData();
        output.writeUTF(data);
        output.close();
    }

    public static String getDeviceName() {
        return deviceName;
    }

}
