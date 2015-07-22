package com.example.matchcontroller.data;

import org.json.JSONObject;

/**
 * Created by UnVrai on 2015/7/19.
 */
public class MatchData {
    String name1;
    String name2;
    int set;
    int set1;
    int set2;
    ScoreData[] scores;
    String date;

    public MatchData(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;
        set = 0;
        scores = new ScoreData[3];
        scores[0] = new ScoreData();
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public int getSet() {
        return set;
    }

    public int getSet1() {
        return set1;
    }

    public int getSet2() {
        return set2;
    }

    public String getDate() {
        return date;
    }

    public JSONObject getScoreJSON() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name1", name1);
        jsonObject.put("name2", name2);
        jsonObject.put("score1", scores[set].score1);
        jsonObject.put("score2", scores[set].score2);
        jsonObject.put("set1", set1);
        jsonObject.put("set2", set2);
        return  jsonObject;
    }

    public JSONObject getMatchJSON() throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("nameone", name1);
        jo.put("nametwo", name2);
        jo.put("setone", set1);
        jo.put("settwo", set2);
        jo.put("scoreone_1", scores[0].score1);
        jo.put("scoreone_2", scores[0].score2);
        jo.put("scoretwo_1", scores[1].score1);
        jo.put("scoretwo_2", scores[1].score2);
        jo.put("scoretri_1", scores[2].score1);
        jo.put("scoretri_2", scores[2].score2);
        jo.put("date", date);
        return jo;
    }

    public void setSetScores(int set1,int set2) {
        this.set1 = set1;
        this.set2 = set2;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addScoreData() {
        set++;
        scores[set] = new ScoreData();
    }

    public void setScore(int score1, int score2) {
        scores[set].score1 = score1;
        scores[set].score2 = score2;
    }

    boolean incScore1() {
        return scores[set].incScore1();
    }

    boolean incScore2() {
        return scores[set].incScore2();
    }

    boolean decScore1() {
        return scores[set].decScore1();
    }

    boolean decScore2() {
        return scores[set].incScore2();
    }



    class ScoreData {
        int score1;
        int score2;
        int isWin;

        boolean isWiner() {
            return true;
        }

        boolean incScore1() {
            score1++;
            return true;
        }

        boolean incScore2() {
            score2++;
            return true;
        }

        boolean decScore1() {
            if (score1 <= 0) {
                return false;
            } else {
                score1--;
                return true;
            }
        }

        boolean decScore2() {
            if (score2 <= 0) {
                return false;
            } else {
                score2--;
                return true;
            }
        }


    }
}
