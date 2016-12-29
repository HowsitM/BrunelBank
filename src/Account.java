import java.net.Socket;

public class Account {

    private String AccountName;
    private double Balance;
    private Socket Socket;

    public Account(String threadName, Socket socket){

        this.AccountName = threadName;
        this.Balance = 100;
        this.Socket = socket;
        System.out.println("A new account has been created, Name:" + this.AccountName + "Balance: " + this.Balance + "socket: " + this.Socket);
    }

    public String getName(){ return this.AccountName;}

    public synchronized double getBalance(){
        //lock the account, then check the balance
        //release the lock
        //notify other threads that the lock has been lifted.
        return this.Balance;
    }

    public void setBalance(double balance){
        this.Balance = balance;
    }

    public synchronized void setLock(){
    }

    public synchronized void setRelease(){
    }
}
