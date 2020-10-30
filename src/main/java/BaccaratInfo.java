import java.io.Serializable;
import java.util.ArrayList;

public class BaccaratInfo implements Serializable {
    int playerTotal;
    int bankerTotal;
    String whoWon;
    String eval;
    int numOfClients;
    String clientConnected;
    int bet;
    boolean playingAnotherHand;
    String betOn;
    ArrayList<Card> playerCards;
    ArrayList<Card> bankerCards;
    int totalEarnings;

    BaccaratInfo() {
        playerTotal = 0;
        bankerTotal = 0;
        totalEarnings = 0;
        whoWon = "";
        eval = "";
        numOfClients = 0;
        clientConnected = "Disconnected";
        bet = 0;
        playingAnotherHand = false;
        betOn = "";
        playerCards = new ArrayList<>();
        bankerCards = new ArrayList<>();

    }
    void clear(){
        playerTotal = 0;
        bankerTotal = 0;
        totalEarnings = 0;
        whoWon = "";
        eval = "";
        numOfClients = 0;
        bet = 0;
        playingAnotherHand = false;
        betOn = "";
        playerCards.clear();
        bankerCards.clear();
    }
}
