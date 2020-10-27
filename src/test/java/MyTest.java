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
	@Test
	void handTotal(){
		BaccaratGameLogic b = new BaccaratGameLogic();
		ArrayList<Card> c = new ArrayList<>();
		c.add(new Card("Clubs", 5));
		c.add(new Card("Clubs", 1));
		c.add(new Card("Clubs", 5));
		assertEquals(1, b.handTotal(c));
	}
	@Test
	void whoWon(){
		BaccaratGameLogic b = new BaccaratGameLogic();
		ArrayList<Card> c1 = new ArrayList<>();
		ArrayList<Card> c2 = new ArrayList<>();
		c1.add(new Card("Clubs", 5));
		c1.add(new Card("Clubs", 1));
		c1.add(new Card("Clubs", 5));

		c2.add(new Card("Clubs", 1));
		c2.add(new Card("Clubs", 2));
		c2.add(new Card("Clubs", 3));

		assertEquals("Banker", b.whoWon(c1,c2));
	}
	@Test
	void evaluateBankerDraw(){
		BaccaratGameLogic gamelogic = new BaccaratGameLogic();
		ArrayList<Card> c1 = new ArrayList<>();
		c1.add(new Card("Clubs", 5));
		c1.add(new Card("Clubs", 1));
		assertTrue(gamelogic.evaluateBankerDraw(c1,new Card("Clubs",6)));
		c1.clear();
		c1.add(new Card("Clubs", 3));
		c1.add(new Card("Clubs", 1));
		assertTrue(gamelogic.evaluateBankerDraw(c1,new Card("Clubs",3)));
		c1.clear();
		c1.add(new Card("Clubs", 2));
		c1.add(new Card("Clubs", 1));
		assertTrue(gamelogic.evaluateBankerDraw(c1,new Card("Clubs",9)));
		c1.clear();
		c1.add(new Card("Clubs", 6));
		c1.add(new Card("Clubs", 1));
		assertFalse(gamelogic.evaluateBankerDraw(c1,new Card("Clubs",6)));
		c1.clear();
		c1.add(new Card("Clubs", 4));
		c1.add(new Card("Clubs", 1));
		assertFalse(gamelogic.evaluateBankerDraw(c1,new Card("Clubs",1)));
		c1.clear();
		c1.add(new Card("Clubs", 2));
		c1.add(new Card("Clubs", 2));
		assertFalse(gamelogic.evaluateBankerDraw(c1,new Card("Clubs",0)));
	}

	@Test
	void evaluatePlayerDraw(){
		BaccaratGameLogic gamelogic = new BaccaratGameLogic();
		ArrayList<Card> c1 = new ArrayList<>();
		c1.add(new Card("Clubs", 9));
		c1.add(new Card("Clubs", 6));
		assertTrue(gamelogic.evaluatePlayerDraw(c1));
		c1.clear();
		c1.add(new Card("Clubs", 2));
		c1.add(new Card("Clubs", 1));
		assertTrue(gamelogic.evaluatePlayerDraw(c1));
		c1.clear();
		c1.add(new Card("Clubs", 9));
		c1.add(new Card("Clubs", 9));
		assertFalse(gamelogic.evaluatePlayerDraw(c1));
		c1.clear();
		c1.add(new Card("Clubs", 6));
		c1.add(new Card("Clubs", 0));
		assertFalse(gamelogic.evaluatePlayerDraw(c1));
	}
	@Test
	void evaluateWinnings(){
		BaccaratGame game = new BaccaratGame();
		//Bet = player, winner = player
		game.betOn = "Player";
		game.currentBet = 5;
		game.playerHand.add(new Card("Clubs", 5));
		game.playerHand.add(new Card("Clubs", 4));
		game.bankerHand.add(new Card("Clubs", 1));
		game.bankerHand.add(new Card("Clubs", 1));
		assertEquals(5,game.evaluateWinnings());
		//Bet = player, winner = banker
		//Bet = player, winner = draw
		//Bet = banker, winner = player
		//Bet = banker, winner = banker
		//Bet = banker, winner = draw
		//Bet = banker, winner = player
		//Bet = banker, winner = banker
		//Bet = banker, winner = draw
	}

}
