package freecell.model;

/**
 * This interface represents a Pile in a game of freecell. A pile is any type of collection of
 * cards. Cards can be moved within piles. A pile can be represented in a string format. Every pile
 * string representation will contain a symbol for the pile, the number followed by the contents of
 * the pile in string representation.
 */
public interface Pile {
  /**
   * Returns the string representation of a particular pile. If there are no cards in a pile, then
   * the pile content is represented by empty string.
   *
   * @return state of a pile as string.
   */
  String getPileState();

  /**
   * Calculates the number of elements in a particular column/entry of a pile.
   *
   * @param pileNumber the column number or pile number in the pile whose length is to be returned.
   * @return the length of a column in the pile.
   */
  int getPileLength(int pileNumber);

  /**
   * Moves a card from source pile to destination pile if its a valid move and the source and
   * destination pile numbers and card index are valid.
   *
   * @param sourcePileNumber      the column of the source while from where card would be moved.
   * @param cardIndex             the index position of the card.
   * @param destination           the destination Pile where the card is to be moved.
   * @param destinationPileNumber the pile number in the destination pile.
   */
  void moveTo(int sourcePileNumber, int cardIndex, Pile destination, int destinationPileNumber);

}
