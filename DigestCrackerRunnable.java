import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class DigestCrackerRunnable implements Runnable{

    private final byte[] md5Hash; // this variable holds the decoded MD% hash code for the airport
    private final BlockingQueue<String> codeList; //This holds the inputed blocking queue
    private final MessageDigest digester = MessageDigest.getInstance("MD5"); // Generates an instance of an MD% digester
    private int iterationTracker = 0;

    /**
     * The constructor
     * @param airportCodeList the input list of airport codes
     * @param hash the MD5 hash code that was given
     * @throws NoSuchAlgorithmException when the MessageDigest instance does not exist
     */
    public DigestCrackerRunnable(BlockingQueue<String> airportCodeList, byte[] hash) throws NoSuchAlgorithmException {
        this.codeList = airportCodeList;
        this.md5Hash = hash;
    }

    /**
     * The method dictating the runtime parameters of the thread. This will iterate through the blocking queue
     * while evaluating if the has value of the current code matches given MD5 hash. When the program finds the matching
     * hash value it will clear the blocking queue.
     */
    @Override
    public void run() {
        // peek @ queue to make sure it's not empty
        String code = codeList.poll();
        while (code != null) {
            // if not null (empty), take the highest priority element

            byte[] existingCodeHash = digester.digest( code.getBytes() );
            if (Arrays.equals(existingCodeHash, this.md5Hash)) {
                System.out.println("You found the code! The deal will happen at " + code);
                codeList.clear(); // do something
            }
            iterationTracker++;
            code = this.codeList.poll();
        }
        System.out.println("Number of iterations ran: " + iterationTracker);


    }
}
