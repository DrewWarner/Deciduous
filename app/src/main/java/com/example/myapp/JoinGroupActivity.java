package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import edu.illinois.CS465.Decidiuous.R;

public class JoinGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_layout);

        EditText username_response = (EditText)findViewById(R.id.username_response);
        String username = username_response.getText().toString();

        EditText jc1 = (EditText)findViewById(R.id.join_code1);
        EditText jc2 = (EditText)findViewById(R.id.join_code2);
        EditText jc3 = (EditText)findViewById(R.id.join_code3);
        EditText jc4 = (EditText)findViewById(R.id.join_code4);
        EditText jc5 = (EditText)findViewById(R.id.join_code5);
        EditText jc6 = (EditText)findViewById(R.id.join_code6);

        String c1 = jc1.getText().toString();
        String c2 = jc2.getText().toString();
        String c3 = jc3.getText().toString();
        String c4 = jc4.getText().toString();
        String c5 = jc5.getText().toString();
        String c6 = jc6.getText().toString();

        String join_code = c1 + c2 + c3 + c4 + c5 + c6;

        Button join_button = (Button) findViewById(R.id.join_button) ;
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinGroupActivity.this, GroupActivity.class);
                intent.putExtra("join_code_value", join_code);
                intent.putExtra("username_value", username);
                startActivity(intent);
                finish();
            }
        });

    }

}
