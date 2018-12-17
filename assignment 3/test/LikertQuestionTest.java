import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class LikertQuestionTest {
  private Question question;

  @Test
  public void testGetPriority() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality")
            .getPriority(), 2);
  }

  @Test
  public void testGetQuestionText() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality")
            .getQuestionText(), "Growth mindset leads to better personality");
  }

  @Test
  public void testGetAnswerChoice() {
    String [] choices = new String[]{"Strongly Agree", "Agree", "Neither Agree Nor Disagree",
        "Disagree", "Strongly Disagree"};
    assertArrayEquals(new LikertQuestion("Growth mindset leads to better personality")
            .getAnswerChoices(), choices);
  }

  @Test
  public void testValidQuestionCreation() {
    question = new LikertQuestion("Growth mindset leads to better personality");
    assertEquals(question.getQuestionText(), "Growth mindset leads to better personality");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidQuestionEmpty() {
    question = new LikertQuestion("");
    question = new LikertQuestion(" ");
  }

  @Test
  public void testIncorrectAnswerOutOfBound() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("0"), "Incorrect");
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("6"), "Incorrect");
  }

  @Test
  public void testIncorrectAnswerEmpty() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer(""), "Incorrect");
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer(" "), "Incorrect");
  }

  @Test
  public void testIncorrectAnswerAlphabet() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("a"), "Incorrect");
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer(".#"), "Incorrect");
  }

  @Test
  public void testIncorrectAnswerString() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("Agree"), "Incorrect");
  }

  @Test
  public void testIncorrectAnswerNegative() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("-2"), "Incorrect");
  }

  @Test
  public void testIncorrectAnswerMultiple() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("1 2"), "Incorrect");
  }

  @Test
  public void testIncorrectAnswerAdditionalCharacters() {
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("#1"), "Incorrect");
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("1  ."), "Incorrect");
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("1."), "Incorrect");
    assertEquals(new LikertQuestion("Growth mindset leads to better personality ?")
            .evaluateAnswer("1 a"), "Incorrect");
  }
}