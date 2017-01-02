import com.sun.corba.se.spi.activation.Server;

import java.net.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class BankServerThread extends Thread{

    private Socket ServerSocket = null;

    public BankServerThread(Socket serverSocket){
        super("Brunel Bank");
        this.ServerSocket = serverSocket;
    }

    public void run(){
    //Create the account that I'm assigning to the thread
        System.out.println(BankServerThread.currentThread() + " initialising...");
        try{
            PrintWriter out = new PrintWriter(ServerSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(ServerSocket.getInputStream()));
            int inputLine;
            String outputLine;

            //assigns the current thread to an account
            new Account(currentThread().getName(),this.ServerSocket);

            out.println("BrunelBank Menu:\nChoose an option:\n1. Balance\n2. Deposit\n3. Withdraw\n4. Transfer\n");
            inputLine = parseInt(in.readLine());
            switch(inputLine){

                case 1: BankState.doBalance(); break;
                case 2: BankState.doDeposit(); break;
                case 3: BankState.doWithdraw(); break;
                case 4: BankState.doTransfer(); break;
                case 5: break;
                default: BankState.doBalance(); break;
            }

        }catch (Exception e){
            System.err.println(e);
        }
    }
}