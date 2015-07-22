package com.example.matchcontroller.activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.matchcontroller.R;
import com.example.matchcontroller.data.MatchData;
import com.example.matchcontroller.services.HTTPService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends ActionBarActivity {
    ProgressDialog m_pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        m_pDialog = new ProgressDialog(this);
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_pDialog.setMessage("正在获取数据。。。");
        m_pDialog.setIndeterminate(false);
        m_pDialog.setCancelable(false);
        m_pDialog.show();
        Handler handler = new Handler() {

            public void handleMessage(Message msg)
            {
                m_pDialog.dismiss();
                switch (msg.what) {
                    case 0:new  AlertDialog.Builder(RecordActivity.this)
                                .setTitle("连接")
                                .setMessage("无法连接服务器。。。" )
                                .setPositiveButton("确定" ,  null )
                                .show();
                        break;
                    case 1:showData();
                }
            }
        };
        HTTPService.getHttpData(handler);
    }

    public void showData() {
        MatchData[] matchDatas = HTTPService.getData();
        ListView mListView = (ListView)findViewById(R.id.recordList);
        ArrayList<Map<String,String>> mData= new ArrayList<>();;

        int lengh = matchDatas.length;
        for(int i =0; i < lengh; i++) {
            Map<String,String> item = new HashMap<>();
            item.put("title", matchDatas[i].getName1() + " VS " + matchDatas[i].getName2());
            item.put("date", matchDatas[i].getDate());
            mData.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2,
                new String[]{"title","date"},new int[]{android.R.id.text1,android.R.id.text2});
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });

    }
}
