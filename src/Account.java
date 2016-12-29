import java.net.Socket;

public class Account {

    private String Name;
    private double Balance;
    private Socket Socket;

    public Account(String name, Socket socket){
        System.out.println("A new account has been created");
        this.Name = name;
        this.Balance = 100;
        this.Socket = socket;
    }

    public String getName(){ return this.Name;}

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
