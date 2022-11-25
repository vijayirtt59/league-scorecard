package com.span.score.domain;

public class Team {
    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        if(score == 1){
            return name + ", "+score + " pt";
        }
        return name + ", " +score + " pts";
    }
}
