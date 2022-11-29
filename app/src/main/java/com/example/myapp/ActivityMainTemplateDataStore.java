package com.example.myapp;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.ArrayList;

public class ActivityMainTemplateDataStore {

    private final HashMap<Integer, ArrayList<GroupInfo>> ActivityMainTemplateInfo;

    public void initNewQuestionActivity(Integer groupId) {
        if (!ActivityMainTemplateInfo.containsKey(groupId)) {
            ActivityMainTemplateInfo.put(groupId, new ArrayList<>());
        }
    }
    public void putGroup(Integer groupId, GroupInfo groupInfo) {
        if (ActivityMainTemplateInfo.containsKey(groupId)) {
            ArrayList<GroupInfo> list = ActivityMainTemplateInfo.get(groupId);
            assert list != null;
            for (GroupInfo info : list) {
                if (info.getId() == groupInfo.getId()) {
                    info.updateGroup(groupInfo);
                    return;
                }
            }
            list.add(groupInfo);
        } else {
            throw new InvalidParameterException("group does not exist");
        }
    }

    public ArrayList<GroupInfo> getGroupsList(Integer groupId) {
        ArrayList<GroupInfo> res = ActivityMainTemplateInfo.get(groupId);
        if (res == null) {
            throw new InvalidParameterException("group does not exist");
        }
        return res;
    }

    public ActivityMainTemplateDataStore() {
        ActivityMainTemplateInfo = new HashMap<>();
    }
    public static ActivityMainTemplateDataStore getInstance() { return holder; }
    private static final ActivityMainTemplateDataStore holder = new ActivityMainTemplateDataStore();

}
