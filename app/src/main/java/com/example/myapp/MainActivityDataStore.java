package com.example.myapp;

import java.util.HashMap;

/**
 * assuming group name unique, map group name to group datastore
 */
public class MainActivityDataStore {
  private final HashMap<String, GroupDataStore> groupDataMap;  // groupName -> groupDataStore

  /**
   * init new group activity if not exist
   * @param groupName groupName as string
   */
  public void initNewGroupActivity(String groupName) {
    if (!groupDataMap.containsKey(groupName)) {
      groupDataMap.put(groupName, new GroupDataStore(groupName));
    }
  }

  /**
   * @param groupName name of the group to get
   * @return corresponding group data store
   */
  public GroupDataStore getGroupDataStore(String groupName) { return groupDataMap.get(groupName); }

  private MainActivityDataStore() {
    groupDataMap = new HashMap<>();
  }

  public static MainActivityDataStore getInstance() { return holder; }
  private static final MainActivityDataStore holder = new MainActivityDataStore();
}
