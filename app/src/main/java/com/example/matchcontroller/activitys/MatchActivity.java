package com.example.matchcontroller.activitys;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.TextView;

import com.example.matchcontroller.R;
import com.example.matchcontroller.data.MatchData;
import com.example.matchcontroller.services.DataService;
import com.example.matchcontroller.services.LinkService;


public class MatchActivity extends ActionBarActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView tx1;
    private TextView tx2;
    private TextView tx3;
    private TextView tx4;
    private TextView tx5;
    private TextView tx6;
    private TextView tx7;
    private TextView tx8;
    private TextView tx9;
    private TextView tx10;
    private TextView tx11;
    private TextView tx12;
    private TextView tx13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        tx1 = (TextView) findViewById(R.id.tx1);
        tx2 = (TextView) findViewById(R.id.tx2);
        tx3 = (TextView) findViewById(R.id.tx3);
        tx4 = (TextView) findViewById(R.id.tx4);
        tx6 = (TextView) findViewById(R.id.tx6);
        tx7 = (TextView) findViewById(R.id.tx7);
        tx9 = (TextView) findViewById(R.id.tx9);
        tx10 = (TextView) findViewById(R.id.tx10);
        tx12 = (TextView) findViewById(R.id.tx12);
        tx13 = (TextView) findViewById(R.id.tx13);

        addoneListener aol = new addoneListener();
        suboneListener sol = new suboneListener();
        addtwoListener atl = new addtwoListener();
        subtwoListener stl = new subtwoListener();
        button1.setOnClickListener(aol);
        button2.setOnClickListener(sol);
        button3.setOnClickListener(atl);
        button4.setOnClickListener(stl);
        setTexts();
    }

    void setTexts() {
        MatchData data = DataService.getMatch();
        tx1.setText(data.getName1());
        tx2.setText(data.getName2());
        tx3.setText(data.getName1());
        tx4.setText(data.getName2());
        tx6.setText("" + data.getScore1(0));
        tx7.setText("" + data.getScore2(0));
        int set = data.getSet();
        if (set > 0) {
            tx9.setText("" + data.getScore1(1));
            tx10.setText("" + data.getScore2(1));
        } else {
            tx9.setText("--");
            tx10.setText("--");
        }
        if (set > 0) {
            tx12.setText("" + data.getScore1(2));
            tx13.setText("" + data.getScore2(2));
        } else {
            tx12.setText("--");
            tx13.setText("--");
        }
        if (LinkService.getDeviceName() != null) {
            LinkService.sendData();
        }
    }
    class addoneListener implements OnClickListener
    {
        public void onClick(View arg0)
        {
            DataService.incScore1();
            setTexts();
        }
    }
    class suboneListener implements OnClickListener
    {
        public void onClick(View arg0)
        {
            DataService.decScore1();
            setTexts();
        }
    }
    class addtwoListener implements OnClickListener
    {
        public void onClick(View arg0)
        {
            DataService.incScore2();
            setTexts();
        }
    }
    class subtwoListener implements OnClickListener
    {
        public void onClick(View arg0)
        {
            DataService.decScore2();
            setTexts();
        }
    }

}
