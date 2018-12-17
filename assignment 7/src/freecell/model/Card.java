package freecell.model;

/**
 * A type of 13 different types of cards in a regular suit and special type for joker(null or blank)
 * card. Each Card has a face value that will be a part of it's string representation. This enum
 * provides method to get the face value of a card. NULL card is represented by 0. ACE card is
 * represented by A. KING card by K. QUEEN card by Q. JACK card by J. All other number cards are
 * represented by their numeric representations.
 */
public enum Card {
  NULL("0"),
  ACE("A"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8"),
  NINE("9"),
  TEN("10"),
  JACK("J"),
  QUEEN("Q"),
  KING("K");
  private final String faceValue;

  private Card(String faceValue) {
    this.faceValue = faceValue;
  }

  /**
   * Returns the face value of a card.
   *
   * @return a string representing the face value.
   */
  public String getFaceValue() {
    return this.faceValue;
  }
}
