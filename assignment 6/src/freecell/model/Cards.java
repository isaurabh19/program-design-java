package freecell.model;

/**
 * This interface is for the Cards type. This represents each card. It offers operations to get the
 * properties of a card such as suit, color and value. There are two types of cards: regular
 * cards,i.e. cards from ace to king for all four suits, and null cards (also known as joker cards)
 * that have no properties. A string representation of a card should represent the face value of a
 * card as it is seen physically.
 */
public interface Cards {

  /**
   * Returns the suit of a card.
   *
   * @return a string representing suit of the card.
   */
  String getSuit();

  /**
   * Returns the color of the card as black or red.
   *
   * @return string representation of color value.
   */
  String getColor();

  /**
   * Returns an integer value for a card. This value can be used for comparing a card within a
   * suit.
   *
   * @return positive integer comparison value.
   */
  int getValue();

  /**
   * Returns a boolean whether the card is a null card, Otherwise its a regular card.
   *
   * @return a boolean indicating if its a null card.
   */
  boolean isNullCard();
}
