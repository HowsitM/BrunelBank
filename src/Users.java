import java.util.ArrayList;

public class Users {

    private static ArrayList<String> activeUsers = new ArrayList<>();

    // - addActiveUsers() Adds the AccountId to the ArrayList, this is used to keep track of all active accounts connected
    // - to the server.

    public static void addActiveUsers(String AccountId){
        activeUsers.add(AccountId);
    }

    // - getActiveUsers() returns the contents of the ArrayList.

    public static ArrayList<String> getActiveUsers(){
        return activeUsers;
    }
}