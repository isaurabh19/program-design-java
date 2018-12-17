package stock.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * This is the screen for the strategy. It has panels, radio-buttons and other features to make the
 * application easy to use. It has a sequence of flow which is to be followed to successfully learn
 * the application.
 */
public class StrategySequenceScreen extends JPanel {
  private JPanel enterStrategyPanel;
  private JPanel regularStrategyPanel;
  private JPanel dollarCostInitialPanel;
  private JPanel applyToPortfolioPanel;
  private JRadioButton jRadioButtonStrategy1;
  private JRadioButton jRadioButtonStrategy2;
  private JButton sendStockInfoButtonDollarCost;
  private JButton addMoreStocksToStrategyDollarCost;
  private JButton savePortfolioStrategyStocksDollarCost;
  private JButton sendStockInfoButtonRegular;
  private JButton savePortfolioStrategyStocksRegular;
  private JTextField regularStrategyTickerText;
  private JTextField strategyNameText;
  private JTextField regularStrategyDateText;
  private JTextField regularStrategyAmountText;
  private JTextField regularStrategyCommissionText;
  private JTextField dollarCostCommission;
  private JTextField dollarCostStartDate;
  private JTextField dollarCostEndDate;
  private JTextField dollarCostFrequency;
  private JTextField dollarCostAmount;
  private JRadioButton radioButtonEqualWeight;
  private JRadioButton radioButtonCustomWeight;
  private JPanel dollarCostStockPanel;
  private JTextField[] regularStrategyTickerTextArr = new JTextField[10];
  private JTextField[] regularStrategyWeightTextArr = new JTextField[10];
  private boolean isEqualWeights;
  private boolean isRegular;
  private JButton applyToPortfolioButton;

  private String strategyName;
  private String tickerSymbol;
  private List<String> tickerList = new ArrayList<>();
  private Map<String, Double> tickerAndWeightMap = new HashMap<>();
  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate dateOfPurchase;
  private double amount;
  private double commission = 0;
  private int frequencyOfDays;
  private JTextField portfolioNameToApplyStrategy;
  private JFileChooser chooseLoadedPortfolio;
  private File selectedPortfolio;
  private JButton loadPortfolioFromMemory;
  private JButton loadPortfolioFromFile;
  private JRadioButton shouldSavePortfolio;

  /**
   * This public constructor is used to create an object and start the flow of the application to
   * take in data from the user. This handles all the scenarios about adding, saving and applying of
   * strategies to portfolios.
   *
   * @param features to communicate with the controller.
   */
  public StrategySequenceScreen(Features features) {
    initStrategyPanel();
    initRegularStrategyPanel();
    initDollarCostStrategyPanel();
    initApplyStrategyPanel();
    initActionListeners(features);
    add(enterStrategyPanel);
  }

  private void initApplyStrategyPanel() {
    applyToPortfolioPanel = new JPanel();
    applyToPortfolioPanel.setLayout(new BoxLayout(applyToPortfolioPanel, BoxLayout.Y_AXIS));
    applyToPortfolioPanel.setPreferredSize(new Dimension(500, 550));
    applyToPortfolioPanel.add(new JLabel("Select the radio button to save the portfolio" +
            "after buying."));
    shouldSavePortfolio = new JRadioButton();
    shouldSavePortfolio.setText("Save the portfolio after buying the stocks with the strategy");
    applyToPortfolioPanel.add(shouldSavePortfolio);

    applyToPortfolioPanel.add(new JLabel("Where do you want to load portfolio from?"));

    JButton loadFromFileButton = new JButton("Load From Saved Portfolios");
    applyToPortfolioPanel.add(loadFromFileButton);

    JButton loadFromMemoryButton = new JButton("Load from portfolios not yet saved");
    applyToPortfolioPanel.add(loadFromMemoryButton);


    JPanel helperPanel = new JPanel();
    portfolioNameToApplyStrategy = new JTextField(10);
    loadPortfolioFromMemory = new JButton("Load from portfolios that are not yet " +
            "saved to file");
    helperPanel.add(new JLabel("Enter the unique portfolio name: "));
    helperPanel.add(portfolioNameToApplyStrategy);
    helperPanel.add(loadPortfolioFromMemory);


    JPanel helperPanelFile = new JPanel();
    chooseLoadedPortfolio = new JFileChooser(FileSystemView.getFileSystemView()
            .getHomeDirectory());

    chooseLoadedPortfolio.setDialogTitle("Choose the saved portfolio file");
    chooseLoadedPortfolio.setFileSelectionMode(JFileChooser.FILES_ONLY);
    loadPortfolioFromFile = new JButton("Load from file");
    helperPanelFile.add(new JLabel("Choose the saved portfolio file: "));
    helperPanelFile.add(chooseLoadedPortfolio);
    helperPanelFile.add(loadPortfolioFromFile);

    loadFromFileButton.addActionListener(l -> {
      int returnValue = chooseLoadedPortfolio.showOpenDialog(applyToPortfolioPanel);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        selectedPortfolio = chooseLoadedPortfolio.getSelectedFile();
        applyToPortfolioPanel.removeAll();
        loadFromFileButton.setEnabled(false);
        loadFromMemoryButton.setEnabled(false);
        applyToPortfolioPanel.add(new JLabel("Requested to apply strategy to "
                + selectedPortfolio.getAbsolutePath()));
        applyToPortfolioPanel.add(loadFromFileButton);
        applyToPortfolioPanel.add(loadFromMemoryButton);
        applyToPortfolioPanel.add(loadPortfolioFromFile);
        applyToPortfolioPanel.updateUI();
      }
    });

