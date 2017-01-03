import java.net.*;
import java.io.*;

import static java.lang.Integer.parseInt;

public class BankServerThread extends Thread{

    private Socket ServerSocket;
    private String idName;
    private Account account;
    private PrintWriter out;


    public BankServerThread(Socket serverSocket, String newThreadName){
        super(newThreadName);
        this.idName = newThreadName;
        this.ServerSocket = serverSocket;
    }

    public void run(){
    //Create the account that I'm assigning to the thread
        String tryName;

        try{
            this.out = new PrintWriter(this.ServerSocket.getOutputStream(), true);}
        catch(IOException e){
            e.printStackTrace();
        }

        System.out.println(BankServerThread.currentThread() + " initialising...");

//attempts to rename the current thread
//        try{
//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//            out.println("What is the name of your account? ");
//            tryName = in.readLine();
//            idName = tryName;
//
//        }catch  (IOException e) {
//            e.printStackTrace();
//        }

        //assigns the current thread to an account
        Account account = new Account(idName, this.ServerSocket);
        this.account = account;

            //account.setAccountId(idName);
            out.println("You are logged in as: " +  account.getAccountId());
            Menu();
    }

    public synchronized void doBalance(){

        account.setLock();
        out.println("Account "  + account.getAccountId() + " has " + account.getBalance());
        account.setRelease();
        Menu();
    }

    private synchronized void doDeposit(){

        double depositAmount;
        double newBalance;
        out.println("How much would you like to deposit?");

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(ServerSocket.getInputStream()));
            account.setLock();
            depositAmount = Double.parseDouble(in.readLine());
            newBalance = account.getBalance() + depositAmount;
            account.setBalance(newBalance);
            out.println("You have deposited " + depositAmount + " into account " + account.getAccountId() +
                                        " Your balance has been updated to " + this.account.getBalance());
            account.setRelease();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Menu();
    }

    private synchronized void doWithdraw(){

        double withdrawAmount;
        double newBalance;
        out.println("How much would you like to withdraw?");

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(ServerSocket.getInputStream()));
            account.setLock();
            withdrawAmount = Double.parseDouble(in.readLine());
            newBalance = account.getBalance() - withdrawAmount;
            account.setBalance(newBalance);
            out.println("You have withdrawn " + withdrawAmount + " from account " + account.getAccountId() +
                                            " Your balance has been updated to " + this.account.getBalance());
            account.setRelease();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Menu();
    }

    private synchronized void doTransfer(){

        //give unique names to users
        //display all active users
        //ask which user they want to send money too and take input
        //ask how much they want to send
        //take money out of current user account balance
        //update balance
        //add money to selected users account balance
        //update balance

        Menu();
    }

    private synchronized void Menu(){

        try{
            PrintWriter out = new PrintWriter(ServerSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(ServerSocket.getInputStream()));
            int inputLine;

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

}