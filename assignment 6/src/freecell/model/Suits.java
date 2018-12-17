package freecell.model;

/**
 * Type for 4 suits of a card deck. Each suit has two properties: color and character symbol. This
 * enum provides an additional suit for representing null(joker) cards in a suit. They have no color
 * and null character('\0') as it's symbol. As in a standard suit, HEARTS and DIAMONDS are red in
 * color and CLUBS and SPADES are black in color.
 */
public enum Suits {
  HEARTS("RED", '\u2665'),
  DIAMONDS("RED", '\u2666'),
  SPADES("BLACK", '\u2660'),
  CLUBS("BLACK", '\u2663'),
  NULL_CARD("NONE", '\u0000');
  private final String color;
  private final char code;

  private Suits(String color, char code) {
    this.color = color;
    this.code = code;
  }

  /**
   * Returns the color of a particular suit as either RED or BLACK for normal suits and NONE for
   * null card.
   *
   * @return string representation of the color value.
   */
  public String getColor() {
    return this.color;
  }

  /**
   * Returns a unicode character symbol of the suit.
   *
   * @return a char representing unicode symbol.
   */
  public char getCode() {
    return this.code;
  }
}
