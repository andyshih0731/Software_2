import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Mingzhu Bao and Andy Shih
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Test the empty map4 constructor.
     */
    @Test
    public final void constructorTestEmpty() {
        Map<String, String> s = this.constructorTest();
        Map<String, String> sExpected = this.constructorRef();
        assertEquals(s, sExpected);
    }

    /**
     * Test the Non-Empty map4 constructor.
     */
    @Test
    public final void constructorTestNonEmpty() {
        Map<String, String> s = this.createFromArgsTest("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        Map<String, String> sExpected = this.createFromArgsRef("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        assertEquals(s, sExpected);
    }

    /**
     * Border test for add method.
     */
    @Test
    public final void addBorder() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();
        s.add("Bananas", "20");
        sExpected.add("Bananas", "20");
        assertEquals(s, sExpected);
    }

    /**
     * Routine test for add method.
     */
    @Test
    public final void addRoutine() {
        Map<String, String> s = this.createFromArgsTest("Black", "100", "20",
                "20");
        Map<String, String> sExpected = this.createFromArgsRef("Bananas", "20",
                "Black", "100", "5", "5", "20", "20");
        s.add("Bananas", "20");
        s.add("5", "5");
        assertEquals(s, sExpected);

        s.add("Apples", "5");
        assertTrue(!s.equals(sExpected));
    }

    /**
     * Border Test for remove method.
     */
    @Test
    public final void removeBorder() {
        Map<String, String> s = this.createFromArgsTest("Bananas", "20");
        Map<String, String> sExpected = this.constructorRef();
        Pair<String, String> removed = s.remove("Bananas");
        assertEquals(removed.key(), "Bananas");
        assertEquals(removed.value(), "20");
        assertEquals(s, sExpected);
    }

    /**
     * Routine Test for remove method.
     */
    @Test
    public final void removeRoutine() {
        Map<String, String> s = this.createFromArgsTest("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        Map<String, String> sExpected = this.createFromArgsRef("Apples", "5",
                "Bananas", "20", "5", "5", "20", "20");
        Pair<String, String> removed = s.remove("Black");
        assertEquals(removed.key(), "Black");
        assertEquals(removed.value(), "100");
        assertEquals(s, sExpected);
    }

    /**
     * Border Test for removeAny method.
     */
    @Test
    public final void removeAnyBorder() {
        Map<String, String> s = this.createFromArgsTest("Bananas", "20");
        Map<String, String> sExpected = this.constructorRef();
        Pair<String, String> removed = s.removeAny();
        //value and key methods will have been proven functional through test written
        //below (b/c @Test methods do not run necessarily in consecutive order)
        assertEquals(removed.key(), "Bananas");
        assertEquals(removed.value(), "20");
        assertEquals(s, sExpected);
    }

    /**
     * Routine Test for removeAny method.
     */
    @Test
    public final void removeAnyRoutine() {
        Map<String, String> s = this.createFromArgsTest("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        Pair<String, String> removed = s.removeAny();
        Map<String, String> sExpected = this.createFromArgsRef("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        Pair<String, String> testRemoved = sExpected.remove(removed.key());
        assertEquals(removed, testRemoved);
        assertEquals(s, sExpected);
    }

    /**
     * Routine Test for value method. No test for border of value.
     */
    @Test
    public final void valueRoutine1() {
        Map<String, String> s = this.createFromArgsTest("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        String first = s.value("Apples");
        String second = s.value("5");
        assertEquals(first, second);
        assertEquals(first, "5");
    }

    /**
     * Routine Test for value method.
     */
    @Test
    public final void valueRoutine2() {
        Map<String, String> s = this.createFromArgsTest("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "25", "20");
        String first = s.value("Bananas");
        String second = s.value("25");
        assertEquals(first, second);
        assertEquals(first, "20");
    }

    /**
     * Border Test for hasKey method.
     */
    @Test
    public final void hasKeyTestBorder() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();
        String keyUT = "Black";
        //keyUT = Key_Under_Test
        assertEquals(false, s.hasKey(keyUT)); //keyUT = Key_Under_Test
        assertEquals(sExpected.hasKey(keyUT), s.hasKey(keyUT));
    }

    /**
     * Border Test for hasKey method.
     */
    @Test
    public final void hasKeyTestBorder2() {
        //this test is for Map4() constructor
        Map<String, String> s = this.createFromArgsTest("Apples", "5");
        Map<String, String> sExpected = this.createFromArgsRef("Apples", "5");
        String keyUT = "Black";
        //keyUT = Key_Under_Test

        assertEquals(false, s.hasKey(keyUT)); //keyUT = Key_Under_Test
        assertEquals(s.hasKey(keyUT), sExpected.hasKey(keyUT));
    }

    /**
     * Routine Test for hasKey method.
     */
    @Test
    public final void hasKeyTestRoutine() {
        //this test is for Map4() constructor
        Map<String, String> s = this.createFromArgsTest("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        Map<String, String> sExpected = this.createFromArgsRef("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        String keyUT = "Black"; //keyUT = Key_Under_Test

        assertEquals(true, s.hasKey(keyUT)); //keyUT = Key_Under_Test
        assertEquals(s.hasKey(keyUT), sExpected.hasKey(keyUT));
        keyUT = "100";
        assertEquals(false, s.hasKey(keyUT)); //keyUT = Key_Under_Test
        assertEquals(s.hasKey(keyUT), sExpected.hasKey(keyUT));
    }

    /**
     * Border Test for size method.
     */
    @Test
    public final void sizeTestBorder() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();
        assertEquals(0, s.size());
        assertEquals(sExpected.size(), s.size());
    }

    /**
     * Border Test for size method.
     */
    @Test
    public final void sizeTestBorder2() {
        //this test is for Map4() constructor
        Map<String, String> s = this.createFromArgsTest("Apples", "5");
        Map<String, String> sExpected = this.createFromArgsRef("Apples", "5");
        assertEquals(1, s.size());
        assertEquals(sExpected.size(), s.size());
    }

    /**
     * Routine Test for size method.
     */
    @Test
    public final void sizeTestRoutine() {
        //this test is for Map4() constructor
        Map<String, String> s = this.createFromArgsTest("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        Map<String, String> sExpected = this.createFromArgsRef("Apples", "5",
                "Bananas", "20", "Black", "100", "5", "5", "20", "20");
        assertEquals(5, s.size());
        assertEquals(sExpected.size(), s.size());
        s.add("Oranges", "50");
        sExpected.add("Oranges", "50");
        assertEquals(6, s.size());
        assertEquals(sExpected.size(), s.size());
        s.remove("Black");
        s.remove("5");
        assertEquals(4, s.size());
    }

}
