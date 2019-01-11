package Score;

public class MainDavid {
    //nie wiem jak wyrzucić na tabele  więc zrobiłem maina mam nadzieje że mi pomożecie

        public static void main(String[] args) {
            HighscoreManager hm = new HighscoreManager();
            hm.addScore("Bart",240,10);
            hm.addScore("Marge",300,30);
            hm.addScore("Maggie",220,20);
            hm.addScore("Homer",100,10);
            hm.addScore("Lisa",270,30);

            System.out.print(hm.getHighscoreString());
        }
    }

