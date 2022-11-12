package com.example.myapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class QuestionActivity extends AppCompatActivity {
  private final String questionTitle = "Test title"; // TODO: get from prev intent
  private final int questionId = 1;   // TODO: get from prev intent

  private LinearLayout proposalsContainer;
  private LinearLayout confirmCancelContainer;
  private Button addProposalBtn;
  private Button confirmProposalBtn;
  private Button cancelProposalBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    // == FOR TESTING ONLY, DELETE THIS LINE LATER ==
    QuestionActivityDataStore.getInstance().initNewQuestionActivity(questionId);
    // ==============================================

    ((TextView) findViewById(R.id.questionTitle)).setText(questionTitle);
    proposalsContainer = findViewById(R.id.proposalsContainer);
    confirmCancelContainer = findViewById(R.id.confirmCancelContainer);
    addProposalBtn = findViewById(R.id.addProposalBtn);
    confirmProposalBtn = findViewById(R.id.confirmProposalBtn);
    cancelProposalBtn = findViewById(R.id.cancelProposalBtn);

    ArrayList<ProposalTagInfo> proposalsList = QuestionActivityDataStore.getInstance().getProposalsList(questionId);
    for (ProposalTagInfo info : proposalsList) {
      LinearLayout newTag = createProposalTag(info, false);
      proposalsContainer.addView(newTag);
    }

    // add new proposal button
    addProposalBtn.setOnClickListener(view -> {
      HashMap<String, Pair<Integer, Boolean>> testEntries = new HashMap<>();
      testEntries.put("\uD83D\uDE00", new Pair<>(1, false));
      testEntries.put("\uD83E\uDD29", new Pair<>(2, false));

      addProposalBtn.setVisibility(View.GONE);
      confirmCancelContainer.setVisibility(View.VISIBLE);

      //  FIXME: use pre defined emoji set in prev intent
      ProposalTagInfo info = new ProposalTagInfo(ViewCompat.generateViewId(), "", testEntries);
      LinearLayout proposalTag = createProposalTag(info, true);
      proposalsContainer.addView(proposalTag);
    });
  }

  /**
   * create a proposal tag and update QuestionActivityDataStore
   * @param info ProposalTag info to init tag
   * @param isEditMode true if tag is edit mode, else false
   * @return LinearLayout of the proposal tag
   */
  private LinearLayout createProposalTag(ProposalTagInfo info, Boolean isEditMode) {
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    LinearLayout proposalTag = (LinearLayout) inflater.inflate(R.layout.proposal_tag, proposalsContainer, false);
    LinearLayout emojiEntryContainer = (LinearLayout) proposalTag.getChildAt(2);
    proposalTag.setId(info.getId());
    ((TextView) proposalTag.getChildAt(1)).setText(info.getTitle());

    for (Entry<String, Pair<Integer, Boolean>> entry : info.getEmojiEntries().entrySet()) {
      String emojiCode = entry.getKey();
      String countText = entry.getValue().first.toString();
      Boolean isClicked = entry.getValue().second;

      LinearLayout emojiEntry = (LinearLayout) inflater.inflate(R.layout.emoji_entry, proposalTag, false);
      TextView emojiView = (TextView) emojiEntry.getChildAt(0);
      TextView countView = (TextView) emojiEntry.getChildAt(1);
      emojiView.setText(emojiCode);
      countView.setText(countText);

      if (isClicked) {
        emojiEntry.setBackgroundColor(Color.YELLOW);
        emojiEntry.setOnClickListener(v -> unClickEmojiEntry(emojiEntry, info));
      } else {
        emojiEntry.setBackgroundColor(Color.TRANSPARENT);
        emojiEntry.setOnClickListener(v -> clickEmojiEntry(emojiEntry, info));
      }
      emojiEntryContainer.addView(emojiEntry);
    }

    EditText titleInput = (EditText) proposalTag.getChildAt(0);
    TextView proposalTitle = (TextView) proposalTag.getChildAt(1);

    if (isEditMode) {
      confirmProposalBtn.setOnClickListener(v -> {
        // update screen component
        proposalTitle.setText(titleInput.getText().toString());
        titleInput.setVisibility(View.GONE);
        proposalTitle.setVisibility(View.VISIBLE);
        // update QuestionActivityDataStore
        info.setTitle(titleInput.getText().toString());
        QuestionActivityDataStore.getInstance().putProposal(questionId, info);
        // update screen buttons
        addProposalBtn.setVisibility(View.VISIBLE);
        confirmCancelContainer.setVisibility(View.GONE);
      });

      cancelProposalBtn.setOnClickListener(v -> {
        addProposalBtn.setVisibility(View.VISIBLE);
        confirmCancelContainer.setVisibility(View.GONE);
        proposalsContainer.removeView(proposalTag);
      });
    } else {
      titleInput.setVisibility(View.GONE);
      proposalTitle.setVisibility(View.VISIBLE);
    }

    return proposalTag;
  }

  @SuppressLint("SetTextI18n")
  private void clickEmojiEntry(LinearLayout emojiEntry, ProposalTagInfo info) {
    String emojiCode = ((TextView) emojiEntry.getChildAt(0)).getText().toString();
    TextView countView = (TextView) emojiEntry.getChildAt(1);
    emojiEntry.setBackgroundColor(Color.YELLOW);
    // upVoteEmoji, and change emojiEntry LinearLayout content
    countView.setText(info.upVoteEmoji(emojiCode).toString());
    // change on click method
    emojiEntry.setOnClickListener(view -> unClickEmojiEntry(emojiEntry, info));
  }

  @SuppressLint("SetTextI18n")
  private void unClickEmojiEntry(LinearLayout emojiEntry, ProposalTagInfo info) {
    String emojiCode = ((TextView) emojiEntry.getChildAt(0)).getText().toString();
    TextView countView = (TextView) emojiEntry.getChildAt(1);
    emojiEntry.setBackgroundColor(Color.TRANSPARENT);
    // downVoteEmoji, and change emojiEntry LinearLayout content
    countView.setText(info.downVoteEmoji(emojiCode).toString());
    // change on click method
    emojiEntry.setOnClickListener(view -> clickEmojiEntry(emojiEntry, info));
  }
}
