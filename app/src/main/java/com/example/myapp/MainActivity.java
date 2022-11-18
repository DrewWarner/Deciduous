package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<GroupInfo> groups;

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load saved data
        if(savedInstanceState != null) {
            groups = savedInstanceState.getParcelableArrayList("groups");
        }

        addButton = (Button) findViewById(R.id.button_add_group);

        addButton.setOnClickListener(this::onClick);
    }

    protected void onSaveInstanceState(Bundle savedInstance) {
        super.onSaveInstanceState(savedInstance);
        savedInstance.putParcelableArrayList("groups", groups);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button_add_group) {
            Intent intent = new Intent(this, JoinGroupActivity.class);
            startActivity(intent);
        }
    }
}