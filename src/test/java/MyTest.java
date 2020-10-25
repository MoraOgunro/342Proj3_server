import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest {

	@Test
	void cardConstructor(){
		Card c = new Card("Hearts",7);
		assertEquals(c.value, 7);
		assertEquals(c.suite, "Hearts");
	}
	@Test
	void generateDeck(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		assertEquals(52,dealer.deck.size());
	}
	@Test
	void shuffleDeck(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.shuffleDeck();
		assertEquals(52,dealer.deck.size());
	}
	@Test
	void deckSize(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.shuffleDeck();
		assertEquals(52,dealer.deck.size());
	}
	@Test
	void drawOne(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		dealer.shuffleDeck();

		ArrayList<Card> c = new ArrayList<>();
		c.add(dealer.drawOne());
		assertEquals(51,dealer.deck.size());
		assertEquals(1,c.size());
	}
	@Test
	void dealHand(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		dealer.shuffleDeck();

		ArrayList<Card> c = dealer.dealHand();
		assertEquals(50,dealer.deck.size());
		assertEquals(2,c.size());
	}
}
