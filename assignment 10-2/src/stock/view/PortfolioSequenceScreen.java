package stock.view;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import stock.controller.Features;

/**
 * This panel is used to create a portfolio. It takes in the name of the portfolio to be created and
 * passes to the controller. It then displays the required instructions.
 */
public class PortfolioSequenceScreen extends JPanel {
  private JPanel createLoadPortfolioPanel;
  private JPanel createPortfolioPanel;
  private JButton portfolioNameButton;
  private JTextField portfolioNameTextField;

  /**
   * This is the constructor for the class of portfolio and takes in an object of type feature to
   * communicate with the controller.
   *
   * @param features to communicate with the controller.
   */
  public PortfolioSequenceScreen(Features features) {
    initPanels();
    initActionListeners(features);
    add(createLoadPortfolioPanel);
  }

  private void initPanels() {
    initCreateLoadPortfolioPanel();
    initCreatePortfolioPanel();
  }

  private void initActionListeners(Features features) {
    portfolioNameButton.addActionListener(l -> {
      portfolioNameButton.setEnabled(false);
      createPortfolioPanel.add(footer("Portfolio name entered successfully."));
      createPortfolioPanel.add(footer("Proceed to strategy to create strategy and add stocks "
              + "to portfolio"));
      createPortfolioPanel.add(footer("To add another portfolio, click on \"Portfolio\""));
      createPortfolioPanel.add(footer(" option on left panel and enter the portfolio name."));
      createPortfolioPanel.updateUI();
      portfolioNameTextField.setColumns(10);
      updateUI();
      features.createPortfolio(portfolioNameTextField.getText());
    });
  }

  //TODO See how things work in strategy and adjust here.
  private void initCreateLoadPortfolioPanel() {

    createLoadPortfolioPanel = new JPanel();
    createLoadPortfolioPanel.add(new JLabel("Welcome to portfolio screen"));
    createLoadPortfolioPanel.setLayout(new BoxLayout(createLoadPortfolioPanel, BoxLayout.Y_AXIS));
    createLoadPortfolioPanel.updateUI();

    JPanel helperPanel = new JPanel();
    helperPanel.setLayout(new FlowLayout());

    JButton createPortfolioButton = new JButton("Create New Portfolio");
    createPortfolioButton.addActionListener(l -> {
      //Change the panel
      removeAll();
      add(createPortfolioPanel);
      updateUI();
    });
    helperPanel.add(createPortfolioButton);



    createLoadPortfolioPanel.add(helperPanel);
    this.add(createLoadPortfolioPanel);
    this.revalidate();
    this.repaint();

  }


  private void initCreatePortfolioPanel() {
    createPortfolioPanel = new JPanel();
    createPortfolioPanel.setLayout(new BoxLayout(createPortfolioPanel, BoxLayout.Y_AXIS));

    createPortfolioPanel.add(new JLabel("Enter unique Portfolio Name"));
    portfolioNameTextField = new JTextField(10);
    createPortfolioPanel.add(portfolioNameTextField);

    portfolioNameButton = new JButton("Enter Portfolio Name");
    portfolioNameButton.setActionCommand("Enter Portfolio Name");

    createPortfolioPanel.add(portfolioNameButton);
  }


  private JPanel footer(String text) {
    JPanel footerPanel = new JPanel();
    footerPanel.add(new JLabel(text));
    return footerPanel;
  }

}
