package com.example.matchcontroller.services;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BAKA on 2015/7/20.
 */
public class WiFiService {
    private static LinkThread linkThread;
    private static Map<String, String> deviceMap = new HashMap<>();
    private static String device;
    private  static SearchThread searchThread;
    //获取设备
    public static String[] getDevicesName() {
        List<String> devicesName = new ArrayList<>();
        for(Map.Entry<String, String> entry:deviceMap.entrySet()){
            devicesName.add(entry.getKey());
        }
        return devicesName.toArray(new String[0]);
    }
    //设置WiFi设备
    public static void setWiFiDevice(String device) {
        WiFiService.device = device;
    }
    //在局域网中查找设备
    public static void startSearch(String hostIp) {
        sendBroacast(hostIp);
        searchThread = new SearchThread();
    }
    public static void finishSearch() {
            searchThread.interrupt();
    }

    //发送广播
    public static void sendBroacast(final String hostIp) {
        new Thread() {
            public void run() {
                try {
                    DatagramSocket ds = new DatagramSocket(9998);
                    byte[] data = hostIp.getBytes();
                    InetAddress address = InetAddress.getByName("255.255.255.255");
                    DatagramPacket dataPacket = new DatagramPacket(data, data.length, address, 9998);
                    ds.send(dataPacket);
                    ds.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //搜寻设备线程
    private static class SearchThread extends Thread {

        SearchThread() {
            start();
        }
        public void run() {
            DatagramSocket ds = null;
            try {
                ds = new DatagramSocket(5544);
                while (true) {
                    byte[] buf = new byte[1024];
                    DatagramPacket dp = new DatagramPacket(buf,0,buf.length);
                    ds.receive(dp);
                    JSONObject jo = new JSONObject(new String(buf,0,dp.getLength()));
                    deviceMap.put(jo.getString("name"),jo.getString("ip"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //启动WiFi连接
    public static void link(Handler waitingHandler) throws Exception {
        linkThread = new LinkThread(waitingHandler);
    }
    //WiFi连接线程
    private static class LinkThread extends Thread {

        private Handler mHandler;
        private Looper mLooper;
        private Socket socket = null;
        private Handler waitingHandler;

        LinkThread(Handler waitingHandler) {
            this.waitingHandler = waitingHandler;
            start();
        }

        public void run() {
            try {
                socket = new Socket(deviceMap.get(device), 30000);
            } catch (Exception e) {
                waitingHandler.sendEmptyMessage(0);
                e.printStackTrace();
            }
            if (socket.isConnected()) {
                LinkService.setDevice(device, LinkService.WIFI);
                waitingHandler.sendEmptyMessage(1);
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
                try {
                    socket.close();
                    LinkService.setDevice(null, LinkService.NO_DEVICE);
                } catch (Exception e) {
                    e.printStackTrace();
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
}
