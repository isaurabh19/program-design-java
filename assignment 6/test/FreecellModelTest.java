import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import freecell.model.Card;
import freecell.model.Cards;
import freecell.model.CardsImpl;
import freecell.model.FreecellModel;
import freecell.model.FreecellOperations;
import freecell.model.PileType;
import freecell.model.Suits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FreecellModelTest {
  private FreecellOperations<Cards> freecellGame;
  private FreecellOperations<Cards> freecellGameMoveTest;
  FreecellOperations<Cards> freecellGameLocal;

  private final String[] deckOfCardsArray = {"A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥",
    "10♥", "J♥", "Q♥", "K♥", "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦",
    "J♦", "Q♦", "K♦", "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠",
    "Q♠", "K♠", "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"};

  /**
   * Set up for ease.
   */
  @Before
  public void setup() {
    freecellGame = FreecellModel.getBuilder().build();

    freecellGameMoveTest = FreecellModel.getBuilder().opens(8).cascades(8).build();
    List<Cards> newDeck = freecellGameMoveTest.getDeck();
    freecellGameMoveTest.startGame(newDeck, false);

  }

  /**
   * Move before game is started.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveBeforeStartGame() {
    FreecellOperations<Cards> freecellGameMoveBeforeStart;
    freecellGameMoveBeforeStart = FreecellModel.getBuilder().opens(8).cascades(8).build();
    freecellGameMoveBeforeStart.move(PileType.CASCADE, 0, 3,
            PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfSourcePileCascade() {
    freecellGameMoveTest.move(PileType.CASCADE, -2, 3, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfSourcePileOpen() {
    freecellGameMoveTest.move(PileType.OPEN, -2, 3, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfSourcePileFoundation() {
    freecellGameMoveTest.move(PileType.FOUNDATION, -2, 3, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfCardNumberCascade() {
    freecellGameMoveTest.move(PileType.FOUNDATION, 2, -3, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfCardNumberOpen() {
    freecellGameMoveTest.move(PileType.OPEN, 2, -3, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfCardNumberFoundation() {
    freecellGameMoveTest.move(PileType.OPEN, 2, -3, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfDestinationPileNumberCascade() {
    freecellGameMoveTest.move(PileType.OPEN, 2, 3, PileType.CASCADE, -9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfDestinationPileNumberOpen() {
    freecellGameMoveTest.move(PileType.OPEN, 2, 3, PileType.OPEN, -9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfDestinationPileNumberFoundation() {
    freecellGameMoveTest.move(PileType.OPEN, 2, 3, PileType.FOUNDATION, -9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSourcePileType() {
    freecellGameMoveTest.move(null, 2,
            3, PileType.OPEN, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullDestinationPileType() {
    freecellGameMoveTest.move(PileType.OPEN, 2, 3, null, 9);
  }

  /**
   * Successful moves from C-C.
   * Cascade to cascade movement with red suit on black suit.
   */
  @Test
  public void testC_CRedOnBlack() {
    freecellGameLocal = FreecellModel.getBuilder().opens(3).cascades(8).build();
    List<Cards> deck = freecellGameLocal.getDeck();
    freecellGameLocal.startGame(deck, false);

    //9DIAMOND on 10CLUB
    freecellGameLocal.move(PileType.CASCADE, 5, 5, PileType.OPEN, 0);
    freecellGameLocal.move(PileType.CASCADE, 5, 4, PileType.OPEN, 1);
    freecellGameLocal.move(PileType.CASCADE, 5, 3, PileType.OPEN, 2);
    freecellGameLocal.move(PileType.CASCADE, 5, 2, PileType.CASCADE, 0);
    assertEquals(expectedOutput9D10C, freecellGameLocal.getGameState());
  }

  /**
   * Cascade to cascade movement with black suit on red suit.
   */
  @Test
  public void testC_CBlackOnRed() {
    FreecellOperations<Cards> freecellGameLocal;
    freecellGameLocal = FreecellModel.getBuilder().opens(3).cascades(8).build();
    List<Cards> deck = freecellGameLocal.getDeck();
    freecellGameLocal.startGame(deck, false);
    //QCLUB on KDIAMOND
    freecellGameLocal.move(PileType.CASCADE, 1, 6, PileType.OPEN, 0);
    freecellGameLocal.move(PileType.CASCADE, 1, 5, PileType.OPEN, 1);
    freecellGameLocal.move(PileType.CASCADE, 1, 4, PileType.OPEN, 2);
    freecellGameLocal.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 1);
    assertEquals(expectedOutputQCKD, freecellGameLocal.getGameState());
  }


  /**
   * Unsuccessful moves from C-C.
   * cascade to cascade - red suit on red suit.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testC_CRedOnRed() {
    FreecellOperations<Cards> freecellGameLocal;
    freecellGameLocal = FreecellModel.getBuilder().opens(6).build();
    List<Cards> deck = freecellGameLocal.getDeck();
    freecellGameLocal.startGame(deck, false);
    //QDIAMOND on KDIAMOND
    freecellGameLocal.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    freecellGameLocal.move(PileType.CASCADE, 0, 5, PileType.OPEN, 1);
    freecellGameLocal.move(PileType.CASCADE, 0, 4, PileType.OPEN, 2);
    freecellGameLocal.move(PileType.CASCADE, 1, 6, PileType.OPEN, 3);
    freecellGameLocal.move(PileType.CASCADE, 1, 5, PileType.OPEN, 4);
    freecellGameLocal.move(PileType.CASCADE, 1, 4, PileType.OPEN, 5);
    freecellGameLocal.move(PileType.CASCADE, 0, 3, PileType.CASCADE, 1);
  }

  /**
   * cascade to cascade - black suit on black suit.
   */

  @Test(expected = IllegalArgumentException.class)
  public void testC_CBlackOnBlack() {
    freecellGame = FreecellModel.getBuilder().opens(1).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, 0, 6, PileType.CASCADE, 1);
  }

  /**
   * cascade to cascade - value of card greater than current.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testC_CMovedValueGreaterThanCurrent() {
    freecellGame = FreecellModel.getBuilder().opens(52).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 0);
    //JCLUB On 10CLUB
  }

  /**
   * cascade to cascade - card not last on the deck.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testC_CCardToBeMovedNotLastCardInDeck() {
    freecellGame = FreecellModel.getBuilder().opens(52).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 0);
  }

  /**
   * cascade to cascade - source pile out of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testC_CSourcePileNumberOutOfBoundMore() {
    freecellGame = FreecellModel.getBuilder().opens(52).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, 82, 6, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testC_CDestinationPileNumberOutOfBoundMore() {
    freecellGame = FreecellModel.getBuilder().opens(52).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, 1, 6, PileType.CASCADE, 80);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testC_CSourcePileNumberOutOfBoundLess() {
    freecellGame = FreecellModel.getBuilder().opens(52).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, -1, 6, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testC_CDestinationPileNumberOutOfBoundLess() {
    freecellGame = FreecellModel.getBuilder().opens(52).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, 2, 6, PileType.CASCADE, -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeIndexOfSourcePile() {
    freecellGame = FreecellModel.getBuilder().opens(52).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, -2, 6, PileType.CASCADE, 5);
  }

  @Test
  public void testNegativeIndexOfCardNumber() {
    freecellGame = FreecellModel.getBuilder().opens(1).cascades(13).build();
    assertEquals("", freecellGame.getGameState());
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    assertEquals(initialGameWith1O13C.toString(), freecellGame.getGameState());
    try {
      freecellGame.move(PileType.CASCADE, 2, -6, PileType.CASCADE, 5);
      fail("Do not run.");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGame.getGameState());
    }
  }

  @Test
  public void testNegativeIndexOfDestinationPileNumber() {
    freecellGame = FreecellModel.getBuilder().opens(1).cascades(13).build();
    assertEquals("", freecellGame.getGameState());
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    assertEquals(initialGameWith1O13C.toString(), freecellGame.getGameState());
    try {
      freecellGame.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 5);
      fail("Do not run.");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGame.getGameState());
    }
  }

  @Test
  public void testC_CPileNumberOutOfBoundSource() {
    freecellGame = FreecellModel.getBuilder().opens(1).cascades(13).build();
    assertEquals("", freecellGame.getGameState());
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    assertEquals(initialGameWith1O13C.toString(), freecellGame.getGameState());
    try {
      freecellGame.move(PileType.CASCADE, 14, 6, PileType.CASCADE, 5);
      fail("Do not run.");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGame.getGameState());
    }
  }

  @Test
  public void testC_CPileNumberOutOfBoundDestination() {
    freecellGame = FreecellModel.getBuilder().opens(1).cascades(13).build();
    assertEquals("", freecellGame.getGameState());
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    assertEquals(initialGameWith1O13C.toString(), freecellGame.getGameState());
    try {
      freecellGame.move(PileType.CASCADE, 4, 6, PileType.CASCADE, 15);
      fail("Do not run.");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGame.getGameState());
    }
  }

  /**
   * Unsuccessful movement from cascade to foundation.
   */

  @Test
  public void testCascadeToFoundationWithWrongSuit() {
    initializeGameWith1Open13Cascades();
    for (int i = 0; i < 4; i++) {
      assertFalse(freecellGameLocal.isGameOver());
      freecellGameLocal.move(PileType.CASCADE, i, 3, PileType.FOUNDATION, 0);
      assertFalse(freecellGameLocal.isGameOver());
    }
    freecellGameLocal.move(PileType.CASCADE, 4, 3, PileType.OPEN, 0);
    assertFalse(freecellGameLocal.isGameOver());
    assertEquals(strTestCascadeToFoundationWithWrongSuit.toString(), freecellGameLocal
            .getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, 4, 2, PileType.FOUNDATION, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Move not possible.", e.getMessage());
      assertFalse(freecellGameLocal.isGameOver());
      assertEquals(strTestCascadeToFoundationWithWrongSuit.toString(), freecellGameLocal
              .getGameState());
    }
  }

  @Test
  public void testFailedMoveValueEqualToValueOfPile() {
    initializeGameWith1Open13Cascades();
    for (int i = 0; i < 4; i++) {
      assertFalse(freecellGameLocal.isGameOver());
      freecellGameLocal.move(PileType.CASCADE, i, 3, PileType.FOUNDATION, 0);
      assertFalse(freecellGameLocal.isGameOver());
    }
    freecellGameLocal.move(PileType.CASCADE, 4, 3, PileType.OPEN, 0);
    assertFalse(freecellGameLocal.isGameOver());
    assertEquals(strTestCascadeToFoundationWithWrongSuit.toString(), freecellGameLocal
            .getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, 3, 2, PileType.FOUNDATION, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Move not possible.", e.getMessage());
      assertFalse(freecellGameLocal.isGameOver());
      assertEquals(strTestCascadeToFoundationWithWrongSuit.toString(), freecellGameLocal
              .getGameState());
    }
  }

  @Test
  public void testFailedMoveValueLessThanValueOfPile() {
    initializeGameWith1Open13Cascades();
    for (int i = 0; i < 4; i++) {
      assertFalse(freecellGameLocal.isGameOver());
      freecellGameLocal.move(PileType.CASCADE, i, 3, PileType.FOUNDATION, 0);
      assertFalse(freecellGameLocal.isGameOver());
    }
    freecellGameLocal.move(PileType.CASCADE, 4, 3, PileType.OPEN, 0);
    assertFalse(freecellGameLocal.isGameOver());
    assertEquals(strTestCascadeToFoundationWithWrongSuit.toString(), freecellGameLocal
            .getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, 2, 2, PileType.FOUNDATION, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Move not possible.", e.getMessage());
      assertFalse(freecellGameLocal.isGameOver());
      assertEquals(strTestCascadeToFoundationWithWrongSuit.toString(), freecellGameLocal
              .getGameState());
    }
  }

  @Test
  public void testFailedMoveCardNotAtEndOfCascadePile() {
    initializeGameWith1Open13Cascades();
    assertFalse(freecellGameLocal.isGameOver());
    assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, 0, 2, PileType.FOUNDATION, 0);
      fail("Do not run");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
      assertEquals("Not a valid cascade pile source", e.getMessage());
    }
  }

  @Test
  public void testFailedMovePileNumberOutOfBound() {
    //2 of hearts is put on 3 of diamonds
    initializeGameWith1Open13Cascades();
    assertFalse(freecellGameLocal.isGameOver());
    assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, 40, 3, PileType.FOUNDATION, 0);
      fail("Do not run");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
      assertEquals("Not a valid cascade pile source", e.getMessage());
    }
  }

  @Test
  public void testMoveNegativeSourcePileNumber() {
    initializeGameWith1Open13Cascades();
    assertFalse(freecellGameLocal.isGameOver());
    assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, -4, 3, PileType.FOUNDATION, 0);
      fail("Do not run");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
      assertEquals("Not a valid cascade pile source", e.getMessage());
    }
  }

  @Test
  public void testMoveNegativeDestPileNumber() {
    initializeGameWith1Open13Cascades();
    assertFalse(freecellGameLocal.isGameOver());
    assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, 0, 3, PileType.FOUNDATION, -8);
      fail("Do not run");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
      assertEquals("Enter valid values for PileType and Pile Number.", e.getMessage());
    }

  }

  @Test
  public void testMoveNegativeCardIndex() {
    initializeGameWith1Open13Cascades();
    assertFalse(freecellGameLocal.isGameOver());
    assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
    try {
      freecellGameLocal.move(PileType.CASCADE, 0, -3, PileType.FOUNDATION, 0);
      fail("Do not run");
    } catch (IllegalArgumentException e) {
      assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
      assertEquals("Not a valid cascade pile source", e.getMessage());
    }
  }


  private void initializeGameWith1Open13Cascades() {
    freecellGameLocal = FreecellModel.getBuilder().opens(1).cascades(13).build();
    assertEquals("", freecellGameLocal.getGameState());
    List<Cards> deck = freecellGameLocal.getDeck();
    freecellGameLocal.startGame(deck, false);
    assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
  }

  /**
   * Tests the movement from cascade to the foundation with isGameOver at every point.
   */
  @Test
  public void testMoveFromCascadeToFoundation() {
    freecellGameLocal = FreecellModel.getBuilder().opens(1).cascades(13).build();
    assertEquals("", freecellGameLocal.getGameState());
    List<Cards> deck = freecellGameLocal.getDeck();
    freecellGameLocal.startGame(deck, false);
    assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < 13; i++) {
        assertFalse(freecellGameLocal.isGameOver());
        freecellGameLocal.move(PileType.CASCADE, i, 3 - j, PileType.FOUNDATION, j);
      }
    }
    assertTrue(freecellGameLocal.isGameOver());
    assertEquals(completedGame1.toString(), freecellGameLocal.getGameState());
  }

  /**
   * Test movement from the cascade to open then to foundation.
   * Successful movement from open to foundation hard coded.
   */
  @Test
  public void cascadeToOpenToFoundation() {
    freecellGameLocal = FreecellModel.getBuilder().opens(1).cascades(13).build();
    assertEquals("", freecellGameLocal.getGameState());
    List<Cards> deck = freecellGameLocal.getDeck();
    freecellGameLocal.startGame(deck, false);
    assertEquals(initialGameWith1O13C.toString(), freecellGameLocal.getGameState());
    for (int i = 0; i < 13; i++) {
      freecellGameLocal.move(PileType.CASCADE, i, 3, PileType.OPEN, 0);
      assertFalse(freecellGameLocal.isGameOver());
      freecellGameLocal.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
      assertFalse(freecellGameLocal.isGameOver());
    }
    assertEquals(cascadeToOpenToFoundationClubDone.toString(), freecellGameLocal.getGameState());
    for (int i = 0; i < 13; i++) {
      freecellGameLocal.move(PileType.CASCADE, i, 2, PileType.OPEN, 0);
      assertFalse(freecellGameLocal.isGameOver());
      freecellGameLocal.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 1);
      assertFalse(freecellGameLocal.isGameOver());
    }
    assertEquals(getCascadeToOpenToFoundationSpadeDone.toString(), freecellGameLocal
            .getGameState());
    for (int i = 0; i < 13; i++) {
      freecellGameLocal.move(PileType.CASCADE, i, 1, PileType.OPEN, 0);
      assertFalse(freecellGameLocal.isGameOver());
      freecellGameLocal.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 2);
      assertFalse(freecellGameLocal.isGameOver());
    }
    for (int i = 0; i < 13; i++) {
      freecellGameLocal.move(PileType.CASCADE, i, 0, PileType.OPEN, 0);
      assertFalse(freecellGameLocal.isGameOver());
      freecellGameLocal.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 3);
    }
    assertTrue(freecellGameLocal.isGameOver());
    assertEquals(completedGame1.toString(), freecellGameLocal.getGameState());
  }


  @Test
  public void testOpenToFoundationSample() {
    FreecellOperations<Cards> freecellGameLocal;
    freecellGameLocal = FreecellModel.getBuilder().opens(6).cascades(16).build();
    List<Cards> deck = freecellGameLocal.getDeck();
    freecellGameLocal.startGame(deck, false);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "O5:\n" +
            "O6:\n" +
            "C1: A♥, 4♦, 7♠, 10♣\n" +
            "C2: 2♥, 5♦, 8♠, J♣\n" +
            "C3: 3♥, 6♦, 9♠, Q♣\n" +
            "C4: 4♥, 7♦, 10♠, K♣\n" +
            "C5: 5♥, 8♦, J♠\n" +
            "C6: 6♥, 9♦, Q♠\n" +
            "C7: 7♥, 10♦, K♠\n" +
            "C8: 8♥, J♦, A♣\n" +
            "C9: 9♥, Q♦, 2♣\n" +
            "C10: 10♥, K♦, 3♣\n" +
            "C11: J♥, A♠, 4♣\n" +
            "C12: Q♥, 2♠, 5♣\n" +
            "C13: K♥, 3♠, 6♣\n" +
            "C14: A♦, 4♠, 7♣\n" +
            "C15: 2♦, 5♠, 8♣\n" +
            "C16: 3♦, 6♠, 9♣", freecellGameLocal.getGameState());
    freecellGameLocal.move(PileType.CASCADE, 7, 2, PileType.OPEN, 0);
    assertEquals("F1:\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1: A♣\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "O5:\n" +
            "O6:\n" +
            "C1: A♥, 4♦, 7♠, 10♣\n" +
            "C2: 2♥, 5♦, 8♠, J♣\n" +
            "C3: 3♥, 6♦, 9♠, Q♣\n" +
            "C4: 4♥, 7♦, 10♠, K♣\n" +
            "C5: 5♥, 8♦, J♠\n" +
            "C6: 6♥, 9♦, Q♠\n" +
            "C7: 7♥, 10♦, K♠\n" +
            "C8: 8♥, J♦\n" +
            "C9: 9♥, Q♦, 2♣\n" +
            "C10: 10♥, K♦, 3♣\n" +
            "C11: J♥, A♠, 4♣\n" +
            "C12: Q♥, 2♠, 5♣\n" +
            "C13: K♥, 3♠, 6♣\n" +
            "C14: A♦, 4♠, 7♣\n" +
            "C15: 2♦, 5♠, 8♣\n" +
            "C16: 3♦, 6♠, 9♣", freecellGameLocal.getGameState());
    freecellGameLocal.move(PileType.OPEN, 0, 0, PileType.FOUNDATION, 0);
    assertEquals("F1: A♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "O5:\n" +
            "O6:\n" +
            "C1: A♥, 4♦, 7♠, 10♣\n" +
            "C2: 2♥, 5♦, 8♠, J♣\n" +
            "C3: 3♥, 6♦, 9♠, Q♣\n" +
            "C4: 4♥, 7♦, 10♠, K♣\n" +
            "C5: 5♥, 8♦, J♠\n" +
            "C6: 6♥, 9♦, Q♠\n" +
            "C7: 7♥, 10♦, K♠\n" +
            "C8: 8♥, J♦\n" +
            "C9: 9♥, Q♦, 2♣\n" +
            "C10: 10♥, K♦, 3♣\n" +
            "C11: J♥, A♠, 4♣\n" +
            "C12: Q♥, 2♠, 5♣\n" +
            "C13: K♥, 3♠, 6♣\n" +
            "C14: A♦, 4♠, 7♣\n" +
            "C15: 2♦, 5♠, 8♣\n" +
            "C16: 3♦, 6♠, 9♣", freecellGameLocal.getGameState());
    freecellGameLocal.move(PileType.CASCADE, 8, 2, PileType.FOUNDATION, 0);
    for (int i = 0; i <= 6; i++) {
      freecellGameLocal.move(PileType.CASCADE, 9 + i, 2, PileType.FOUNDATION, 0);
    }
    for (int i = 0; i <= 3; i++) {
      freecellGameLocal.move(PileType.CASCADE, i, 3, PileType.FOUNDATION, 0);
    }
    assertEquals("F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
            "F2:\n" +
            "F3:\n" +
            "F4:\n" +
            "O1:\n" +
            "O2:\n" +
            "O3:\n" +
            "O4:\n" +
            "O5:\n" +
            "O6:\n" +
            "C1: A♥, 4♦, 7♠\n" +
            "C2: 2♥, 5♦, 8♠\n" +
            "C3: 3♥, 6♦, 9♠\n" +
            "C4: 4♥, 7♦, 10♠\n" +
            "C5: 5♥, 8♦, J♠\n" +
            "C6: 6♥, 9♦, Q♠\n" +
            "C7: 7♥, 10♦, K♠\n" +
            "C8: 8♥, J♦\n" +
            "C9: 9♥, Q♦\n" +
            "C10: 10♥, K♦\n" +
            "C11: J♥, A♠\n" +
            "C12: Q♥, 2♠\n" +
            "C13: K♥, 3♠\n" +
            "C14: A♦, 4♠\n" +
            "C15: 2♦, 5♠\n" +
            "C16: 3♦, 6♠", freecellGameLocal.getGameState());

  }


  private final String expectedOutput9D10C = "F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1: 7♣\n" +
          "O2: Q♠\n" +
          "O3: 4♠\n" +
          "C1: A♥, 9♥, 4♦, Q♦, 7♠, 2♣, 10♣, 9♦\n" +
          "C2: 2♥, 10♥, 5♦, K♦, 8♠, 3♣, J♣\n" +
          "C3: 3♥, J♥, 6♦, A♠, 9♠, 4♣, Q♣\n" +
          "C4: 4♥, Q♥, 7♦, 2♠, 10♠, 5♣, K♣\n" +
          "C5: 5♥, K♥, 8♦, 3♠, J♠, 6♣\n" +
          "C6: 6♥, A♦\n" +
          "C7: 7♥, 2♦, 10♦, 5♠, K♠, 8♣\n" +
          "C8: 8♥, 3♦, J♦, 6♠, A♣, 9♣";

  private final String expectedOutputQCKD = "F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1: J♣\n" +
          "O2: 3♣\n" +
          "O3: 8♠\n" +
          "C1: A♥, 9♥, 4♦, Q♦, 7♠, 2♣, 10♣\n" +
          "C2: 2♥, 10♥, 5♦, K♦, Q♣\n" +
          "C3: 3♥, J♥, 6♦, A♠, 9♠, 4♣\n" +
          "C4: 4♥, Q♥, 7♦, 2♠, 10♠, 5♣, K♣\n" +
          "C5: 5♥, K♥, 8♦, 3♠, J♠, 6♣\n" +
          "C6: 6♥, A♦, 9♦, 4♠, Q♠, 7♣\n" +
          "C7: 7♥, 2♦, 10♦, 5♠, K♠, 8♣\n" +
          "C8: 8♥, 3♦, J♦, 6♠, A♣, 9♣";

  private StringBuilder initialGameWith1O13C = new StringBuilder("F1:\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1:\n" +
          "C1: A♥, A♦, A♠, A♣\n" +
          "C2: 2♥, 2♦, 2♠, 2♣\n" +
          "C3: 3♥, 3♦, 3♠, 3♣\n" +
          "C4: 4♥, 4♦, 4♠, 4♣\n" +
          "C5: 5♥, 5♦, 5♠, 5♣\n" +
          "C6: 6♥, 6♦, 6♠, 6♣\n" +
          "C7: 7♥, 7♦, 7♠, 7♣\n" +
          "C8: 8♥, 8♦, 8♠, 8♣\n" +
          "C9: 9♥, 9♦, 9♠, 9♣\n" +
          "C10: 10♥, 10♦, 10♠, 10♣\n" +
          "C11: J♥, J♦, J♠, J♣\n" +
          "C12: Q♥, Q♦, Q♠, Q♣\n" +
          "C13: K♥, K♦, K♠, K♣");

  private StringBuilder completedGame1 = new StringBuilder("F1: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣," +
          " 9♣, 10♣, J♣, Q♣, K♣\n" +
          "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
          "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n" +
          "F4: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n" +
          "O1:\n" +
          "C1:\n" +
          "C2:\n" +
          "C3:\n" +
          "C4:\n" +
          "C5:\n" +
          "C6:\n" +
          "C7:\n" +
          "C8:\n" +
          "C9:\n" +
          "C10:\n" +
          "C11:\n" +
          "C12:\n" +
          "C13:");
  private StringBuilder getCascadeToOpenToFoundationSpadeDone = new StringBuilder("F1: A♣, 2♣," +
          " 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
          "F2: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n" +
          "F3:\n" +
          "F4:\n" +
          "O1:\n" +
          "C1: A♥, A♦\n" +
          "C2: 2♥, 2♦\n" +
          "C3: 3♥, 3♦\n" +
          "C4: 4♥, 4♦\n" +
          "C5: 5♥, 5♦\n" +
          "C6: 6♥, 6♦\n" +
          "C7: 7♥, 7♦\n" +
          "C8: 8♥, 8♦\n" +
          "C9: 9♥, 9♦\n" +
          "C10: 10♥, 10♦\n" +
          "C11: J♥, J♦\n" +
          "C12: Q♥, Q♦\n" +
          "C13: K♥, K♦");
  private StringBuilder cascadeToOpenToFoundationClubDone = new StringBuilder("F1: A♣, 2♣, 3♣," +
          " 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1:\n" +
          "C1: A♥, A♦, A♠\n" +
          "C2: 2♥, 2♦, 2♠\n" +
          "C3: 3♥, 3♦, 3♠\n" +
          "C4: 4♥, 4♦, 4♠\n" +
          "C5: 5♥, 5♦, 5♠\n" +
          "C6: 6♥, 6♦, 6♠\n" +
          "C7: 7♥, 7♦, 7♠\n" +
          "C8: 8♥, 8♦, 8♠\n" +
          "C9: 9♥, 9♦, 9♠\n" +
          "C10: 10♥, 10♦, 10♠\n" +
          "C11: J♥, J♦, J♠\n" +
          "C12: Q♥, Q♦, Q♠\n" +
          "C13: K♥, K♦, K♠");

  private StringBuilder strTestCascadeToFoundationWithWrongSuit = new StringBuilder("F1: A♣, 2♣," +
          " 3♣, 4♣\n" +
          "F2:\n" +
          "F3:\n" +
          "F4:\n" +
          "O1: 5♣\n" +
          "C1: A♥, A♦, A♠\n" +
          "C2: 2♥, 2♦, 2♠\n" +
          "C3: 3♥, 3♦, 3♠\n" +
          "C4: 4♥, 4♦, 4♠\n" +
          "C5: 5♥, 5♦, 5♠\n" +
          "C6: 6♥, 6♦, 6♠, 6♣\n" +
          "C7: 7♥, 7♦, 7♠, 7♣\n" +
          "C8: 8♥, 8♦, 8♠, 8♣\n" +
          "C9: 9♥, 9♦, 9♠, 9♣\n" +
          "C10: 10♥, 10♦, 10♠, 10♣\n" +
          "C11: J♥, J♦, J♠, J♣\n" +
          "C12: Q♥, Q♦, Q♠, Q♣\n" +
          "C13: K♥, K♦, K♠, K♣");

  @Test
  public void testSizeOfDeck() {
    List<Cards> deck = freecellGame.getDeck();
    assertEquals(52, deck.size());
  }

  @Test
  public void testPresenceOfAllCards() {
    List<Cards> deck = freecellGame.getDeck();
    assertEquals(52, deck.size());
    //System.out.println(deck.stream().map(s->s.toString()).collect(Collectors.joining(",")));
    for (int i = 0; i <= 51; i++) {
      //System.out.println(deck.get(i).toString());
      assertEquals(deckOfCardsArray[i], deck.get(i).toString());
    }
  }

  @Test
  public void testIsGameOverBeforeStart() {
    assertFalse(freecellGame.isGameOver());
  }

  @Test
  public void testIsGameOverAfterStartGameNotOver() {
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, true);
    assertFalse(freecellGame.isGameOver());
    freecellGame.startGame(deck, false);
    assertFalse(freecellGame.isGameOver());
  }

  @Test
  public void testIsGameOverConsecutiveAndStartGameAfterGameOver() {
    freecellGame = FreecellModel.getBuilder().cascades(52).opens(1).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    int k = -1;
    for (int j = 0; j < 52; j++) {
      if (j % 13 == 0) {
        k++;
      }
      freecellGame.move(PileType.CASCADE, j, 0, PileType.FOUNDATION, k);
    }
    assertTrue(freecellGame.isGameOver());
    assertTrue(freecellGame.isGameOver());
    freecellGame.startGame(deck, false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    gameState.add("O1:");
    for (int i = 0; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(String.join("\n", gameState), freecellGame.getGameState());
  }

  @Test
  public void testStartGameUnShuffled() {
    freecellGame = FreecellModel.getBuilder().cascades(52).opens(1).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    gameState.add("O1:");
    for (int i = 0; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(String.join("\n", gameState), freecellGame.getGameState());
  }

  @Test
  public void testStartGameShuffledUnshuffledNotSame() {
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, true);
    String shuffledGame = freecellGame.getGameState();
    freecellGame = FreecellModel.getBuilder().build();
    //List<Cards> deck2 = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    String unshuffledGame = freecellGame.getGameState();
    assertNotEquals(shuffledGame, unshuffledGame);
  }

  @Test
  public void testStartGameConsecutive() {
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    String gameState1 = freecellGame.getGameState();
    freecellGame.startGame(deck, false);
    String gameState2 = freecellGame.getGameState();
    assertEquals(gameState1, gameState2);
  }

  @Test
  public void testNewStartGameAfterMove() {
    freecellGame = FreecellModel.getBuilder().cascades(52).opens(1).build();
    List<Cards> deck = freecellGame.getDeck();
    freecellGame.startGame(deck, false);
    freecellGame.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    String gameState1 = freecellGame.getGameState();
    freecellGame.startGame(deck, false);
    String gameState2 = freecellGame.getGameState();
    assertNotEquals(gameState1, gameState2);

    List<String> gameState = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      gameState.add("F" + (i + 1) + ":");
    }
    gameState.add("O1:");
    for (int i = 0; i < 52; i++) {
      gameState.add("C" + (i + 1) + ": " + deckOfCardsArray[i]);
    }
    assertEquals(String.join("\n", gameState), freecellGame.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckLessThan52() {
    List<Cards> deck = freecellGame.getDeck();
    deck.remove(deck.size() - 1);
    freecellGame.startGame(deck, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckMoreThan52() {
    List<Cards> deck = freecellGame.getDeck();
    deck.add(new CardsImpl(Suits.NULL_CARD, Card.NULL, 1));
    freecellGame.startGame(deck, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeckNull() {
    freecellGame.startGame(null, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck52Duplicates() {
    List<Cards> deck = freecellGame.getDeck();
    deck.add(new CardsImpl(Suits.CLUBS, Card.ACE, 1));
    freecellGame.startGame(deck, false);
  }

  @Test
  public void testInvalidStartGameGameStateCheck() {
    List<Cards> deck = freecellGame.getDeck();
    deck.add(new CardsImpl(Suits.CLUBS, Card.ACE, 1));
    try {
      freecellGame.startGame(deck, false);
    } catch (IllegalArgumentException e) {
      assertEquals("", freecellGame.getGameState());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameInvalidDeck52InvalidSuit() {
    List<Cards> deck = freecellGame.getDeck();
    deck.remove(0);
    deck.remove(1);
    deck.add(new CardsImpl(Suits.NULL_CARD, Card.ACE, 1));
    deck.add(new CardsImpl(Suits.HEARTS, Card.TWO, 2));
    freecellGame.startGame(deck, false);
  }

  @Test
  public void testGetGameStateAfterGetDeck() {
    List<Cards> deck = freecellGame.getDeck();
    assertEquals("", freecellGame.getGameState());
  }
}