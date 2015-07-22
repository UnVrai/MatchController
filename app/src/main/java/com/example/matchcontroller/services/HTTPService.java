package com.example.matchcontroller.services;

import android.os.Handler;

import com.example.matchcontroller.data.MatchData;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by BAKA on 2015/7/21.
 */
public class HTTPService {
    private static String urlString = "http://dwh123.wicp.net:8080/database/index.jsp?id=";
    private static MatchData[] matchDatas;
    private static Handler handler;

    public static MatchData[] getData() {
        return matchDatas;
    }

    public static void getHttpData(Handler handler) {
        HTTPService.handler = handler;
        new getThread().start();
    }

    public static void upData() {
        new upDataThread();
    }

    static class upDataThread extends Thread {
        public void run() {
            try {
                URL url = new URL(urlString + 1);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setDoOutput(true);//设置允许输出
                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "Fiddler");
                conn.setRequestProperty("Content-Type", "application/json");
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                String  data = DataService.getMatchData();
                os.writeBytes(data);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class getThread extends Thread {
        public void run() {
            try {
                URL url = new URL(urlString + 0);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int code = conn.getResponseCode();
                if(code == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String retData = null;
                    String responseData = "";
                    while ((retData = in.readLine()) != null) {
                        responseData += retData;
                    }
                    JSONArray matchArray = new JSONArray(responseData);
                    int l = matchArray.length();
                    matchDatas = new MatchData[l];
                    for (int i = 0;i < l; i++) {
                        matchDatas[i] = DataService.jsonToData(matchArray.getJSONObject(i));
                    }
                }
                handler.sendEmptyMessage(1);
            } catch (Exception e) {
                handler.sendEmptyMessage(0);
                e.printStackTrace();
            }
        }

    }


}
