package com.example.matchcontroller.services;

import com.example.matchcontroller.data.MatchData;

import org.json.JSONObject;

/**
 * Created by BAKA on 2015/7/20.
 */
public class DataService {
    static MatchData matchData;

    public static void startNewMatch(String name1, String name2) {
        matchData = new MatchData(name1, name2);
    }

    public static String getScoreData() throws Exception{
        JSONObject jsonObject = matchData.getScoreJSON();
        return jsonObject.toString();
    }
}
