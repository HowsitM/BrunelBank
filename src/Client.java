import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) throws IOException{

        //set up a socket and in and out variables

        Socket BankClientSocket = null;
        PrintWriter out = null;
        BufferedReader in  = null;

        int port = 4545;

        //Attempts to create a connection to the Bank Server

        try {
            BankClientSocket = new Socket("localhost", port);
            out = new PrintWriter(BankClientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(BankClientSocket.getInputStream()));
            System.out.println("Connection established to server on port: " + port);
        } catch(UnknownHostException e){
            System.out.println("Error. Could not connect to the server on port: " + port);
            e.printStackTrace();
            System.exit(1);
        }

        //inputs received from the user is added to an arraylist

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        List<String> outPut = new ArrayList<>();
        String fromUser, fromServer;

        while((fromServer = in.readLine()) != null){
            if(fromServer.isEmpty()){
                for(String s : outPut){
                    System.out.println(s);
                }
                outPut.clear();
            }
            if(!in.ready()){
                System.out.println(fromServer);
                fromUser = stdIn.readLine();

                if (!fromUser.equals("")){

                }
                else{ outPut.add(fromServer);}
            }
        }

        //the connection is closed.

        out.close();
        in.close();
        stdIn.close();
        BankClientSocket.close();
    }
}