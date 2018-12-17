===============================Design Changes====================================================
The new feature to now persist strategy and portfolio lead to a new interface for the model that
added methods to persist a portfolio. It extends the previous model interface, thus adding new features
 as part of this interface. However, the model accepts a PersistPortfolio object to
to whom it delegates the job of IO operations. Thereby, model is still free from tasks not related
to it.

For strategy, PersistStrategy class handles loading and saving of strategies. These persist
classes are instantiated by the controller. So the controller can decide which format to convert
these files to.

The view is now implemented as a GUI. To facilitate this view, the new interface for GUI extends the
previous interface and allows the controller to add features as callbacks.

We implement a new class for the controller interface. The previous class supports console operations
along with the new features added.

The third part library used to serialize and deserialize the portfolio and strategy is gson-2.8.0.
The link is attached here:GSON : https://github.com/google/gson/blob/master/LICENSE

To plot graph we use, JFreeChart.
The link for that is here:jFreeChart : http://www.jfree.org/gpl.php

===============================Design changes=====================================================
The new feature added in this version is ability to add stocks to the portfolio by a strategy.
To incorporate this feature, we have designed a Strategy interface. Previous way of adding stocks
is now called regular strategy implemented by RegularStrategy class that implements Strategy
interface. The new strategy is placed in DollarCostStrategy class. This design is open to addition
of new strategies without any design change as any new strategy has to implement the Strategy
interface.

Now since Strategy handles the task of adding stocks, the previous method signature of add method
in model now accepts an object of Strategy. Everything remains the same.

In order to add commission feature, the internal class PortfolioImpl handles the cost adjustment
for commission fee.

Model defined granular level operations and any strategy is a combination of adding stocks with
predicates. Thus there is no further design change for the model.

Our previous design was agnostic to the data source used to fetch stock data. Now to switch between
data sources, we defined a config.properties file where user can specify the source. A data source
factory DAOFactory then reads the properties files are sets the corresponding data source object.
A new addition of data source would only mean a new object class has to be defined implementing
DAOInterface as per the previous design. Now, a new object mapping will be created in the DAOFactory
for this new class and by just setting this property in properties file will allow the program to
switch to a new data source.

To facilitate this new feature to the user, the command for add stock now takes one more
parameter as strategy, either 'regular' or 'dollar_cost'.

Due to command design pattern, AddToPortfolio handled adding stocks. Now that class further delegates
the job to AddToPortfolioByStrategy interface. AddToPortfolioDollarCost and AddToPortfolioRegular
implement this interface and handle the job of adding stocks. This is the place where the Strategy
objects are fetched from a StrategyFactory class.

The corresponding object from the factory is then passed the data required to implement that strategy
in its constructor and this object is now passed to model.

There are no further design changes as the previous design along with these changes is able to
absorb new features.
====================================================================================================

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
