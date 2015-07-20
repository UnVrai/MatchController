package com.example.matchcontroller.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.matchcontroller.R;
import com.example.matchcontroller.services.BluetoothService;


public class LinkActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);

        Button blueToothButton = (Button)findViewById(R.id.bluetooth);
        Button wifiButton = (Button)findViewById(R.id.wifi);

        blueToothButton.setOnClickListener(new BlueToothButtonListener());
        wifiButton.setOnClickListener(new WiFiButtonListener());

    }

    void waitForLink() throws Exception {
        BluetoothService.link();

        ProgressDialog m_pDialog;
        m_pDialog = new ProgressDialog(this);
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_pDialog.setMessage(getString(R.string.wait_bluetooth_msg));
        m_pDialog.setIndeterminate(false);
        m_pDialog.setCancelable(false);

        m_pDialog.show();

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
                builder.setTitle(getString(R.string.choose_bluetooth_msg));
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        BluetoothService.setBluetoothDevice(items[arg1]);
                        try {
                            waitForLink();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.create().show();
            }
        }
    }

    class WiFiButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mWifi.isConnected()) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS );
                startActivity(intent);
            }
        }
    }

}