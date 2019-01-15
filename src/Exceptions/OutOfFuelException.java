package Exceptions;

/**
 *  Wyjątek odpowiadający za brak paliwa w rakiecie
 */
public class OutOfFuelException extends Exception {
    public OutOfFuelException(){
        System.out.println("Out of fuel");
    }
}
