package Exceptions;

/**
 *  Wyjątek wyrzucany podczas rozbicia rakiety
 */
public class RocketCrashedException extends Exception {
    public RocketCrashedException() {
        System.out.println("Crashed");
    }


}
