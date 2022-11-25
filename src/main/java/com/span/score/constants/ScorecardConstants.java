package com.span.score.constants;

public class ScorecardConstants {
    private ScorecardConstants(){

    }

    public static final String CODE_INVALID_FILE_PATH = "SCORE_CARD_EXP_01";
    public static final String MESSAGE_INVALID_FILE_PATH = "Please provide valid Input file";
    public static final String CODE_FILE_NOT_FOUND = "SCORE_CARD_EXP_02";
    public static final String MESSAGE_FILE_NOT_FOUND = "Input file path is not found. Please provide valid filepath";

    public static final String CODE_INVALID_INPUT = "SCORE_CARD_EXP_03";
    public static final String MESSAGE_INVALID_INPUT = "Input is invalid. Please provide valid input Ex: Lions 3, Snakes 3";

    public static final String CODE_NO_SCORES_PROVIDED = "SCORE_CARD_EXP_04";
    public static final String MESSAGE_NO_SCORES_PROVIDED = "No Scores found in the input file";
}
