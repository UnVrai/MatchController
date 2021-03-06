package com.example.matchcontroller.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.widget.Button;
import android.widget.TextView;

import com.example.matchcontroller.R;
import com.example.matchcontroller.services.DataService;
import com.example.matchcontroller.services.SQLiteService;


public class MainActivity extends ActionBarActivity {
    boolean hasData = false;
    TextView deviceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button continueButton = (Button)findViewById(R.id.continued);
        Button newMatchButton = (Button)findViewById(R.id.newmatch);
        Button recordButton = (Button)findViewById(R.id.record);
        Button linkButton = (Button)findViewById(R.id.link);
        Button exitButton = (Button)findViewById(R.id.exit);

        hasData = SQLiteService.hasData(this);

        if (hasData) {
            continueButton.setVisibility(View.VISIBLE);
        }

        continueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MatchActivity.class);
                startActivity(intent);
            }
        });

        newMatchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasData) {
                    SQLiteService.deleteMatchData(MainActivity.this);
                }
                DataService.startNewMatch("张三", "李四", MainActivity.this);
                Intent intent = new Intent(MainActivity.this, MatchActivity.class);
                startActivity(intent);
            }
        });

        recordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        linkButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LinkActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onDestroy() {
        if (hasData){
            SQLiteService.deleteMatchData(MainActivity.this);
        }
        super.onDestroy();
        System.exit(0);
    }

}
