package stock.view;

import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
 * This class is the panel to calculate the cost basis and the value of the portfolio members. It
 * provides the flexibility of choosing the date, and the location of portfolio
 */
public class ValueCostBasis extends JPanel {
  private JTextField portfolioNameText;
  private JTextField dateText;
  private JRadioButton portfolioFromFile;
  private JFileChooser chooseLoadedPortfolio;
  private JButton loadPortfolioFromFile;
  private File portfolioFile;
  private JLabel selectedFileName;

  public ValueCostBasis(Features features) {
    super();
    initActionListeners(features);
  }


  private void initActionListeners(Features features) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    portfolioNameText = new JTextField(10);

    portfolioFromFile = new JRadioButton();
    portfolioFromFile.setText("Portfolio selected from saved list of Portfolios ");


    JPanel helper1 = new JPanel();
    helper1.setLayout(new BoxLayout(helper1, BoxLayout.Y_AXIS));

    JPanel helperPanel = new JPanel();
    helperPanel.add(new JLabel("Enter portfolio name: "));
    helperPanel.add(portfolioNameText);
    helper1.add(helperPanel);
    this.add(helper1);

    helper1.add(new JLabel("Or"));
    this.add(helper1);
    //Do the file chooser for portfolio

    chooseLoadedPortfolio = new JFileChooser(FileSystemView.getFileSystemView()
            .getHomeDirectory());

    chooseLoadedPortfolio.setDialogTitle("Choose the saved portfolio file");
    chooseLoadedPortfolio.setFileSelectionMode(JFileChooser.FILES_ONLY);
    loadPortfolioFromFile = new JButton("Load from file");

    selectedFileName = new JLabel();
    selectedFileName.setText("");

    helperPanel = new JPanel();
    helperPanel.add(loadPortfolioFromFile);
    helperPanel.add(selectedFileName);
    helper1.add(helperPanel);

    this.add(helper1);

    loadPortfolioFromFile.addActionListener(l -> {
      loadPortfolioFromFile.setEnabled(false);
      int returnValue = chooseLoadedPortfolio.showOpenDialog(this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        portfolioFile = chooseLoadedPortfolio.getSelectedFile();
        selectedFileName.setText(portfolioFile.getAbsolutePath());
        helper1.updateUI();
      }
    }
    );

    this.add(helper1);
    helper1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    helperPanel = new JPanel();
    helperPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    helperPanel.add(new JLabel("Enter the date"));
    dateText = new JTextField(10);
    helperPanel.add(dateText);
    this.add(helperPanel);


    this.add(new JLabel("Select the radio button if portfolio is loaded from file."));
    this.add(portfolioFromFile);
    this.add(new JLabel("If selected portfolio was chosen from saved list, uncheck box"));

    helperPanel = new JPanel();
    JButton calculateCostBasisButton = new JButton("Calculate cost basis");
    JButton calculateValueButton = new JButton("Calculate Value");
    helperPanel.add(calculateCostBasisButton);
    helperPanel.add(calculateValueButton);
    this.add(helperPanel);

    calculateCostBasisButton.addActionListener(l -> {
      Optional<Double> costBasis;
      if (!isValidDate(dateText.getText())) {
        JOptionPane.showInputDialog(this, "Enter valid string");
        //System.out.println("Enter valid string");
      } else if (portfolioFromFile.isSelected()) {
        costBasis = features.getCostBasis(true, portfolioFile.getAbsolutePath(),
                LocalDate.parse(dateText.getText()));
        costBasis.ifPresent(aDouble -> JOptionPane.showMessageDialog(this, aDouble,
                "Cost basis of portfolio", JOptionPane.PLAIN_MESSAGE));
      } else {
        costBasis = features.getCostBasis(false, portfolioNameText.getText(),
                LocalDate.parse(dateText.getText()));
        costBasis.ifPresent(aDouble -> JOptionPane.showMessageDialog(this, aDouble,
                "Cost basis of portfolio", JOptionPane.PLAIN_MESSAGE));
      }
    });

    calculateValueButton.addActionListener(l -> {
      Optional<Double> value;
      if (!isValidDate(dateText.getText())) {
        JOptionPane.showInputDialog(this, "Enter valid date");
        //System.out.println("Enter valid date");
      } else if (portfolioFromFile.isSelected()) {
        value = features.getCurrentValue(true, portfolioFile.getAbsolutePath(),
                LocalDate.parse(dateText.getText()));
        value.ifPresent(aDouble -> JOptionPane.showMessageDialog(this, aDouble,
                "Value of portfolio", JOptionPane.PLAIN_MESSAGE));
      } else {
        value = features.getCurrentValue(false, portfolioNameText.getText(),
                LocalDate.parse(dateText.getText()));
        value.ifPresent(aDouble -> JOptionPane.showMessageDialog(this, aDouble,
                "Value of portfolio", JOptionPane.PLAIN_MESSAGE));
      }
    });
  }

  private boolean isValidDate(String date) {
    try {
      LocalDate.parse(date);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}

