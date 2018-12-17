package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class implements the FreecellOperations interface. It represents the model of the game with
 * three piles: Open, Foundation and Cascade. Each pile is a Collection of Cards interface. A game
 * has 52 cards belonging to 4 suits.
 * It provides a builder method to instantiate an object of this class. A game can be played by
 * getting a deck, starting the game and making valid moves.
 * The game is started by allocating a valid deck of cards among the cascade piles in a round robin
 * fashion.Observer method to get the game state will return an empty string if called before a game
 * has started. The game is over when the foundation piles have 52 cards in total. The status of
 * game over is false otherwise. An attempt to make a move before starting game will throw an
 * exception. Calling startGame() during a game will restart the game.
 */

public class FreecellModel implements FreecellOperations<Cards> {
  private final int cascadeSize;
  private final int openSize;
  private boolean isGameStarted;

  private FreecellModel(int cascadeSize, int openSize) {
    this.cascadeSize = cascadeSize;
    this.openSize = openSize;
    this.isGameStarted = false;
  }

  private Pile cascadePile;
  private Pile foundationPile;
  private Pile openPile;


  /**
   * Returns a builder object that can be used to create and instance of this class.
   *
   * @return an instance of this class.
   */
  public static FreecellOperationsBuilderImpl getBuilder() {
    return new FreecellOperationsBuilderImpl();
  }

  @Override
  public List<Cards> getDeck() {
    Card[] cards = Card.values();
    Suits[] suits = Suits.values();
    List<Cards> deck = new ArrayList<>();
    for (int j = 0; j < suits.length - 1; j++) {
      for (int index = 1; index < cards.length; index++) {
        deck.add(new CardsImpl(suits[j], cards[index], index));
      }
    }
    return deck;
  }

  @Override
  public boolean isGameOver() {
    if (!isGameStarted) {
      return false;
    }
    int numOfCards = 0;
    for (int i = 0; i < 4; i++) {
      numOfCards += foundationPile.getPileLength(i);
    }
    return numOfCards == 52;
  }

  @Override
  public String getGameState() {
    if (!isGameStarted) {
      return "";
    }
    return foundationPile.getPileState() + openPile.getPileState() + cascadePile.getPileState();
  }


  @Override
  public void startGame(List<Cards> deck, boolean shuffle) throws IllegalArgumentException {
    //Check if the deck is valid.
    if (!isValidDeck(deck)) {
      throw new IllegalArgumentException("The deck is invalid");
    }
    //Shuffle the deck of cards, if asked.
    List<Cards> newDeck = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(newDeck);
    }
    cascadePile = new CascadePile(this.cascadeSize, newDeck);
    openPile = new OpenPile(this.openSize);
    foundationPile = new FoundationPile();
    this.isGameStarted = true;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException, IllegalStateException {

    checkInitialStateOfGame(source, destination, destPileNumber);
    Pile sourcePile = getPileTypeObject(source);
    Pile destinationPile = getPileTypeObject(destination);
    sourcePile.moveTo(pileNumber, cardIndex, destinationPile, destPileNumber);
  }

  private boolean isValidDeck(List<Cards> deck) {
    List<Cards> newDeck = getDeck();
    if (deck == null || deck.size() != 52) {
      return false;
    }
    for (Cards card : newDeck) {
      if (!deck.contains(card)) {
        return false;
      }
    }
    return true;
  }

  private boolean isPileTypeNull(PileType source, PileType destination) {
    return source == null || destination == null;
  }


  private void checkInitialStateOfGame(PileType source, PileType destination, int destPileNumber) {
    if (!isGameStarted) {
      throw new IllegalStateException("Cannot make a move before game starts.");
    }
    if (isPileTypeNull(source, destination) || destPileNumber < 0) {
      throw new IllegalArgumentException("Enter valid values for PileType and Pile Number.");
    }
  }

  private Pile getPileTypeObject(PileType pileType) {
    switch (pileType) {
      case FOUNDATION:
        return this.foundationPile;
      case OPEN:
        return this.openPile;
      case CASCADE:
        return this.cascadePile;
      default:
        throw new IllegalArgumentException("Invalid Enum value");
    }
  }

  /**
   * A class that implements a FreecellOperationsBuilder. The default values for cascade and open
   * piles is 4 and 1 respectively. Any value less than these is not allowed and will throw an
   * exception.
   */
  public static class FreecellOperationsBuilderImpl implements FreecellOperationsBuilder {
    private int cascadeSize;
    private int openSize;

    private FreecellOperationsBuilderImpl() {
      cascadeSize = 4;
      openSize = 1;
    }

    @Override
    public FreecellOperationsBuilder cascades(int cascade) {
      if (cascade < 4) {
        throw new IllegalArgumentException("Cascade deck size cannot be " + cascade +
                ". It should be at least 4.");
      }
      cascadeSize = cascade;
      return this;
    }

    @Override
    public FreecellOperationsBuilder opens(int open) {
      if (open < 1) {
        throw new IllegalArgumentException("Open deck size cannot be " + open + ". " +
                "It should be at least 1");
      }
      openSize = open;
      return this;
    }

    @Override
    public FreecellOperations<Cards> build() {
      return new FreecellModel(cascadeSize, openSize);
    }
  }
}