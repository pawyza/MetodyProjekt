package score;

import java.io.File;

public class DAOTest {
    public static void main(String[] args) throws Exception {
        ScoreDAO dao = new TextScoreDAO(new File("resources/scores.txt"));
        System.out.println(dao.findAll());
    }
}
