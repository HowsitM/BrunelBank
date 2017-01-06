import java.net.*;
import java.io.*;

import static java.lang.Integer.parseInt;

    //BankServerThread extends thread creates a new thread once a client has connected.

    public class BankServerThread extends Thread{

    private Socket ServerSocket;
    private String idName;
    private Account account;
    private PrintWriter out;

        // -This constructor names the thread which corresponds to the variables passed through from BankServer.


    public BankServerThread(Socket serverSocket, String newThreadName){
        super(newThreadName);
        this.idName = newThreadName;
        this.ServerSocket = serverSocket;
    }

        // -Once the thread has been created run() starts the thread.
        // -This is where the magic happens.
        // -A new account object is created passing the thread name (idName) and the Socket object (ServerSocket) as parameters.
        // -A list of the currently active users is then displayed from the Users class.
        // -The menu is then displayed.

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
        out.println("You are logged in as: " +  account.getAccountId() + "\r");
        Menu();
    }

        // - doBalance() will lock the account object that is currently accessing this function.
        // - The balance is then accessed from the account class through the getBalance() method.
        // - Once the balance has been returned the account object is released.
        // - The keyword synchronized is there so that the method can not be accessed by two threads at the same time

    public synchronized void doBalance(){

        account.setLock();
        out.println("Account "  + account.getAccountId() + " has " + account.getBalance());
        account.setRelease();
        Menu();
    }


        // -doDeposit() takes in an amount from the user creates a newBalance variable passing it to the account.setBalance()
        // method as a parameter which updates the database.
        // -As soon as the user has selected the menu option for deposit the account is locked to avoid concurrency issues with
        //   other accounts. Once the update has occurred the lock is released on the account. And the menu is displayed again.

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
                                        " Your balance has been updated to " + Database.getAccountBalance(account.getAccountId())+"\r");
            account.setRelease();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Menu();
    }

        // -doWithdraw() takes in an amount from the user creates a newBalance variable passing it to the account.setBalance()
        // method as a parameter which updates the database.
        // -As soon as the user has selected the menu option for Withdraw the account is locked to avoid concurrency issues with
        //   other accounts. Once the update has occurred the lock is released on the account. And the menu is displayed again.

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

        // -doTransfer() will lock the current account and then ask the user who they want to transfer to and how much.
        // -Once the account name of the receiver is know that account is returned from the Database class and stored for the duration of the method
        // -The amount is then subtracted from the current account and the balance is updated
        // -The amount is then added to the receivers account and the balance is updated.
        // -Once the update has occurred both locks are released for each account.

    private synchronized void doTransfer(){

        Account currentAccount = account;
        String receiverAccountName;
        Account receiverAccount;
        Double transferAmount;
        Double currentAccountNewBalance;
        Double receiverAccountNewBalance;

        //Lock the account you are using

        out.println("Who would you like to send money to?\n");
        Users.getActiveUsers();
        currentAccount.setLock();
        try{

            BufferedReader in = new BufferedReader(new InputStreamReader(ServerSocket.getInputStream()));

            receiverAccountName = in.readLine(); //who you are sending it too
            receiverAccount = Database.getAccount(receiverAccountName);
            receiverAccount.setLock();

            out.println("How much would you like to send them?");
            transferAmount = Double.parseDouble(in.readLine());

            currentAccountNewBalance = currentAccount.getBalance() - transferAmount;
            currentAccount.setBalance(currentAccountNewBalance);

            receiverAccountNewBalance = receiverAccount.getBalance() + transferAmount;
            receiverAccount.setBalance(receiverAccountNewBalance);

            out.println("You have sent " + transferAmount + " to " + receiverAccount.getAccountId()
                    + ". Your balance is now" + currentAccount.getBalance());

            currentAccount.setRelease();
            System.out.println("Current account " + currentAccount.getAccountId() + " has been released");
            receiverAccount.setRelease();
            System.out.println("Receiver account " + receiverAccount.getAccountId() + " has been released");

        }catch(IOException  | NullPointerException e){
            e.printStackTrace();
        }

        Menu();
    }


        //Menu() simply displays and asks the user for input for the actions they want to take.
        //Based on the users choice the corresponding method is run.

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
                default: Menu(); break;
            }

        }catch (Exception e){
            System.err.println(e);
        }
    }
}