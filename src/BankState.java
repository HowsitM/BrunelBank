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
    }

    public static double doDeposit(){
        return 0;
    }

    public static double doWithdraw(){
        return 0;
    }

    public static synchronized void doTransfer(){

    }
}
