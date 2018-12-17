import tester.Tester;

/**
 * 
 * JSON is a way of storing tree-shaped data, much like XML.
 * You have four tasks:
 * 1. Create the JSONObject class. The JSONObject class
 *    is a JSON and contains a list of JSONPairs.
 *    A JSONPair consists of a key, which is a String,
 *    and a value, which is a JSON. For a Fundies 1
 *    style data definition, see:
 *    https://course.ccs.neu.edu/cs2500f17/lab8.html
 *    Beware the data definition in this link uses Symbols,
 *    which Java does not have; as said above, use Strings instead.
 *    
 * 2. Create the following example in the ExamplesJSON class
 *    and name it myBigExample, and be sure to use JSONNull
 *    as opposed to the Java null (see the classes below).

{
  "zero": 0,
  "junk": [
    "",
    1,
    "cat",
    {
      "foo": null
    }
  ],
  "stuff": {
    "hello": true,
    "goodbye": "cat"
  }
}
 * 3. Design a method containsAllDefaults which determines if a JSON
 *    contains *all* four "default values." These are false,
 *    the empty string, 0, and null (again, JSONNull, not Java null). 
 *    Note that the keys in a JSONPair do not count as value contained
 *    within the JSON. For brevity's sake, you do not have to write 
 *    purpose statements. Testing requirements are described in task 4.
 *
 * 4. Test containsAllDefaults on this object. Let's say
 *    myBigExample.containsAllDefaults() equals b.
 *    Write a test on another JSON object where containsAllDefaults
 *    would return !b. See the code in testContainsDefaults if 
 *    this is confusing. For time's sake, you do not have to test helpers,
 *    but are welcome to.
 *    
 * Some differences between real-world JSON and this file:
 * In real-world JSON, numbers don't have to be integers.
 * Also, in real-world JSON, the keys in a JSONObject
 * must be unique. We omit both of these for simplicity.
 * 
 * Best of luck!
 */


abstract class JSON {
  //TODO: 3. Design containsAllDefaults.
}

abstract class JSONAtom extends JSON {}

class JSONBool extends JSONAtom {

  Boolean bool;

  JSONBool(Boolean bool) {
    this.bool = bool;
  }
}

class JSONString extends JSONAtom {

  String str;

  JSONString(String str) {
    this.str = str;
  }  
}

class JSONNumber extends JSONAtom {

  Number number;

  JSONNumber(Integer number) {
    this.number = number;
  }  
}

class JSONNull extends JSONAtom {}


class JSONArray extends JSON {
  
  ILoJSON array;
  
  JSONArray(ILoJSON array) {
    this.array = array;
  }
}


interface ILoJSON {}

class MTLoJSON implements ILoJSON {}

class ConsLoJSON implements ILoJSON {
  
  JSON first;
  ILoJSON rest;
  
  ConsLoJSON(JSON first, ILoJSON rest) {
    this.first = first;
    this.rest = rest;
  }
}

//TODO: 1. Create JSONObject class.

class ExamplesJSON {
//TODO: 2. Create myBigExample.

  boolean testContainsDefaults(Tester t) {
    // TODO: 4. Run real tests here.
    // return t.checkExpect(myBigExample.containsDefaults(), true/false) &&
    // t.checkExpect(someOtherExample.containsDefaults(), false/true);
    return false; 
  }
}