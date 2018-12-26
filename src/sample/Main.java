package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

/*      WAZNE
Pamietajcie ze przez to ze pracujemy razem nad projektem warto robic na bierzaca dokumentacje dla innych.
Skrot ulatwiajacy tworzenie javadoca ctrl+shift+a -> fix doc comment
Jesli macie jakis problem przemyslnie czy komentarz potrzebny na etapie pisania piszcie go zwyklym komentarzem //
Poki nie zrobimy bardzo estetycznego gui mozemy dodawac kolorki i jakies pierdoly zeby utrzymac minimum estetyki, polecam uzywanie
palety z tego linka(to paleta ktora uzywam): https://coolors.co/c5d1eb-91a7d6-5a6784-39445b-151d2d
ogolnie polecam stronke bo tworzy paletki wedlug roznych teorii koloru (chodzi o stale odleglosci w odpowiednim ukladzie przestrzennym barw)
oczywiscie do takiej paletki zawsze pasuje czysty czarny i bialy
Tutaj zrobmy tez liste rzeczy ktore nalezy zrobic wraz z pomyslami przemysleniami:
-System obliczen
tutaj nie mamy zbyt duzego pola do popisu bo musimy kierowac sie tym co zostalo podane w poleceniu projektu
podejrzewam ze jest to kwestia odpowiedniego dostosowania tego co juz zrobilismy na laboratioriach
uwazam ze wystarczy zrobic to jako klase ktorej obiekt bedzie tworzony w controlerze, i ten obiekt bedzie posiadal osobny watek
watek bedzie liczyl do 1 sekundy i wtedy bedzie pobieral stan czy silnik wlaczany czy nie z controlera, oczywiscie klasa posiadajaca ten watek powinna posiadac obiekty klas
ktorych celem bedzie liczenie(po prostu zawsze trzeba bedzie liczyc dla tp =0 do tk= 1 (no bo za kazdym razem pomiedzy pobraniem wartosci bedzie to uplyw 1 sekundy)
 i wtedy warunki tk beda nowymi tp i tak do końca
-System aktualizowania wyswietlanych informacji
wydaje mi sie ze trzeba zrobic osobny watek ktory bedzie co jakas stala(najlepiej co kazde wyliczenie wartosci w integratorze liczacym te rozniczki) zadaniem tego systemu byloby
aktualizowanie pol tekstowych (zeby ladnie plynnie sie zmienialy a nie co uplyw 1 sekundy) oraz bedzie aktualizowal wyswietlana grafike
-systemy rysowania mapy i statku
ogolnie tutaj wyobrazam sobie to tak ze na mapie po prostu zrobi sie oznaczenie statku jakims kwadratem bialym czy innym trojkatem i na dole oznaczy sie ziemie, zrobimy tak ze po prostu droga od poczatku
do ziemi to bedzie 100% i bedzie sie ladnie przesuwal co jakas tam podzialke(trzeba bedzie pobrac ilosc pikseli od statku do ziemi i sobie to ladnie dzielic tak zeby przsuniecie o iles pikseli
odpowiadalo przesunieciu o iles metrow(tych obliczonych)
w wypadku zwyklego rysowania to wydaje mi sie ze mozna ladnie zasymulowac ruch statku robiac w kilku roznych planach takie symboliczne gwiady(kropki i inny kosmiczny syf) ktory bedzie sie przesowal z odpowiednia predkascia
po ekranie ktora bedzie wyliczana na podstawie predkosci z ktora porusza sie statek zeby jako zaprezentowac jego predkosc(wiem ze to nie realistyczne ale latce na 100 spodobalby sie taki bajer bo tak sie robi takie rzeczy
w gierkach) i na koncu po prostu zrobimy tez ladnie wysuwajaca sie ziemie

ogolnie to wydaje mi sie ze to wszystko co bezdie potrzebne
wydaje mi sie ze najtrudniejsza i najbardziej rakowa bedzie animacja ale jak chcecie moge to zrobic bo ja lubie sie bawic takimi wizualnymi bajerami
 */