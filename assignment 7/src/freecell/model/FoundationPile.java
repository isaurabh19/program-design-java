package freecell.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * This class implements a foundation pile of freecell game. Foundation pile is collection of stack
 * of cards. Only the top card of a single stack can be moved. Any other invalid index position will
 * result in an exception being raised. The symbol F will be used in string representation of stacks
 * in this pile. A valid move on this pile can only be made if the incoming card is of the same suit
 * as the top card of the pile its moved to and its value is one more than the current top card of
 * the pile. There number of piles is same as number of suits, which is 4.
 */

public class FoundationPile extends AbstractPile implements Pile {
  private List<Stack<Cards>> foundation;

  /**
   * Constructs a foundation pile with 4 piles of cards.
   */
  public FoundationPile() {
    foundation = new ArrayList<>();
    initialiseFoundationPile();
  }

  private void initialiseFoundationPile() {
    for (int i = 0; i < 4; i++) {
      foundation.add(new Stack<>());
    }
  }

  @Override
  public String toString() {
    String foundationPileNumber = "F";
    StringBuilder foundationPileString = new StringBuilder();
    for (int i = 0; i < foundation.size(); i++) {
      String newFoundationPileNumber = foundationPileNumber + (i + 1) + ":";
      foundationPileString.append(newFoundationPileNumber);
      String singlePile = foundation.get(i).stream().map(Cards::toString)
              .collect(Collectors.joining(", "));
      if (!singlePile.isEmpty()) {
        foundationPileString.append(" ").append(singlePile);
      }
      foundationPileString.append('\n');
    }
    return foundationPileString.toString();
  }

  @Override
  public int getPileLength(int pileNumber) {

    if (pileNumber > foundation.size() || pileNumber < 0) {
      throw new IllegalArgumentException("Enter valid Foundation Pile Number");
    }
    return foundation.get(pileNumber).size();
  }

  @Override
  protected boolean isValidCardIndex(int cardIndex, int sourcePileNumber) {
    return cardIndex == foundation.get(sourcePileNumber).size() - 1;
  }

  @Override
  public int getPileSize() {
    return this.foundation.size();
  }

  @Override
  public void moveTo(int sourcePileNumber, int cardIndex, Pile destination,
                     int destinationPileNumber) {
    //We know that source is foundation.
    if (isValidPileNumber(sourcePileNumber) && isValidCardIndex(cardIndex, sourcePileNumber)) {
      AbstractPile abstractDestination = (AbstractPile) destination;
      List<Cards> listOfCards = new ArrayList<>();
      listOfCards.add(foundation.get(sourcePileNumber).peek());
      if (abstractDestination.moveFrom(listOfCards, destinationPileNumber)) {
        foundation.get(sourcePileNumber).pop();
      } else {
        throw new IllegalArgumentException("Move not possible 2");
      }
    } else {
      throw new IllegalArgumentException("Enter valid index.");
    }
  }

  @Override
  protected boolean moveFrom(List<Cards> cards, int destPileNo) {
    if (cards.size() > 1) {
      throw new IllegalArgumentException("Attempted to move more cards in Open");
    }
    Cards card = cards.get(0);
    if (isValidPileNumber(destPileNo)
            && ((foundation.get(destPileNo).isEmpty() && card.getValue() == 1)
            || isValidFoundationMove(card, foundation.get(destPileNo).peek()))) {
      foundation.get(destPileNo).push(card);
      return true;
    }
    return false;
  }

  private boolean isValidFoundationMove(Cards incomingCard, Cards destinationCard) {
    return incomingCard.getSuit().equalsIgnoreCase(destinationCard.getSuit())
            && incomingCard.getValue() == destinationCard.getValue() + 1;
  }
}
