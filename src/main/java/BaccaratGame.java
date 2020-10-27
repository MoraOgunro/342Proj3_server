import java.util.ArrayList;

public class BaccaratGame {
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BaccaratDealer theDealer;
    double currentBet;
    double totalWinnings;

    String betOn;

    BaccaratGame(){
        theDealer = new BaccaratDealer();
        theDealer.generateDeck();
        theDealer.shuffleDeck();
        currentBet = 0;
        totalWinnings = 0;
        betOn = "";
        playerHand = new ArrayList<>();
        bankerHand = new ArrayList<>();
    }

    public double evaluateWinnings(){
        //TODO determine if the user won or lost their bet and return the amount won or lost based on the value in currentBet.
        if((betOn.equals("Player")) && (BaccaratGameLogic.whoWon(playerHand, bankerHand).equals("Player"))){
            totalWinnings+=currentBet;
            return currentBet;
        }else if((betOn.equals("Banker")) && (BaccaratGameLogic.whoWon(playerHand, bankerHand).equals("Banker"))){
            totalWinnings+=currentBet;
            return currentBet;
        }else if ((betOn.equals("Draw")) && (BaccaratGameLogic.whoWon(playerHand, bankerHand).equals("Draw"))){
            totalWinnings+=currentBet;
            return currentBet;
        }
        totalWinnings-=currentBet;
        return (-currentBet);
    }

}
