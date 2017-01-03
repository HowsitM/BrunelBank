import java.net.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class BankServerThread extends Thread{

    private Socket ServerSocket = null;
    private String idName;
    private Account account;

    public BankServerThread(Socket serverSocket, String idName){
        super(idName);
        this.idName = idName;
        this.ServerSocket = serverSocket;
    }
    private PrintWriter out;

    public void run(){
    //Create the account that I'm assigning to the thread

        try{
            this.out = new PrintWriter(this.ServerSocket.getOutputStream(), true);}
        catch(IOException e){
            e.printStackTrace();
        }


        System.out.println(BankServerThread.currentThread() + " initialising...");

        try{
            PrintWriter out = new PrintWriter(ServerSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(ServerSocket.getInputStream()));
            int inputLine;
            String outputLine;

            //assigns the current thread to an account
            Account account = new Account(BankServerThread.currentThread().getName(), this.ServerSocket);
            this.account = account;
            out.println("The Current users are: " +  account.getAccountId());

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

    public synchronized void doBalance(){
        account.setLock();
        out.println("Account "  + account.getAccountId() + " has " + account.getBalance());
        account.setRelease();
    }

    private synchronized double doDeposit(){
        System.out.println("Still got to do this part");
        return 0;
    }

    private synchronized double doWithdraw(){
        System.out.println("Still got to do this part");
        return 0;
    }

    private synchronized void doTransfer(){
        System.out.println("Still got to do this part");
    }

}