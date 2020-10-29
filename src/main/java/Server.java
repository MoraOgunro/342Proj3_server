import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

    int count = 0;
    int a = -99;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;
    HashMap<Integer,BaccaratInfo> baccaratInfoHashMap;


    Server(Consumer<Serializable> call){
        baccaratInfoHashMap = new HashMap<>();
        baccaratInfoHashMap.put(1,new BaccaratInfo());
        baccaratInfoHashMap.put(2,new BaccaratInfo());
        callback = call;
        server = new TheServer();
        server.start();
    }
    void updateCount(){
        count--;
    }
    void updateConnectionStatus(int clientNum, String status){
        baccaratInfoHashMap.get(clientNum).clientConnected = status;
    }
    void updateGameInfo(){
        callback.accept(baccaratInfoHashMap);
        //clients.get(0).syncClient();
    }


    public class TheServer extends Thread{

        public void run() {
            try(ServerSocket mysocket = new ServerSocket(5555,2)){
                System.out.println("Server is waiting for a client!");

                while(true) {
                    if(count < 3){
                        Socket socket = mysocket.accept();
                        if(baccaratInfoHashMap.get(1).clientConnected.equals("Disconnected")){
                            a = 1;
                        }else if (baccaratInfoHashMap.get(2).clientConnected.equals("Disconnected")){
                            a = 2;
                        }else{
                            break;
                        }
                        ClientThread c = new ClientThread(socket, a);
                        clients.add(c);
                        count++;
                        updateConnectionStatus(a,"Connected");
                        updateGameInfo();
                        //clients.get(0).syncClient();
                        c.start();
                    }
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

        public void syncClient() {
//            for(int i = 0; i < clients.size(); i++) {
//                ClientThread t = clients.get(i);
//                try {
//                    t.out.writeObject(message);
//                }
//                catch(Exception e) {}
//            }
            try{
                out.writeObject(baccaratInfoHashMap.get(this.clientCount));
                out.reset();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void send(BaccaratInfo data) {

            try {
                out.writeObject(data);
                out.reset();
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
                syncClient();
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            //updateClients("new client on server: client #"+count);


            while(true) {
                try {
                    BaccaratInfo incomingFromClientData = (BaccaratInfo) in.readObject();
                    //TODO update gui when client sends data back
                    //Determine what client's hashmap index it is from
                    //Update hashmap
                    //send with callback
                    baccaratInfoHashMap.replace(clientCount,incomingFromClientData);
                    System.out.println(this.getName());
                    updateGameInfo();
                    //updateClients("client #"+clientCount+" said: "+data);

                }
                catch(Exception e) {
                    updateCount();
                    updateConnectionStatus(clientCount,"Disconnected");
                    updateGameInfo();
                    //send(count);
                    clients.remove(this);
                    break;
                }
            }
        }//end of run
    }//end of client thread
}