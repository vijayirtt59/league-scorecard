package com.span;

import com.span.score.constants.ScorecardConstants;
import com.span.score.exception.ScorecardException;
import com.span.score.service.ScoreService;

/**
 * This App lets you to calculate the ranking table for the league
 * based on the result of individual league matches. In this league,
 * a draw (tie) is worth 1 point and a win is worth 3 points.
 * A loss is worth 0 points.
 *
 * If two or more teams have the same number of points,
 * they should have the same rank and be printed in alphabetical order
 * (as in the tie for 3rd place in the sample data).
 * Sample input data is available in index.txt file
 */
public class Main {

    /**
     * This is the starting point of the application.
     * It takes argument - fileName and process it to provide ranking table as output
     * The Output is now printed in the terminal as sysout.
     */
    public static void main(String[] args) throws ScorecardException {
        if (null == args || args.length == 0) {
            throw new ScorecardException(ScorecardConstants.MESSAGE_INVALID_FILE_PATH);
        }
        String filepath = args[0];
        ScoreService scoreService = new ScoreService();
        scoreService.calculateScoreCard(filepath);
    }


}
