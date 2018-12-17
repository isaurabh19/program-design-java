import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents questions whose answer choices can be any number of options, not less
 * than 3 or greater than 8. Each options has to be a non empty string. Input answer and correct
 * answer to the questions of this type can be passed as multiple numbers between [1 to number of
 * options] (both inclusive) enclosed as string and separated by whitespace. Any other input is
 * considered as invalid and evaluates to incorrect answer for user input and exception for creation
 * of object. These objects have precedence value 4 meaning they have the fourth highest order of
 * precedence.
 */
public class MultipleAnswerQuestion extends AbstractQuestion implements Question {
  // Set operations allow any order of option numbers e.g 1 4 3 and 1 3 4 to be evaluated as same.
  private Set<Integer> hashSet;

  /**
   * Constructs a new MultipleAnswerQuestion object with a question text, correct answer choice
   * numbers, and set of answer choices for the question.
   *
   * @param questionText  the text of the question.
   * @param correctAnswer correct answer as option numbers.
   * @param answerChoices string array of option values.
   * @throws IllegalArgumentException when any invalid input is passed, such as: 1. Empty string
   *                                  question, correct answer or answer choice value. 2. Invalid
   *                                  value for option number e.g anything other than numbers or
   *                                  whitespaces. 3. Out of Bound Option number values. 4. Out of
   *                                  bound answer options.
   */
  public MultipleAnswerQuestion(String questionText, String correctAnswer, String[] answerChoices)
          throws IllegalArgumentException {
    questionText = questionText.trim();
    correctAnswer = correctAnswer.trim();
    if (answerChoices.length < 3 || answerChoices.length > 8) {
      throw new IllegalArgumentException("Number of choices cannot be less than 3 or greater than "
              + "8");
    }
    if (questionText.isEmpty() || correctAnswer.isEmpty()) {
      throw new IllegalArgumentException("Empty string arguments not allowed");
    }
    if (!correctAnswer.matches("(\\s*\\d+\\s*)+")) {
      throw new IllegalArgumentException("Answer can only contain numbers and/or whitespaces " +
              "between them");
    }
    this.hashSet = new HashSet<Integer>();
    String[] answerValues = correctAnswer.split(" ");
    int[] answerNumbers = new int[answerValues.length];
    for (int counter = 0; counter < answerValues.length; counter++) {
      if (answerValues[counter].isEmpty()) {
        throw new IllegalArgumentException("Extra space before/after numbers");
      }
      int value = Integer.parseInt(answerValues[counter]);
      if (value > answerChoices.length || value < 1) {
        throw new IllegalArgumentException("Answer choice number out of bound");
      }
      answerNumbers[counter] = value;
      this.hashSet.add(value);
    }
    this.choices = new String[answerChoices.length];
    for (int counter = 0; counter < answerChoices.length; counter++) {
      if (answerChoices[counter].trim().isEmpty()) {
        throw new IllegalArgumentException("Choices values cannot be empty strings");
      }
      this.choices[counter] = answerChoices[counter];
    }
    this.questionText = questionText;
    this.correctAnswer = correctAnswer;
    this.priority = 4;
  }

  @Override
  public String evaluateAnswer(String answer) {
    this.inputAnswer = answer;
    answer = answer.trim();
    // checks for validity of correct answer pattern.
    if (answer.matches("(\\s*\\d+\\s*)+")) {
      String[] answerValues = answer.split(" ");
      Set<Integer> answerHashSet = new HashSet<Integer>();
      for (String answerValue : answerValues) {
        if (!answerValue.isEmpty()) {
          answerHashSet.add(Integer.parseInt(answerValue));
        }
      }
      if (this.hashSet.equals(answerHashSet)) {
        return CORRECT;
      }
    }
    return INCORRECT;
  }
}
