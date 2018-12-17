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

import static org.junit.Assert.assertEquals;

public class FreecellControllerTest {

  private IFreecellController<Cards> freecellController;
  private FreecellOperations<Cards> freecellMultiMoveModel;
  private FreecellOperations<Cards> singleMoveModel;
  //private FreecellOperations<Cards> freecellMultiMoveModel;
  private final String[] deckOfCardsArray = {"A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥",
    "10♥", "J♥", "Q♥", "K♥", "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦",
    "J♦", "Q♦", "K♦", "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠",
    "Q♠", "K♠", "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"};

  /**
   * Sets up the objects for ease of testing.
   */
  @Before
  public void setFreecellController() {
    freecellMultiMoveModel = FreecellMultiMoveModel.getBuilder().cascades(52).opens(52).build();
    singleMoveModel = FreecellModel.getBuilder().cascades(52).opens(52).build();
    //freecellMultiMoveModel = FreecellMultiMoveModel.getBuilder().build();
  }

  @Test
  public void testInvalidMoveWrongPileNumber() {
    Reader reader = new StringReader("C53 1 F4 Q\n C1 1 F4\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel,
            false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(getGameStateStartTestString() + "\nInvalid Move. Try again.Enter valid " +
            "Cascade Pile Number\n" + String.join("\n", gameState) + "\nGame quit" +
            " prematurely.", stringBuffer.toString());
  }

  @Test
  public void testInvalidMoveWrongPileNumberSingle() {
    Reader reader = new StringReader("C53 1 F4 Q\n C1 1 F4\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(getGameStateStartTestString() + "\nInvalid Move. "
            + "Try again.Enter valid Cascade Pile Number\n" + String
            .join("\n", gameState) + "\nGame quit prematurely.", stringBuffer.toString());
  }

  @Test
  public void testInvalidMoveWrongCardIndex() {
    Reader reader = new StringReader("C52 2 F4 q\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel,
            false);
    assertEquals(getGameStateStartTestString() + "\nInvalid Move. Try again."
            + "Enter valid card index.\n"
            + getGameStateStartTestString() + "\nGame quit prematurely.", stringBuffer.toString());

  }

  @Test
  public void testInvalidMoveWrongCardIndexSingle() {
    Reader reader = new StringReader("C52 2 F4 q\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);
    assertEquals(getGameStateStartTestString() + "\nInvalid Move. "
            + "Try again.Not a valid cascade pile source\n"
            + getGameStateStartTestString() + "\nGame quit prematurely.", stringBuffer.toString());

  }

  @Test
  public void testInvalidMultiMoveToOpen() {
    Reader reader = new StringReader("C1 1 C28\nC28 1 O2\n C28 1 F2 q");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel,
            false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 27; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    gameState.add("C28: 2♠, A♥");
    for (int i = 28; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }

    assertEquals(getGameStateStartTestString() + "\n" + String.join("\n",
            gameState)
            + "\nInvalid Move. Try again.Attempted to move more cards in Open\n" + String
            .join("\n", gameState) + "\nInvalid Move. Try again.Attempted to"
            + " move more cards " +
            "in Open\n" + String.join("\n", gameState)
            + "\nGame quit prematurely.", stringBuffer.toString());

  }

  @Test
  public void testInvalidSingleMoveToOpen() {
    Reader reader = new StringReader("C1 1 O1\nC28 1 O1 q\n C28 1 F2 q");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    gameState.add("O1: A♥");
    for (int i = 1; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(getGameStateStartTestString() + "\n" + String.join("\n",
            gameState)
            + "\nInvalid Move. Try again.Move from cascade not possible.\n" + String
            .join("\n", gameState)
            + "\nGame quit prematurely.", stringBuffer.toString());

  }

  @Test
  public void testInvalidSingleMoveToFoundation() {
    Reader reader = new StringReader("C1 1 F1\nC28 1 F1 q");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);

    List<String> gameState = new ArrayList<>();
    gameState.add("F1: A♥");
    for (int i = 1; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(getGameStateStartTestString() + "\n" + String.join("\n",
            gameState)
            + "\nInvalid Move. Try again.Move from cascade not possible.\n"
            + String.join("\n", gameState)
            + "\nGame quit prematurely.", stringBuffer.toString());

  }

  @Test
  public void testInvalidMultiMoveToFoundation() {
    Reader reader = new StringReader("C1 1 C28\nC28 1 F2 q");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel,
            false);

    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 27; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    gameState.add("C28: 2♠, A♥");
    for (int i = 28; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(getGameStateStartTestString() + "\n" + String.join("\n", gameState)
            + "\nInvalid Move. Try again.Attempted to move more cards in Open\n" +
            String.join("\n", gameState)
            + "\nGame quit prematurely.", stringBuffer.toString());

  }

  @Test
  public void testInvalidMoveWrongPileType() {
    Reader reader = new StringReader("O1 1 F5 q\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel, false);
    assertEquals(getGameStateStartTestString() + "\nInvalid Move. Try again.Enter valid " +
            "move.\n" + getGameStateStartTestString() + "\nGame quit prematurely.", stringBuffer
            .toString());
    List<String> gameState = new ArrayList<>();
    gameState.add("F1: A♥, 2♥");
    for (int i = 1; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    gameState.add("C2:");
    for (int i = 2; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }

    reader = new StringReader("F1 1 C2 q");
    stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel, false);
    assertEquals(getGameStateStartTestString() + "\nInvalid Move. Try again.Enter valid " +
            "index.\n" + getGameStateStartTestString() + "\nGame quit prematurely.", stringBuffer
            .toString());
  }

  @Test
  public void testWrongPileNumberSingle() {
    Reader reader = new StringReader("O1 1 F5 q\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);
    assertEquals(getGameStateStartTestString() + "\nInvalid Move. Try again.Enter valid " +
            "move.\n" + getGameStateStartTestString() + "\nGame quit prematurely.", stringBuffer
            .toString());
  }

  @Test
  public void testIncorrectFirstValueLowerCase() {
    Reader reader = new StringReader("c1 x1 C1 1 F4 q\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel,
            false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    gameState.add("F4: A♥");
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(getGameStateStartTestString() + "\nIncorrect First Input. Enter Again.\n"
            + "Incorrect First Input. " + "Enter Again.\n" + String.join("\n", gameState)
            + "\nGame quit prematurely.", stringBuffer.toString());

    reader = new StringReader("O1 1 F5 q\n");
    stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);
    assertEquals(getGameStateStartTestString() + "\nInvalid Move. Try again.Enter valid " +
            "move.\n" + getGameStateStartTestString() + "\nGame quit prematurely.", stringBuffer
            .toString());
  }

  @Test
  public void testIncorrectSecondValue() {
    Reader reader = new StringReader("c1 CY1 C1 1100000000212121000 1 f4 g4 F4 q\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel, false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    gameState.add("F4: A♥");
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getGameStateStartTestString()).append("\n");
    for (int i = 0; i < 2; i++) {
      stringBuilder.append("Incorrect First Input. Enter Again.\n");
    }
    for (int i = 0; i < 1; i++) {
      stringBuilder.append("Incorrect Second Input. Enter Again.\n");
    }
    for (int i = 0; i < 2; i++) {
      stringBuilder.append("Incorrect Third Input. Enter Again.\n");
    }
    assertEquals(stringBuilder.toString() + String.join("\n", gameState) +
            "\nGame quit prematurely.", stringBuffer.toString());
    reader = new StringReader("c1 CY1 C1 1100000000212121000 1 f4 g4 F4 q\n");
    StringBuffer sb = new StringBuffer();
    freecellController = new FreecellController(reader, sb);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);
    assertEquals(stringBuilder.toString() + String.join("\n", gameState)
            + "\nGame quit prematurely.", stringBuffer.toString());
  }

  private String getGameStateStartTestString() {
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    return String.join("\n", gameState);
  }

  @Test
  public void testQuitSecondArgument() {
    Reader reader = new StringReader("C1 q\n");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel, false);
    assertEquals(getGameStateStartTestString() + "\nGame quit prematurely.",
            stringBuffer.toString());

    reader = new StringReader("C1 q\n");
    stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);

  }

  @Test
  public void testQuitIgnoresNext() {
    Reader reader = new StringReader("C1 1 C28\nC28 1 C3\nC3 q C30\nC30 1 C5");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel, false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 27; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    gameState.add("C28: 2♠, A♥");
    for (int i = 28; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    String state1 = getGameStateStartTestString() + "\n" + String.join("\n", gameState);
    gameState.set(56 + 27, "C28:");
    gameState.set(56 + 2, "C3: 3♥, 2♠, A♥");
    String state2 = state1 + "\n" + String.join("\n", gameState);
    assertEquals(state2 + "\nGame quit prematurely.", stringBuffer.toString());
  }

  @Test
  public void testQuitIgnoresNextSingleModel() {
    Reader reader = new StringReader("C1 1 C28\nC28 q 1 C3\nC3 q C30\nC30 1 C5");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(singleMoveModel.getDeck(), singleMoveModel, false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 27; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    gameState.add("C28: 2♠, A♥");
    for (int i = 28; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    String state1 = getGameStateStartTestString() + "\n" + String.join("\n", gameState);
    assertEquals(state1 + "\nGame quit prematurely.", stringBuffer.toString());
  }

  @Test
  public void testInvalidBuildExternal() {
    Reader reader = new StringReader("C1 1 C28\nC28 1 C2 q\nC3 1 C30 q\nC30 1 C5");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel, false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    for (int i = 0; i < 52; i++) {
      gameState.add("O" + (i + 1) + ":");
    }
    gameState.add("C1:");
    for (int i = 1; i < 27; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    gameState.add("C28: 2♠, A♥");
    for (int i = 28; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    String state1 = getGameStateStartTestString() + "\n" + String.join("\n", gameState);

    assertEquals(state1 + "\nInvalid Move. Try again.Move to cascade not possible.\n"
            + String.join("\n", gameState) + "\nGame quit prematurely.", stringBuffer
            .toString());
  }

  private String gameStateDefault = "F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1:\n" +
          "O2:\n" +
          "O3:\n" +
          "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n" +
          "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
          "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n" +
          "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

  @Test
  public void testInvalidBuildInternal() {
    freecellMultiMoveModel = FreecellMultiMoveModel.getBuilder().opens(3).build();
    Reader reader = new StringReader("C1 11 C3 q\n\nC3 1 C30");
    StringBuffer stringBuffer = new StringBuffer();
    freecellController = new FreecellController(reader, stringBuffer);
    freecellController.playGame(freecellMultiMoveModel.getDeck(), freecellMultiMoveModel, false);
    assertEquals(gameStateDefault + "\n" + "Invalid Move. Try again.Move to cascade not " +
            "possible.\n" + gameStateDefault + "\nGame quit prematurely.", stringBuffer.toString());
  }
}