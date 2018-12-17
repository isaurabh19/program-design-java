/**
 * An abstract class implementing Question interface. It providers helper methods as well as common
 * methods across all question classes. It implements Comparable to allow ordering among various
 * question objects of different types as well as same type.
 */
abstract class AbstractQuestion implements Question, Comparable<Question> {
  protected int priority;
  protected static final String CORRECT = "Correct";
  protected static final String INCORRECT = "Incorrect";
  protected String questionText;
  protected String correctAnswer;
  protected String[] choices;
  protected String inputAnswer = "";

  @Override
  public abstract String evaluateAnswer(String answer);

  @Override
  public String getQuestionText() {
    return new String(this.questionText);
  }

  @Override
  public String[] getAnswerChoices() {
    String[] newAnswerChoices = new String[this.choices.length];
    for (int count = 0; count < newAnswerChoices.length; count++) {
      newAnswerChoices[count] = this.choices[count];
    }
    return newAnswerChoices;
  }

  @Override
  public int getPriority() {
    return this.priority;
  }

  /**
   * Compares equality of two objects of type Question based on their question text.
   *
   * @param obj Object to be compared with.
   * @return boolean true if both questions have same text, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Question)) {
      return false;
    }
    Question question = (Question) obj;
    return this.getQuestionText().equalsIgnoreCase(question.getQuestionText());
  }

  /**
   * Compares two question objects with respect to their priority values. If priority of the called
   * object is smaller than the passed object's, returns -1. Otherwise if the priority of the called
   * object is bigger than the passed object's, returns 1. If the priority of both objects if the
   * same then it returns -1 if called object's question text is lexicographically smaller than
   * passed object's question text, otherwise returns 1 if it's bigger than passed object's question
   * text. Returns 0 if both question texts are same.
   *
   * @param question Question object that will be compared with this object.
   * @return int value {1,0,-1} if called object is bigger, equal or smaller than passed object.
   */

  @Override
  public int compareTo(Question question) {
    if (this.getPriority() > question.getPriority()) {
      return 1;
    }
    if (this.getPriority() < question.getPriority()) {
      return -1;
    }
    return this.getQuestionText().compareToIgnoreCase(question.getQuestionText());

  }

  /**
   * A method that retrieves the string that was input as answer.
   *
   * @return a new string representing the answer input by user.
   */
  protected String getInputAnswer() {
    return new String(this.inputAnswer);
  }
}
