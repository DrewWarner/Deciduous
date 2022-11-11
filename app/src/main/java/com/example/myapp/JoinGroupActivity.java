package com.example.myapp;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import edu.illinois.CS465.Decidiuous.R;

public class JoinGroupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_layout);

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
    }
}
