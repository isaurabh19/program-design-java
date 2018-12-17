import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import freecell.model.Cards;
import freecell.model.FreecellModel;
import freecell.model.FreecellMultiMoveModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;

import static org.junit.Assert.assertEquals;

public class MultiMoveModelTest2 {

  private FreecellOperations<Cards> multiMoveModel;
  private FreecellOperations<Cards> singleMoveModel;

  @Before
  public void setMultiMoveModel() {
    multiMoveModel = FreecellMultiMoveModel.getBuilder().opens(52).cascades(52).build();
  }

  private final String[] deckOfCardsArray = {"A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥",
    "10♥", "J♥", "Q♥", "K♥", "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦",
    "J♦", "Q♦", "K♦", "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠",
    "Q♠", "K♠", "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"};

  private String gameStateDefault = "F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1:\n" +
          "C1: A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♠, 7♠, J♠, 2♣, 6♣, 10♣\n" +
          "C2: 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♠, 8♠, Q♠, 3♣, 7♣, J♣\n" +
          "C3: 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♠, 5♠, 9♠, K♠, 4♣, 8♣, Q♣\n" +
          "C4: 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♠, 6♠, 10♠, A♣, 5♣, 9♣, K♣";

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
  public void testDefaultBuild() {
    multiMoveModel = FreecellMultiMoveModel.getBuilder().build();
    multiMoveModel.startGame(multiMoveModel.getDeck(), false);
    assertEquals(gameStateDefault, multiMoveModel.getGameState());
    singleMoveModel = FreecellModel.getBuilder().build();
    singleMoveModel.startGame(singleMoveModel.getDeck(), false);
    assertEquals(gameStateDefault, singleMoveModel.getGameState());
  }

  @Test
  public void testCustomBuild() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), false);
    assertEquals(getGameStateStartTestString(), multiMoveModel.getGameState());
    singleMoveModel = FreecellModel.getBuilder().opens(52).cascades(52).build();
    singleMoveModel.startGame(singleMoveModel.getDeck(), false);
    assertEquals(getGameStateStartTestString(), singleMoveModel.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOpenBuild() {
    multiMoveModel = FreecellMultiMoveModel.getBuilder().opens(0).cascades(5).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOpenBuildSingle() {
    singleMoveModel = FreecellModel.getBuilder().opens(0).cascades(5).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCascadeBuild() {
    multiMoveModel = FreecellMultiMoveModel.getBuilder().opens(0).cascades(1).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCascadeBuildSingle() {
    singleMoveModel = FreecellModel.getBuilder().opens(0).cascades(1).build();
  }

  @Test
  public void testSuccessfulBackWardCompatibilityC2C() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), false);
    multiMoveModel.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 27);
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
    assertEquals(String.join("\n", gameState), multiMoveModel.getGameState());
    multiMoveModel.move(PileType.CASCADE, 27, 0, PileType.CASCADE, 0);
    gameState.set(56, "C1: 2♠, A♥");
    gameState.set(56 + 27, "C28:");
    assertEquals(String.join("\n", gameState), multiMoveModel.getGameState());
    multiMoveModel.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 2);
    gameState.set(56 + 2, "C3: 3♥, 2♠, A♥");
    gameState.set(56, "C1:");
    assertEquals(String.join("\n", gameState), multiMoveModel.getGameState());
  }
}
