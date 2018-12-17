package freecell.model;

import java.util.Objects;

/**
 * This is an implementation of Cards interface. It allows to create a card of a particular suit
 * with a particular face value and a comparision value. Two cards are equal if their string
 * representations are equal. A card is represented in string with its facevalue followed by its
 * unicode symbol.
 */
public class CardsImpl implements Cards {
  private final int value;
  private final Card card;
  private final Suits suit;

  /**
   * Constructs a new object with given suit card and comparison value.
   *
   * @param suit  the suit of the card which is type of Suit enum.
   * @param card  the type of card which is one of the cards defined in Card enum.
   * @param value the comparison value within its suit.
   */
  public CardsImpl(Suits suit, Card card, int value) {
    this.value = value;
    this.card = card;
    this.suit = suit;
  }

  @Override
  public String getSuit() {
    return this.suit.toString();
  }

  @Override
  public String getColor() {
    return this.suit.getColor();
  }

  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public boolean isNullCard() {
    return this.suit.getColor().equals("NONE");
  }

  @Override
  public String toString() {
    return this.card.getFaceValue() + this.suit.getCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.toString());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    return this.toString().equals(obj.toString());
  }
}
