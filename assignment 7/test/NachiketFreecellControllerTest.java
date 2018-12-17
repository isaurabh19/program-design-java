import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import freecell.controller.FreecellController;
import freecell.controller.IFreecellController;
import freecell.model.Cards;
import freecell.model.FreecellModel;
import freecell.model.FreecellMultiMoveModel;
import freecell.model.FreecellOperations;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class NachiketFreecellControllerTest {
  private IFreecellController<Cards> localFreecellGameController;

  /**
   * Set up the objects before testing.
   */

  @Test
  public void testQuittingOfGame10() {
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(52).build();
    Reader reader = new StringReader("C1 1 C28 \nC28 1 C16\n" +
            generateStringToFinishGameTest10() + "q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains(completedGame));
    String[] testArray = stringBuffer.toString().split("\n");
    assertEquals("Game over.", testArray[testArray.length - 1]);
  }

  private String completedGame = "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
          + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
          + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
          + "F4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
          + "O1:\n" + "C1:\n" + "C2:\n" + "C3:\n"
          + "C4:\n" + "C5:\n" + "C6:\n" + "C7:\n"
          + "C8:\n" + "C9:\n" + "C10:\n" + "C11:\n"
          + "C12:\n" + "C13:\n" + helperGenerateEmptyGameCompleteString() + "Game over.";

  private String helperGenerateEmptyGameCompleteString() {
    StringBuilder strReturn = new StringBuilder();
    for (int i = 14; i <= 52; i++) {
      strReturn.append("C" + i + ":\n");
    }
    return strReturn.toString();
  }

  private String generateStringToFinishGameTest10() {

    StringBuilder returnStr = new StringBuilder();

    for (int i = 40; i <= 52; i++) {
      returnStr.append("C" + i + " 1 F1\n");
    }
    returnStr.append("C16 3 F2\n");
    returnStr.append("C16 2 C28\n");
    for (int i = 2; i <= 13; i++) {
      returnStr.append("C" + i + " 1 F2\n");
    }
    for (int i = 14; i <= 26; i++) {
      returnStr.append("C" + i + " 1 F3\n");
    }
    for (int i = 27; i <= 39; i++) {
      returnStr.append("C" + i + " 1 F4\n");
    }
    return returnStr.toString();
  }

  @Test
  public void testShuffleThroughControllerMultiMove() {
    FreecellOperations<Cards> localFreecellGameMultiUnshuffled;
    localFreecellGameMultiUnshuffled = FreecellMultiMoveModel.getBuilder().opens(1)
            .cascades(13).build();
    Reader reader = new StringReader("C1 4 O1\n" + "q");
    StringBuffer stringBufferUnshuffled = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBufferUnshuffled);
    localFreecellGameController.playGame(localFreecellGameMultiUnshuffled.getDeck(),
            localFreecellGameMultiUnshuffled, false);

    FreecellOperations<Cards> localFreecellGameMultiShuffled;
    localFreecellGameMultiShuffled = FreecellMultiMoveModel.getBuilder().opens(1)
            .cascades(13).build();
    Reader readerShuffled = new StringReader("C1 4 O1\n" + "q");
    StringBuffer stringBufferShuffled = new StringBuffer();
    localFreecellGameController = new FreecellController(readerShuffled, stringBufferShuffled);
    localFreecellGameController.playGame(localFreecellGameMultiShuffled.getDeck(),
            localFreecellGameMultiShuffled, true);

    assertNotEquals(stringBufferShuffled.toString(), stringBufferUnshuffled.toString());
  }

  @Test
  public void testShuffleThroughControllerSingleMove() {
    FreecellOperations<Cards> localFreecellGameSingleUnshuffled;
    localFreecellGameSingleUnshuffled = FreecellModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader("C1 4 O1\n" + "q");
    StringBuffer stringBufferUnshuffled = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBufferUnshuffled);
    localFreecellGameController.playGame(localFreecellGameSingleUnshuffled.getDeck(),
            localFreecellGameSingleUnshuffled, false);

    FreecellOperations<Cards> localFreecellGameMultiShuffled;
    localFreecellGameMultiShuffled = FreecellModel.getBuilder().opens(1).cascades(13).build();
    Reader readerShuffled = new StringReader("C1 4 O1\n" + "q");
    StringBuffer stringBufferShuffled = new StringBuffer();
    localFreecellGameController = new FreecellController(readerShuffled, stringBufferShuffled);
    localFreecellGameController.playGame(localFreecellGameMultiShuffled.getDeck(),
            localFreecellGameMultiShuffled, true);

    assertNotEquals(stringBufferShuffled.toString(), stringBufferUnshuffled.toString());
  }

  @Test
  public void testMulipleCardMoveOnSingleMoveModelFails() {
    FreecellOperations<Cards> localFreecellGameSingleUnshuffled;
    localFreecellGameSingleUnshuffled = FreecellModel.getBuilder().opens(2).cascades(13).build();
    Reader reader = new StringReader("C3 4 O1\n C3 3 O2 C1 4 F1 C1 3 F2 C1 2 C2 C2 4 C3 "
            + "q");
    StringBuffer stringBufferUnshuffled = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBufferUnshuffled);
    localFreecellGameController.playGame(localFreecellGameSingleUnshuffled.getDeck(),
            localFreecellGameSingleUnshuffled, false);

    String[] testArray = stringBufferUnshuffled.toString().split("\n");
    assertEquals("Invalid Move. Try again.Not enough empty Piles.", testArray[114]);
  }
}
