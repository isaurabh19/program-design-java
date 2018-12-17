import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import freecell.controller.FreecellController;
import freecell.controller.IFreecellController;
import freecell.model.Cards;
import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;
import freecell.model.MockModel;
import freecell.model.PileType;

import static junit.framework.TestCase.assertEquals;

public class NachiketTestMockModel {
  private FreecellOperations<Cards> freecellGameSingleMove;

  /**
   * Sets up the required objects for ease of testing.
   */
  @Before
  public void setup() {
    //create single move
    freecellGameSingleMove = FreecellModel.getBuilder().opens(52).cascades(52).build();
    List<Cards> newDeck = freecellGameSingleMove.getDeck();
    freecellGameSingleMove.startGame(newDeck, false);
  }

  @Test
  public void testMockModel() {
    freecellGameSingleMove = FreecellModel.getBuilder().opens(52).cascades(52).build();
    List<Cards> newDeck = freecellGameSingleMove.getDeck();
    freecellGameSingleMove.startGame(newDeck, false);

    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("C1 1 O1 C2 9 F2 C3 6 C4 O1 1 F1 O2 1 C2 F2 2 C2 F4 4 O1 q");
    IFreecellController<Cards> testController = new FreecellController(in, out);
    StringBuilder log = new StringBuilder();
    testController.playGame(newDeck, new MockModel(log), false);
    StringBuilder mockString = new StringBuilder(buildInitialMockString(newDeck, false));
    mockString.append(checkGameState())
            .append(buildMockMoveString(PileType.CASCADE, 0, 0, PileType.OPEN, 0))
            .append(buildMockMoveString(PileType.CASCADE, 1, 8, PileType.FOUNDATION, 1))
            .append(buildMockMoveString(PileType.CASCADE, 2, 5, PileType.CASCADE, 3))
            .append(buildMockMoveString(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0))
            .append(buildMockMoveString(PileType.OPEN, 1, 0, PileType.CASCADE, 1))
            .append(buildMockMoveString(PileType.FOUNDATION, 1, 1, PileType.CASCADE, 1))
            .append(buildMockMoveString(PileType.FOUNDATION, 3, 3, PileType.OPEN, 0));
    assertEquals(mockString.toString(), log.toString());
    assertEquals("Asked the game state\n" +
            "Asked the game state\n" +
            "Asked the game state\n" +
            "Asked the game state\n" +
            "Asked the game state\n" +
            "Asked the game state\n" +
            "Asked the game state\n" +
            "Asked the game state\n" +
            "Game quit prematurely.", out.toString());
  }

  private String checkGameState() {
    return "\nReturn the game state\n" +
            "Game over?\n";
  }

  private String buildMockMoveString(PileType source, int pileNumber, int cardIndex,
                                     PileType destination, int destPileNumber) {
    StringBuilder log = new StringBuilder();
    log.append("\nMove from:" + source + ", Pile No:" + pileNumber + ", Card Index: "
            + cardIndex + "\nTo: " + destination + ", Pile No: " + destPileNumber + "\n")
            .append(checkGameState());
    return log.toString();
  }


  private String buildInitialMockString(List<Cards> deck, boolean shuffle) {
    StringBuilder logString = new StringBuilder();
    logString.append("\nGame started with following: \nDeck: ").append(deck.toString())
            .append("\n")
            .append("Shuffle: " + shuffle);
    return logString.toString();
  }
}
