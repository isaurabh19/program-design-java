package stock.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Implements the persisting of Strategy object into a JSON and convert it back. Uses GSON to
 * perform JSON activities. The path where the jsons are stored is passed in the constructor.
 */
public class PersistStrategyJSONImpl implements PersistStrategyJSON {
  private final Gson gson;
  private final String pathToSave;

  /**
   * Constructs a new object with the save path set.
   *
   * @param pathToSave the path where the jsons will be saved.
   */
  public PersistStrategyJSONImpl(String pathToSave) {
    this.pathToSave = pathToSave;
    gson = new GsonBuilder().create();
  }

  @Override
  public void saveStrategy(Strategy strategy) throws IOException {
    String strategyJSON = "";
    BufferedWriter bufferedWriter = null;
    try {
      String filename = strategy.toString() + ".json";
      File file = Paths.get(pathToSave, filename).toFile();
      Writer writer = new FileWriter(file);
      bufferedWriter = new BufferedWriter(writer);
      if (strategy.getType().equalsIgnoreCase("DollarCost")) {
        strategyJSON = gson.toJson(strategy, DollarCostStrategy.class);
      } else {
        strategyJSON = gson.toJson(strategy, RegularStrategy.class);
      }
      bufferedWriter.write(strategyJSON);
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
  public Strategy loadStrategy(String strategyPath) {
    String strategyJSON = "";
    try {
      strategyJSON = new String(Files.readAllBytes(Paths.get(strategyPath)));
    } catch (IOException e) {
      throw new IllegalArgumentException("File path doesn't exist.\n");
    }
    JsonParser parser = new JsonParser();
    JsonObject jsonObject = parser.parse(strategyJSON).getAsJsonObject();
    String type = jsonObject.get("TYPE").getAsString();
    if (type.equalsIgnoreCase("DollarCost")) {
      return gson.fromJson(strategyJSON, new TypeToken<DollarCostStrategy>() {{
        }}.getType());
    }
    return gson.fromJson(strategyJSON, new TypeToken<RegularStrategy>() {{
      }}.getType());
  }
}
