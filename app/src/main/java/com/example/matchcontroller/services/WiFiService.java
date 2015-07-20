package com.example.matchcontroller.services;

/**
 * Created by BAKA on 2015/7/20.
 */
public class WiFiService {
    private static LinkThread linkThread;

    //启动连接设备的线程
    public static void link() throws Exception {
        linkThread = new LinkThread();
    }

    private static class LinkThread extends Thread {

    }
}
