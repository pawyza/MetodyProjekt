package score;

import java.util.List;

public interface ScoreDAO {
    public List<Score> findAll() throws Exception;
    void add(Score score) throws Exception;
}
