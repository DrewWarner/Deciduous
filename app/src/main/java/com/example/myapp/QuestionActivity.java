package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {
  private LinearLayout proposalsContainer;
  private TextView questionTitle;
  private LinearLayout confirmCancelContainer;
  private Button addProposalBtn;
  private Button confirmProposalBtn;
  private Button cancelProposalBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    questionTitle = findViewById(R.id.questionTitle); // TODO: set title based on data
    confirmCancelContainer = findViewById(R.id.confirmCancelContainer);
    proposalsContainer = findViewById(R.id.proposalsContainer);
    confirmProposalBtn = findViewById(R.id.confirmProposalBtn);
    cancelProposalBtn = findViewById(R.id.cancelProposalBtn);

    // add new proposal button
    addProposalBtn = findViewById(R.id.addProposalBtn);
    addProposalBtn.setOnClickListener(view -> {
      LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
      LinearLayout questionTag = (LinearLayout) inflater.inflate(R.layout.proposal_tag, proposalsContainer, false);

      // TODO: add emoji set based on first input
      LinearLayout emojiEntry = (LinearLayout) inflater.inflate(R.layout.emoji_entry, questionTag, false);
      questionTag.addView(emojiEntry);

      addProposalBtn.setVisibility(View.GONE);
      confirmCancelContainer.setVisibility(View.VISIBLE);

      confirmProposalBtn.setOnClickListener(v -> {
        EditText titleInput = (EditText) questionTag.getChildAt(0);
        TextView proposalTitle = (TextView) questionTag.getChildAt(1);
        proposalTitle.setText(titleInput.getText().toString());
        titleInput.setVisibility(View.GONE);
        proposalTitle.setVisibility(View.VISIBLE);
        addProposalBtn.setVisibility(View.VISIBLE);
        confirmCancelContainer.setVisibility(View.GONE);
      });

      cancelProposalBtn.setOnClickListener(v -> {
        addProposalBtn.setVisibility(View.VISIBLE);
        confirmCancelContainer.setVisibility(View.GONE);
        proposalsContainer.removeView(questionTag);
      });

      proposalsContainer.addView(questionTag);
    });

    // TODO: use SharedPreference and JSON to store data
    // https://stackoverflow.com/questions/3624280/how-to-use-sharedpreferences-in-android-to-store-fetch-and-edit-values
  }
}
