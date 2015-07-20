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

    public MatchData(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;
        set = 0;
        scores = new ScoreData[3];
        scores[0] = new ScoreData();
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
