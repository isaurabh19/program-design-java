package freecell.model;

/**
 * An abstract builder class for instantiating a freecell model game. Any builder extending this
 * class must implement a method to return the instance of the particular model class where the
 * builder resides or is made for.
 */
abstract class AbstractFreecellOperationsBuilder implements FreecellOperationsBuilder {
  protected int openSize;
  protected int cascadeSize;

  protected AbstractFreecellOperationsBuilder() {
    this.cascadeSize = 4;
    this.openSize = 1;
  }

  @Override
  public FreecellOperationsBuilder cascades(int cascadeSize) {
    if (cascadeSize < 4) {
      throw new IllegalArgumentException("Cascade deck size cannot be " + cascadeSize +
              ". It should be at least 4.");
    }
    this.cascadeSize = cascadeSize;
    return this;
  }

  @Override
  public FreecellOperationsBuilder opens(int openSize) {
    if (openSize < 1) {
      throw new IllegalArgumentException("Open deck size cannot be " + openSize + ". " +
              "It should be at least 1");
    }
    this.openSize = openSize;
    return this;
  }

  @Override
  public FreecellOperations<Cards> build() {
    return this.getModelInstance(this.cascadeSize, this.openSize);
  }

  protected abstract FreecellOperations<Cards> getModelInstance(int cascadeSize, int openSize);
}
