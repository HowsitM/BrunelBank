import java.net.Socket;

public class Account {

    private String AccountId;
    private double Balance;
    private Socket Socket;
    private int iD; //created but never used

    // - The main constructor for the Account class.

    Account(String newThreadName, Socket socket){

        this.AccountId = newThreadName;
        this.Balance = 100;
        this.Socket = socket;
        Database.createAccount(this.AccountId,Balance);
    }

    // - A constructor used by the Database class creating an account.

    Account(int id, String accountId, Double balance){

        this.AccountId = accountId;
        this.Balance = balance;
        this.iD = id;
    }

    // -getAccountId() returns the accountId when called. A getter is used to access private variables.

    String getAccountId(){
        return AccountId;
    }

    // - Checks to see if a lock can be acquired on the account object, if it can the balance is returned to the user.
    // - If it can't get the lock it returns the last known balance.

    public synchronized double getBalance(){

        if(BankState.acquireLock(AccountId)){
            System.out.println("Lock acquired for account " + AccountId);
            return  Database.getAccountBalance(AccountId);
        } else {
            System.out.println("Lock could not be acquired for account before getting the balance");
            return Balance;
        }
    }

    // - Checks to see if a lock can be acquired on the account object, if it can the balance is set by the user.
    // - If a lock could not be acquired the balance is not set.

    public synchronized void setBalance(double balance){

        if (BankState.acquireLock(AccountId)){
            this.Balance = balance;
            Database.setAccountBalance(this.AccountId, this.Balance);
            System.out.println(AccountId + " Balance has been updated");
        }
        else{
            System.out.println(AccountId + " could not be updated while the account was locked.");
        }
    }

    // - setLock() checks to see if a lock is on the account, if it is the lock is acquired.
    // - If the lock could not be acquired it waits until the lock can be achieved.

    public synchronized void setLock(){
        try
        {
            while(!BankState.acquireLock(AccountId)){
                System.out.println(" Attempting to acquire a lock on account " + AccountId);
                System.out.println("Waiting for a lock as someone else is accessing...");
                wait();
            }
            System.out.println(AccountId + " has acquired a lock");
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    // - setRelease() unlocks the thread, and notifies all other threads waiting on that specific account that the
    // - account has been released allowing them to try and lock it.

    public synchronized void setRelease(){
        try{
            BankState.unlock(AccountId);
            System.out.println(AccountId + "has released it's lock");
            notifyAll();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}