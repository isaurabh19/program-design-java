package stock.view;

import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import stock.controller.Features;

/**
 * This class represents the side panel for the buying of stocks. It takes in the strategy and
 * portfolio and pushes to the controller. For the ease of user it uses Panels and other elements.
 */
public class BuyStockScreen extends JPanel {
  private JButton buyStockButton;
  private JButton loadPortfolioFromFile;
  private JButton loadStrategyFromFile;
  private JTextField portfolioNameText;
  private JRadioButton shouldSave;
  private JRadioButton portfolioFromFile;
  private File portfolioFile;
  private File strategyFile;
  private JFileChooser chooseLoadedPortfolio;
  private JFileChooser chooseLoadedStrategy;

  /**
   * This is the constructor for the buying of stock panel.
   *
   * @param features are taken in to send data to the controller.
   */
  public BuyStockScreen(Features features) {
    super();

    initActionListeners(features);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    portfolioNameText = new JTextField(10);

    this.add(new JLabel("Select the radio button if portfolio is selected from file."));
    portfolioFromFile = new JRadioButton();
    portfolioFromFile.setText("Portfolio selected from file (Leave blank otherwise)");
    this.add(portfolioFromFile);

    JPanel helperBorder = new JPanel();
    //helperBorder.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    JPanel helperPanel = new JPanel();
    helperPanel.add(new JLabel("Enter portfolio name: "));
    helperPanel.add(portfolioNameText);
    this.add(helperPanel);

    helperBorder.add(helperPanel);
    helperBorder.add(new JLabel("Or"));

    //Do the file chooser for portfolio

    chooseLoadedPortfolio = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    chooseLoadedPortfolio.setDialogTitle("Choose the saved portfolio file");
    chooseLoadedPortfolio.setFileSelectionMode(JFileChooser.FILES_ONLY);
    loadPortfolioFromFile = new JButton("Load from file");
    JLabel tempMessage = new JLabel("Choose the saved portfolio file: ");
    this.add(tempMessage);

    helperPanel = new JPanel();
    helperPanel.add(loadPortfolioFromFile);
    JLabel helperLabel = new JLabel();
    helperLabel.setText("");
    helperPanel.add(helperLabel);


    loadPortfolioFromFile.addActionListener(l -> {
      int returnValue = chooseLoadedPortfolio.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        portfolioFile = chooseLoadedPortfolio.getSelectedFile();
        helperLabel.setText(portfolioFile.getAbsolutePath());
        helperBorder.add(helperLabel);
        loadPortfolioFromFile.setEnabled(false);
        this.updateUI();
      }
    }
    );
    helperBorder.add(loadPortfolioFromFile);
    helperBorder.add(helperPanel);
    this.add(helperBorder);
    this.add(helperPanel);
    //do file chooser for strategy

    chooseLoadedStrategy = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    chooseLoadedStrategy.setDialogTitle("Choose the saved strategy file");
    chooseLoadedStrategy.setFileSelectionMode(JFileChooser.FILES_ONLY);
    loadStrategyFromFile = new JButton("Load from file");
    this.add(new JLabel("Choose the saved strategy file: "));
    helperPanel = new JPanel();
    helperPanel.add(loadStrategyFromFile);

    JLabel selectedStrategyFile = new JLabel();
    selectedStrategyFile.setText("");
    helperPanel.add(selectedStrategyFile);

    loadStrategyFromFile.addActionListener(l -> {
      int returnValue = chooseLoadedStrategy.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        strategyFile = chooseLoadedStrategy.getSelectedFile();
        selectedStrategyFile.setText(strategyFile.getAbsolutePath());
        loadStrategyFromFile.setEnabled(false);
        this.updateUI();
      }
    }
    );
    this.add(helperPanel);
    this.add(new JLabel("Select the radio button to save the portfolio" +
            "after buying."));
    shouldSave = new JRadioButton();
    shouldSave.setText("Save the portfolio after buying the stocks with the strategy");
    this.add(shouldSave);
    helperPanel = new JPanel();
    helperPanel.add(buyStockButton);
    this.add(helperPanel);
  }


  private void initActionListeners(Features features) {

    buyStockButton = new JButton("Done selecting portfolio and strategy, proceed.");
    buyStockButton.addActionListener(l -> {
      if (!portfolioFromFile.isSelected()) {
        features.addStock(portfolioFromFile.isSelected(), portfolioNameText.getText(),
                strategyFile.toString(), shouldSave.isSelected());
      } else {
        System.out.println("portfolio path " + portfolioFile.getAbsolutePath());
        System.out.println("Strategy path: " + strategyFile.getAbsolutePath());
        features.addStock(portfolioFromFile.isSelected(), portfolioFile.getAbsolutePath(),
                strategyFile.getAbsolutePath(), shouldSave.isSelected());
      }
      buyStockButton.setEnabled(false);
    });
  }
}
