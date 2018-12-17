package stock.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Implements the persisting of Portfolio object into a JSON and convert it back. Uses GSON to
 * perform JSON activities. The path where the jsons are stored is passed in the constructor.
 */
public class PersistPortfolioJSONImpl implements PersistPortfolioJSON {
  private final Gson gson;
  private final String pathToSave;

  /**
   * Constructs a new object with the save path set.
   *
   * @param pathToSave the path where the jsons will be saved.
   */
  public PersistPortfolioJSONImpl(String pathToSave) {
    gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(IStock.class, new StockAdapter())
            .enableComplexMapKeySerialization()
            .create();
    this.pathToSave = pathToSave;
  }

  @Override
  public void savePortfolio(IPortfolio portfolio) throws IOException {
    String portfolioJSON = "";
    BufferedWriter bufferedWriter = null;
    try {
      String filename = portfolio.getPortfolioName() + ".json";
      File file = Paths.get(pathToSave, filename).toFile();
      Writer writer = new FileWriter(file);
      bufferedWriter = new BufferedWriter(writer);
      portfolioJSON = gson.toJson(portfolio, PortfolioImpl.class);
      bufferedWriter.write(portfolioJSON);
    } finally {
      try {
        if (bufferedWriter != null) {
          bufferedWriter.close();
        }
      } catch (Exception e) {
        //IO error in buffered reader.
      }
    }
  }

  @Override
  public IPortfolio loadPortfolio(String portfolioPath) {
    String portfolioJSON = "";
    try {
      portfolioJSON = new String(Files.readAllBytes(Paths.get(portfolioPath)));
    } catch (IOException e) {
      throw new IllegalArgumentException("File path doesn't exist.\n");
    } catch (JsonParseException e) {
      throw new IllegalArgumentException("Invalid json format.\n");
    }
    return gson.fromJson(portfolioJSON, new TypeToken<PortfolioImpl>() {{
      }}.getType());
  }
}
