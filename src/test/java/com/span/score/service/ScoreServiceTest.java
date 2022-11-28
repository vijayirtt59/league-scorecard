package com.span.score.service;

import com.span.score.domain.ScoreCard;
import com.span.score.domain.Team;
import com.span.score.exception.ScorecardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreServiceTest {

    private ScoreService scoreService;

    @BeforeEach
    public void init() {
        scoreService = new ScoreService();
    }

    @Test
    void testSortList() {
        Collection<ScoreCard> teams = getScoreCardCollection();
        List<ScoreCard> scoreCardList = scoreService.sortList(teams);
        assertEquals(5, scoreCardList.size());
        assertEquals("Tarantulas", scoreCardList.get(0).getTeamName());
        assertEquals("Snakes", scoreCardList.get(3).getTeamName());
    }

    @Test
    void testPrintTeams() {
        Collection<ScoreCard> scoreCards = getScoreCardCollection();
        List<ScoreCard> sortedTeamList = scoreService.sortList(scoreCards);
        List<ScoreCard> teamList = scoreService.printTeamsByRank(sortedTeamList);
        assertEquals(1, teamList.get(0).getRank());
        assertEquals(3, teamList.get(2).getRank());
        assertEquals(3, teamList.get(3).getRank());
        assertEquals(4, teamList.get(4).getRank());
    }

    @Test
    void testPrintTeams_samePts() {
        Collection<ScoreCard> scoreCards = new ArrayList<>();
        scoreCards.add(new ScoreCard("France", 1));
        scoreCards.add(new ScoreCard("Spain", 1));
        scoreCards.add(new ScoreCard("Argentina", 1));

        List<ScoreCard> sortedScoreCards = scoreService.sortList(scoreCards);
        List<ScoreCard> scoreCardList = scoreService.printTeamsByRank(sortedScoreCards);
        assertEquals(1, scoreCardList.get(0).getRank());
        assertEquals(1, scoreCardList.get(1).getRank());
        assertEquals(1, scoreCardList.get(2).getRank());
    }

    @Test
    void testPrintTeams_diffPts() {
        Collection<ScoreCard> scoreCards = new ArrayList<>();
        scoreCards.add(new ScoreCard("France", 7));
        scoreCards.add(new ScoreCard("Spain", 6));
        scoreCards.add(new ScoreCard("Argentina", 5));
        scoreCards.add(new ScoreCard("Mexico", 9));
        scoreCards.add(new ScoreCard("Saudi Arabia", 3));

        List<ScoreCard> sortedTeamList = scoreService.sortList(scoreCards);
        List<ScoreCard> teamList = scoreService.printTeamsByRank(sortedTeamList);
        assertEquals(1, teamList.get(0).getRank());
        assertEquals("Mexico", teamList.get(0).getTeamName());
        assertEquals(5, teamList.get(4).getRank());
        assertEquals("Saudi Arabia", teamList.get(4).getTeamName());
    }

    @Test
    void testGetTeam_Exceptions() {
        Exception exception = assertThrows(ScorecardException.class, () -> scoreService.getTeam(""));
        assertEquals( "Input is invalid. Please provide valid input Ex: Lions 3, Snakes 3", exception.getMessage());
        Exception exception1 = assertThrows(ScorecardException.class, () -> scoreService.getTeam(null));
        assertEquals( "Input is invalid. Please provide valid input Ex: Lions 3, Snakes 3", exception1.getMessage());

        Exception exception3 = assertThrows(ScorecardException.class, () -> scoreService.getTeam("testString"));
        assertEquals( "Input format is invalid. Please provide valid input Ex: Lions 3", exception3.getMessage());
    }

    @Test
    void testGetTeam() throws ScorecardException {
        Team team = scoreService.getTeam("Lions 3");
        assertEquals(3, team.getPoints());
        assertEquals("Lions", team.getName());
    }

    @Test
    void testCalculateTeamScore_win() throws ScorecardException {
        Map<String, ScoreCard> scoreCardMap = scoreService.calculateTeamScore(new HashMap<>(), new Team("FC Barcelona", 2), new Team("Real Madrid", 1));
        assertEquals(3, scoreCardMap.get("FC Barcelona").getScore());
        assertEquals(0, scoreCardMap.get("Real Madrid").getScore());
    }

    @Test
    void testCalculateTeamScore_loss() throws ScorecardException {
        Map<String, ScoreCard> scoreCardMap = scoreService.calculateTeamScore(new HashMap<>(), new Team("FC Barcelona", 1), new Team("Real Madrid", 2));
        assertEquals(0, scoreCardMap.get("FC Barcelona").getScore());
        assertEquals(3, scoreCardMap.get("Real Madrid").getScore());
    }

    @Test
    void calculateTeamScore() throws ScorecardException {
        Map<String, ScoreCard> scoreCardMap = scoreService.calculateTeamScore(new HashMap<>(), new Team("FC Barcelona", 2), new Team("Real Madrid", 2));
        Map<String, ScoreCard> scoreCardMap1 = scoreService.calculateTeamScore(scoreCardMap, new Team("FC Barcelona", 3), new Team("Man City", 1));
        assertEquals(4, scoreCardMap1.get("FC Barcelona").getScore());
        assertEquals(1, scoreCardMap1.get("Real Madrid").getScore());
        assertEquals(0, scoreCardMap1.get("Man City").getScore());
    }

    @Test
    void testCalculateScoreCard() throws ScorecardException {
        List<ScoreCard> scoreCardList = scoreService.calculateScoreCard("src/test/resources/input.txt");
        assertEquals(5, scoreCardList.size());
    }

    @Test
    void testCalculateScoreCard_Exception() throws ScorecardException {
        Exception noFileException = assertThrows(ScorecardException.class, ()->scoreService.calculateScoreCard(""));
        assertEquals("Please provide valid Input file", noFileException.getMessage());

        Exception nullFileException = assertThrows(ScorecardException.class, ()->scoreService.calculateScoreCard(null));
        assertEquals("Please provide valid Input file", nullFileException.getMessage());

        Exception emptyInputsException = assertThrows(ScorecardException.class, ()->scoreService.calculateScoreCard("src/test/resources/emptyInput.txt"));
        assertEquals("No Scores found in the input file", emptyInputsException.getMessage());

        Exception inValidInputsException = assertThrows(ScorecardException.class, ()->scoreService.calculateScoreCard("src/test/resources/invalidInput.txt"));
        assertEquals("Input is invalid. Please provide valid input Ex: Lions 3, Snakes 3", inValidInputsException.getMessage());

        Exception inValidFileException = assertThrows(ScorecardException.class, ()->scoreService.calculateScoreCard("src/test/resources/invalidFile.txt"));
        assertEquals("Input file path is not found. Please provide valid filepath", inValidFileException.getMessage());
    }

    public Collection<ScoreCard> getScoreCardCollection() {
        Collection<ScoreCard> scoreCardCollection = new ArrayList<>();
        scoreCardCollection.add(new ScoreCard("FC Awesome", 1));
        scoreCardCollection.add(new ScoreCard("Snakes", 1));
        scoreCardCollection.add(new ScoreCard("Grouches", 0));
        scoreCardCollection.add(new ScoreCard("Tarantulas", 6));
        scoreCardCollection.add(new ScoreCard("Lions", 5));
        return scoreCardCollection;
    }

}