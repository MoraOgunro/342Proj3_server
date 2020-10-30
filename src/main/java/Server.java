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
    HashMap<Integer,BaccaratGame> game;


    Server(Consumer<Serializable> call){
        baccaratInfoHashMap = new HashMap<>();
        baccaratInfoHashMap.put(1,new BaccaratInfo());
        baccaratInfoHashMap.put(2,new BaccaratInfo());
        game = new HashMap<>();
        game.put(1, new BaccaratGame());
        game.put(2, new BaccaratGame());
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

            while(true) {
                try {
                    BaccaratInfo incomingFromClientData = (BaccaratInfo) in.readObject();

                    baccaratInfoHashMap.replace(clientCount,incomingFromClientData);
                    System.out.println(this.getName());
                    //TODO receive all needed data into game
                    game.get(clientCount).currentBet =  baccaratInfoHashMap.get(clientCount).bet;
                    game.get(clientCount).betOn = baccaratInfoHashMap.get(clientCount).betOn;
                    game.get(clientCount).totalWinnings = baccaratInfoHashMap.get(clientCount).playerTotal;
                    //TODO make game calculations
                    game.get(clientCount).dealBothHands();
                    game.get(clientCount).evaluateWinnings();
                    //TODO store all data in baccaratInfo
                    baccaratInfoHashMap.get(clientCount).totalEarnings = (int) game.get(clientCount).totalWinnings;
                    baccaratInfoHashMap.get(clientCount).playerTotal = BaccaratGameLogic.handTotal(game.get(clientCount).playerHand);
                    baccaratInfoHashMap.get(clientCount).bankerTotal = BaccaratGameLogic.handTotal(game.get(clientCount).bankerHand);
                    baccaratInfoHashMap.get(clientCount).whoWon = game.get(clientCount).whoWon;
                    baccaratInfoHashMap.get(clientCount).eval = "You bet " + game.get(clientCount).betOn + " and " + game.get(clientCount).whoWon + " won";
                    baccaratInfoHashMap.get(clientCount).playingAnotherHand = false;
                    baccaratInfoHashMap.get(clientCount).playerCards = game.get(clientCount).playerHand;
                    baccaratInfoHashMap.get(clientCount).bankerCards = game.get(clientCount).bankerHand;
                    //TODO send baccaratInfo back
                    updateGameInfo();
                    syncClient();


                }
                catch(Exception e) {
                    updateCount();
                    updateConnectionStatus(clientCount,"Disconnected");
                    baccaratInfoHashMap.get(clientCount).clear();
                    updateGameInfo();
                    clients.remove(this);
                    try {
                        connection.shutdownInput();
                        connection.shutdownOutput();
                        connection.close();

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    this.currentThread().interrupt();
                    break;
                }
            }
        }//end of run
    }//end of client thread
}