import java.io.IOException;
import java.net.ServerSocket;

public class BankServer {


    // -Where the program starts.
    // -A new ServerSocket object is created which is what clients will be connecting to.
    // -This method also creates the database where client information is stored.
    // -Each time a connection is detected the connection number is turned into the accountName
    //  which becomes the accountId.
    // A new thread is created for each connection to the ServerSocket.

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
