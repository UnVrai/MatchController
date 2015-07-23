package com.example.matchcontroller.services;

import android.app.Activity;
import android.content.ContentValues;

import com.example.matchcontroller.data.MatchData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BAKA on 2015/7/20.
 */
public class DataService {
    private static MatchData matchData;

    public static void startNewMatch(String name1, String name2, Activity activity) {
        matchData = new MatchData(name1, name2);
        ContentValues cv = new ContentValues();
        cv.put("name1", matchData.getName1());
        cv.put("name2", matchData.getName2());
        cv.put("sets", matchData.getSet());
        cv.put("set1", matchData.getSet1());
        cv.put("set2", matchData.getSet2());
        SQLiteService.createNewMatch(activity, cv);
    }

    public static void incScore1() {
        matchData.incScore1();
    }
    public static void incScore2() {
        matchData.incScore2();
    }
    public static void decScore1() {
        matchData.decScore1();
    }
    public static void decScore2() {
        matchData.decScore2();
    }

    public static String getScoreData() throws Exception{
        JSONObject jsonObject = matchData.getScoreJSON();
        return jsonObject.toString();
    }

    public static String getMatchData() throws Exception {
        JSONObject jsonObject = matchData.getMatchJSON();
        return jsonObject.toString();
    }

    public static MatchData getMatch() {
        return  matchData;
    }

    public static MatchData jsonToData(JSONObject jo) {
        MatchData match = null;
        try {
            match = new MatchData(jo.getString("nameone"),jo.getString("nametwo"));
            match.setSetScores(jo.getInt("setone"), jo.getInt("settwo"));
            match.setScore(jo.getInt("scoreone_1"), jo.getInt("scoreone_2"));
            match.addScoreData();
            match.setScore(jo.getInt("scoretwo_1"), jo.getInt("scoretwo_2"));
            match.addScoreData();
            match.setScore(jo.getInt("scoretwo_1"), jo.getInt("scoretwo_2"));
            match.setDate(jo.getString("date"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return match;
    }
}
