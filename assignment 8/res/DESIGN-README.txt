The design of the program follows a MVC pattern. Each component is placed in its corresponding
package. Thus all source code resides in stock package, which contains packages: model, view and
controller. The controller asks the view to obtain the user input and then transfers those to
the model as it would expect. Similarly, the controller takes the output from the model and sends
it to the view to display.

package controller;
This package contains a Launch.java which has the main method. This class initiates the execution
of the program. The main method initializes the model, view with System.in and System.out, and
the controller with the created view and model. The main method then passed the control to the
controller.

IStockController.java is the interface for the controller. StockController implements this
interface. This class takes the view and model in the constructor. The controller accepts first
input from the view as a command. Based on the command the controller delegates the task of
handling inputs and calling the model to respective command classes defined in the 'commands'
package.

package controller.commands;
TradeCommands.java is the interface that all new commands will implement. All the implementing
command classes implement this method and handle inputs and call appropriate model methods.

This way, the controller delegates each command to its corresponding class thereby following a
command design pattern. This makes the controller flexible to addition of new commands.
The controller is agnostic to what view or model are implemented as.

package model:
IPortfolioManagerModel.java is the model interface. Any model implementing this interface will not
affect the controller logic as it relies on the contract of this interface.
PortfolioManagerModel.java class implements this interface. The controller communicates only to
this model class by sending the user inputs from the view and outputs from this model to the view.
Model class is a collection of IPortfolio.java objects. IPortfolio is a collection of IStock.java.
IStock object represents a stock of a company.
The model delegates any operation on the individual IPortfolio object which in turn
delegates the tasks on IStock objects.

package model.dao;
The model only stores the business logic .i.e managing stocks and portfolios. In order to get the
data for these operations, the model relies on the DAOInterface.java. DAOInterface does the job
of fetching the data from the source, which is configured in the class implementing this interface.
The class that will implement this interface is configured in class that implements the
IPortfolio.
This way the model is agnostic to change in which source is used for getting data and how to get
that data. The DAOInterface takes care of that and the model only has to access the methods defined
in this interface.

package view;
IStockView.java is the interface for the view. Any view implementing this interface will have no
effect on the implementation of the controller.

Thus all three components are designed such that new features would lead to minimal changes in them.
