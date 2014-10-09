package com.runbytech.simameet.vo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liwenzhi on 14-10-8.
 */
public class GroupAction {

    private String clubId;
    private String clubName;
    private String actId;
    private int isMember;
    private int memberNum;
    private String actName;
    private int recommendNum;
    private int startTime;
    private int state;

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public int getIsMember() {
        return isMember;
    }

    public void setIsMember(int isMember) {
        this.isMember = isMember;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public int getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(int recommendNum) {
        this.recommendNum = recommendNum;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static GroupAction parseJsonToObj(JSONObject json) throws JSONException {
        GroupAction ga = new GroupAction();
        ga.setActId(json.getString("id"));
        ga.setActName(json.getString("name"));
        ga.setClubId(json.getString("clubId"));
        ga.setClubName(json.getString("clubName"));
        ga.setIsMember(json.getInt("isMember"));
        ga.setMemberNum(json.getInt("memberNum"));
        ga.setRecommendNum(json.getInt("recommendNum"));
        ga.setStartTime(json.getInt("startTime"));
        ga.setState(json.getInt("state"));

        return ga;
    }


}
