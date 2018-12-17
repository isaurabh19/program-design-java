import org.junit.Before;
import org.junit.Test;

import java.util.List;

import freecell.model.Cards;
import freecell.model.FreecellMultiMoveModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FreecellMultiMoveModelTest {
  private FreecellOperations<Cards> freecellGameLocal;

  /**
   * Set up object for ease.
   */
  @Before
  public void setup() {
    FreecellOperations<Cards> defaultFreecellGame;

    defaultFreecellGame = FreecellMultiMoveModel.getBuilder().build();

  }

  @Test
  public void notEnoughEmptyPiles() {
    freecellGameLocal = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    List<Cards> newDeck = freecellGameLocal.getDeck();
    assertEquals("", freecellGameLocal.getGameState());
    freecellGameLocal.startGame(newDeck, false);
    assertEquals(initialGameStateForO1C13, freecellGameLocal.getGameState());

    freecellGameLocal.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, 0);
    freecellGameLocal.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 1);
    freecellGameLocal.move(PileType.CASCADE, 0, 1, PileType.FOUNDATION, 2);
    freecellGameLocal.move(PileType.CASCADE, 1, 3, PileType.FOUNDATION, 0);

    freecellGameLocal.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 1);
    freecellGameLocal.move(PileType.CASCADE, 2, 3, PileType.FOUNDATION, 0);
    freecellGameLocal.move(PileType.CASCADE, 2, 2, PileType.OPEN, 0);

    freecellGameLocal.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 2);
    freecellGameLocal.move(PileType.CASCADE, 3, 3, PileType.CASCADE, 0);

    initialGameStateForO1C13 = initialGameStateForO1C13
            .replace("F1:\n", "F1: A♣, 2♣, 3♣\n");
    initialGameStateForO1C13 = initialGameStateForO1C13
            .replace("F2:\n", "F2: A♠\n");
    initialGameStateForO1C13 = initialGameStateForO1C13
            .replace("F3:\n", "F3: A♦\n");
    initialGameStateForO1C13 = initialGameStateForO1C13
            .replace("O1:\n", "O1: 3♠\n");
    initialGameStateForO1C13 = initialGameStateForO1C13
            .replace("C1: A♥, A♦, A♠, A♣", "C1: 4♣");
    initialGameStateForO1C13 = initialGameStateForO1C13
            .replace("C2: 2♥, 2♦, 2♠, 2♣", "C2: 2♥, 2♦");
    initialGameStateForO1C13 = initialGameStateForO1C13
            .replace("C3: 3♥, 3♦, 3♠, 3♣", "C3: 3♥, 3♦, 2♠, A♥");
    initialGameStateForO1C13 = initialGameStateForO1C13
            .replace("C4: 4♥, 4♦, 4♠, 4♣", "C4: 4♥, 4♦, 4♠");

    assertEquals(initialGameStateForO1C13, freecellGameLocal.getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, 2, 1, PileType.CASCADE, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Not enough empty Piles.", e.getMessage());
      assertEquals(initialGameStateForO1C13, freecellGameLocal.getGameState());
    }
  }

  private String initialGameStateForO1C13 = "F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1:\n" +
          "C1: A♥, A♦, A♠, A♣\n" +
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
          "C13: K♥, K♦, K♠, K♣";

  @Test
  public void testSuccessfulMultiMove1() {
    freecellGameLocal = FreecellMultiMoveModel.getBuilder().opens(1).cascades(52).build();
    List<Cards> newDeck = freecellGameLocal.getDeck();
    assertEquals("", freecellGameLocal.getGameState());
    freecellGameLocal.startGame(newDeck, false);
    assertEquals(initialGameStateForO1C52, freecellGameLocal.getGameState());

    freecellGameLocal.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 27);
    initialGameStateForO1C52 = initialGameStateForO1C52
            .replace("C1: A♥\n", "C1:\n");
    initialGameStateForO1C52 = initialGameStateForO1C52
            .replace("C28: 2♠\n", "C28: 2♠, A♥\n");
    freecellGameLocal.move(PileType.CASCADE, 27, 0, PileType.CASCADE, 15);
    freecellGameLocal.move(PileType.CASCADE, 15, 0, PileType.CASCADE, 29);

    initialGameStateForO1C52 = initialGameStateForO1C52
            .replace("C16: 3♦\n", "C16:\n");
    initialGameStateForO1C52 = initialGameStateForO1C52
            .replace("C28: 2♠, A♥\n", "C28:\n");
    initialGameStateForO1C52 = initialGameStateForO1C52
            .replace("C30: 4♠\n", "C30: 4♠, 3♦, 2♠, A♥\n");
    assertEquals(initialGameStateForO1C52, freecellGameLocal.getGameState());
  }

  @Test
  public void testSuccessfulMultiMove2() {

    FreecellOperations<Cards> freecellGameLocalMulti = FreecellMultiMoveModel
            .getBuilder().opens(1).cascades(51).build();
    List<Cards> newDeck = freecellGameLocalMulti.getDeck();
    freecellGameLocalMulti.startGame(newDeck, false);
    assertEquals(testSuccessfulMultiMove1, freecellGameLocalMulti.getGameState());

    freecellGameLocalMulti.move(PileType.CASCADE, 0, 1, PileType.OPEN, 0);
    testSuccessfulMultiMove1 = testSuccessfulMultiMove1
            .replace("C1: A♥, K♣\n", "C1: A♥\n");
    testSuccessfulMultiMove1 = testSuccessfulMultiMove1
            .replace("O1:\n", "O1: K♣\n");
    assertEquals(testSuccessfulMultiMove1, freecellGameLocalMulti.getGameState());

    freecellGameLocalMulti.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 27);
    testSuccessfulMultiMove1 = testSuccessfulMultiMove1
            .replace("C1: A♥\n", "C1:\n");
    testSuccessfulMultiMove1 = testSuccessfulMultiMove1
            .replace("C28: 2♠\n", "C28: 2♠, A♥\n");
    assertEquals(testSuccessfulMultiMove1, freecellGameLocalMulti.getGameState());

    freecellGameLocalMulti.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);
    testSuccessfulMultiMove1 = testSuccessfulMultiMove1
            .replace("O1: K♣\n", "O1:\n");
    testSuccessfulMultiMove1 = testSuccessfulMultiMove1
            .replace("C1:\n", "C1: K♣\n");
    assertEquals(testSuccessfulMultiMove1, freecellGameLocalMulti.getGameState());


    freecellGameLocalMulti.move(PileType.CASCADE, 27, 0, PileType.CASCADE, 15);
    assertEquals(testSucessfulMultiMoveFinal, freecellGameLocalMulti.getGameState());
  }

  private String testSuccessfulMultiMove1 = "F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1:\n" +
          "C1: A♥, K♣\n" +
          "C2: 2♥\n" +
          "C3: 3♥\n" +
          "C4: 4♥\n" +
          "C5: 5♥\n" +
          "C6: 6♥\n" +
          "C7: 7♥\n" +
          "C8: 8♥\n" +
          "C9: 9♥\n" +
          "C10: 10♥\n" +
          "C11: J♥\n" +
          "C12: Q♥\n" +
          "C13: K♥\n" +
          "C14: A♦\n" +
          "C15: 2♦\n" +
          "C16: 3♦\n" +
          "C17: 4♦\n" +
          "C18: 5♦\n" +
          "C19: 6♦\n" +
          "C20: 7♦\n" +
          "C21: 8♦\n" +
          "C22: 9♦\n" +
          "C23: 10♦\n" +
          "C24: J♦\n" +
          "C25: Q♦\n" +
          "C26: K♦\n" +
          "C27: A♠\n" +
          "C28: 2♠\n" +
          "C29: 3♠\n" +
          "C30: 4♠\n" +
          "C31: 5♠\n" +
          "C32: 6♠\n" +
          "C33: 7♠\n" +
          "C34: 8♠\n" +
          "C35: 9♠\n" +
          "C36: 10♠\n" +
          "C37: J♠\n" +
          "C38: Q♠\n" +
          "C39: K♠\n" +
          "C40: A♣\n" +
          "C41: 2♣\n" +
          "C42: 3♣\n" +
          "C43: 4♣\n" +
          "C44: 5♣\n" +
          "C45: 6♣\n" +
          "C46: 7♣\n" +
          "C47: 8♣\n" +
          "C48: 9♣\n" +
          "C49: 10♣\n" +
          "C50: J♣\n" +
          "C51: Q♣";
  private String testSucessfulMultiMoveFinal = "F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1:\n" +
          "C1: K♣\n" +
          "C2: 2♥\n" +
          "C3: 3♥\n" +
          "C4: 4♥\n" +
          "C5: 5♥\n" +
          "C6: 6♥\n" +
          "C7: 7♥\n" +
          "C8: 8♥\n" +
          "C9: 9♥\n" +
          "C10: 10♥\n" +
          "C11: J♥\n" +
          "C12: Q♥\n" +
          "C13: K♥\n" +
          "C14: A♦\n" +
          "C15: 2♦\n" +
          "C16: 3♦, 2♠, A♥\n" +
          "C17: 4♦\n" +
          "C18: 5♦\n" +
          "C19: 6♦\n" +
          "C20: 7♦\n" +
          "C21: 8♦\n" +
          "C22: 9♦\n" +
          "C23: 10♦\n" +
          "C24: J♦\n" +
          "C25: Q♦\n" +
          "C26: K♦\n" +
          "C27: A♠\n" +
          "C28:\n" +
          "C29: 3♠\n" +
          "C30: 4♠\n" +
          "C31: 5♠\n" +
          "C32: 6♠\n" +
          "C33: 7♠\n" +
          "C34: 8♠\n" +
          "C35: 9♠\n" +
          "C36: 10♠\n" +
          "C37: J♠\n" +
          "C38: Q♠\n" +
          "C39: K♠\n" +
          "C40: A♣\n" +
          "C41: 2♣\n" +
          "C42: 3♣\n" +
          "C43: 4♣\n" +
          "C44: 5♣\n" +
          "C45: 6♣\n" +
          "C46: 7♣\n" +
          "C47: 8♣\n" +
          "C48: 9♣\n" +
          "C49: 10♣\n" +
          "C50: J♣\n" +
          "C51: Q♣";
  private String initialGameStateForO1C52 = "F1:\n" + "F2:\n" + "F3:\n" + "F4:\n" + "O1:\n"
          + "C1: A♥\n" + "C2: 2♥\n" + "C3: 3♥\n" + "C4: 4♥\n" + "C5: 5♥\n" + "C6: 6♥\n"
          + "C7: 7♥\n" + "C8: 8♥\n" + "C9: 9♥\n" + "C10: 10♥\n" + "C11: J♥\n" + "C12: Q♥\n"
          + "C13: K♥\n" + "C14: A♦\n" + "C15: 2♦\n" + "C16: 3♦\n" + "C17: 4♦\n" + "C18: 5♦\n"
          + "C19: 6♦\n" + "C20: 7♦\n" + "C21: 8♦\n" + "C22: 9♦\n" + "C23: 10♦\n" + "C24: J♦\n"
          + "C25: Q♦\n" + "C26: K♦\n" + "C27: A♠\n" + "C28: 2♠\n" + "C29: 3♠\n" + "C30: 4♠\n"
          + "C31: 5♠\n" + "C32: 6♠\n" + "C33: 7♠\n" + "C34: 8♠\n" + "C35: 9♠\n" + "C36: 10♠\n"
          + "C37: J♠\n" + "C38: Q♠\n" + "C39: K♠\n" + "C40: A♣\n" + "C41: 2♣\n"
          + "C42: 3♣\n" + "C43: 4♣\n" + "C44: 5♣\n" + "C45: 6♣\n" +
          "C46: 7♣\n" + "C47: 8♣\n" + "C48: 9♣\n" + "C49: 10♣\n" + "C50: J♣\n"
          + "C51: Q♣\n" + "C52: K♣";
}