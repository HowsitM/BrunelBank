import java.net.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class BankServerThread extends Thread{

    private Socket ServerSocket = null;
    private String idName;

    public BankServerThread(Socket serverSocket, String idName){
        super(idName);
        this.idName = idName;
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
            new Account(BankServerThread.currentThread().getName(), this.ServerSocket);
            out.println("The Current users are: " + Users.getActiveUsers());

            out.println("BrunelBank Menu:\nChoose an option:\n1. Balance\n2. Deposit\n3. Withdraw\n4. Transfer\n\r");
            inputLine = parseInt(in.readLine());
            switch(inputLine){

                case 1: doBalance(); break;
                case 2: doDeposit(); break;
                case 3: doWithdraw(); break;
                case 4: doTransfer(); break;
                case 5: break;
                default: doBalance(); break;
            }

        }catch (Exception e){
            System.err.println(e);
        }
    }

    public void doBalance(){
//Having issues with static and non static calls :(

        BankState.aquireLock(idName);
        //Account.getBalance();
        //check the threads accounts balance
        //unlock the thread
        //notify everyone
    }

    private double doDeposit(){
        System.out.println("Still got to do this part");
        return 0;
    }

    private double doWithdraw(){
        System.out.println("Still got to do this part");
        return 0;
    }

    private synchronized void doTransfer(){
        System.out.println("Still got to do this part");
    }

}