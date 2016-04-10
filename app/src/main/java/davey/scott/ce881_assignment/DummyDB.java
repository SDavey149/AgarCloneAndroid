package davey.scott.ce881_assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Scott Davey on 10/04/2016.
 */
public class DummyDB implements LeaderboardDB {
    ArrayList<Score> scores;

    public DummyDB() {
        scores = new ArrayList<>();
    }

    @Override
    public Score getHighScore() {
        return getMax(scores);
    }

    @Override
    public List<Score> topN(int n) {
        ArrayList<Score> topSc = new ArrayList<>();
        ArrayList<Score> scores2 = new ArrayList<>(scores);
        for (int i = 0; i < n; i++) {
            Score max = getMax(scores2);
            topSc.add(max);
            scores2.remove(max);
        }
        Collections.sort(topSc);
        return topSc;
    }

    @Override
    public void saveScore(Score score) {
        scores.add(score);
    }

    private Score getMax(List<Score> scoreList) {
        int maxScore = 0;
        Score max = null;
        for (Score sc : scoreList) {
            if (sc.score > maxScore) {
                maxScore = sc.score;
                max = sc;
            }
        }
        return max;
    }
}
