import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests to check object arrays can be maintained in proper order.
 */
public class AbstractQuestionTest {

  private Question questionYN1;
  private Question questionLK1;
  private Question questionMC1;
  private Question questionMA1;
  private Question questionYN2;
  private Question questionLK2;
  private Question questionMC2;
  private Question questionMA2;

  /**
   * Sets up multiple objects of all question types.
   */
  @Before
  public void setUp() {
    questionYN1 = new YesNoQuestion("Is sun a star?", YesNoQuestion.Choices.YES);
    questionLK1 = new LikertQuestion("Growth mindset leads to better personality");
    questionMA1 = new MultipleAnswerQuestion("Which of these are moons of Jupiter ?",
            "1 3 4", new String[]{"Europa", "Titan", "Io", "Callisto", "Mimas"});
    questionMC1 = new MultipleChoiceQuestion("Which is the third planet in our solar" +
            " system?", "1", new String[]{"Earth", "Mars", "Jupiter"});
    questionYN2 = new YesNoQuestion("Is earth flat?", YesNoQuestion.Choices.NO);
    questionLK2 = new LikertQuestion("Money can buy happiness");
    questionMA2 = new MultipleAnswerQuestion("Who among these are mammals?",
            "1 2", new String[]{"Humans", "Monkeys", "Chicken"});
    questionMC2 = new MultipleChoiceQuestion("which vitamin do we get from sunlight?",
            "08 ", new String[]{"A1", "B8", "C", "B12", "B9", "B10", "K", "D",});
  }

  @Test
  public void testArraySort() {
    Question[] questionBank = new Question[9];
    // HAD TO DO THIS BECAUSE THE INDENTATION ERROR WAS NOT SOLVED
    questionBank[0] = questionMA1;
    questionBank[1] = questionYN1;
    questionBank[2] = questionMC2;
    questionBank[3] = questionLK2;
    questionBank[4] = questionLK1;
    questionBank[5] = questionMA2;
    questionBank[6] = questionYN2;
    questionBank[7] = questionMC1;
    questionBank[8] = questionYN2;
    Arrays.sort(questionBank);
    String[] sortedQuestionBank = new String[questionBank.length];
    for (int counter = 0; counter < sortedQuestionBank.length; counter++) {
      sortedQuestionBank[counter] = questionBank[counter].getQuestionText();
    }
    String [] answer = new String[9];
    answer[0] = "Is earth flat?";
    answer[1] = "Is earth flat?";
    answer[2] = "Is sun a star?";
    answer[3] = "Growth mindset leads to better personality";
    answer[4] = "Money can buy happiness";
    answer[5] = "Which is the third planet in our solar system?";
    answer[6] = "which vitamin do we get from sunlight?";
    answer[7] = "Which of these are moons of Jupiter ?";
    answer[8] = "Who among these are mammals";
    assertArrayEquals(sortedQuestionBank,answer);
  }
}