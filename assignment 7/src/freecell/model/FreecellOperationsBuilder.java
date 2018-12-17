package freecell.model;

/**
 * This interface represents a builder interface for creating instances of FreecellOperations. It
 * provides methods to set all class instance fields of Freecell game i.e number of cascades and
 * open piles. After setting the field values call build to create the object with the set values.
 */
public interface FreecellOperationsBuilder {

  /**
   * Sets the number of cascade piles in the game.
   *
   * @param cascadeSize the number of cascade piles.
   * @return An instance of this builder with the new value of cascade and remaining set fields.
   */
  FreecellOperationsBuilder cascades(int cascadeSize);

  /**
   * Sets the number of open piles in the game.
   *
   * @param openSize the number of open piles.
   * @return An instance of this builder with the new value of open and remaining set fields.
   */
  FreecellOperationsBuilder opens(int openSize);

  /**
   * Constructs a FreecellOperations object with the set values of its field attributes.
   *
   * @param <K> the card type that a game of freecell will work with
   * @return an instance of FreecellOperations.
   */
  <K> FreecellOperations<K> build();
}