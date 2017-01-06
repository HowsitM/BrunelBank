import java.util.ArrayList;

public class Users {

    private static ArrayList<String> activeUsers = new ArrayList<>();

    public static void addActiveUsers(String AccountId){
        activeUsers.add(AccountId);
    }

    public static ArrayList<String> getActiveUsers(){
        return activeUsers;
    }

}