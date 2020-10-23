import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

}
