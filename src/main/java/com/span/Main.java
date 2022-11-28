package com.span;

import com.span.score.constants.ScorecardConstants;
import com.span.score.domain.Team;
import com.span.score.exception.ScorecardException;
import com.span.score.service.ScoreService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws ScorecardException {
        if (null == args || args.length == 0) {
            throw new ScorecardException(ScorecardConstants.CODE_INVALID_FILE_PATH);
        }
        String filepath = args[0];
        ScoreService scoreService = new ScoreService();
        Map<String, Team> teamMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] teams = currentLine.split(",");
                if (null == teams || teams.length != 2) {
                    throw new ScorecardException(ScorecardConstants.CODE_INVALID_INPUT);
                }
                Team team1 = scoreService.getTeam(teams[0]);
                Team team2 = scoreService.getTeam(teams[1]);
                scoreService.calculateScore(teamMap, team1, team2);
            }
            if (teamMap.size() == 0) {
                throw new ScorecardException(ScorecardConstants.CODE_NO_SCORES_PROVIDED);
            }

            List<Team> sortedTeamByScore = scoreService.sortList(teamMap.values());
            for (Team team : sortedTeamByScore) {
                System.out.println(team);
            }

        } catch (IOException ioException) {
            if (ioException.getClass().equals(FileNotFoundException.class))
                throw new ScorecardException(ScorecardConstants.CODE_FILE_NOT_FOUND);
        }

    }


}
