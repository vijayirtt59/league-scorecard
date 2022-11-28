package com.span.score.service;

import com.span.score.constants.ScorecardConstants;
import com.span.score.domain.ScoreCard;
import com.span.score.domain.Team;
import com.span.score.exception.ScorecardException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreService {

    private int rank = 0;

    public Team getTeam(String teamString) throws ScorecardException {
        Team team = new Team();
        if (null == teamString || teamString.isEmpty()) {
            throw new ScorecardException(ScorecardConstants.MESSAGE_INVALID_INPUT);
        }
        try {
            team.setName(teamString.substring(0, teamString.length() - 1).trim());
            team.setPoints(Integer.valueOf(teamString.substring(teamString.length() - 1)));
        } catch (Exception exception){
            throw new ScorecardException(ScorecardConstants.MESSAGE_INVALID_INPUT_FORMAT);
        }

        return team;
    }

    public Map<String, ScoreCard> calculateScore(Map<String, ScoreCard> scoreCardMap, Team team1, Team team2) throws ScorecardException {
        if (null == scoreCardMap || null == team1 || null == team2) {
            throw new ScorecardException(ScorecardConstants.MESSAGE_CALCULATE_SCORES);
        }
        if (team1.getPoints() == team2.getPoints()) {
            updateScore(scoreCardMap, team1, 1);
            updateScore(scoreCardMap, team2, 1);
        } else if (team1.getPoints() > team2.getPoints()) {
            updateScore(scoreCardMap, team1, 3);
            updateScore(scoreCardMap, team2, 0);
        } else {
            updateScore(scoreCardMap, team1, 0);
            updateScore(scoreCardMap, team2, 3);
        }
        return scoreCardMap;
    }

    private void updateScore(Map<String, ScoreCard> scoreCardMap, Team team, int score) {
        if (scoreCardMap.containsKey(team.getName())) {
            ScoreCard availableTeam = scoreCardMap.get(team.getName());
            availableTeam.setScore(availableTeam.getScore() + score);
        } else {
            scoreCardMap.put(team.getName(), new ScoreCard(team.getName(), score));
        }
    }

    public List<ScoreCard> sortList(Collection<ScoreCard> scoreCardCollection) {
        Comparator<ScoreCard> comparatorByScore = Comparator.comparing(ScoreCard::getScore).reversed().thenComparing(ScoreCard::getTeamName);
        return scoreCardCollection.stream().sorted(comparatorByScore).collect(Collectors.toList());
    }

    public List<ScoreCard> printTeamsByRank(List<ScoreCard> scoreCardList){
        int previousPts = 0;
        for(ScoreCard scoreCard: scoreCardList){
            int currentPts = scoreCard.getScore();
            if(currentPts != previousPts){
                rank++;
            }
            scoreCard.setRank(rank);
            previousPts = currentPts;
            System.out.println(scoreCard);
        }
        return scoreCardList;
    }
}
