package freecell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An abstract freecell model that provides the common operations any freecell model may have. It
 * provides helpers to validate inputs and format outputs.
 */
abstract class AbstractFreecellModel implements FreecellOperations<Cards> {
  protected Pile cascadePile;
  private Pile foundationPile;
  private Pile openPile;

  private final int cascadeSize;
  private final int openSize;
  private boolean isGameStarted;

  AbstractFreecellModel(int cascadeSize, int openSize) {
    this.cascadeSize = cascadeSize;
    this.openSize = openSize;
  }

  protected abstract Pile getCascadePile(int cascadeSize, List<Cards> deck);

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

  @Override
  public void startGame(List<Cards> deck, boolean shuffle) throws IllegalArgumentException {
    if (!isValidDeck(deck)) {
      throw new IllegalArgumentException("The deck is invalid");
    }
    //Shuffle the deck of cards, if asked.
    List<Cards> newDeck = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(newDeck);
    }
    cascadePile = getCascadePile(cascadeSize, newDeck);//new CascadePile(this.cascadeSize, newDeck);
    openPile = new OpenPile(this.openSize);
    foundationPile = new FoundationPile();
    this.isGameStarted = true;
  }

  private void checkInitialStateOfGame(PileType source, PileType destination, int destPileNumber) {
    if (!isGameStarted) {
      throw new IllegalStateException("Cannot make a move before game starts.");
    }
    if (isPileTypeNull(source, destination) || destPileNumber < 0) {
      throw new IllegalArgumentException("Enter valid values for PileType and Pile Number.");
    }
  }

  private boolean isPileTypeNull(PileType source, PileType destination) {
    return source == null || destination == null;
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

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException, IllegalStateException {

    checkInitialStateOfGame(source, destination, destPileNumber);
    Pile sourcePile = getPileTypeObject(source);
    Pile destinationPile = getPileTypeObject(destination);
    if (isMultiCardMove(source, sourcePile, pileNumber, cardIndex)) {
      if (!sufficientEmptyPilesPresent(pileNumber, cardIndex)) {
        throw new IllegalArgumentException("Not enough empty Piles.");
      }
    }
    sourcePile.moveTo(pileNumber, cardIndex, destinationPile, destPileNumber);
  }

  private boolean isMultiCardMove(PileType sourcePileType, Pile source, int sourcePileNumber,
                                  int cardIndex) {
    if (sourcePileType == PileType.CASCADE && cardIndex >= 0) {
      int len = source.getPileLength(sourcePileNumber);
      return len - cardIndex > 1;
    }
    return false;
  }

  private int getEmptyPiles(Pile pile, int size) {
    int count = 0;
    for (int i = 0; i < size; i++) {
      if (pile.getPileLength(i) == 0) {
        count++;
      }
    }
    return count;
  }


  private int getNumberOfCardsToBeMoved(int sourcePileNumber, int cardIndex) {
    //removed +1 to test empty piles.
    return cascadePile.getPileLength(sourcePileNumber) - cardIndex;
  }

  private boolean sufficientEmptyPilesPresent(int sourcePileNumber, int cardIndex) {
    int emptyOpenPiles = getEmptyPiles(openPile, openSize);
    int emptyCascadePiles = getEmptyPiles(cascadePile, cascadeSize);
    int numberOfCards = getNumberOfCardsToBeMoved(sourcePileNumber, cardIndex);
    boolean test = numberOfCards <= ((emptyOpenPiles + 1) * (Math.pow(2, emptyCascadePiles)));
    return test;
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
    return foundationPile.toString() + openPile.toString() + cascadePile.toString();
  }
}
