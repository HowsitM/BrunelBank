import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Users {

    private static ArrayList<String> activeUsers = new ArrayList<>();
    private PrintWriter out;


    public Users(Account account, Socket socket)  {

        try{
            this.out = new PrintWriter(socket.getOutputStream(), true);}
        catch(IOException e){
            e.printStackTrace();
        }
        addActiveUsers(account.getAccountId());
        this.out.println("You have signed into account: " + account.getAccountId());
    }

    public static void addActiveUsers(String AccountId){
        activeUsers.add(AccountId);
    }

    public static ArrayList<String> getActiveUsers(){
        return activeUsers;
    }
}