package com.example.myapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GroupInfo implements Parcelable {
    private String name;
    private String owner;
    private ArrayList<QuestionInfo> questions;

    public GroupInfo(String name, String owner, ArrayList<QuestionInfo> questions) {
        this.name = name;
        this.owner = owner;
        this.questions = questions;
    }

    private GroupInfo(Parcel in) {
        name = in.readString();
        owner = in.readString();
        questions = in.readArrayList(QuestionInfo.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Group Name: " + name + "; Owned by: " + owner + " with " + questions.size() + " questions";
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(owner);
        out.writeList(questions);
    }

    public static final Creator<GroupInfo> CREATOR = new Creator<GroupInfo>() {
        public GroupInfo createFromParcel(Parcel in) {
            return new GroupInfo(in);
        }

        public GroupInfo[] newArray(int size) {
            return new GroupInfo[size];
        }
    };
}
