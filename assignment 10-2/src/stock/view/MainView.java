package stock.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import stock.controller.Features;

/**
 * This class is the main face of the controller. It connects all the necessary operations. It has 2
 * main panels. The main panel (the left one, is stationary and does not change) where as the right
 * panel changes as required by the user. It offers features to ease the user strain of learning
 * trading. It takes care of switching the moving panel.
 */
public class MainView extends JFrame implements GUIStockView {
  private JPanel fixedPanel;
  private JPanel movingPanel;
  private JButton portfolioButton;
  private JButton strategyButton;
  private JButton buyStocksButton;
  private JButton valueCostBasisButton;
  private JButton helpButton;
  private JButton graphButton;
  private JSplitPane splitPaneStatus;

  /**
   * This is the constructor which takes a string as caption and begins functioning as the view.
   *
   * @param caption to be displayed at the top of the window.
   */
  public MainView(String caption) {

    super(caption);

    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);

    this.setPreferredSize(new Dimension(800, 350));
    this.setLayout(new GridLayout(1, 2));

    splitPaneStatus = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    //splitPaneStatus.setResizeWeight(0.5);
    splitPaneStatus.setEnabled(false);
    JLabel status = new JLabel("Welcome to Virtual Trading");
    splitPaneStatus.add(status);

    fixedPanel = new JPanel();
    initFixedPanel();

    movingPanel = new JPanel();
    movingPanel.add(new HelpPanel());

    JSplitPane splitPaneMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPaneMain.setResizeWeight(0.15);
    splitPaneMain.setEnabled(false);
    splitPaneMain.add(fixedPanel);
    splitPaneMain.add(movingPanel);


    splitPaneStatus.add(splitPaneMain);

    this.add(splitPaneStatus);
    this.pack();
    setVisible(true);
  }

  private void initFixedPanel() {
    fixedPanel.setLayout(new BoxLayout(fixedPanel, BoxLayout.Y_AXIS));
    fixedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    createFixedPanelButtons();
  }

  private void createFixedPanelButtons() {
    JPanel helperPanel = new JPanel();

    portfolioButton = new JButton("Portfolio");
    helperPanel.add(portfolioButton);
    fixedPanel.add(helperPanel);

    helperPanel = new JPanel();
    strategyButton = new JButton("Strategy");
    helperPanel.add(strategyButton);
    fixedPanel.add(helperPanel);

    helperPanel = new JPanel();
    buyStocksButton = new JButton("Buy Stocks");
    helperPanel.add(buyStocksButton);
    fixedPanel.add(helperPanel);

    helperPanel = new JPanel();
    valueCostBasisButton = new JButton("Value / Cost Basis");
    helperPanel.add(valueCostBasisButton);
    fixedPanel.add(helperPanel);

    helperPanel = new JPanel();
    graphButton = new JButton("Graph");
    helperPanel.add(graphButton);
    fixedPanel.add(helperPanel);

    helperPanel = new JPanel();
    helpButton = new JButton("Help");
    helperPanel.add(helpButton);
    fixedPanel.add(helperPanel);
  }

  @Override
  public String read() {
    return JOptionPane.showInputDialog(this, "Enter the input");
  }

  @Override
  public void write(String text) {
    System.out.println(text);
    JOptionPane.showMessageDialog(this, text);
    //status = new JLabel(text);
    splitPaneStatus.updateUI();
  }

  @Override
  public void flush() {
    // flush as future feature.
  }


  @Override
  public void addFeatures(Features features) {
    buyStocksButton.addActionListener(l -> {
      movingPanel.removeAll();
      movingPanel.add(new BuyStockScreen(features));
      movingPanel.updateUI();
    });
    portfolioButton.addActionListener(l -> {
      movingPanel.removeAll();
      movingPanel.add(new PortfolioSequenceScreen(features));
      movingPanel.updateUI();
    });
    strategyButton.addActionListener(l -> {
      movingPanel.removeAll();
      movingPanel.add(new StrategySequenceScreen(features));
      movingPanel.updateUI();
    });
    valueCostBasisButton.addActionListener(l -> {
      movingPanel.removeAll();
      movingPanel.add(new ValueCostBasis(features));
      movingPanel.updateUI();
    });
    helpButton.addActionListener(l -> {
      movingPanel.removeAll();
      movingPanel.add(new HelpPanel());
      movingPanel.updateUI();
    });
    graphButton.addActionListener(l -> {
      movingPanel.removeAll();
      movingPanel.add(new GraphPanel(features));
      movingPanel.updateUI();
    });
  }

}
