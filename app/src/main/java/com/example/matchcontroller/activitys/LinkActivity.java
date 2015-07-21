package com.example.matchcontroller.activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.matchcontroller.R;
import com.example.matchcontroller.services.BluetoothService;
import com.example.matchcontroller.services.WiFiService;


public class LinkActivity extends ActionBarActivity {
    ProgressDialog m_pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        Button blueToothButton = (Button)findViewById(R.id.bluetooth);
        Button wifiButton = (Button)findViewById(R.id.wifi);

        blueToothButton.setOnClickListener(new BlueToothButtonListener());
        wifiButton.setOnClickListener(new WiFiButtonListener());

        prepareDialog();
    }

    void prepareDialog() {
        m_pDialog = new ProgressDialog(this);
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_pDialog.setMessage("等待连接。。。");
        m_pDialog.setIndeterminate(false);
        m_pDialog.setCancelable(false);
    }


    class BlueToothButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (!BluetoothService.getAdapter().isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(intent);
            } else {
                final String[] items = BluetoothService.getDevicesName();
                AlertDialog.Builder builder = new AlertDialog.Builder(LinkActivity.this);
                builder.setTitle("配对蓝牙");
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (items != null) {
                            BluetoothService.setBluetoothDevice(items[arg1]);
                        }
                        try {
                            BluetoothService.link(new waitingHandler());
                            m_pDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.create().show();
            }
        }
    }

    void showMessage(String msg) {
        new  AlertDialog.Builder(this)
                .setTitle("连接" )
                .setMessage(msg )
                .setPositiveButton("确定" ,  null )
                .show();
    }

    class waitingHandler extends Handler {
        public void handleMessage(Message msg)
        {
            m_pDialog.dismiss();
            switch (msg.what) {
                case 0:showMessage("连接失败");
                    break;
                case 1:showMessage("连接成功");
                    break;
            }
        }
    }

    class WiFiButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = intToIp(ipAddress);
            WiFiService.startSearch(ip);
            final Handler handler = new Handler() {
                public void handleMessage(Message msg)
                {
                    m_pDialog.dismiss();
                    WiFiService.finishSearch();
                }
            };
            new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                        handler.sendEmptyMessage(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
    }

    public void showWiFiResult() {
        final String[] items = WiFiService.getDevicesName();
        AlertDialog.Builder builder = new AlertDialog.Builder(LinkActivity.this);
        builder.setTitle("选择设备");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (items != null) {
                    WiFiService.setWiFiDevice(items[arg1]);
                }
                try {
                    WiFiService.link(new waitingHandler());
                    m_pDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.create().show();
    }

    private String intToIp(int i) {
        return (i & 0xFF ) + "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) + "." + ( i >> 24 & 0xFF) ;
    }

}