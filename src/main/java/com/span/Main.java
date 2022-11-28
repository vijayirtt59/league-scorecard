package com.span;

import com.span.score.constants.ScorecardConstants;
import com.span.score.exception.ScorecardException;
import com.span.score.service.ScoreService;


public class Main {

    public static void main(String[] args) throws ScorecardException {
        if (null == args || args.length == 0) {
            throw new ScorecardException(ScorecardConstants.MESSAGE_INVALID_FILE_PATH);
        }
        String filepath = args[0];
        ScoreService scoreService = new ScoreService();
        scoreService.calculateScoreCard(filepath);
    }


}
