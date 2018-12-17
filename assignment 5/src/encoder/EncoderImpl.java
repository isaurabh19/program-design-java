package encoder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Implements an Encoder interface using Huffman encoding. The Huffman encoding is implemented using
 * a PriorityQueue. The codes generated by this implementation will be prefix codes. It maintains a
 * frequency table of symbol in the message and it's occurrence. It generates a code table of
 * message symbol and it's representing code. The priority of any (Symbol,Frequency) entry is
 * decided by the frequency and in case of same frequency by lexicographical priority of symbol.
 */
public class EncoderImpl implements Encoder {

  private Set<Character> codeSymbols = new LinkedHashSet<>();
  private Map<String, Integer> frequencyTable = new HashMap<>();
  private Map<Character, String> codeTable = new HashMap<>();
  private Queue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(
      (o1, o2) -> {
        if (o1.getValue().equals(o2.getValue())) {
          return o1.getKey().compareTo(o2.getKey());
        }
        return (int) o1.getValue() - o2.getValue();
      });

  private void createFrequencyTable(String message) {
    for (Character character : message.toCharArray()) {
      if (frequencyTable.containsKey(character + "")) {
        int count = frequencyTable.get(character + "");
        frequencyTable.put(character + "", count + 1);
      } else {
        frequencyTable.put(character + "", 1);
      }
      if (!codeTable.containsKey(character)) {
        codeTable.put(character, "");
      }
    }
  }

  private String encodeMessage(String message) {
    StringBuilder encodedMessage = new StringBuilder();
    for (Character symbol : message.toCharArray()) {
      encodedMessage.append(codeTable.get(symbol));
    }
    return encodedMessage.toString();
  }

  private void createCodeSymbolSet(String codingSymbols) {
    if (codingSymbols.isEmpty()) {
      throw new IllegalArgumentException("empty coding symbols not allowed");
    }
    for (Character c : codingSymbols.toCharArray()) {
      codeSymbols.add(c);
    }
  }

  private void updateCodeTable(List<Map.Entry<String, Integer>> listOfSymbolEntries) {
    Iterator<Character> codeSymbolsIterator = codeSymbols.iterator();
    for (Map.Entry<String, Integer> entry : listOfSymbolEntries) {
      String currentSymbol = entry.getKey();
      char newCode = codeSymbolsIterator.next();
      for (int i = 0; i < currentSymbol.length(); i++) {
        String codeSoFar = codeTable.get(currentSymbol.charAt(i));
        codeTable.put(currentSymbol.charAt(i), newCode + codeSoFar);
      }
    }
  }

  @Override
  public String encode(String message, String codingSymbols) throws IllegalArgumentException {
    createCodeSymbolSet(codingSymbols);
    createFrequencyTable(message);
    priorityQueue.addAll(frequencyTable.entrySet());
    while (priorityQueue.size() > 1) {
      StringBuilder newEntryString = new StringBuilder();
      List<Map.Entry<String, Integer>> listOfSymbolEntries = new ArrayList<>();
      int newCount = 0;
      for (int i = 0; i < codeSymbols.size(); i++) {
        if (!priorityQueue.isEmpty()) {
          Map.Entry<String, Integer> entry = priorityQueue.poll();
          listOfSymbolEntries.add(entry);
          newEntryString.append(entry.getKey());
          newCount += entry.getValue();
        }
      }
      updateCodeTable(listOfSymbolEntries);
      Map.Entry<String, Integer> newEntry = new AbstractMap.SimpleEntry<String, Integer>(
              newEntryString.toString(), newCount);
      priorityQueue.add(newEntry);

    }

    return encodeMessage(message);
  }

  @Override
  public Map<Character, String> getCodeDictionary() {
    return this.codeTable;
  }
}
