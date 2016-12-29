import java.net.*;
import java.io.*;

public class BankServerThread extends Thread{

    private Socket ServerSocket = null;


    public BankServerThread(Socket serverSocket){
        super("Brunel Bank");
        this.ServerSocket = serverSocket;
    }

    public void run(){
    //Create the account that I'm assigning to the thread

        System.out.println("New Client Connection");
        new Account(currentThread(),this.ServerSocket);

    }
}