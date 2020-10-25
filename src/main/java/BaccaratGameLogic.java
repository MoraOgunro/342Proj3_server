import java.util.ArrayList;

public class BaccaratGameLogic {
    public static String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2){
        int playerPoints = handTotal(hand1);;
        int bankerPoints = handTotal(hand2);

        if(playerPoints > bankerPoints){
            return "Player";
        }else if(playerPoints == bankerPoints){
            return "Draw";
        }else {
            return "Banker";
        }
    }
    public static int handTotal(ArrayList<Card> hand){
        int total = 0;
        for(Card c: hand){
            total += c.value;
        }
        return total;
    }
    public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard){
        //TODO return true if banker should be dealt a third card, otherwise return false.
        return false;
    }
    public static boolean evaluatePlayerDraw(ArrayList<Card> hand){
        //TODO return true if player should be dealt a third card, otherwise return false.
        return false;
    }

}
