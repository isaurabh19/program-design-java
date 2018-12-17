package freecell.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class implements a cascade pile of freecell game. Cascade pile is collection of list of
 * cards. Currently, only the last card of a single list can be moved. Any other invalid index
 * position will result in an exception being raised. The symbol C will be used in string
 * representation of lists in this pile. A valid move on this pile can only be made if the incoming
 * card is of the opposite color of the last card of cascade pile its moved to and its value is one
 * less than the current last card of the cascade pile.
 */
public class CascadePile extends AbstractPile implements Pile {
  protected List<List<Cards>> cascade;

  /**
   * Constructs a new object with given size as the number of columns and assigns the given deck in
   * its pile in a round robin fashion.
   *
   * @param size the number of columns in the pile.
   * @param deck the deck to be assigned to this pile.
   */
  public CascadePile(int size, List<Cards> deck) {
    cascade = new ArrayList<>();
    initialiseCascadePile(size, deck);
  }

  private void initialiseCascadePile(int cascadeSize, List<Cards> deck) {
    //Initialise each column of the cascade with an empty List<Cards>
    for (int i = 0; i < cascadeSize; i++) {
      cascade.add(new ArrayList<>());
    }
    //Add a card to each column in a round robin manner.
    for (int i = 0; i < 52; i++) {
      cascade.get(i % cascadeSize).add(deck.get(i));
    }
  }

  @Override
  public String toString() {
    String cascadePileNumber = "C";
    StringBuilder cascadePileString = new StringBuilder();
    for (int i = 0; i < cascade.size(); i++) {
      String newCascadePileNumber = cascadePileNumber + (i + 1) + ":";
      cascadePileString.append(newCascadePileNumber);
      String singlePile = cascade.get(i).stream().map(Objects::toString)
              .collect(Collectors.joining(", "));
      if (!singlePile.isEmpty()) {
        cascadePileString.append(" ").append(singlePile);
      }
      if (i != cascade.size() - 1) {
        cascadePileString.append('\n');
      }
    }
    return cascadePileString.toString();
  }

  @Override
  public int getPileLength(int pileNumber) {
    if (pileNumber >= cascade.size() || pileNumber < 0) {
      throw new IllegalArgumentException("Enter valid Cascade Pile Number");
    }
    return this.cascade.get(pileNumber).size();
  }


  @Override
  protected boolean isValidCardIndex(int cardIndex, int pileNumber) {
    return cardIndex >= 0 && cardIndex == cascade.get(pileNumber).size() - 1;
  }

  @Override
  public void moveTo(int sourcePileNumber, int cardIndex, Pile destination,
                     int destinationPileNumber) {
    /* We understand that the source is cascade. */
    if (isValidPileNumber(sourcePileNumber) && isValidCardIndex(cardIndex, sourcePileNumber)) {

      List<Cards> cardsToBeSent = new ArrayList<>();
      cardsToBeSent.add(cascade.get(sourcePileNumber).get(cardIndex));

      AbstractPile abstractDestination = (AbstractPile) destination;
      if (abstractDestination.moveFrom(cardsToBeSent, destinationPileNumber)) {
        cascade.get(sourcePileNumber).remove(cardIndex);
      } else {
        throw new IllegalArgumentException("Move from cascade not possible.");
      }
    } else {
      throw new IllegalArgumentException("Not a valid cascade pile source");
    }
  }


  @Override
  boolean moveFrom(List<Cards> cards, int destPileNumber) {
    if (isValidPileNumber(destPileNumber)) {
      if (cascade.get(destPileNumber).isEmpty()) {
        cascade.get(destPileNumber).addAll(cards);
        return true;
      }
      if (isValidBuild(cards)
              && isValidCascadeMove(cards.get(0), cascade.get(destPileNumber).get(cascade
              .get(destPileNumber).size() - 1))) {
        cascade.get(destPileNumber).addAll(cards);
        return true;
      }
    }
    return false;
  }

  private boolean isValidBuild(List<Cards> cards) {
    //Check build.
    if (cards.size() == 1) {
      return true;
    }
    for (int i = 0; i < cards.size() - 1; i++) {
      int j = i + 1;
      if (!isValidCascadeMove(cards.get(j), cards.get(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getPileSize() {
    return this.cascade.size();
  }

  private boolean isValidCascadeMove(Cards incomingCard, Cards destinationCard) {
    return (!(incomingCard.getColor().equalsIgnoreCase(destinationCard.getColor()))
            && (incomingCard.getValue() == destinationCard.getValue() - 1));
  }
}