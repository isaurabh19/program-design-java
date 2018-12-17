/**
 * Inherits MultipleAnswerQuestion class. MultipleChoice is a special case of MultipleAnswer where
 * number of answers = 1. This class represents questions whose answer choices can be any number of
 * options, not less than 3 or greater than 8. Each options has to be a non empty string. Input
 * answer and correct answer to the questions of this type can be passed as a number between [1 to
 * number of options] (both inclusive) enclosed as string. Any other input is considered as invalid
 * and evaluates to incorrect answer for user input and exception for creation of object. These
 * objects have precedence value 3 meaning they have the third highest order of precedence.
 */
public class MultipleChoiceQuestion extends MultipleAnswerQuestion implements Question {

  /**
   * Constructs a new MultipleAnswerQuestion object with a question text, correct answer choice
   * number, and set of answer choices for the question.
   *
   * @param questionText  the text of the question.
   * @param correctAnswer correct answer as option number.
   * @param answerChoices string array of option values.
   * @throws IllegalArgumentException when any invalid input is passed, such as: 1. Empty string
   *                                  question, correct answer or answer choice value. 2. Invalid
   *                                  value for option number e.g anything other than number or
   *                                  whitespace. 3. Out of Bound Option number values. 4. Out of
   *                                  bound answer options.
   */
  public MultipleChoiceQuestion(String questionText, String correctAnswer, String[] answerChoices)
          throws IllegalArgumentException {
    super(questionText, correctAnswer, answerChoices);
    this.priority = 3;
  }
}
