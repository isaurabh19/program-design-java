/**
 * An interface that allows accessing various attributes of questions. Every Question object will
 * have a text describing the question and an array of answer choices and correct answer(s). Empty
 * questions, answer choices and their values, answer(s) are not allowed. Evaluation of the answer
 * results in either of the two values : "Correct" , "Incorrect". Every different type of question
 * has an order of precedence. Currently supports the following type of questions: 1. YesNo
 * questions: Answer value either yes or no. 2. Likert questions: Answer value one of 5 fixed Likert
 * Scale values. 3. Multiple Choice questions: Answer value as one of the choices. 4. Multiple
 * Answer questions: Answer value as one or many of the choices.
 */
public interface Question {

  /**
   * Accepts input from the user as a string. Compares the user input with the correct answer of the
   * question and returns the evaluation result. For any invalid inputs, returns evaluation as
   * "Incorrect".
   *
   * @param answer string representing the answer of the user.
   * @return A string either "Correct" or "Incorrect"
   */
  String evaluateAnswer(String answer);

  /**
   * Creates a copy string of question text and returns the text of the question created.
   *
   * @return new string containing text of the question.
   */
  String getQuestionText();

  /**
   * Creates a copy of the answer choices and returns that array of answer choices set for this
   * question.
   *
   * @return new string array containing array choices.
   */
  String[] getAnswerChoices(); //returns new copy of string[] answer choices

  /**
   * Returns the precedence value of this question as positive integer.
   *
   * @return integer value denoting the priority value of this question.
   */
  int getPriority();
}
