package com.span.score.service;

import com.span.score.constants.ScorecardConstants;
import com.span.score.domain.Team;
import com.span.score.exception.ScorecardException;

import java.util.Map;

public class ScoreService {

    public Team getTeam(String teamString) throws ScorecardException {
        Team team = new Team();
        if (teamString.isEmpty()) {
            throw new ScorecardException(ScorecardConstants.CODE_INVALID_INPUT);
        }
        team.setName(teamString.substring(0, teamString.length() - 1).trim());
        team.setScore(Integer.valueOf(teamString.substring(teamString.length() - 1)));
        return team;
    }

    public void calculateScore(Map<String, Team> teamMap, Team team1, Team team2) throws ScorecardException {
        if (teamMap == null) {
            throw new ScorecardException("");
        }
        if(null == team1 || null == team2){
            throw new ScorecardException("");
        }
        if(team1.getScore() == team2.getScore()){
            updateScore(teamMap, team1, 1);
            updateScore(teamMap, team2, 1);
        } else if(team1.getScore() > team2.getScore()){
            updateScore(teamMap, team1, 3);
            updateScore(teamMap, team2, 0);
        } else{
            updateScore(teamMap, team1, 0);
            updateScore(teamMap, team2, 3);
        }
    }

    public void updateScore(Map<String, Team> teamMap, Team team, int score){
        if(teamMap.containsKey(team.getName())){
            Team availableTeam = teamMap.get(team.getName());
            availableTeam.setScore(availableTeam.getScore() + score);
        } else {
            team.setScore(score);
            teamMap.put(team.getName(), team);
        }
    }
}
