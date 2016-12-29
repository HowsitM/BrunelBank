import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BankState {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Account account;

    public BankState(){

    }


    public static synchronized void doBalance(){
        //this is printed out to the server
        System.out.println("Still got to do this part");
    }

    public static double doDeposit(){
        System.out.println("Still got to do this part");
        return 0;
    }

    public static double doWithdraw(){
        System.out.println("Still got to do this part");
        return 0;
    }

    public static synchronized void doTransfer(){
        System.out.println("Still got to do this part");
    }
}
