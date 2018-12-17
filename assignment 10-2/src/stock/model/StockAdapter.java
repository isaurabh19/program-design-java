package stock.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The adapter class for converting Stock interface objects into their JSON and vice versa.
 */
public class StockAdapter extends TypeAdapter<StockImpl> {
  @Override
  public void write(JsonWriter jsonWriter, StockImpl iStock) throws IOException {
    if (iStock == null) {
      jsonWriter.nullValue();
      return;
    }
    String stockRepresentation = iStock.getTickerSymbol() + "," + iStock.getDateOfPurchase() + "," +
            iStock.getCostBasisOfStock();
    jsonWriter.value(stockRepresentation);
  }

  @Override
  public StockImpl read(JsonReader jsonReader) throws IOException {
    if (jsonReader.peek() == null) {
      jsonReader.nextNull();
      return null;
    }
    String stockRepresentation = jsonReader.nextString();
    String[] stockFields = stockRepresentation.split(",");
    String tickerSymbol = stockFields[0];
    String dateTime = stockFields[1];
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
    double costBasis = Double.parseDouble(stockFields[2]);
    return new StockImpl(tickerSymbol, costBasis, localDateTime);
  }
}
