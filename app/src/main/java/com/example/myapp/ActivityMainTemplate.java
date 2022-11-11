package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMainTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_template);
        Intent intent = getIntent();
        int group_number = intent.getIntExtra(AddGroup.GROUP_COUNT, 1);
        switch(group_number) {
            case 1:
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.viewGroup1);
                layout.setVisibility(View.VISIBLE);
                break;
            case 2:
                layout = (RelativeLayout) findViewById(R.id.viewGroup2);
                layout.setVisibility(View.VISIBLE);
                break;
            case 3:
                layout = (RelativeLayout) findViewById(R.id.viewGroup3);
                layout.setVisibility(View.VISIBLE);
                break;
        }
    }
}
