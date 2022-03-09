import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/*
* This is the main class of the assignment
* it takes in the MD5 hash code and decodes it from base64
* it also generates a blocking queue for all possible combinations from AAA to ZZZ
* */
public class DigestCracker {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        byte[] digest = Base64.getDecoder().decode(args[0]);
        BlockingQueue<String> IataCodeQueue = new LinkedBlockingQueue<>(genrateIataCodes());

        Runnable DigestCracker1 = new DigestCrackerRunnable(IataCodeQueue, digest);
        Runnable DigestCracker2 = new DigestCrackerRunnable(IataCodeQueue, digest);

        Thread thread1 = new Thread(DigestCracker1);
        Thread thread2 = new Thread(DigestCracker2);

        thread2.setPriority(Thread.MIN_PRIORITY);
        thread1.setPriority(Thread.MAX_PRIORITY);

        thread1.start();
        thread2.start();
    }


    /**
     * This function generates a linked blocking queue made of all the possible combinations of AAA to ZZZ
     * @return airportCodes a linked blocking queue full of Generated codes to iterated through
     */

    public static LinkedBlockingQueue<String> genrateIataCodes() {

        LinkedBlockingQueue<String> airportCodes = new LinkedBlockingQueue();

        String[] alphabet = {"A", "B", "C", "D","E", "F", "G", "H", "I", "J",
        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String code;

        try {
            for (String item : alphabet) {
                for (String value : alphabet) {
                    for (String s : alphabet) {
                        code = String.join(item, value, s);
                        airportCodes.add(code);
                    }
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("Collection to large to fit in blokcing queue" + e.getMessage());
        }


        return airportCodes;
    }
}

