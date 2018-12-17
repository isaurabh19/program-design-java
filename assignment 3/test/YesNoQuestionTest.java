import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class YesNoQuestionTest {
  private Question question;

  @Test
  public void testGetPriority() {
    assertEquals(new YesNoQuestion("Is sun a star?", YesNoQuestion.Choices.YES)
            .getPriority(), 1);
  }

  @Test
  public void testGetQuestionText() {
    assertEquals(new YesNoQuestion("Is sun a star?", YesNoQuestion.Choices.YES)
            .getQuestionText(), "Is sun a star?");
  }

  @Test
  public void testGetAnswerChoice() {
    assertArrayEquals(new YesNoQuestion("Is sun a star?", YesNoQuestion.Choices.YES)
            .getAnswerChoices(), new String[]{YesNoQuestion.Choices.YES.toString(), YesNoQuestion
            .Choices.NO.toString()});
  }

  @Test
  public void testValidQuestionCreation() {
    question = new YesNoQuestion("Is sun a star?", YesNoQuestion.Choices.YES);
    assertEquals(question.getQuestionText(), "Is sun a star?");
    question = new YesNoQuestion("Is earth flat?", YesNoQuestion.Choices.NO);
    assertEquals(question.getQuestionText(), "Is earth flat?");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyQuestionCreation() {
    question = new YesNoQuestion("", YesNoQuestion.Choices.YES);

  }

  @Test
  public void testNoQuestionMark() {
    question = new YesNoQuestion("Is it raining", YesNoQuestion.Choices.NO);
    assertEquals(question.getQuestionText(), "Is it raining");
  }

  @Test
  public void testCorrectYesGivenYes() {
    Question q = new YesNoQuestion("Is Washington DC capital of USA?", YesNoQuestion
            .Choices.YES);
    assertEquals(q.evaluateAnswer("YES"), "Correct");
  }

  @Test
  public void testIgnoreCasCorrectYesGivenYes() {
    Question q = new YesNoQuestion("Is Washington DC capital of USA?", YesNoQuestion
            .Choices.YES);
    assertEquals(q.evaluateAnswer("yes"), "Correct");
    assertEquals(q.evaluateAnswer("Yes"), "Correct");
    assertEquals(q.evaluateAnswer("yEs"), "Correct");
  }

  @Test
  public void testCorrectYesGivenNo() {
    Question q = new YesNoQuestion("Is Washington DC capital of USA?", YesNoQuestion
            .Choices.YES);
    assertEquals(q.evaluateAnswer("NO"), "Incorrect");
    assertEquals(q.evaluateAnswer("no"), "Incorrect");
    assertEquals(q.evaluateAnswer("No"), "Incorrect");
    assertEquals(q.evaluateAnswer("nO"), "Incorrect");
  }

  @Test
  public void testCorrectNoGivenYes() {
    Question q = new YesNoQuestion("Is NYC capital of USA?", YesNoQuestion.Choices.NO);
    assertEquals(q.evaluateAnswer("YES"), "Incorrect");
    assertEquals(q.evaluateAnswer("yes"), "Incorrect");
    assertEquals(q.evaluateAnswer("Yes"), "Incorrect");
  }

  @Test
  public void testCorrectNoGivenNo() {
    Question q = new YesNoQuestion("Is Washington DC capital of USA?", YesNoQuestion
            .Choices.NO);
    assertEquals(q.evaluateAnswer("NO"), "Correct");
    assertEquals(q.evaluateAnswer("no"), "Correct");
    assertEquals(q.evaluateAnswer("No"), "Correct");
  }

  @Test
  public void testInvalidInput() {
    Question q = new YesNoQuestion("Is Washington DC capital of USA?", YesNoQuestion
            .Choices.NO);
    assertEquals(q.evaluateAnswer("abc"), "Incorrect");
    assertEquals(q.evaluateAnswer("1"), "Incorrect");
    assertEquals(q.evaluateAnswer("y"), "Incorrect");

  }
}