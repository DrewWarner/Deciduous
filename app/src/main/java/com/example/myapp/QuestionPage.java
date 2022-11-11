package com.example.myapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;

public class QuestionPage extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.question_layout);

    // TODO: use SharedPreference and JSON to store data
    // https://stackoverflow.com/questions/3624280/how-to-use-sharedpreferences-in-android-to-store-fetch-and-edit-values
  }
}
