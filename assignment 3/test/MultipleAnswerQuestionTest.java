import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MultipleAnswerQuestionTest {
  private Question question;

  @Test
  public void testGetPriority() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .getPriority(), 4);
  }

  @Test
  public void testGetAnswerChoices() {
    assertArrayEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .getAnswerChoices(), new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
  }

  @Test
  public void testGetQuestionText() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .getQuestionText(), "Which of these are moons of Jupiter ?");
  }

  @Test
  public void testValidQuestionCreation() {

    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 4 3", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    assertEquals(question.getQuestionText(), "Which of these are moons of Jupiter ?");
  }

  @Test
  public void testValidDuplicateElementAnswer() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    assertEquals(question.getQuestionText(), "Which of these are moons of Jupiter ?");
  }

  @Test
  public void testValidAnswerWithSpaces() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            " 1 3 4 ", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    assertEquals(question.getQuestionText(), "Which of these are moons of Jupiter ?");
  }

  @Test
  public void testValidAnswersTrailingZero() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "01 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    assertEquals(question.getQuestionText(), "Which of these are moons of Jupiter ?");
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "01 03 04", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    assertEquals(question.getQuestionText(), "Which of these are moons of Jupiter ?");
  }

  @Test
  public void testValidSingleAnswer() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1", new String[]{"Europa", "Titan", "Venus", "Zeus", "Mimas"});
    assertEquals(question.getQuestionText(), "Which of these are moons of Jupiter ?");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessThan3Choices() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1", new String[]{"Europa", "Titan"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoreThan8Choices() {
    question = new MultipleAnswerQuestion("Which of these are perfect sqaures ?", "3 05 ",
            new String[]{"2", "3", "4", "5", "9", "10", "11", "15", "24", "30"});
    question = new MultipleAnswerQuestion("Which of these are perfect sqaures ?",
            "3 05 ", new String[]{"2", "3", "4", "5", "9", "10", "11", "15", ""});
    question = new MultipleAnswerQuestion("Which of these are perfect sqaures ?",
            "3 05 ", new String[]{"2", "3", "4", "5", "9", "10", "11", "15", " "});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidQuestionExtraSpaces() {
    question = new MultipleAnswerQuestion("Which of these are perfect sqaures ?", "3" +
            "   05 ", new String[]{"2", "3", "4", "5", "9", "10", "11", "15", "24", "30"});

  }

  @Test(expected = IllegalArgumentException.class)
  public void testOptionNumberOutOfBound() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 6", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyQuestion() {
    question = new MultipleAnswerQuestion("",
            "1 3 04", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testJustSpace() {
    question = new MultipleAnswerQuestion(" ",
            "1 3 04", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            " ", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoChoices() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyChoices() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{" ", " ", "", " "});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyAnswer() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            " ", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAnswerValue() {
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 $", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    question = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "! # $", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
  }

  @Test
  public void testInvalidSpecialCharAnswer() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3 4 $"), "Incorrect");
  }

  @Test
  public void testInvalidAlphabetAnswer() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3   4 d"), "Incorrect");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("a c d"), "Incorrect");
  }

  @Test
  public void testInvalidStringAnswer() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 Io 4"), "Incorrect");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("Europa io callisto"), "Incorrect");
  }

  @Test
  public void testInvalidNegativeNumberAnswer() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("-1 -3 -4"), "Incorrect");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("-1 3 -4"), "Incorrect");
  }

  @Test
  public void testInvalidEmptyAnswer() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer(""), "Incorrect");
  }

  @Test
  public void testIncorrectAnserInvalidCharacters() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3 % 4"), "Incorrect");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3 4 ="), "Incorrect");
  }

  @Test
  public void testCorrectAnswerDuplicateElements() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3 3 4"), "Correct");
  }

  @Test
  public void testCorrectAnswerOutOfOrder() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 4 3"), "Correct");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("4 3 1"), "Correct");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "4 3 1", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3  4"), "Correct");
  }

  @Test
  public void testCorrectAnswerAscendingOrder() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3 4"), "Correct");
  }

  @Test
  public void testCorrectAnswerDescendingOrder() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "4 3 1", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("4 3 1"), "Correct");
  }

  @Test
  public void testCorrectAnswerLeadingZero() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3 04"), "Correct");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("01  03 04"), "Correct");
  }

  @Test
  public void testCorrectAnswerWithEndSpaces() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer(" 1 3 4 "), "Correct");
  }

  @Test
  public void testIncorrectAnswerMoreThan8OrLessThan3InputAnswers() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3"), "Incorrect");
  }

  @Test
  public void testIncorrectAnswer() {
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 2 4"), "Incorrect");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("134"), "Incorrect");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("4 3 1 2"), "Incorrect");
    assertEquals(new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"})
            .evaluateAnswer("1 3 4 2"), "Incorrect");
  }
}