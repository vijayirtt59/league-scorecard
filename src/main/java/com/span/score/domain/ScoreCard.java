package com.span.score.domain;

/**
 * Output Domain
 */
public class ScoreCard {
    private String teamName;
    private int score;
    private int rank;

    public ScoreCard(String teamName, int score) {
        this.teamName = teamName;
        this.score = score;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        if (score == 1) {
            return rank + ". " + teamName + ", " + score + " pt";
        }
        return rank + ". " + teamName + ", " + score + " pts";
    }
}
