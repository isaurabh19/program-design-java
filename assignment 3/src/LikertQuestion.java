/**
 * A class that represents questions whose answer choices are restricted to any one value from
 * 5-point Likert scale : (Strongly Agree, Agree, Neither Agree nor Disagree, Disagree, Strongly
 * Disagree) only. Input answer to the questions of this type can be passed as a number between
 * [1,5] ( both inclusive) enclosed as string. Any other input is considered as invalid and
 * evaluates to incorrect answer. These objects have precedence value 2 meaning they have the second
 * highest order of precedence.
 */
public class LikertQuestion extends AbstractQuestion implements Question {

  /**
   * Constructs a new LikertQuestion object with a question text, and default set of answer choices
   * : (Strongly Agree, Agree, Neither Agree nor Disagree, Disagree, Strongly Disagree).
   *
   * @param questionText the text of the question.
   * @throws IllegalArgumentException when empty question string is passed.
   */
  public LikertQuestion(String questionText) throws IllegalArgumentException {
    if (questionText.trim().isEmpty()) {
      throw new IllegalArgumentException("Question text cannot be empty");
    }
    this.questionText = questionText;
    this.priority = 2;
    this.choices = new String[]{"Strongly Agree", "Agree", "Neither Agree Nor Disagree", "Disagree",
        "Strongly Disagree"};
  }

  @Override
  public String evaluateAnswer(String answer) {
    this.inputAnswer = answer;
    answer = answer.trim();
    if (answer.length() != 1) {
      return INCORRECT;
    }
    // If an attempt to parse into integer fails then an invalid input was passed.
    try {
      int option = Integer.parseInt(answer.split(" ")[0]);
      if (option > this.choices.length || option < 1) {
        return INCORRECT;
      }
    } catch (NumberFormatException e) {
      return INCORRECT;
    }
    return CORRECT;
  }
}
