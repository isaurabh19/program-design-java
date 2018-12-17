import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import freecell.controller.FreecellController;
import freecell.controller.IFreecellController;
import freecell.model.Cards;
import freecell.model.FreecellModel;
import freecell.model.FreecellMultiMoveModel;
import freecell.model.FreecellOperations;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public class NachiketLiteralTextTests {
  private FreecellOperations<Cards> freecellGameSingleMove;
  private FreecellOperations<Cards> freecellGameMultipleMove;
  private IFreecellController<Cards> localFreecellGameController;

  /**
   * Setup the objects for ease of tesing.
   */
  @Before
  public void setup() {
    //create single move
    freecellGameSingleMove = FreecellModel.getBuilder().opens(52).cascades(52).build();
    List<Cards> newDeck = freecellGameSingleMove.getDeck();
    freecellGameSingleMove.startGame(newDeck, false);

    //Create multi move
    freecellGameMultipleMove = FreecellModel.getBuilder().opens(52).cascades(52).build();
    freecellGameMultipleMove.startGame(newDeck, false);
  }

  @Test
  public void testNotEnoughEmptyPiles3() {
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(52).build();
    Reader reader = new StringReader("C1 1 C15 q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains("Invalid Move. " +
            "Try again.Move to cascade not possible."));

  }


  @Test
  public void testInvalidDeck5aSingle() {
    try {
      Reader reader = new StringReader("C53 1 F4\n C1 1 F4\n");
      StringBuffer stringBuffer = new StringBuffer();
      IFreecellController<Cards> localFreecellGameController = new FreecellController(reader,
              stringBuffer);
      localFreecellGameController.playGame(null, freecellGameSingleMove, false);
      //test the invalid deck
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Null parameters passed.", e.getMessage());
    }
  }

  @Test
  public void testInvalidDeck5aMultiple() {
    try {
      Reader reader = new StringReader("C53 1 F4\n");
      StringBuffer stringBuffer = new StringBuffer();
      IFreecellController<Cards> localFreecellGameController = new FreecellController(reader,
              stringBuffer);
      localFreecellGameController.playGame(null, freecellGameMultipleMove, false);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Null parameters passed.", e.getMessage());
    }
  }

  @Test
  public void testInvalidDeck5aNotNullSingle() {
    try {
      Reader reader = new StringReader("C53 1 F4\n C1 1 F4\n");
      StringBuffer stringBuffer = new StringBuffer();
      IFreecellController<Cards> localFreecellGame = new FreecellController(reader, stringBuffer);
      List<Cards> deck = freecellGameSingleMove.getDeck();
      deck.remove(51);
      localFreecellGame.playGame(deck, freecellGameSingleMove, true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The deck is invalid", e.getMessage());
    }
  }

  @Test
  public void testInvalidDeck5aNotNullMultiple() {
    try {
      Reader reader = new StringReader("C53 1 F4\n");
      StringBuffer stringBuffer = new StringBuffer();
      IFreecellController<Cards> localFreecellGame = new FreecellController(reader, stringBuffer);
      List<Cards> deck = freecellGameMultipleMove.getDeck();
      deck.remove(51);
      localFreecellGame.playGame(deck, freecellGameMultipleMove, true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The deck is invalid", e.getMessage());
    }
  }

  @Test
  public void testEmptyListDeck5aNotNullSingle() {
    try {
      Reader reader = new StringReader("C53 1 F4\nq");
      StringBuffer stringBuffer = new StringBuffer();
      IFreecellController<Cards> localFreecellGame = new FreecellController(reader, stringBuffer);

      localFreecellGame.playGame(new ArrayList<Cards>(), freecellGameSingleMove, true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The deck is invalid", e.getMessage());
    }
  }

  @Test
  public void testEmptyListDeck5aNotNullMultiple() {
    try {
      Reader reader = new StringReader("C53 1 F4\n");
      StringBuffer stringBuffer = new StringBuffer();
      IFreecellController<Cards> localFreecellGame = new FreecellController(reader, stringBuffer);

      localFreecellGame.playGame(new ArrayList<Cards>(), freecellGameMultipleMove, true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The deck is invalid", e.getMessage());
    }
  }

  @Test
  public void testInvalidModel5a() {
    Reader reader = new StringReader("C53 1 F4\n C1 1 F4\n");
    StringBuffer stringBuffer = new StringBuffer();
    IFreecellController<Cards> localFreecellGame = new FreecellController(reader, stringBuffer);
    List<Cards> deck = freecellGameSingleMove.getDeck();
    try {
      localFreecellGame.playGame(deck, null, true);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Null parameters passed.", e.getMessage());
    }
  }

  @Test
  public void testFreecellControllerAppendableNull6() {
    Reader reader = new StringReader("C53 1 F4\n C1 1 F4\n");
    try {
      IFreecellController<Cards> localFreecellGame = new FreecellController(reader, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Null parameters passed to constructor", e.getMessage());
    }
  }

  @Test
  public void testFreecellControllerReadableNull6() {
    StringBuffer stringBuffer = new StringBuffer();
    try {
      IFreecellController<Cards> localFreecellGame = new FreecellController(null,
              stringBuffer);
    } catch (IllegalArgumentException e) {
      assertEquals("Null parameters passed to constructor", e.getMessage());
    }

  }

  @Test
  public void testMoveWhenSequenceIsNotEndingWithNewLine7() {
    //when the sequence of inputs does not end with newline.
    Reader reader = new StringReader("C1 1 F4\n C14 1 F3 q");
    StringBuffer stringBuffer = new StringBuffer();
    IFreecellController<Cards> localFreecellGameController = new FreecellController(reader,
            stringBuffer);
    List<Cards> deck = freecellGameSingleMove.getDeck();
    localFreecellGameController.playGame(deck, freecellGameSingleMove, false);
    assertTrue(stringBuffer.toString().contains("Game quit prematurely."));
  }

  @Test
  public void testGameIsWon8() {
    //Transmit the final game state.
    //Transmit message of "Game Over"
    //End game play
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader(returnStringForCompletedGame());
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    String[] test = stringBuffer.toString().split("\n");
    assertEquals("Game over.", test[954]);
  }

  private String completedGame = "F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
          + "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
          + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
          + "F4: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
          + "O1:\n" + "C1:\n" + "C2:\n" + "C3:\n"
          + "C4:\n" + "C5:\n" + "C6:\n" + "C7:\n"
          + "C8:\n" + "C9:\n" + "C10:\n" + "C11:\n"
          + "C12:\n" + "C13:\n" + "Game over.";

  private String returnStringForCompletedGame() {
    StringBuilder finishedGame = new StringBuilder();
    for (int i = 1; i <= 13; i++) {
      finishedGame.append("C" + i + " " + "4 " + "F1\n");
    }
    for (int i = 1; i <= 13; i++) {
      finishedGame.append("C" + i + " " + "3 " + "F2\n");
    }
    for (int i = 1; i <= 13; i++) {
      finishedGame.append("C" + i + " " + "2 " + "F3\n");
    }
    for (int i = 1; i <= 13; i++) {
      finishedGame.append("C" + i + " " + "1 " + "F4\n");
    }
    return finishedGame.toString();
  }


  @Test
  public void testQuittingOfGame10() {
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader("C1 1 F1 q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains("Game quit prematurely."));
  }

  @Test
  public void testLetterOtherThanCFO11a() {
    //When the letter is anything apart from CFO
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader("B1 1 F1 q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains("Incorrect First Input. Enter Again.\nGame quit" +
            " prematurely."));
  }

  @Test
  public void testLetterOtherThanCFO11aB() {
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader("C1 1 T1 q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains("Incorrect Third Input. Enter Again.\nGame quit " +
            "prematurely."));
  }

  @Test
  public void testPileNumberIncorret11bFirst() {
    //When the pile number is invalid, ie, apart from a number. - Cq 3 F2, C4 5 Fw
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader("Ca 1 T1 q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains("Incorrect First Input. Enter Again.\nGame quit" +
            " prematurely."));
  }

  @Test
  public void testPileNumberIncorret11bSecond() {
    //When the pile number is invalid, ie, apart from a number. - Cq 3 F2, C4 5 Fw
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader("C1 1 Ov q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains("Incorrect Third Input. Enter Again.\nGame quit " +
            "prematurely."));
  }

  @Test
  public void testCardIndexIncorrectly11c() {
    //When the card index is incorrect.
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader("C1 t O5 q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains("Incorrect Second Input. Enter Again.\nGame quit " +
            "prematurely."));
  }

  @Test
  public void testDestinationPileNumberIncorrect11d() {
    FreecellOperations<Cards> localFreecellGameMulti;
    localFreecellGameMulti = FreecellMultiMoveModel.getBuilder().opens(1).cascades(13).build();
    Reader reader = new StringReader("C1 1 Ot q");
    StringBuffer stringBuffer = new StringBuffer();
    localFreecellGameController = new FreecellController(reader, stringBuffer);
    localFreecellGameController.playGame(localFreecellGameMulti.getDeck(), localFreecellGameMulti,
            false);
    assertTrue(stringBuffer.toString().contains("Incorrect Third Input. Enter Again.\nGame quit" +
            " prematurely."));
  }


}