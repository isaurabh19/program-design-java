package stock.view;

import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import stock.controller.Features;

/**
 * This class is the side panel of drawing the graph. It takes in the information from the user to
 * plot the graph (the portfolio, start date, end date). It sends this information to the class who
 * draws the graph.
 */
public class GraphPanel extends JPanel {
  private JPanel enterPortfolio;
  private JButton loadFromFileButton;
  private JButton loadFromMemoryButton;
  private JFileChooser loadPortfolioChooser;
  private File selectedPortfolioFile;
  private String portfolioNameText;
  private JRadioButton portfolioFromMemoryRadioButton;
  private JRadioButton portfolioFromFileRadioButton;
  private JTextField startDate;
  private JTextField endDate;

  /**
   * This constructor is to create and initialise the side panel of the main window which then takes
   * in information to build the graph (create an object and delegate the work).
   *
   * @param features are used for the communication between the controller and the view.
   */
  public GraphPanel(Features features) {
    super();
    initGUI(features);
    this.add(enterPortfolio);
  }

  private void initGUI(Features features) {

    loadPortfolioChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    enterPortfolio = new JPanel();
    enterPortfolio.setLayout(new BoxLayout(enterPortfolio, BoxLayout.Y_AXIS));
    enterPortfolio.setBorder(BorderFactory.createLineBorder(Color.BLACK));


    JPanel helperPanel = new JPanel();
    helperPanel.add(new JLabel("Enter portfolio name: "));

    loadFromFileButton = new JButton("Load from file");

    loadFromMemoryButton = new JButton("Load from memory");
    //When loading from memory, put pop up with name.

    loadFromMemoryButton.addActionListener(l -> {
      portfolioNameText = new JOptionPane().showInputDialog(enterPortfolio,
              "Enter portfolio name");
      loadFromMemoryButton.setEnabled(false);
      loadFromFileButton.setEnabled(false);
    });

    helperPanel.add(loadFromFileButton);
    loadPortfolioChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    JLabel selectedPortfolioName = new JLabel();
    selectedPortfolioName.setText("");
    helperPanel.add(loadFromMemoryButton);
    enterPortfolio.add(helperPanel);
    enterPortfolio.add(selectedPortfolioName);

    loadFromFileButton.addActionListener(l -> {
      loadFromFileButton.setEnabled(false);
      loadFromMemoryButton.setEnabled(false);
      int returnValue = loadPortfolioChooser.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        selectedPortfolioFile = loadPortfolioChooser.getSelectedFile();
        selectedPortfolioName.setText(selectedPortfolioFile.getAbsolutePath());
        enterPortfolio.updateUI();
      }
    });


    helperPanel = new JPanel();

    enterPortfolio.add(new JLabel("Please choose your source"));
    portfolioFromFileRadioButton = new JRadioButton();
    portfolioFromFileRadioButton.setText("From file");
    portfolioFromMemoryRadioButton = new JRadioButton();
    portfolioFromMemoryRadioButton.setText("From Memory");
    createButtonGroup(portfolioFromFileRadioButton, portfolioFromMemoryRadioButton);

    helperPanel.add(portfolioFromFileRadioButton);
    helperPanel.add(portfolioFromMemoryRadioButton);
    enterPortfolio.add(helperPanel);


    helperPanel = new JPanel();

    helperPanel.add(new JLabel("Enter start date (yyyy-mm-dd):"));
    startDate = new JTextField(7);
    helperPanel.add(startDate);
    enterPortfolio.add(helperPanel);

    helperPanel = new JPanel();
    helperPanel.add(new JLabel("Enter end date(yyyy-mm-dd): "));
    endDate = new JTextField(7);
    helperPanel.add(endDate);

    enterPortfolio.add(helperPanel);

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(l -> {
      if (portfolioFromFileRadioButton.isSelected()
              && !startDate.getText().isEmpty()
              && !endDate.getText().isEmpty()
              && isValidDate(startDate.getText())
              && isValidDate(endDate.getText())) {
        showGraph(features);

      } else if (portfolioFromMemoryRadioButton.isSelected()
              && portfolioNameText != null
              && !portfolioNameText.isEmpty()) {
        showGraph(features);
      } else {
        JOptionPane.showMessageDialog(enterPortfolio, "Enter valid inputs");
      }
    });
    enterPortfolio.add(submitButton);
    enterPortfolio.updateUI();

  }

  private boolean isValidDate(String date) {
    try {
      LocalDate.parse(date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private void showGraph(Features features) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd,HH:mm");
    if (!portfolioFromFileRadioButton.isSelected()) {
      //Make call with portfolioFile
      new DrawGraph(features.getGraphData(portfolioFromFileRadioButton.isSelected(),
              portfolioNameText, LocalDateTime.parse(startDate.getText() + ",16:00",
                      formatter),
              LocalDateTime.parse(endDate.getText() + ",16:00", formatter)).getCostBasis(),
              features.getGraphData(portfolioFromFileRadioButton.isSelected(),
                      portfolioNameText, LocalDateTime.parse(startDate.getText() + ",16:00"
                              , formatter), LocalDateTime.parse(endDate.getText() + ",16:00"
                              , formatter)
              ).getCurrentValue());
    } else {
      new DrawGraph(features.getGraphData(portfolioFromFileRadioButton.isSelected(),
              selectedPortfolioFile.getAbsolutePath(), LocalDateTime.parse(startDate
                      .getText() + ",16:00", formatter),
              LocalDateTime.parse(endDate.getText() + ",16:00", formatter)).getCostBasis(),
              features.getGraphData(portfolioFromFileRadioButton.isSelected(),
                      selectedPortfolioFile.getAbsolutePath(), LocalDateTime.parse(startDate
                                      .getText() + ",16:00"
                              , formatter), LocalDateTime.parse(endDate.getText() + ",16:00"
                              , formatter)
              ).getCurrentValue());
    }
  }

  private void createButtonGroup(JRadioButton button1, JRadioButton button2) {
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(button1);
    buttonGroup.add(button2);
  }
}
