package com.example.matchcontroller.services;

import com.example.matchcontroller.data.MatchData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BAKA on 2015/7/20.
 */
public class DataService {
    private static MatchData matchData;

    public static void startNewMatch(String name1, String name2) {
        matchData = new MatchData(name1, name2);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return match;
    }
}
