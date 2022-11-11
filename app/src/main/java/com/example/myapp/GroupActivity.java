package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashSet;

enum TagStatus {
  NONE("#FFF6F6F6"),
  IN_PROGRESS("#FF3700B3"),
  URGENT("#FFFFCA18"),
  DONE("#FF00FF00"),
  ARCHIVED("#FF585858");

  private final String color;

  TagStatus(String action) {
    this.color = action;
  }

  public String getColor() {
    return this.color;
  }
}

public class GroupActivity extends AppCompatActivity {
  private final String groupName = "Sid's Fav Friends"; // TODO: need dynamic (maybe intent)

  private TextView groupNameTextView;
  private Button addQuestionBtn;

  private RelativeLayout editQuestionContainer;
  private LinearLayout questionsContainer;
  private PopupWindow addQuestionPopupWindow;  // add question pop up window
  private PopupWindow setStatusPopupWindow;

  private HashSet<LinearLayout> selectedTags;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.group_page);

    // group name
    groupNameTextView = findViewById(R.id.groupName);
    groupNameTextView.setText(groupName);

    // container to hold delete and edit button
    initEditQuestionBar();

    // container to hold all question tags
    questionsContainer = findViewById(R.id.questionsContainer);

    // add button
    initAddQuestionPopupView();

    selectedTags = new HashSet<>();
  }

  private void addNewQuestionTag(String text, TagStatus status) {
    // inflate the layout of the popup window
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    LinearLayout questionTag = (LinearLayout) inflater.inflate(R.layout.question_tags, questionsContainer, false);

    // single click
    setQuestionTagReadMode(questionTag);

    // long press
    questionTag.setOnLongClickListener(view -> {
      // set status select options
      ListView statusOptionList = new ListView(this);
      ArrayAdapter<TagStatus> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TagStatus.values());
      statusOptionList.setAdapter(adapter);
      int width = LinearLayout.LayoutParams.MATCH_PARENT;
      int height = LinearLayout.LayoutParams.WRAP_CONTENT;
      setStatusPopupWindow = new PopupWindow(statusOptionList, width, height, false);  // prevent tap outside to dismiss
      setStatusPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
      statusOptionList.setOnItemClickListener((parent, v, position, id) -> {
        TagStatus selectedStatus = (TagStatus) statusOptionList.getAdapter().getItem(position);
        for (LinearLayout tag : selectedTags) {
          tag.getChildAt(1).setBackgroundColor(Color.parseColor(selectedStatus.getColor()));
        }
        exitEditMode();
      });

      // select current question, unselect all other questions
      for (int i = 0; i < questionsContainer.getChildCount(); i++) {
        LinearLayout currentTag = (LinearLayout) questionsContainer.getChildAt(i);
        setQuestionTagEditMode(currentTag);
        CheckBox box = (CheckBox) currentTag.getChildAt(0);
        box.setVisibility(View.VISIBLE);
        box.setChecked(false);
      }
      ((CheckBox) questionTag.getChildAt(0)).setChecked(true);
      selectedTags.clear();
      selectedTags.add(questionTag);

      // show edit buttons and status options
      editQuestionContainer.setVisibility(View.VISIBLE);
      groupNameTextView.setVisibility(View.GONE);
      addQuestionBtn.setVisibility(View.GONE);
      return true;
    });

    // status color
    questionTag.getChildAt(1).setBackgroundColor(Color.parseColor(status.getColor()));
    // question content
    ((TextView) questionTag.getChildAt(2)).setText(text);

    // add this new tag to the question container
    questionsContainer.addView(questionTag);
  }

  /**
   * Edit Mode: click to toggle tag selection
   * @param currentTag LinearLayout of the current question tag
   */
  private void setQuestionTagEditMode(LinearLayout currentTag) {
    currentTag.setOnClickListener(v -> {
      CheckBox checkbox = (CheckBox) currentTag.getChildAt(0);
      checkbox.toggle();
      if (checkbox.isChecked()) {
        selectedTags.add(currentTag);
      } else {
        selectedTags.remove(currentTag);
      }
    });
  }

  /**
   * Read Mode: click question tags to go to the questions
   * @param currentTag LinearLayout of the current question tag
   */
  private void setQuestionTagReadMode(LinearLayout currentTag) {
    currentTag.setOnClickListener(v -> {
      Intent intent = new Intent(this, QuestionPage.class);
      startActivity(intent);
    });
  }

  private void initEditQuestionBar() {
    editQuestionContainer = findViewById(R.id.editQuestionContainer);

    // delete button handler
    ((Button) editQuestionContainer.getChildAt(0)).setOnClickListener(v -> {
      for (LinearLayout tag : selectedTags) {
        questionsContainer.removeView(tag);
      }
      exitEditMode();
    });

    // cancel button handler
    ((Button) editQuestionContainer.getChildAt(1)).setOnClickListener(v -> exitEditMode());
  }

  private void exitEditMode() {
    for (int i = 0; i < questionsContainer.getChildCount(); i++) {
      LinearLayout currentTag = (LinearLayout) questionsContainer.getChildAt(i);
      CheckBox box = (CheckBox) currentTag.getChildAt(0);
      box.setVisibility(View.GONE);
      box.setChecked(false);
      setQuestionTagReadMode(currentTag);
    }
    selectedTags.clear();
    editQuestionContainer.setVisibility(View.GONE);
    groupNameTextView.setVisibility(View.VISIBLE);
    addQuestionBtn.setVisibility(View.VISIBLE);
    setStatusPopupWindow.dismiss();
  }

  private void initAddQuestionPopupView() {
    // inflate the layout of the popup window
    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
    LinearLayout addQuestionPopupView = (LinearLayout) inflater.inflate(R.layout.add_question_popup, null);
    // status
    ArrayAdapter<TagStatus> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, TagStatus.values());
    ((Spinner) addQuestionPopupView.getChildAt(1)).setAdapter(adapter);
    // popup add button
    ((Button) addQuestionPopupView.getChildAt(2)).setOnClickListener(v -> {
      String questionText = ((EditText) addQuestionPopupView.getChildAt(0)).getText().toString();
      TagStatus selectedStatus = (TagStatus) ((Spinner) addQuestionPopupView.getChildAt(1)).getSelectedItem();
      addNewQuestionTag(questionText, selectedStatus);
      addQuestionPopupWindow.dismiss();
    });

    // + icon add button
    addQuestionBtn = findViewById(R.id.addQuestionBtn);
    addQuestionBtn.setOnClickListener(v -> {
      // create the popup window
      int width = LinearLayout.LayoutParams.MATCH_PARENT;
      int height = LinearLayout.LayoutParams.WRAP_CONTENT;
      addQuestionPopupWindow = new PopupWindow(addQuestionPopupView, width, height, true);  // tap outside to dismiss
      // show the popup window
      // which view you pass in doesn't matter, it is only used for the window token
      addQuestionPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    });
  }
}