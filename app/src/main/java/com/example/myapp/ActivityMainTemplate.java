package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityMainTemplate extends AppCompatActivity {
    private LinearLayout groupsContainer;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_template);
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupid_value", -1);
        groupsContainer = findViewById(R.id.groupsContainer);


        ArrayList<GroupInfo> groupsList = ActivityMainTemplateDataStore.getInstance().getGroupsList(groupId);
        for (GroupInfo info : groupsList) {
            LinearLayout newTag = createGroupTag(info);
            groupsContainer.addView(newTag);
        }

        Button addGroup_button = findViewById(R.id.button_add);
        addGroup_button.setOnClickListener(view -> {
            ArrayList<QuestionInfo> q = new ArrayList<>();
            GroupInfo info = new GroupInfo( "Sid's New Group", "Sid", q, 1);
            LinearLayout new_group = createGroupTag(info);
            groupsContainer.addView(new_group);
            Intent new_intent = new Intent(ActivityMainTemplate.this, JoinGroupActivity.class);
            startActivity(new_intent);
            finish();
        });
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout groupTag = (LinearLayout) inflater.inflate(R.layout.group_tag, groupsContainer, false);

        for(int i = 0; i < groupId; i++) {
            Button group_button = (Button) groupTag.getChildAt(i);
            String group_name = (String) ((Button) groupTag.getChildAt(i)).getText();
            group_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityMainTemplate.this, GroupActivity.class);
                    intent.putExtra("groupname_value", group_name);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private LinearLayout createGroupTag(GroupInfo info) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout groupTag = (LinearLayout) inflater.inflate(R.layout.group_tag, groupsContainer, false);
        groupTag.setId(info.getId());
        Button groupname_button = (Button) groupTag.getChildAt(0);

        groupname_button.setText(info.getName());
        groupname_button.setVisibility(View.VISIBLE);



        return groupTag;
    }

}
