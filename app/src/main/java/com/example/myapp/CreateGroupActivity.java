package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.security.acl.Group;

public class CreateGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_layout);

        EditText groupname_response = findViewById(R.id.groupname_response);

        findViewById(R.id.create_button).setOnClickListener(v -> {
            String groupName = groupname_response.getText().toString();

            MainActivityDataStore.getInstance().initNewGroupActivity(groupName);

            Intent intent = new Intent(this, GroupActivity.class);
            intent.putExtra("groupname_value", groupName);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.join_option).setOnClickListener(v -> {
            String groupName = groupname_response.getText().toString();

            MainActivityDataStore.getInstance().initNewGroupActivity(groupName);

            Intent intent = new Intent(this, JoinGroupActivity.class);
            intent.putExtra("groupname_value", groupName);
            startActivity(intent);
            finish();
        });
    }
}