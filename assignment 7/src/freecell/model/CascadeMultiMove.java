package freecell.model;

import java.util.List;

/**
 * This class is an extension of a cascade pile. It only has one enhancement over its parent where
 * it allows for multiple cards to be moved from cascade to cascade pile.
 */
public class CascadeMultiMove extends CascadePile implements Pile {

  /**
   * Constructs a new object with given size as the number of columns and assigns the given deck in
   * its pile in a round robin fashion.
   *
   * @param size the number of columns in the pile.
   * @param deck the deck to be assigned to this pile.
   */
  public CascadeMultiMove(int size, List<Cards> deck) {
    super(size, deck);
  }

  private List<Cards> getCardsToBeSent(int sourcePileNumber, int cardIndex) {
    if (cardIndex >= 0 && cardIndex < cascade.get(sourcePileNumber).size()) {
      return cascade.get(sourcePileNumber).subList(cardIndex, cascade.get(sourcePileNumber).size());
    }
    throw new IllegalArgumentException("Enter valid card index.");
  }

  @Override
  public void moveTo(int sourcePileNumber, int cardIndex, Pile destination,
                     int destinationPileNumber) {
    List<Cards> cardsToBeSent = getCardsToBeSent(sourcePileNumber, cardIndex);

    AbstractPile abstractDestination = (AbstractPile) destination;
    if (abstractDestination.moveFrom(cardsToBeSent, destinationPileNumber)) {
      cascade.get(sourcePileNumber).removeAll(cardsToBeSent);
    } else {
      throw new IllegalArgumentException("Move to cascade not possible.");
    }
  }
}
