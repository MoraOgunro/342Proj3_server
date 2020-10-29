import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

    int count = 0;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;
    BaccaratInfo baccaratInfo;


    Server(Consumer<Serializable> call){
        baccaratInfo = new BaccaratInfo();
        baccaratInfo.numOfClients = count;
        callback = call;
        server = new TheServer();
        server.start();
    }
    void updateCount(){
        count--;
    }
    void updateGameInfo(){
        baccaratInfo.numOfClients = count;
        callback.accept(baccaratInfo);
    }


    public class TheServer extends Thread{

        public void run() {
            try(ServerSocket mysocket = new ServerSocket(5555,2)){
                System.out.println("Server is waiting for a client!");

                while(true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count+1);
                    count++;
                    updateGameInfo();
                    clients.add(c);
                    c.start();
                }
            }//end of try
            catch(Exception e) {
                System.out.println("Server socket did not launch");
            }
        }//end of while
    }

    class ClientThread extends Thread{

        Socket connection;
        int clientCount;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.clientCount = count;
        }

        public void updateClients(String message) {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(message);
                }
                catch(Exception e) {}
            }
        }

        public void send(BaccaratInfo data) {

            try {
                out.writeObject(data);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            //updateClients("new client on server: client #"+count);


            while(true) {
                try {
                    String data = in.readObject().toString();
                    //callback.accept("client: " + clientCount + " sent: " + data);
                    //updateClients("client #"+clientCount+" said: "+data);

                }
                catch(Exception e) {
                    updateCount();
                    updateGameInfo();
                    //send(count);
                    //updateClients("Client #"+clientCount+" has left the server!");
                    clients.remove(this);
                    break;
                }
            }
        }//end of run
    }//end of client thread
}