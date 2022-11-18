package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group_layout);


        EditText username_response = (EditText) findViewById(R.id.username_response);
        EditText groupname_response = (EditText) findViewById(R.id.groupname_response);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String username = (String) b.get("username_value");
            username_response.setText(username);
        }

        String username = username_response.getText().toString();

        String groupname = groupname_response.getText().toString();

        Button create_button = (Button) findViewById(R.id.create_button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroupActivity.this, GroupActivity.class);
                intent.putExtra("groupname_value", groupname);
                intent.putExtra("username_value", username);
                startActivity(intent);
                finish();
            }
        });

        Button join_button = (Button) findViewById(R.id.join_option);
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroupActivity.this, JoinGroupActivity.class);
                intent.putExtra("groupname_value", groupname);
                intent.putExtra("username_value", username);
                startActivity(intent);
                finish();
            }
        });
    }
}