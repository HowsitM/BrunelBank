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
}
