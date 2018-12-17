package freecell.model;

import java.util.List;

/**
 * This class implements the FreecellOperations interface. It represents the model of the game with
 * three piles: Open, Foundation and Cascade. Each pile is a Collection of Cards interface. A game
 * has 52 cards belonging to 4 suits. It provides a builder method to instantiate an object of this
 * class. A game can be played by getting a deck, starting the game and making valid moves.
 * <br>
 * The game is started by allocating a valid deck of cards among the cascade piles in a round robin
 * fashion. Observer method to get the game state will return an empty string if called before a
 * game has started. The game is over when the foundation piles have 52 cards in total. The status
 * of game over is false otherwise. An attempt to make a move before starting game will throw an
 * exception. Calling startGame() during a game will restart the game. Trying to move the same card
 * on the same pile and on the same place will be considered as an Invalid move.
 * <br>
 * This implementation of the game allows for multiple cards to be the moved from one cascade pile
 * to another cascade pile. It can move multiple cards if and only if: 1. The cards to be moved form
 * a correct build. 2. The source card forms a correct build with the card at the given destination.
 * 3. There empty open and cascade piles equal to (N+1)*2^K, where N,K are empty open and cascade
 * piles respectively.
 * <br>
 * In case of an empty cascade pile, any incoming build can be placed.
 */

public class FreecellMultiMoveModel extends AbstractFreecellModel implements
        FreecellOperations<Cards> {

  private FreecellMultiMoveModel(int cascadeSize, int openSize) {
    super(cascadeSize, openSize);
  }

  /**
   * Returns a builder object that can be used to create and instance of this class.
   *
   * @return an instance of this class.
   */
  public static FreecellOperationsBuilder getBuilder() {
    return new FreecellMultiMoveOperationsBuilderImpl();
  }

  @Override
  protected Pile getCascadePile(int cascadeSize, List<Cards> deck) {
    return new CascadeMultiMove(cascadeSize, deck);
  }

  /**
   * A class that implements a FreecellOperationsBuilder. The default values for cascade and open
   * piles is 4 and 1 respectively. Any value less than these is not allowed and will throw an
   * exception.
   */
  public static class FreecellMultiMoveOperationsBuilderImpl extends
          AbstractFreecellOperationsBuilder {

    private FreecellMultiMoveOperationsBuilderImpl() {
      super();
    }

    @Override
    protected FreecellOperations<Cards> getModelInstance(int cascadeSize, int openSize) {
      return new FreecellMultiMoveModel(cascadeSize, openSize);
    }
  }
}
