import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 13/12/2016.
 */
public class Client {

    public static void main(String[] args) throws IOException{

        //set up a socket and in and out variables

        Socket BankClientSocket = null;
        PrintWriter out = null;
        BufferedReader in  = null;

        int port = 4545;

        try {
            BankClientSocket = new Socket("localhost", port);
            out = new PrintWriter(BankClientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(BankClientSocket.getInputStream()));

        } catch(UnknownHostException e){
            e.printStackTrace();
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        List<String> outPut = new ArrayList<>();
        String fromUser, fromsServer;

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

        out.close();
        in.close();
        stdIn.close();
        BankClientSocket.close();
    }
}