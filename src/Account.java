import java.net.Socket;

public class Account {

    private String AccountId;
    private double Balance;
    private Socket Socket;

    Account(String threadName, Socket socket){

        this.AccountId = threadName;
        this.Balance = 100;
        this.Socket = socket;
        System.out.println("A new account has been created, Name: " + AccountId + " Balance: "
                + Balance + " Socket: " + Socket + " Thread: " + BankServerThread.currentThread().getId());
        Users.addActiveUsers(AccountId);
    }

    String getAccountId(){ return AccountId;}

    public synchronized double getBalance(){
        if(BankState.aquireLock(AccountId)){
            System.out.println("Lock acquired for account " + AccountId);
            return Balance;
        } else {
            System.out.println("Lock could not be acquired for account before getting the balance");
            return 0;
        }
    }

    public void setBalance(double balance){

        if (BankState.aquireLock(AccountId)){
            this.Balance = balance;
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
