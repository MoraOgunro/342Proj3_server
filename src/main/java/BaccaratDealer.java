import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class BaccaratDealer {
    ArrayList<Card> deck = new ArrayList<>();

    public void generateDeck(){
        deck.clear();
        generateDeckHelper("Clubs");
        generateDeckHelper("Diamonds");
        generateDeckHelper("Hearts");
        generateDeckHelper("Spades");
    }
    public ArrayList<Card> dealHand(){
        ArrayList<Card> c = new ArrayList<>();
        c.add(drawOne());
        c.add(drawOne());
        return c;
    }
    public Card drawOne(){
        Random random = new Random();
        int index = random.nextInt(deck.size());
        Card card = deck.get(index);
        deck.remove(index);
        return card;
    }
    public void shuffleDeck(){
        deck.clear();
        generateDeckHelper("Clubs");
        generateDeckHelper("Diamonds");
        generateDeckHelper("Hearts");
        generateDeckHelper("Spades");
        Collections.shuffle(deck);
    }
    public int deckSize(){
        return deck.size();
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

