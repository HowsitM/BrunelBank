import java.io.IOException;
import java.net.ServerSocket;

public class BankServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        int serverPort = 4545;

        try{ //try creating the server on port
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server started on server port:" + serverPort);
        }
        catch (IOException e){
            System.err.println("Could not create port on port:" + serverPort);
        }
        //create a new thread/user when a new client has connected.
        new Database(); //creating the database
        System.out.println("Database has been created");

        while(true){

            for (int i =1; i <= 100;i++) {
                new BankServerThread(serverSocket.accept(), Integer.toString(i)).start();
                Users.getActiveUsers();
            }
        }
    }
}
