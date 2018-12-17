import org.junit.Before;
import org.junit.Test;

import java.util.List;


import freecell.model.Cards;
import freecell.model.CascadePile;
import freecell.model.FoundationPile;
import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;
import freecell.model.OpenPile;
import freecell.model.Pile;

import static org.junit.Assert.assertEquals;

public class PileTest {

  private Pile foundationPiletest;
  private Pile cascadePileTest;
  private Pile openPileTest;

  /**
   * Sets up the required variables.
   */
  @Before
  public void setup() {
    foundationPiletest = new FoundationPile();
    FreecellOperations<Cards> freecellGameMoveTest = FreecellModel.getBuilder().opens(8)
            .cascades(13).build();
    List<Cards> newDeck = freecellGameMoveTest.getDeck();
    cascadePileTest = new CascadePile(13, newDeck);
    openPileTest = new OpenPile(8);
  }

  @Test
  public void testGetPileState() {
    assertEquals(emptyFoundationString.toString(), foundationPiletest.toString());
    assertEquals(initialCascadeString.toString(), cascadePileTest.toString());
    assertEquals(initialOpenString.toString(), openPileTest.toString());
  }

  @Test
  public void testGetPileLength() {
    for (int i = 0; i < 4; i++) {
      assertEquals(0, foundationPiletest.getPileLength(i));
    }

  }

  @Test
  public void testMoveFrom() {
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < 13; i++) {
        cascadePileTest.moveTo(i, 3 - j, openPileTest, 0);
        openPileTest.moveTo(0, 0, foundationPiletest, j);
      }

    }
    assertEquals(finalFoundationString.toString(), foundationPiletest.toString());
    assertEquals(emptyCascadeString.toString(), cascadePileTest.toString());
    assertEquals(initialOpenString.toString(), openPileTest.toString());
  }

  private StringBuilder finalFoundationString = new StringBuilder("F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, " +
          "7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
          "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
          "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
          "F4: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n");
  private StringBuilder emptyCascadeString = new StringBuilder("C1:\n" +
          "C2:\n" +
          "C3:\n" +
          "C4:\n" +
          "C5:\n" +
          "C6:\n" +
          "C7:\n" +
          "C8:\n" +
          "C9:\n" +
          "C10:\n" +
          "C11:\n" +
          "C12:\n" +
          "C13:");
  private StringBuilder emptyFoundationString = new StringBuilder("F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n");
  private StringBuilder initialCascadeString = new StringBuilder("C1: A♥, A♦, A♠, A♣\n" +
          "C2: 2♥, 2♦, 2♠, 2♣\n" +
          "C3: 3♥, 3♦, 3♠, 3♣\n" +
          "C4: 4♥, 4♦, 4♠, 4♣\n" +
          "C5: 5♥, 5♦, 5♠, 5♣\n" +
          "C6: 6♥, 6♦, 6♠, 6♣\n" +
          "C7: 7♥, 7♦, 7♠, 7♣\n" +
          "C8: 8♥, 8♦, 8♠, 8♣\n" +
          "C9: 9♥, 9♦, 9♠, 9♣\n" +
          "C10: 10♥, 10♦, 10♠, 10♣\n" +
          "C11: J♥, J♦, J♠, J♣\n" +
          "C12: Q♥, Q♦, Q♠, Q♣\n" +
          "C13: K♥, K♦, K♠, K♣");
  private StringBuilder initialOpenString = new StringBuilder("O1:\n" +
          "O2:\n" +
          "O3:\n" +
          "O4:\n" +
          "O5:\n" +
          "O6:\n" +
          "O7:\n" +
          "O8:\n");
}