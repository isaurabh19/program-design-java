package stock.view;

import stock.controller.Features;

/**
 * This is the interface for the Graphical User Interface. It extends the IStockViewInterface, as it
 * is just an extension of the class and has the similar methods. It is the view for the GUI and
 * communicates with the controller.
 */
public interface GUIStockView extends IStockView {
  /**
   * This method is used for the communication between the controller and the view. It is used for
   * transfer of data and to maintain separation from the view.
   *
   * @param features are the channel to communicate with the view.
   */
  void addFeatures(Features features);
}
