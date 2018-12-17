package freecell.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a open pile of freecell game. Open pile is collection of cards. Currently,
 * only single card can be placed in each position of open pile. Any other invalid index position
 * will result in an exception being raised. The symbol O will be used in string representation of
 * this pile. A valid move on this pile can only be made if position in open pile does not have any
 * valid card.
 */

public class OpenPile extends AbstractPile implements Pile {
  private List<Cards> open;

  /**
   * Constructs a pile with the given size and initialises each position with a null card.
   *
   * @param size the size of open pile.
   */
  public OpenPile(int size) {
    open = new ArrayList<>();
    initialiseOpenPile(size);
  }

  private void initialiseOpenPile(int openSize) {
    for (int i = 0; i < openSize; i++) {
      open.add(new CardsImpl(Suits.NULL_CARD, Card.NULL, 0));
    }
  }

  @Override
  public String toString() {
    String openPileNumber = "O";
    StringBuilder openPileString = new StringBuilder();
    for (int i = 0; i < open.size(); i++) {
      String newOpenPileNumber = openPileNumber + (i + 1) + ":";
      openPileString.append(newOpenPileNumber);
      if (!(open.get(i).toString().equals("0\u0000"))) {
        openPileString.append(" ").append(open.get(i).toString());
      }
      openPileString.append('\n');
    }
    return openPileString.toString();
  }

  @Override
  public int getPileLength(int pileNumber) {
    if (pileNumber > open.size() || pileNumber < 0) {
      throw new IllegalArgumentException("Enter valid pile number in Open");
    }
    if (open.get(pileNumber).isNullCard()) {
      return 0;
    }
    return 1;
  }

  @Override
  protected boolean isValidCardIndex(int cardIndex, int pileNumber) {
    return cardIndex == 0;
  }

  @Override
  protected boolean isValidPileNumber(int pileNumber) {
    return pileNumber >= 0 && pileNumber <= open.size() - 1;
  }

  @Override
  public int getPileSize() {
    return this.open.size();
  }

  @Override
  public void moveTo(int sourcePileNumber, int cardIndex, Pile destination,
                     int destinationPileNumber) {
    if (isValidPileNumber(sourcePileNumber) && isValidCardIndex(cardIndex, sourcePileNumber)) {
      AbstractPile abstractDestination = (AbstractPile) destination;

      List<Cards> cardsToBeSent = new ArrayList<>();
      cardsToBeSent.add(open.get(sourcePileNumber));

      if (abstractDestination.moveFrom(cardsToBeSent, destinationPileNumber)) {
        open.set(sourcePileNumber, new CardsImpl(Suits.NULL_CARD, Card.NULL, 0));
      } else {
        throw new IllegalArgumentException("Enter valid move.");
      }
    } else {
      throw new IllegalArgumentException("Enter valid card index.");
    }
  }


  @Override
  protected boolean moveFrom(List<Cards> cards, int destPileNumber) {
    if (cards.size() > 1) {
      throw new IllegalArgumentException("Attempted to move more cards in Open");
    }
    if (isValidPileNumber(destPileNumber)
            && isValidOpenMove(open.get(destPileNumber))) {
      open.set(destPileNumber, cards.get(0));
      return true;
    }
    return false;
  }

  private boolean isValidOpenMove(Cards destinationCard) {
    return destinationCard.isNullCard();
  }
}