import java.net.*;
import java.io.*;
import java.util.HashMap;

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

        try{
            this.out = new PrintWriter(this.ServerSocket.getOutputStream(), true);}
        catch(IOException e){
            e.printStackTrace();
        }

        System.out.println(BankServerThread.currentThread() + " initialising...");

        //assigns the current thread to an account
        Account account = new Account(idName, this.ServerSocket);
        this.account = account;
        Users.addActiveUsers(idName);
        System.out.println("These are the active users "+ Users.getActiveUsers());
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
                                        " Your balance has been updated to " + this.account.getBalance()+"\r");
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

        String currentUser = this.idName;
        String transferToUser;
        double transferAmount = 0;

        out.println("Who would you like to send money to?\n"+ Users.getActiveUsers());
        Users.getActiveUsers();
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(ServerSocket.getInputStream()));
            transferToUser = in.readLine(); //who you are sending it too

            out.println("How much would you like to send them?");
            Double.parseDouble(in.readLine());

        }catch(IOException e){
            e.printStackTrace();
        }
        account.setLock();
        double newBalance = account.getBalance() - transferAmount;
        account.setBalance(newBalance);
        account.setRelease();

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