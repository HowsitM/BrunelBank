import java.net.Socket;

public class Account {

    private String Name;
    private double Balance;
    private Socket Socket;

    public Account(String name, Socket socket){
        this.Name = name;
        this.Balance = 100;
        this.Socket = socket;
    }

    public String getName(){ return this.Name;}

    public synchronized double getBalance(){

        return this.Balance;
    }

    public void setBalance(double balance){

    }

    public synchronized void setLock(){

    }

    public synchronized void setRelease(){

    }
}
