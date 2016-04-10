package davey.scott.ce881_assignment;

import java.util.List;

/**
 * Created by Scott Davey on 10/04/2016.
 */
public interface LeaderboardDB {
    Score getHighScore();
    List<Score> topN(int n);
    void saveScore(Score score);
}
