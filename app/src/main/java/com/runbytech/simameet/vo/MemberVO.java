package com.runbytech.simameet.vo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liwenzhi on 14-10-14.
 */
public class MemberVO {

    private String groupId;
    private String userId;
    private String userName;
    private int rank;
    private int state;

    public static MemberVO parseJsonToObj(JSONObject json) throws JSONException {
        MemberVO mo = new MemberVO();
        mo.setGroupId(json.getString("groupId"));
        mo.setUserId(json.getString("id"));
        mo.setUserName(json.getString("name"));
        mo.setRank(json.getInt("rank"));
        mo.setState(json.getInt("state"));

        return mo;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
