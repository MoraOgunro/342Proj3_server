import java.io.Serializable;

public class BaccaratInfo implements Serializable {
    int playerTotal;
    int bankerTotal;
    String whoWon;
    String eval;
    int numOfClients;

    BaccaratInfo() {
        playerTotal = 0;
        bankerTotal = 0;
        whoWon = "";
        eval = "";
        numOfClients = 0;
    }
}
