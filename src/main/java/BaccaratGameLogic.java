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
        return (total % 10);
    }
    public static boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard){
        int bankerTotal = handTotal(hand);
        if(bankerTotal > 6){
            return false;
        }
        if(bankerTotal <= 2){
            return true;
        }else if (bankerTotal <= 6){
            if(bankerTotal == 3 ){
                return true;
            }
            if(playerCard == null){
                if(bankerTotal <=5){
                    return true;
                }
            }
            switch (playerCard.value){
                case 9:
                case 8:
                case 1:
                case 0:
                    return false;
                case 7:
                case 6:
                    if((bankerTotal == 6) ||(bankerTotal == 5) ||(bankerTotal == 4)){
                        return true;
                    }
                    return false;
                case 5:
                case 4:
                    if((bankerTotal == 5) ||(bankerTotal == 4)){
                        return true;
                    }
                    return false;
                case 3:
                case 2:
                    if(bankerTotal == 4){
                        return true;
                    }
                    return false;
                default:
                    return false;
            }
        }
        return false;
    }
    public static boolean evaluatePlayerDraw(ArrayList<Card> hand){
        if(handTotal(hand) <=5){
            return true;
        }
        return false;
    }

}
