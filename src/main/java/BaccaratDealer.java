import java.util.ArrayList;
import java.util.Random;

public class BaccaratDealer {
    ArrayList<Card> deck = new ArrayList<>();

    public void generateDeck(){
        //TODO generate a new standard 52 card deck where each card is an instance of the Card class in the ArrayList<Card> deck
        deck.clear();
        generateDeckHelper("Clubs");
        generateDeckHelper("Diamonds");
        generateDeckHelper("Hearts");
        generateDeckHelper("Spades");
    }
    public ArrayList<Card> dealHand(){
        //TODO deal two cards and return them in an ArrayList<Card>.
        return null;
    }
    public Card drawOne(){
        //TODO deal a single card and return it.

        return null;
    }
    public void shuffleDeck(){
        //TODO create a new deck of 52 cards and “shuffle”
    }
    public int deckSize(){
        //TODO return how many cards are in this.deck at any given time
        return 0;
    }

    private void generateDeckHelper(String suite){
        //ace
        deck.add(new Card(suite,1));

        for (int i = 2; i < 11; i++) {
            deck.add(new Card(suite,i));
        }
        //jack
        deck.add(new Card(suite, 11));
        //queen
        deck.add(new Card(suite, 12));
        //king
        deck.add(new Card(suite, 13));
    }

}

