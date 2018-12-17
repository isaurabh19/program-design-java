The format of storing portfolio and strategy is in JSON.

For example, Portfolio object is stored as,
{"portfolioName":"FANG","mapOfStocks":{"MSFT,2018-11-14T10:30,104.97":[47.0,1.5],"GOOG,2018-11-14T10:30,1043.66":[4.0,1.5],"FB,2018-11-14T10:30,144.22":[34.0,1.5],"msft,2017-11-14T10:30,84.05":[29.0,1.5],"msft,2017-11-14T10:30,84.05":[29.0,1.5],"msft,2017-11-14T10:30,84.05":[29.0,1.5],"msft,2017-11-14T10:30,84.05":[29.0,1.5]}}

where portfolioName is the unique name for the portfolio.
mapOfStocks is a mapping of stock object and array of quantity of stock and commission.

The stock object is represented as for example,
{"MSFT,2018-11-14T11:30"}
where each comma separated value is the instance field.

The strategy is represented as:
{"tickerAndWeight":{"msft":50.0,"fb":50.0},"amount":10000.0,"startDate":{"date":{"year":2015,"month":2,"day":2},"time":{"hour":16,"minute":0,"second":0,"nano":0}},"endDate":{"date":{"year":2018,"month":12,"day":6},"time":{"hour":13,"minute":0,"second":59,"nano":677000000}},"unending":true,"intervalInDays":90,"commissionFee":1.0,"strategyName":"dc1","TYPE":"DollarCost"}

where each key is the attribute of each Strategy class.