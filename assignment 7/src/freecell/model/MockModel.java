package freecell.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A mock model class that performs no logic of a freecell game. This class helps in verifying
 * whether the controller sends correct inputs.
 */
public class MockModel implements FreecellOperations<Cards> {
  private StringBuilder log;

  /**
   * Constructs a mock model object with a log object where it writes the received inputs.
   *
   * @param log the log where the received inputs will be written to.
   */
  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<Cards> getDeck() {
    log.append("\nRequested Deck Cards");
    return new ArrayList<>();
  }

  @Override
  public void startGame(List<Cards> deck, boolean shuffle) throws IllegalArgumentException {
    log.append("\nGame started with following: \n" + "Deck: " + deck.toString() + "\nShuffle: "
            + shuffle);
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    log.append("\nMove from:" + source + ", Pile No:" + pileNumber + ", Card Index: " + cardIndex
            + "\nTo: " + destination + ", Pile No: " + destPileNumber + "\n");
  }

  @Override
  public boolean isGameOver() {
    log.append("\nGame over?\n");
    return false;
  }

  @Override
  public String getGameState() {
    log.append("\nReturn the game state");
    return "Asked the game state";
  }
}