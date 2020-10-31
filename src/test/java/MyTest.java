import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.XMLFormatter;

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
	}
	@Test
	void evaluateWinnings2(){
		BaccaratGame game = new BaccaratGame();
		//Bet = player, winner = player
		game.betOn = "Player";
		game.currentBet = 5;
		game.playerHand.add(new Card("Clubs", 1));
		game.playerHand.add(new Card("Clubs", 2));
		game.bankerHand.add(new Card("Clubs", 4));
		game.bankerHand.add(new Card("Clubs", 1));
		assertEquals(-5,game.evaluateWinnings());
	}

	@Test
	void gameConstructor(){
		BaccaratGame game = new BaccaratGame();
		//Bet = player, winner = player
		assertEquals(0, game.currentBet);
		assertEquals(0,game.totalWinnings);
		assertEquals("", game.betOn);
		assertEquals("", game.whoWon);
		assertEquals(52,game.theDealer.deck.size());
	}
	@Test
	void serverConstructor(){
		ConnectionInfo.setPORT(5555);
		Server serverConnection = new Server(data -> {
			Platform.runLater(() -> {
			});
		});

		assertEquals(0, serverConnection.count);
		assertEquals(2,serverConnection.baccaratInfoHashMap.size());
		assertEquals(2,serverConnection.game.size());
	}
	@Test
	void baccaratInfoConstructor(){
		BaccaratInfo bac = new BaccaratInfo();
		assertEquals(0,bac.playerTotal);
		assertEquals(0,bac.bankerTotal);
		assertEquals(0,bac.totalEarnings);
		assertEquals("",bac.whoWon);
		assertEquals("",bac.eval);
		assertEquals(0,bac.numOfClients);
		assertEquals("Disconnected",bac.clientConnected);
	}
	@Test
	void connectionInfoConstructor(){
		ConnectionInfo c = new ConnectionInfo();
		assertEquals(0,c.getPORT());
	}
	@Test
	void generateDeck2(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		assertEquals(1,dealer.deck.get(0).value);
	}
	@Test
	void shuffleDeck2(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		dealer.shuffleDeck();
		assertEquals(52,dealer.deck.size());
	}
	@Test
	void deckSize2(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.shuffleDeck();
		dealer.drawOne();
		assertEquals(51,dealer.deck.size());
	}
	@Test
	void drawOne2(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		dealer.shuffleDeck();

		ArrayList<Card> c = new ArrayList<>();
		c.add(dealer.drawOne());
		c.add(dealer.drawOne());
		assertEquals(50,dealer.deck.size());
		assertEquals(2,c.size());
	}
	@Test
	void dealHand2(){
		BaccaratDealer dealer = new BaccaratDealer();
		dealer.generateDeck();
		dealer.shuffleDeck();

		ArrayList<Card> c = dealer.dealHand();
		dealer.dealHand();
		assertEquals(48,dealer.deck.size());
		assertEquals(2,c.size());
	}
	@Test
	void handTotal2(){
		BaccaratGameLogic b = new BaccaratGameLogic();
		ArrayList<Card> c = new ArrayList<>();
		c.add(new Card("Clubs", 5));
		c.add(new Card("Clubs", 1));
		c.add(new Card("Clubs", 9));
		assertEquals(5, b.handTotal(c));

	}
	@Test
	void whoWon2(){
		BaccaratGameLogic b = new BaccaratGameLogic();
		ArrayList<Card> c1 = new ArrayList<>();
		ArrayList<Card> c2 = new ArrayList<>();
		c1.add(new Card("Clubs", 9));
		c1.add(new Card("Clubs", 0));


		c2.add(new Card("Clubs", 1));
		c2.add(new Card("Clubs", 2));
		c2.add(new Card("Clubs", 3));

		assertEquals("Player", b.whoWon(c1,c2));
	}
	@Test
	void evaluateBankerDraw2(){
		BaccaratGameLogic gamelogic = new BaccaratGameLogic();
		ArrayList<Card> c1 = new ArrayList<>();

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
	void evaluatePlayerDraw2(){
		BaccaratGameLogic gamelogic = new BaccaratGameLogic();
		ArrayList<Card> c1 = new ArrayList<>();

		c1.add(new Card("Clubs", 9));
		c1.add(new Card("Clubs", 9));
		assertFalse(gamelogic.evaluatePlayerDraw(c1));
		c1.clear();
		c1.add(new Card("Clubs", 6));
		c1.add(new Card("Clubs", 0));
		assertFalse(gamelogic.evaluatePlayerDraw(c1));
	}
}