    loadFromMemoryButton.addActionListener(l -> {
      loadFromMemoryButton.setEnabled(false);
      applyToPortfolioPanel.removeAll();
      applyToPortfolioPanel.add(helperPanel);
      applyToPortfolioPanel.updateUI();
    });

  }

  private void initActionListeners(Features features) {
    if (dollarCostEndDate.getText().isEmpty()) {
      savePortfolioStrategyStocksDollarCost.addActionListener(l -> {
        features.createStrategyDollarCost(strategyName, tickerList, startDate, amount,
                commission, frequencyOfDays, true, false, false,
                "Not Required", true);
        savePortfolioStrategyStocksDollarCost.setEnabled(false);
      });
    } else {
      savePortfolioStrategyStocksDollarCost.addActionListener(l -> {
        features.createStrategyDollarCost(strategyName, tickerList, startDate, endDate, amount,
                commission, frequencyOfDays, true, false, false,
                "Not Required", true);
        savePortfolioStrategyStocksDollarCost.setEnabled(false);
      });
    }

    savePortfolioStrategyStocksRegular.addActionListener(l -> {
      features.createStrategyRegular(tickerSymbol, amount, commission, dateOfPurchase, true,
              false, false, "Not Required", strategyName, false);
      savePortfolioStrategyStocksRegular.setEnabled(false);
      JOptionPane.showMessageDialog(regularStrategyPanel,
              "Request to save strategy successful");
      regularStrategyPanel.updateUI();
    });

    //When user wants to load portfolio from memory.
    //He can either save the portfolio or not.
    loadPortfolioFromMemory.addActionListener(l -> {
      if (isRegular) {
        features.createStrategyRegular(
                tickerSymbol, amount, commission, dateOfPurchase, false, true,
                false,
                portfolioNameToApplyStrategy.getText(), strategyName, false);
        this.removeAll();
        JLabel tempMessage = new JLabel();
        tempMessage.setText("Request to apply " + strategyName + " (strategyName) on   "
                + portfolioNameToApplyStrategy.getText() + " (portfolioName) successful.");
        this.add(tempMessage);
        loadPortfolioFromMemory.setEnabled(false);
        this.updateUI();
      } else if (!isRegular) {
        if (tickerList.isEmpty()) {
          //weights
          if (dollarCostEndDate.getText().isEmpty()) {
            features.createStrategyDollarCost(strategyName, tickerAndWeightMap, startDate,
                    amount, commission,
                    frequencyOfDays, false, true, true,
                    portfolioNameToApplyStrategy.getText(),
                    shouldSavePortfolio.isSelected());
          } else {
            features.createStrategyDollarCost(strategyName, tickerAndWeightMap, startDate,
                    endDate, amount, commission, frequencyOfDays, false, true,
                    true,
                    portfolioNameToApplyStrategy.getText(), shouldSavePortfolio.isSelected());
          }
        } else {
          if (dollarCostEndDate.getText().isEmpty()) {
            features.createStrategyDollarCost(strategyName, tickerList, startDate, amount,
                    commission, frequencyOfDays, false, true, true,
                    portfolioNameToApplyStrategy.getText(), shouldSavePortfolio.isSelected());
          } else {
            features.createStrategyDollarCost(
                    strategyName, tickerList, startDate, endDate, amount, commission,
                    frequencyOfDays,
                    false, true, true,
                    portfolioNameToApplyStrategy.getText(), shouldSavePortfolio.isSelected());
          }
        }
      }
      loadPortfolioFromMemory.setEnabled(false);
    });

    //When the user wants to apply strategy to a portfolio saved from the file.
    //He can either save the portfolio or not (after applying the strategy of course)

    loadPortfolioFromFile.addActionListener(l -> {
      if (isRegular) {
        features.createStrategyRegular(
                tickerSymbol, amount, commission, dateOfPurchase, false,
                true, true,
                selectedPortfolio.getAbsolutePath(), strategyName,
                shouldSavePortfolio.isSelected());
        JLabel tempMessage = new JLabel();
        tempMessage.setText("Request to apply " + strategyName + "(strategyName) on"
                + selectedPortfolio.toString() + " (portfolioPath) successful.");
        this.removeAll();
        this.add(tempMessage);
        this.updateUI();
      } else {
        features.createStrategyDollarCost(strategyName, tickerList, startDate, amount,
                commission, frequencyOfDays
                , false, true, true, portfolioNameToApplyStrategy.getText(),
                shouldSavePortfolio.isSelected());
        JLabel tempMessage = new JLabel();
        tempMessage.setText("Request to apply " + strategyName + "(strategyName) on"
                + selectedPortfolio.toString() + " (portfolioPath) successful.");
        this.removeAll();
        this.add(tempMessage);
        this.updateUI();
      }
    });


    sendStockInfoButtonDollarCost.addActionListener(l -> {
      readDollarCostFinalScreenText();
      sendStockInfoButtonDollarCost.setEnabled(false);
      addMoreStocksToStrategyDollarCost.setEnabled(false);
      savePortfolioStrategyStocksDollarCost.setEnabled(true);
      applyToPortfolioButton.setEnabled(true);
    });

    sendStockInfoButtonRegular.addActionListener(l -> {
      if (isValidDate(regularStrategyDateText.getText())
              && isValidDouble(regularStrategyAmountText.getText())) {
        readTextFields();
        resetTextFields();
        savePortfolioStrategyStocksRegular.setEnabled(true);
        applyToPortfolioButton.setEnabled(true);
        sendStockInfoButtonRegular.setEnabled(false);
        regularStrategyPanel.setLayout(new BoxLayout(regularStrategyPanel, BoxLayout.Y_AXIS));
        JLabel temp1 = new JLabel();
        temp1.setText("Request to buy " + "$" + amount +
                " amount of " + tickerSymbol.toUpperCase() + " on " + dateOfPurchase.toString()
                + " with commission $" + commission);
        regularStrategyPanel.add(temp1);

        temp1 = new JLabel();
        temp1.setText("The Unique Strategy Name is: " + strategyName);
        regularStrategyPanel.add(temp1);

        temp1 = new JLabel();
        temp1.setText("You may either save this operation or apply it to a "
                + "portfolio and then save it.");
        regularStrategyPanel.add(temp1);

        temp1 = new JLabel();
        temp1.setText("Click on \"Buy Stocks\" to apply this strategy "
                + "on a portfolio which you have already created.");
        regularStrategyPanel.add(temp1);
        regularStrategyPanel.validate();
        regularStrategyPanel.updateUI();
      } else {
        JOptionPane.showInputDialog(this, "Enter valid values please.");
      }
    });
  }

  private void readDollarCostFinalScreenText() {
    if (isEqualWeights) {
      for (int i = 1; i <= 5; i++) {
        if (!regularStrategyTickerTextArr[i].getText().isEmpty()) {
          tickerList.add(regularStrategyTickerTextArr[i].getText());
        }
      }
    } else {
      for (int i = 1; i <= 5; i++) {
        if (
                !regularStrategyTickerTextArr[i].getText().isEmpty()
                        && !regularStrategyWeightTextArr[i].getText().isEmpty()
                        && isValidDouble(regularStrategyWeightTextArr[i].getText())
        ) {
          tickerAndWeightMap.put(regularStrategyTickerTextArr[i].getText(),
                  Double.parseDouble(regularStrategyWeightTextArr[i].getText()));
        }
      }
    }
  }

  private void initDollarCostStrategyPanel() {
    dollarCostScreen1();
  }

  private void dollarCostScreen1() {


    dollarCostInitialPanel = new JPanel();
    dollarCostInitialPanel.setLayout(new BoxLayout(dollarCostInitialPanel, BoxLayout.Y_AXIS));

    dollarCostInitialPanel.add(new JLabel("Enter Amount"));
    dollarCostAmount = new JTextField();
    dollarCostInitialPanel.add(dollarCostAmount);

    dollarCostInitialPanel.add(new JLabel("Enter Start date(yyyy-mm-dd)"));
    dollarCostStartDate = new JTextField();
    dollarCostInitialPanel.add(dollarCostStartDate);

    dollarCostInitialPanel.add(new JLabel("Enter end date (if applicable)(yyyy-mm-dd)"));
    dollarCostEndDate = new JTextField();
    dollarCostInitialPanel.add(dollarCostEndDate);

    dollarCostInitialPanel.add(new JLabel("Enter commission (if applicable): "));
    dollarCostCommission = new JTextField();
    dollarCostInitialPanel.add(dollarCostCommission);

    dollarCostInitialPanel.add(new JLabel("Enter frequency (in days)"));
    dollarCostFrequency = new JTextField();
    dollarCostInitialPanel.add(dollarCostFrequency);

    radioButtonEqualWeight = new JRadioButton();
    radioButtonEqualWeight.setText("Equal Weights");
    radioButtonCustomWeight = new JRadioButton();
    radioButtonCustomWeight.setText("Custom Weights");

    createButtonGroup(radioButtonEqualWeight, radioButtonCustomWeight);

    dollarCostInitialPanel.add(radioButtonEqualWeight);
    dollarCostInitialPanel.add(radioButtonCustomWeight);

    JButton loadInitialDollarCostButton = new JButton("Next");
    loadInitialDollarCostButton.addActionListener(l -> {
      if (

              isValidDate(dollarCostStartDate.getText())
                      && (!dollarCostAmount.getText().isEmpty())
                      && isValidDouble(dollarCostAmount.getText())
                      && isValidInteger(dollarCostFrequency.getText()) &&
                      (radioButtonEqualWeight.isSelected()
                              || radioButtonCustomWeight.isSelected())
      ) {
        applyToPortfolioButton.setEnabled(false);
        isEqualWeights = radioButtonEqualWeight.isSelected();
        dollarCostScreen2();
        removeAll();
        amount = Double.parseDouble(dollarCostAmount.getText());
        startDate = LocalDate.parse(dollarCostStartDate.getText());
        if (!dollarCostEndDate.getText().isEmpty()) {
          try {

            endDate = LocalDate.parse(dollarCostEndDate.getText());
          } catch (Exception e) {
            JOptionPane.showInputDialog(this,
                    "Enter valid values please.");
          }
        }
        try {
          commission = Double.parseDouble(dollarCostCommission.getText());
        } catch (Exception e) {
          commission = 0.0;
        }
        frequencyOfDays = Integer.parseInt(dollarCostFrequency.getText());
        add(dollarCostStockPanel);
        updateUI();
      } else {
        JOptionPane.showInputDialog(this,
                "Enter valid values please.");
        //("Enter valid values");
      }
    });
    //Load buttons for dollar cost
    sendStockInfoButtonDollarCost = new JButton("Done adding stocks");
    sendStockInfoButtonDollarCost.setActionCommand("Done adding stocks");
    //dollarCostStockPanel.add(sendStockInfoButton);

    addMoreStocksToStrategyDollarCost = new JButton("Add More Stocks");
    addMoreStocksToStrategyDollarCost.setActionCommand("AddStocksDollarCost");
    //dollarCostStockPanel.add(addMoreStockInfoButton);


    savePortfolioStrategyStocksDollarCost = new JButton("Save to file");
    savePortfolioStrategyStocksDollarCost.setActionCommand("Save to file");
    savePortfolioStrategyStocksDollarCost.setEnabled(false);
    //dollarCostStockPanel.add(savePortfolioStrategyStocks);

    applyToPortfolioButton = new JButton("Apply to a portfolio");
    applyToPortfolioButton.setEnabled(false);
    applyToPortfolioButton.addActionListener(l -> {
      this.removeAll();
      this.add(applyToPortfolioPanel);
      this.updateUI();
    }
    );
    //applyToPortfolioButton.setEnabled(false);

    dollarCostInitialPanel.add(loadInitialDollarCostButton);
  }


  private void dollarCostScreen2() {
    dollarCostStockPanel = new JPanel();
    //dollarCostStockPanel.setLayout(new GridLayout(7, 5));
    dollarCostStockPanel.add(new JLabel("Do not apply the strategy to portfolio" +
            "without clicking on \"Done adding stocks\""));
    dollarCostStockPanel.setLayout(new BoxLayout(dollarCostStockPanel, BoxLayout.Y_AXIS));

    int numOfTickerWeights = 5;
    for (int i = 1; i <= numOfTickerWeights; i++) {
      regularStrategyTickerTextArr[i] = new JTextField(5);
      JPanel helperPanel = new JPanel();
      helperPanel.add(new JLabel("Enter ticker: "));
      helperPanel.add(regularStrategyTickerTextArr[i]);

      regularStrategyWeightTextArr[i] = new JTextField(5);
      if (!isEqualWeights) {
        helperPanel.add(new JLabel("Enter Weight: "));
        helperPanel.add(regularStrategyWeightTextArr[i]);
      }
      dollarCostStockPanel.add(helperPanel);
    }

    //Adding buttons - common to adding buttons in the regular strategy
    JPanel helperPanel = new JPanel();


    helperPanel.add(sendStockInfoButtonDollarCost);
    helperPanel.add(addMoreStocksToStrategyDollarCost);
    helperPanel.add(savePortfolioStrategyStocksDollarCost);
    helperPanel.add(applyToPortfolioButton);

    dollarCostStockPanel.add(helperPanel);

    addMoreStocksToStrategyDollarCost.addActionListener(l -> readDollarCostText());
  }

  private void readDollarCostText() {
    if (checkValidDollarCostTextField()) {
      for (int i = 1; i <= 5; i++) {
        if (isEqualWeights) {
          tickerList.add(regularStrategyTickerTextArr[i].getText());
          regularStrategyTickerTextArr[i].setText("");
        } else {
          tickerAndWeightMap.put(regularStrategyTickerTextArr[i].getText(),
                  Double.parseDouble(regularStrategyWeightTextArr[i].getText()));
          regularStrategyWeightTextArr[i].setText("");
          regularStrategyTickerTextArr[i].setText("");
        }
      }
    } else {
      JOptionPane.showInputDialog(this, "Enter valid values please.");

    }
  }

  private boolean checkValidDollarCostTextField() {
    for (int i = 1; i <= 5; i++) {
      if (regularStrategyTickerTextArr[i].getText().isEmpty()) {
        return false;
      } else {
        if (!isEqualWeights) {
          return !regularStrategyWeightTextArr[i].getText().isEmpty();
        }
      }
    }
    return true;
  }

  private void initRegularStrategyPanel() {
    regularStrategyPanel = new JPanel();

    regularStrategyPanel.setLayout(new GridLayout(7, 4, 5, 5));

    JLabel regularStrategyTicker = new JLabel("Enter ticker symbol: ");
    regularStrategyPanel.add(regularStrategyTicker);

    regularStrategyTickerText = new JTextField(10);
    regularStrategyPanel.add(regularStrategyTickerText);


    JLabel regularStrategyDate = new JLabel("Enter Date of Purchase: (yyyy-mm-dd)");
    regularStrategyPanel.add(regularStrategyDate);

    regularStrategyDateText = new JTextField(10);
    regularStrategyPanel.add(regularStrategyDateText);

    JLabel regularStrategyAmount = new JLabel("Enter amount of purchase: ");
    regularStrategyPanel.add(regularStrategyAmount);

    regularStrategyAmountText = new JTextField(10);
    regularStrategyPanel.add(regularStrategyAmountText);
    JLabel regularStrategyCommission = new JLabel("Enter commission (if any): ");
    regularStrategyPanel.add(regularStrategyCommission);

    regularStrategyCommissionText = new JTextField(10);
    regularStrategyPanel.add(regularStrategyCommissionText);


    sendStockInfoButtonRegular = new JButton("Done adding stocks");
    sendStockInfoButtonRegular.setActionCommand("Done adding stocks");

    regularStrategyPanel.add(sendStockInfoButtonRegular);

    applyToPortfolioButton = new JButton("Apply to a portfolio");
    applyToPortfolioButton.addActionListener(l -> {
      regularStrategyPanel.removeAll();
      regularStrategyPanel.add(applyToPortfolioPanel);
      regularStrategyPanel.updateUI();
    }
    );
    regularStrategyPanel.add(applyToPortfolioButton);

    savePortfolioStrategyStocksRegular = new JButton("Save to file");
    savePortfolioStrategyStocksRegular.setActionCommand("Save to file");
    savePortfolioStrategyStocksRegular.setEnabled(false);
    regularStrategyPanel.add(savePortfolioStrategyStocksRegular);
  }

  private boolean isValidDate(String date) {
    try {
      LocalDate.parse(date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private boolean isValidDouble(String doubleStr) {
    try {
      Double.parseDouble(doubleStr);
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  private void resetTextFields() {
    regularStrategyTickerText.setText("");
    regularStrategyDateText.setText("");
    regularStrategyAmountText.setText("");
    regularStrategyCommissionText.setText("");
  }

  private void readTextFields() {
    dateOfPurchase = LocalDate.parse(regularStrategyDateText.getText());
    tickerSymbol = regularStrategyTickerText.getText();
    amount = Double.parseDouble(regularStrategyAmountText.getText());
    try {
      commission = Double.parseDouble(regularStrategyCommissionText.getText());
    } catch (Exception e) {
      commission = 0;
    }
  }

  private void initStrategyPanel() {
    //stockInfo = new StringBuilder();
    enterStrategyPanel = new JPanel();
    enterStrategyPanel.setLayout(new BoxLayout(enterStrategyPanel, BoxLayout.Y_AXIS));

    jRadioButtonStrategy1 = new JRadioButton();
    jRadioButtonStrategy1.setText("Regular Strategy");
    jRadioButtonStrategy2 = new JRadioButton();
    jRadioButtonStrategy2.setText("Dollar-Cost Strategy");

    createButtonGroup(jRadioButtonStrategy1, jRadioButtonStrategy2);

    strategyNameText = new JTextField(10);

    JButton selectStrategyButton = new JButton("Next");
    selectStrategyButton.setActionCommand("Select Strategy");
    selectStrategyButton.addActionListener(l -> {
      if (jRadioButtonStrategy1.isSelected()) {
        this.removeAll();
        this.add(regularStrategyPanel);
        sendStockInfoButtonRegular.setEnabled(true);

        strategyName = strategyNameText.getText();
        isRegular = true;

        updateUI();
      } else if (jRadioButtonStrategy2.isSelected()) {
        removeAll();
        add(dollarCostInitialPanel);

        strategyName = strategyNameText.getText();
        isRegular = false;

        updateUI();
      } else {
        JOptionPane.showInputDialog(this, "Select a valid strategy.");
        //("Select a valid strategy");
      }
    });


    enterStrategyPanel.add(new JLabel("Select Strategy"));
    enterStrategyPanel.add(jRadioButtonStrategy1);
    enterStrategyPanel.add(jRadioButtonStrategy2);
    enterStrategyPanel.add(new Label("Enter Unique Strategy Name: "));
    enterStrategyPanel.add(strategyNameText);
    enterStrategyPanel.add(selectStrategyButton);
  }

  private boolean isValidInteger(String strInt) {
    try {
      Integer.parseInt(strInt);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private void createButtonGroup(JRadioButton button1, JRadioButton button2) {
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(button1);
    buttonGroup.add(button2);
  }
}