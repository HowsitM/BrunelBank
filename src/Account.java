import java.net.Socket;

public class Account {

    private String AccountId;
    private double Balance;
    private Socket Socket;
    private int iD;

    Account(String newThreadName, Socket socket){

        this.AccountId = newThreadName;
        this.Balance = 100;
        this.Socket = socket;
        Database.createAccount(this.AccountId,Balance);

        //Database.createAccount(this.AccountId, this.Balance);

        //System.out.println("A new account has been created, Name: " + AccountId + " Balance: "
          //      + Balance + " Socket: " + Socket + " Thread: " + BankServerThread.currentThread().getId());
        //Users.addActiveUsers(AccountId);
    }

    Account(int id, String accountId, Double balance){

        this.AccountId = accountId;
        this.Balance = balance;
        this.iD = id;
    }

    String getAccountId(){
        return AccountId;
    }

    public synchronized double getBalance(){
        if(BankState.aquireLock(AccountId)){
            System.out.println("Lock acquired for account " + AccountId);
            this.Balance =  Database.getAccountBalance(AccountId);
            return this.Balance;
        } else {
            System.out.println("Lock could not be acquired for account before getting the balance");
            return Balance;
        }
    }

    public synchronized void setBalance(double balance){

        if (BankState.aquireLock(AccountId)){
            this.Balance = balance;
            Database.setAccountBalance(this.AccountId, this.Balance);
            System.out.println(AccountId + " Balance has been updated");
        }
        else{
            System.out.println(AccountId + " could not be updated while the account was locked.");
        }
    }

    public synchronized void setLock(){
        try
        {
            while(!BankState.aquireLock(AccountId)){
                System.out.println("Attempting to acquire a lock on account " + AccountId);
                wait();
            }
            System.out.println(AccountId + " has acquired a lock");
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

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
