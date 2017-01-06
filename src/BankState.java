import java.io.InterruptedIOException;
import java.util.Map;
import java.util.HashMap;

public class BankState {

    static private Map<String, Long> isLocked = new HashMap<>();

    public static boolean acquireLock(String AccountID){

        Thread thread = Thread.currentThread();

        if(isLocked.containsKey(AccountID)){
            return isLocked.get(AccountID) == thread.getId();
        } else {
          isLocked.put(AccountID, thread.getId());
          return true;
        }
    }

    public static void unlock(String AccountID){
        isLocked.remove(AccountID);
    }
}
