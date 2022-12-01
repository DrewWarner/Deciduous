package com.example.myapp;

import android.util.Pair;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * store data of ONE group by storing map of question -> proposal list
 * question is identify by the view id of the question (generated using ViewCompat.generateViewId())
 */
public class GroupDataStore {
  // map questionId -> proposalsList
  private final HashMap<Integer, ArrayList<ProposalTagInfo>> questionProposalMap;
  // map questionId -> default emoji set of the question
  private final HashMap<Integer, HashMap<String, Pair<Integer, Boolean>>> questionEmojiSetMap;
  private final String joinCode;
  private final String groupName;

  /**
   * init data store of a new question if not yet exist
   * @param questionId questionId of the question
   * @param emojiSet default emoji set of the question
   */
  public void initNewQuestionActivity(Integer questionId, HashMap<String, Pair<Integer, Boolean>> emojiSet) {
    if (!questionProposalMap.containsKey(questionId)) {
      questionProposalMap.put(questionId, new ArrayList<>());
    }
    if (!questionEmojiSetMap.containsKey(questionId)) {
      questionEmojiSetMap.put(questionId, new HashMap<>(emojiSet));
    }
  }

  public HashMap<String, Pair<Integer, Boolean>> getDefaultEmojiSet(Integer questionId) {
    HashMap<String, Pair<Integer, Boolean>> res = questionEmojiSetMap.get(questionId);
    if (res == null) {
      throw new InvalidParameterException("question does not exist");
    }
    return res;
  }

  /**
   * delete question from data store
   * @param questionId id of the question to delete
   */
  public void deleteQuestionActivity(Integer questionId) {
    questionProposalMap.remove(questionId);
  }

  /**
   * put a new proposal to existing question, update existing one if questionId already exist
   * @param questionId id of the question to add to
   * @param proposalInfo proposal info
   */
  public void putProposal(Integer questionId, ProposalTagInfo proposalInfo) {
    if (questionProposalMap.containsKey(questionId)) {
      ArrayList<ProposalTagInfo> list = questionProposalMap.get(questionId);
      assert list != null;
      for (ProposalTagInfo info : list) {
        if (info.getId() == proposalInfo.getId()) {
          info.updateProposal(proposalInfo);
          return;
        }
      }
      list.add(proposalInfo);
    } else {
      throw new InvalidParameterException("question does not exist");
    }
  }

  /**
   * remove proposal from existing question
   * @param questionId id of the question to remove from
   * @param proposalId id of the proposal to remove
   */
  public void removeProposal(Integer questionId, int proposalId) {
    if (questionProposalMap.containsKey(questionId)) {
      ArrayList<ProposalTagInfo> list = questionProposalMap.get(questionId);
      assert list != null;
      for (ProposalTagInfo info : list) {
        if (info.getId() == proposalId) {
          list.remove(info);
          return;
        }
      }
      throw new InvalidParameterException("proposal does not exist");
    } else {
      throw new InvalidParameterException("question does not exist");
    }
  }

  /**
   * get proposalsList of an existing question
   * @param questionId id of the question to get
   * @return proposalsList of the question, or null if question not exist
   */
  public ArrayList<ProposalTagInfo> getProposalsList(Integer questionId) {
    ArrayList<ProposalTagInfo> res = questionProposalMap.get(questionId);
    if (res == null) {
      throw new InvalidParameterException("question does not exist");
    }
    return res;
  }

  public GroupDataStore(String name) {
    questionProposalMap = new HashMap<>();
    questionEmojiSetMap = new HashMap<>();
    joinCode = "7DX5bCG6";  // TODO: random
    groupName = name;
  }

  public String getJoinCode() { return joinCode; }
  public String getGroupName() { return groupName; }
}
