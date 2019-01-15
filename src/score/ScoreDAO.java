package score;

import java.util.List;

/**
 *Interface posiadająca liste wyników do zapisu wyników
 *
 */
public interface ScoreDAO {
    public List<Score> findAll() throws Exception;
    void add(Score score) throws Exception;
}
