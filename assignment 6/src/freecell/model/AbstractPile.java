package freecell.model;

/**
 * An abstract class that implements the Pile and provides common methods for other pile
 * implementations. It provides helpers that can help the pile implementing classes in validating
 * inputs and moving the cards from one pile type to other.
 */
abstract class AbstractPile implements Pile {
  /**
   * Moves card to passed destination from which ever source destination that called it. Also tells
   * whether the move was valid.
   *
   * @param card                  the card to be moved.
   * @param destinationPileNumber the destination pile number where the card will be moved.
   * @return a boolean indicating whether the move was successful.
   */
  abstract boolean moveFrom(Cards card, int destinationPileNumber);

  /**
   * Checks if the passed card index is valid in the given pile number.
   *
   * @param cardIndex  the index position of the card.
   * @param pileNumber the column index number within the pile
   * @return boolean indicating if the index is valid.
   */
  abstract boolean isValidCardIndex(int cardIndex, int pileNumber);

  /**
   * Returns the size, i.e. the number of columns in a pile.
   *
   * @return the size of pile.
   */
  abstract int getPileSize();

  /**
   * Checks if the given pile number is valid for this pile.
   *
   * @param pileNumber the pile number to be checked.
   * @return boolean indicating if the pile number is valid.
   */
  protected boolean isValidPileNumber(int pileNumber) {
    return pileNumber >= 0 && pileNumber <= this.getPileSize() - 1;
  }

}
