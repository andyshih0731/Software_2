import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Mingzhu
 * @author Andy
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Constructor test cases.
     */
    @Test
    public final void testConstructorEmpty() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testConstructorNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "apple", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple", "blue", "green");

        assertEquals(mExpected, m);
    }

    /*
     * Add test cases.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddRoutine() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "black", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "black", "yellow", "blue");
        //add is used thorough createFromArgs() method
        m.add("blue");
        assertEquals(mExpected, m);
    }

    /*
     * changeToExctrationMode() test cases.
     */
    @Test
    public final void changeToExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "Apple", "Bananas", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "Bananas", "Apple");
        mExpected.changeToExtractionMode();
        assertEquals(m, mExpected);
    }

    /*
     * removeFirst() method test cases.
     */
    @Test
    public final void removeFirstBorder() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String removed = m.removeFirst();
        assertEquals(removed, "apple");
        assertEquals(m, mExpected);
    }

    @Test
    public final void removeFirstRoutine() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "bananas", "green", "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "apple", "bananas", "green"); //already sorted order here
        m.changeToExtractionMode(); //extraction mode already tested
        String removed = m.removeFirst();
        assertEquals(removed, "apple");
        assertEquals(removed, mExpected.removeFirst());
        assertEquals(m, mExpected);
    }

    /*
     * IsInInsertionMode() test cases.
     */
    @Test
    public final void testIsInInsertionModeBorder() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected.isInInsertionMode(), true);
    }

    @Test
    public final void testIsInInsertionModeRoutine() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "black", "yellow", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "black", "yellow", "blue");

        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExpected.isInInsertionMode(), false);
    }

    /*
     * Order test cases.
     */
    @Test
    public final void testOrderBorder() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected.order(), m.order());
    }

    @Test
    public final void testOrderRoutine() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "black", "yellow", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "black", "yellow", "blue");

        assertEquals(mExpected.order(), m.order());
    }

    /*
     * Size test cases.
     */
    @Test
    public final void testSizeBorder() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected.size(), 0);
    }

    @Test
    public final void testSizeRoutine1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "black", "yellow", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "black", "blue", "green", "yellow");
        m.changeToExtractionMode();
        //will now set the order and make insertion mode = false
        assertEquals(mExpected, m);
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected.size(), 4);
    }

    @Test
    public final void testSizeRoutine2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "black", "yellow", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "black", "yellow", "blue");
        //here order and equality of m and mExpected is not tested (unlike SizeRoutine1)
        assertEquals(mExpected.size(), m.size());
        assertEquals(mExpected.size(), 4);
    }
}
