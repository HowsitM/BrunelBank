import java.util.Map;
import java.util.HashMap;

public class BankState {

    static private Map<String, Long> isLocked = new HashMap<>();

    // - acquireLock() checks to see if the accountID passed to it is in teh HashMap isLocked
    // - if the accountID is in HashMap return true
    // - if the accountID is not in the HashMap, add it

    public static boolean acquireLock(String AccountID){

        Thread thread = Thread.currentThread();

        if(isLocked.containsKey(AccountID)){
            return isLocked.get(AccountID) == thread.getId();
        } else {
          isLocked.put(AccountID, thread.getId());
          return true;
        }
    }

    //unlock() removes the HashMap entry with the corresponding AccountID

    public static void unlock(String AccountID){
        isLocked.remove(AccountID);
    }
}
