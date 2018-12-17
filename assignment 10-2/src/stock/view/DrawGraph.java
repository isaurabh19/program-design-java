package stock.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Map;

import javax.swing.JFrame;

/**
 * This class represents the drawing of graph. It takes in the values of the controller and
 * represents them using a time-series graph using the JFreeChartAPI. Its a graph of cost-basis vs
 * the value of the portfolio.
 */
public class DrawGraph extends JFrame {
  private Map<LocalDateTime, Double> valueMap;
  private Map<LocalDateTime, Double> costBasisMap;

  /**
   * This is the constructor which takes in the values from the controller and draws the graph on
   * the GUI.
   *
   * @param valueMap     a map of LocalDateTime and the value of the stocks of the portfolio.
   * @param costBasisMap a map of LocalDateTime and the cost basis of the portfolio.
   */
  public DrawGraph(Map<LocalDateTime, Double> valueMap, Map<LocalDateTime, Double> costBasisMap) {
    super();

    this.valueMap = valueMap;
    this.costBasisMap = costBasisMap;

    final XYDataset dataset = createDataset();
    final JFreeChart chart = createChart(dataset);
    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(560, 370));
    chartPanel.setMouseZoomable(true, false);
    this.add(chartPanel);

    this.pack();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  private XYDataset createDataset() {

    final TimeSeries costBasisSeries = new TimeSeries("Cost Basis Series");
    final TimeSeries valueSeries = new TimeSeries("Value Series");

    for (LocalDateTime date : costBasisMap.keySet()) {
      costBasisSeries.add(new Day(Date.valueOf(date.toLocalDate())), costBasisMap.get(date));
    }
    TimeSeriesCollection finalCollection = new TimeSeriesCollection();

    finalCollection.addSeries(costBasisSeries);

    for (LocalDateTime date : valueMap.keySet()) {
      valueSeries.add(new Day(Date.valueOf(date.toLocalDate())), valueMap.get(date));
    }

    finalCollection.addSeries(valueSeries);
    return finalCollection;
  }

  private JFreeChart createChart(final XYDataset dataset) {
    return ChartFactory.createTimeSeriesChart(
            "Performance of Portfolio",
            "Date(in Years)",
            "Amount(in $)",
            dataset,
            true,
            true,
            false);
  }
}