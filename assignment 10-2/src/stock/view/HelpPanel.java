package stock.view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This help panel is used to describe the necessary information about the application. It is mainly
 * for scalability and houses information to make the user understand the application better and to
 * learn better.
 */
public class HelpPanel extends JPanel {
  /**
   * This constructor is to create this panel to solve the difficulties faced by the users.
   */
  public HelpPanel() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(new JLabel("Hello. To begin, kindly create a portfolio. After this,"));
    add(new JLabel("you can apply a strategy to it. To create a strategy follow " +
            "the instructions on screen."));
    add(new JLabel("All your saved portfolios and strategies are present in the /res" +
            "folder. You can also create your own text strategies"));
    add(new JLabel("Provided you follow the format in the documentation"));
    add(new JLabel("You can either save the strategy or apply it to portfolio."));
    add(new JLabel("Kindly do not apply strategies without clicking on " +
            "\"Done adding stocks\""));
    add(new JLabel("For more intricate details, refer documentation."));


  }
}
