package com.example.matchcontroller.activitys;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.widget.Button;

import com.example.matchcontroller.R;



public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button continueButton = (Button)findViewById(R.id.continued);
        Button newMatchButton = (Button)findViewById(R.id.newmatch);
        Button recordButton = (Button)findViewById(R.id.record);
        Button linkButton = (Button)findViewById(R.id.link);
        Button exitButton = (Button)findViewById(R.id.exit);

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
                System.exit(0);
            }
        });
    }

}
