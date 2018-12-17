package freecell.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import freecell.model.FreecellOperations;
import freecell.model.PileType;

/**
 * This class implements a IFreecellController interface. It takes a Readable and an Appendable for
 * reading inputs and writing the outputs. It returns the game state after each transmission. Each
 * transmission is on a new line. Since the indices are 1 based, the controller parses the inputs in
 * the format that works for the model. Entering 'q' or 'Q' force quits the game.
 * <br>
 * The controller responds with following prompts: 1. "Invalid move. Try again." when the move
 * cannot be made followed by error message of model. 2. "Game quit prematurely." when quit command
 * is passed. 3. "Incorrect (i)th Input."  when bad inputs are passed. 4. "Game over." when the game
 * is over.
 */
public class FreecellController implements IFreecellController {

  private Readable readable;
  private Appendable appendable;
  private Map<Character, PileType> pileTypeMap;

  /**
   * Constructs a controller object with passed readable and appendable objects.
   *
   * @param readable   object of type readable as input source.
   * @param appendable object of type appendable as output source.
   * @throws IllegalArgumentException if readable or appendable are passed null.
   */
  public FreecellController(Readable readable, Appendable appendable) throws
          IllegalArgumentException {
    if (readable == null || appendable == null) {
      throw new IllegalArgumentException("Null parameters passed to constructor");
    }
    this.readable = readable;
    this.appendable = appendable;
    pileTypeMap = new HashMap<>();
    pileTypeMap.put('C', PileType.CASCADE);
    pileTypeMap.put('F', PileType.FOUNDATION);
    pileTypeMap.put('O', PileType.OPEN);
  }

  private boolean isValidPile(String source) {
    if (source.length() < 2 || source.length() > 3) {
      return false;
    }
    char symbol = source.charAt(0);
    if (!pileTypeMap.containsKey(symbol)) {
      //symbol != 'C' && symbol != 'F' && symbol != 'O') {
      return false;
    }
    return new Scanner(source.charAt(1) + "").hasNextInt();
  }

  private boolean isNumber(String index) {
    try {
      int cardIndex = Integer.parseInt(index);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  private PileType getPileType(String pile) {
    try {
      return pileTypeMap.get(pile.charAt(0));
    } catch (Exception e) {
      throw new IllegalStateException("Error in reading input");
    }
  }

  private void writeToAppendable(String message) {
    try {
      appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Error in sending output.");
    }
  }

  private String getPileString(Scanner scanner, String errorMessage) {
    //Scanner scanner = new Scanner(this.readable);
    String pile = scanner.next();
    if (pile.equalsIgnoreCase("Q")) {
      writeToAppendable("Game quit prematurely.");
      return pile;
    }
    while (!isValidPile(pile)) {
      if (pile.equalsIgnoreCase("Q")) {
        writeToAppendable("Game quit prematurely.");
        return pile;
      }
      writeToAppendable(errorMessage);
      pile = scanner.next();
    }
    return pile;
  }

  private String getIndexString(Scanner scanner) {
    //Scanner scanner = new Scanner(this.readable);
    String indexString = scanner.next();
    if (indexString.equalsIgnoreCase("Q")) {
      writeToAppendable("Game quit prematurely.");
      return indexString;
    }
    while (!isNumber(indexString)) {
      if (indexString.equalsIgnoreCase("Q")) {
        writeToAppendable("Game quit prematurely.");
        return indexString;
      }
      writeToAppendable("Incorrect Second Input. Enter Again.\n");
      indexString = scanner.next();
    }
    return indexString;
  }


  @Override
  public void playGame(List deck, FreecellOperations model, boolean shuffle) {
    if (deck == null || model == null) {
      throw new IllegalArgumentException("Null parameters passed.");
    }
    model.startGame(deck, shuffle);
    writeToAppendable(model.getGameState() + "\n");
    Scanner scanner = new Scanner(this.readable);
    while (!model.isGameOver()) {
      try {
        String source = getPileString(scanner, "Incorrect First Input. Enter Again.\n");
        if (source.equalsIgnoreCase("Q")) {
          return;
        }
        String indexString = getIndexString(scanner);
        if (indexString.equalsIgnoreCase("Q")) {
          return;
        }
        String destination = getPileString(scanner, "Incorrect Third Input. Enter Again.\n");
        if (destination.equalsIgnoreCase("Q")) {
          return;
        }
        int pileNumber = Integer.parseInt(source.substring(1)) - 1;
        int destPileNumber = Integer.parseInt(destination.substring(1)) - 1;
        int cardIndex = Integer.parseInt(indexString) - 1;
        try {
          model.move(getPileType(source), pileNumber, cardIndex, getPileType(destination),
                  destPileNumber);
        } catch (IllegalArgumentException e) {
          writeToAppendable("Invalid Move. Try again." + e.getMessage() + "\n");
        }
        writeToAppendable(model.getGameState() + "\n");
      } catch (RuntimeException e) {
        throw new IllegalStateException("Error in reading from source.");
      }
    }
    writeToAppendable("Game over.");
  }
}
