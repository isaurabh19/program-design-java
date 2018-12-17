/**
 * A class that represents questions whose answer choices are restricted to two values : YES, NO
 * only. Input answer to the questions of this type can be case insensitive but have to either "yes"
 * or "no". Any other input is considered as invalid and evaluates to incorrect answer. These
 * objects have precedence value 1 meaning they have the highest order of precedence.
 */
public class YesNoQuestion extends AbstractQuestion implements Question {
  //Using enums for type safety. Ensures consistency of values while creating the object.

  /**
   * An enum that allows users who create the object to pass either of the enum values as correct
   * answer.
   */
  public enum Choices {
    YES, NO;
  }

  /**
   * Constructs a new YesNoQuestion object with a question text, correct answer and default set of
   * answer choices: YES, NO.
   *
   * @param questionText  the text of the question.
   * @param correctAnswer the correct answer of the question
   * @throws IllegalArgumentException when empty question string is passed.
   */
  public YesNoQuestion(String questionText, Choices correctAnswer) throws IllegalArgumentException {
    if (questionText.isEmpty()) {
      throw new IllegalArgumentException("Empty question Text not allowed");
    }
    this.questionText = questionText;
    this.correctAnswer = correctAnswer.toString();
    this.priority = 1;
    this.choices = new String[]{Choices.YES.toString(), Choices.NO.toString()};
  }

  @Override
  public String evaluateAnswer(String answer) {
    this.inputAnswer = answer;
    if (answer.equalsIgnoreCase(this.correctAnswer)) {
      return CORRECT;
    }
    return INCORRECT;
  }
}
