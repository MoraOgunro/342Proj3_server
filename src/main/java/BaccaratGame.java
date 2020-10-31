import java.util.ArrayList;

public class BaccaratGame {
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BaccaratDealer theDealer;
    double currentBet;
    double totalWinnings;

    String betOn;
    String whoWon;

    BaccaratGame(){
        theDealer = new BaccaratDealer();
        theDealer.generateDeck();
        theDealer.shuffleDeck();
        currentBet = 0;
        totalWinnings = 0;
        betOn = "";
        playerHand = new ArrayList<>();
        bankerHand = new ArrayList<>();
        whoWon = "";
    }

    public double evaluateWinnings(){
        whoWon = BaccaratGameLogic.whoWon(playerHand, bankerHand);
        if((betOn.equals("Player")) && (whoWon.equals("Player"))){
            totalWinnings+=currentBet;
            return currentBet;
        }else if((betOn.equals("Banker")) && (whoWon.equals("Banker"))){
            totalWinnings+=currentBet;
            return currentBet;
        }else if ((betOn.equals("Draw")) && (whoWon.equals("Draw"))){
            totalWinnings+=currentBet;
            return currentBet;
        }
        totalWinnings-=currentBet;
        return (-currentBet);
    }
    public void dealBothHands(){
        Card c = null;
        playerHand = theDealer.dealHand();
        bankerHand = theDealer.dealHand();
        if(BaccaratGameLogic.evaluatePlayerDraw(playerHand)){
            playerHand.add(theDealer.drawOne());
        }
        if(playerHand.size() == 3){
            c = playerHand.get(2);
        }
        if(BaccaratGameLogic.evaluateBankerDraw(bankerHand,c)){
            bankerHand.add(theDealer.drawOne());
        }

        theDealer.generateDeck();
        theDealer.shuffleDeck();
    }

}
