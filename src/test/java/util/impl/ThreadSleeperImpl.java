package util.impl;

public class ThreadSleeperImpl {

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}
