package Score;

public class MainDavid {
    //nie wiem jak wyrzucić na tabele  więc zrobiłem maina mam nadzieje że mi pomożecie

        public static void main(String[] args) {
            HighscoreManager hm = new HighscoreManager();
            hm.addScore("Bart",240,10,250);
            hm.addScore("Marge",300,30,330);
            hm.addScore("Maggie",220,20,240);
            hm.addScore("Homer",100,10,110);
            hm.addScore("Lisa",270,30,300);

            System.out.print(hm.getHighscoreString());
        }
    }

