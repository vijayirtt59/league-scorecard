package com.span.score.service;

import com.span.score.constants.ScorecardConstants;
import com.span.score.domain.ScoreCard;
import com.span.score.domain.Team;
import com.span.score.exception.ScorecardException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreService {

    private int rank = 0;

    /**
     * calculateScoreCard - This method takes care of all action.
     * Takes the filepath as argument process it to provide the ranking table
     *
     * @param filepath - Input file path. For sample input refer Input.txt
     * @return List of Scorecard which is sorted based on the ranking
     * @throws ScorecardException for any invalid filepath or input string
     */
    public List<ScoreCard> calculateScoreCard(String filepath) throws ScorecardException {
        if (null == filepath || filepath.isEmpty()) {
            throw new ScorecardException(ScorecardConstants.MESSAGE_INVALID_FILE_PATH);
        }
        Map<String, ScoreCard> scoreCardMap = new HashMap<>();
        List<ScoreCard> scoreCardByRank = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] teams = currentLine.split(",");
                if (null == teams || teams.length != 2) {
                    throw new ScorecardException(ScorecardConstants.MESSAGE_INVALID_INPUT);
                }
                Team team1 = getTeam(teams[0]);
                Team team2 = getTeam(teams[1]);
                calculateTeamScore(scoreCardMap, team1, team2);
            }
            if (scoreCardMap.size() == 0) {
                throw new ScorecardException(ScorecardConstants.MESSAGE_NO_SCORES_PROVIDED);
            }

            List<ScoreCard> sortList = sortList(scoreCardMap.values());
            scoreCardByRank = printTeamsByRank(sortList);
        } catch (IOException ioException) {
            if (ioException.getClass().equals(FileNotFoundException.class))
                throw new ScorecardException(ScorecardConstants.MESSAGE_FILE_NOT_FOUND);
        }
        return scoreCardByRank;
    }

    /**
     * getTeam - Helper method to get the team with the name and points
     *
     * @param teamString - Ex. Lions 3
     * @return Team object which will be processed later
     * @throws ScorecardException - for any invalid input string
     */
    public Team getTeam(String teamString) throws ScorecardException {
        Team team = new Team();
        if (null == teamString || teamString.isEmpty()) {
            throw new ScorecardException(ScorecardConstants.MESSAGE_INVALID_INPUT);
        }
        try {
            team.setName(teamString.substring(0, teamString.length() - 1).trim());
            team.setPoints(Integer.parseInt(teamString.substring(teamString.length() - 1)));
        } catch (Exception exception) {
            throw new ScorecardException(ScorecardConstants.MESSAGE_INVALID_INPUT_FORMAT);
        }

        return team;
    }

    /**
     * calculateTeamScore - which calculates score based on Team1 and Team2 points.
     * In this league, a draw (tie) is worth 1 point and a win is worth 3 points.
     * A loss is worth 0 points.
     *
     * @param scoreCardMap - Map which stores teamName and ScoreCard for that team
     * @param team1        - Team 1 - name and points
     * @param team2        - Team 1 - name and points
     * @return - the map of TeamName, Scorecard
     * @throws ScorecardException for any invalid Map, team1 or team2
     */
    public Map<String, ScoreCard> calculateTeamScore(Map<String, ScoreCard> scoreCardMap, Team team1, Team team2) throws ScorecardException {
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

    /**
     * updateScore - update the ScoreCardMap.
     * If the Team is present it updates only the score and if not present it creates a new entry in the Map.
     *
     * @param scoreCardMap - Map which stores teamName and ScoreCard for that team
     * @param team         - Team Details with name and points
     * @param score        - Score that has to be updated in the map
     */
    private void updateScore(Map<String, ScoreCard> scoreCardMap, Team team, int score) {
        if (scoreCardMap.containsKey(team.getName())) {
            ScoreCard availableTeam = scoreCardMap.get(team.getName());
            availableTeam.setScore(availableTeam.getScore() + score);
        } else {
            scoreCardMap.put(team.getName(), new ScoreCard(team.getName(), score));
        }
    }

    /**
     * sortList - Sort the collection from the Map based on the score
     *
     * @param scoreCardCollection - Unsorted collection
     * @return List of sorted Scorecard
     */
    public List<ScoreCard> sortList(Collection<ScoreCard> scoreCardCollection) {
        Comparator<ScoreCard> comparatorByScore = Comparator.comparing(ScoreCard::getScore).reversed().thenComparing(ScoreCard::getTeamName);
        return scoreCardCollection.stream().sorted(comparatorByScore).collect(Collectors.toList());
    }

    /**
     * printTeamsByRank - Method that calculates rank based on the score and prints the ranking table
     *
     * @param scoreCardList - Sorted List
     * @return - Sorted List with ranks updated for individual team.
     */
    public List<ScoreCard> printTeamsByRank(List<ScoreCard> scoreCardList) {
        int previousPts = 0;
        for (ScoreCard scoreCard : scoreCardList) {
            int currentPts = scoreCard.getScore();
            if (currentPts != previousPts) {
                rank++;
            }
            scoreCard.setRank(rank);
            previousPts = currentPts;
            System.out.println(scoreCard);
        }
        return scoreCardList;
    }
}
