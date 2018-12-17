import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MultipleChoiceQuestionTest {
  private Question question;

  @Test
  public void testGetPriority() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar" +
                    "system?", "1", new String[]{"Earth", "Mars", "Jupiter"}).getPriority(),
            3);
  }

  @Test
  public void testGetQuestionText() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar " +
                    "system?", "1", new String[]{"Earth", "Mars", "Jupiter"}).getQuestionText(),
            "Which is the third planet in our solar system?");
  }

  @Test
  public void testGetAnswerChoice() {
    assertArrayEquals(new MultipleChoiceQuestion("Which is the third planet in our " +
            "solar system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .getAnswerChoices(), new String[]{"Earth", "Mars", "Jupiter"});
  }

  @Test
  public void testValidQuestionCreation() {
    question = new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", "1", new String[]{"Earth", "Mars", "Jupiter"});
    assertEquals(question.getQuestionText(), "Which is the third planet in our solar system" +
            "?");
    question = new MultipleChoiceQuestion("which vitamin do we get from sunlight?",
            "08 ", new String[]{"A1", "B8", "C", "B12", "B9", "B10", "K", "D",});
    assertEquals(question.getQuestionText(), "which vitamin do we get from sunlight?");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThan3Choices() {
    question = new MultipleChoiceQuestion("which vitamin do we get from sunlight?",
            "2", new String[]{"A1", "D",});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoreThan8Choices() {
    question = new MultipleChoiceQuestion("which vitamin do we get from sunlight?",
            "8", new String[]{"A1", "B8", "C", "B12", "B9", "B10", "K", "D", "B11"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOptionNumberOutOfBounds() {
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "0", new String[]{"Earth", "Mars", "Jupiter"});
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "4", new String[]{"Earth", "Mars", "Jupiter"});
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "-2", new String[]{"Earth", "Mars", "Jupiter"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyQuestion() {
    question = new MultipleChoiceQuestion("", "1", new String[]{"Earth",
        "Mars", "Jupiter"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoChoices() {
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "1", new String[]{});

  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyChoices() {
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "1", new String[]{"", "", " "});

  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyAnswer() {
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "", new String[]{"Earth", "Mars", "Jupiter"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAnswerValue() {
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "abc", new String[]{"Earth", "Mars", "Jupiter"});
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "w", new String[]{"Earth", "Mars", "Jupiter"});
    question = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            "system?", "*", new String[]{"Earth", "Mars", "Jupiter"});
  }

  @Test
  public void testAnswerStartsWithZeroAndSpace() {
    assertEquals(new MultipleChoiceQuestion("which vitamin do we get from sunlight?",
            "8", new String[]{"A1", "B8", "C", "B12", "B9", "B10", "K", "D",})
            .evaluateAnswer("08"), "Correct");
    assertEquals(new MultipleChoiceQuestion("which vitamin do we get from sunlight?",
            "8", new String[]{"A1", "B8", "C", "B12", "B9", "B10", "K", "D",})
            .evaluateAnswer("08 "), "Correct");
  }

  @Test
  public void testCorrectAnswer() {
    assertEquals(new MultipleChoiceQuestion("which vitamin do we get from sunlight?",
            "8 ", new String[]{"A1", "B8", "C", "B12", "B9", "B10", "K", "D",})
            .evaluateAnswer("8"), "Correct");
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar" +
            " system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer("1"), "Correct");
  }

  @Test
  public void testIncorrectAnswer() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer("2"), "Incorrect");
  }

  @Test
  public void testIncorrectAnswerWithSpace() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar" +
            " system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer("2 "), "Incorrect");
  }

  @Test
  public void testInavalidSpecialCharAnswer() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer("1@"), "Incorrect");
  }

  @Test
  public void testInavalidAlphabetAnswer() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer("a"), "Incorrect");
  }

  @Test
  public void testInavalidStringAnswer() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer("Earth"), "Incorrect");
  }

  @Test
  public void testInavalidNegativeNumberAnswer() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer("-1"), "Incorrect");
  }

  @Test
  public void testInavalidEmptyAnswer() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer(""), "Incorrect");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testJustSpace() {
    question = new MultipleChoiceQuestion(" ", "1", new String[]{"Earth", "Mars",
        "Jupiter"});
    question = new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", " ", new String[]{"Earth", "Mars", "Jupiter"});
  }

  @Test
  public void testInorrectAnswerAdditionalCharcters() {
    assertEquals(new MultipleChoiceQuestion("Which is the third planet in our solar " +
            "system?", "1", new String[]{"Earth", "Mars", "Jupiter"})
            .evaluateAnswer("1  ."), "Incorrect");
  }
}