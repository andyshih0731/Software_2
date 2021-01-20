import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Lewi Noah Estifanos
 * @author Mingzhu Bao
 * @author Andy Shih
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /**
     * Test the empty natural number constructor.
     */
    public final void testNN3ConstructorParamEmpty() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest();
        NaturalNumber sExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test natural number constructor with parameter of integer.
     */
    @Test
    public final void testNN3ConstructorParamIntegerRoutine() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(56);
        NaturalNumber sExpected = this.constructorRef(56);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test natural number constructor with parameter of integer. Test conducted
     * around 0(Border), and compared against reference NN (with object type
     * NN1L)
     */
    @Test
    public final void testNN3ConstructorParamIntegerBorder() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(0);
        NaturalNumber sExpected = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * Test natural number constructor with parameter of String.
     */
    @Test
    public final void testNN3ConstructorParamStringRoutine() {
        /*
         * Call method under test
         */
        NaturalNumber testNN = this.constructorTest("101231453124513561342");
        NaturalNumber refNN = this.constructorRef("101231453124513561342");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testNN, refNN);
    }

    /**
     * Test natural number constructor with parameter of String under border
     * case(around 0).
     */
    @Test
    public final void testNN3ConstructorParamStringBorder() {
        /*
         * Call method under test
         */
        NaturalNumber testNN = this.constructorTest("0");
        NaturalNumber refNN = this.constructorRef("0");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testNN, refNN);
    }

    /**
     * Test natural number constructor with parameter of Natural Number.
     */
    @Test
    public final void testNN3ConstructorParamNNRoutine() {
        /*
         * Since it was proven above that the NNConstructor(String) functions as
         * expected it will be used to create the initial NN value, from which
         * the NNConstructor(NN) will initialize based on.
         */
        NaturalNumber testInput = this.constructorTest("25");
        NaturalNumber refInput = this.constructorRef("25");
        /*
         * Call method under test
         */
        NaturalNumber testNN = this.constructorTest(testInput);
        NaturalNumber refNN = this.constructorRef(refInput);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testNN, refNN);
    }

    /**
     * Test natural number constructor with parameter of Natural Number.
     */
    @Test
    public final void testNN3ConstructorParamNNBorder() {
        /*
         * Since it was proven above that the NNConstructor(String) functions as
         * expected it will be used to create the initial NN value, from which
         * the NNConstructor(NN) will initialize based on.
         */
        NaturalNumber testInput = this.constructorTest("0");
        NaturalNumber refInput = this.constructorRef("0");
        /*
         * Call method under test
         */
        NaturalNumber testNN = this.constructorTest(testInput);
        NaturalNumber refNN = this.constructorRef(refInput);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testNN, refNN);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //Above are constructor tests, below is test for multiplyBy10();

    /**
     * NaturalNumber3 constructor with parameter of Integer is tested by calling
     * "multiplyBy10()" method. Test conducted around 0 (Border), and compared
     * against reference NN (with object type NN1L)
     */
    @Test
    public final void multiplyBy10FromIntBorder() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(0);
        int product = 5;
        s.multiplyBy10(product);
        NaturalNumber sExpected = this.constructorRef(5);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * NaturalNumber3 constructor with parameter of Integer is tested by calling
     * "multiplyBy10()" method. Test conducted under routine case and compared
     * against reference NN (with object type NN1L)
     */
    @Test
    public final void mutiplyBy10FromIntRoutine() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(9673);
        int product = 8;
        s.multiplyBy10(product);
        NaturalNumber sExpected = this.constructorRef(96738);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(s, sExpected);
    }

    /**
     * NaturalNumber3 constructor with parameter of String is tested by calling
     * "multiplyBy10()" method. Test conducted around 0 (Border), and compared
     * against reference NN (with object type NN1L)
     */

    @Test
    public final void testNN3MultiplyBy10FromStringBorder() {

        NaturalNumber testNN = this.constructorTest("0");
        NaturalNumber refNN = this.constructorRef("7");
        /*
         * Call method under test
         */
        testNN.multiplyBy10(7);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testNN, refNN);
    }

    /**
     * NaturalNumber3 constructor with parameter of String is tested by calling
     * "multiplyBy10()" method. Test conducted around large Natural Numbers
     * (Routine), and compared against reference NN (of a different object type)
     */
    @Test
    public final void testNN3MultiplyBy10FromStringRoutine() {

        NaturalNumber testNN = this.constructorTest(
                "1413541533434897731435314134132489135771341349");
        NaturalNumber refNN = this.constructorRef(
                "14135415334348977314353141341324891357713413499");
        /*
         * Call method under test
         */
        testNN.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testNN, refNN);
    }

    /**
     * NaturalNumber3 constructor with parameter of NN is tested by calling
     * "multiplyBy10()" method. Test conducted around 0 (Border), and used to
     * append a number
     */
    @Test
    public final void testNN3MultiplyBy10FromNNBorder() {
        /*
         * Since the NN constructor without any formal parameters has been
         * tested to function as expected, then it will be used to indirectly
         * construct a NN initialized as 0 (see below)
         */
        NaturalNumber testNN = this.constructorTest(this.constructorTest());
        NaturalNumber refNN = this.constructorRef(this.constructorRef(9));
        /*
         * Call method under test
         */
        testNN.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testNN, refNN);
    }

    /**
     * NaturalNumber3 constructor with parameter of NN is tested by calling
     * "multiplyBy10()" method. Test conducted around large Natural Numbers
     * (Routine), and compared against reference NN
     */
    @Test
    public final void testNN3MultiplyBy10FromNNRoutine() {
        /*
         * Since it was proven above that the NNConstructor(String) functions as
         * expected it will be used to create the initial NN value, from which
         * the NNConstructor(NN) will initialize based on.
         */
        NaturalNumber testNN = this
                .constructorTest(this.constructorTest("13489778913577134134"));
        NaturalNumber refNN = this
                .constructorRef(this.constructorRef("134897789135771341349"));
        /*
         * Call method under test
         */
        testNN.multiplyBy10(9);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testNN, refNN);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //Below is the test for isZero();

    /**
     * NaturalNumber3 constructor with parameter of Integer is tested by calling
     * "isZero()" method. Test conducted under border case(around 0) and
     * compared against reference NN (with object type NN1L)
     */
    @Test
    public final void isZeroFromIntBorder() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(0);
        boolean zero = false;
        if (s.isZero()) {
            zero = true;
        }
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(zero, true);
    }

    /**
     * NaturalNumber3 constructor with parameter of Integer is tested by calling
     * "isZero()" method. Test conducted under routine case and compared against
     * reference NN (with object type NN1L)
     */
    @Test
    public final void isZeroFromIntRoutine() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(84);
        boolean zero = false;
        if (s.isZero()) {
            zero = true;
        }
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(zero, false);
    }

    /**
     * NaturalNumber3 constructor with parameter of NN is tested by calling
     * "IsZero()" instance method. Test conducted around 0 (Border), and
     * compared against reference NN (with a different object type)
     */
    @Test
    public final void testNN3IsZeroFromStringBorder() {
        /*
         * Since it was proven above that the NNConstructor(String) functions as
         * expected it will be used to create the initial NN value, from which
         * the NNConstructor(NN) will initialize based on.
         */
        NaturalNumber testNN = this.constructorTest("0");
        NaturalNumber refNN = this.constructorRef("0");
        /*
         * Call method under test
         */
        boolean testZero = testNN.isZero();
        boolean refZero = refNN.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testZero, true);
        assertEquals(testZero, refZero);
    }

    @Test
    public final void testNN3IsZeroFromStringRoutine() {

        NaturalNumber testNN = this
                .constructorTest("13413510113413579890135143415");
        NaturalNumber refNN = this
                .constructorRef("13413510113413579890135143415");
        /*
         * Call method under test
         */
        boolean testZero = testNN.isZero();
        boolean refZero = refNN.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testZero, false);
        assertEquals(testZero, refZero);
    }

    /**
     * NaturalNumber3 constructor with parameter of NN is tested by calling
     * "IsZero()" instance method. Test conducted around 0 (Border), and
     * compared against reference NN (with a different object type)
     */
    @Test
    public final void testNN3IsZeroFromNNBorder() {
        /*
         * Since it was proven above that the NNConstructor(String) functions as
         * expected it will be used to create the initial NN value, from which
         * the NNConstructor(NN) will initialize based on.
         */
        NaturalNumber testNN = this
                .constructorTest(this.constructorTest(97654));
        NaturalNumber refNN = this.constructorRef(this.constructorRef(97654));
        /*
         * Call method under test
         */
        boolean testZero = testNN.isZero();
        boolean refZero = refNN.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testZero, false);
        assertEquals(testZero, refZero);
    }

    /**
     * NaturalNumber3 constructor with parameter of NN is tested by calling
     * "IsZero()" instance method. Test conducted around 0 (Border), and
     * compared against reference NN (with a different object type)
     */
    @Test
    public final void testNN3IsZeroFromNNRutine() {
        /*
         * Since it was proven above that the NNConstructor(String) functions as
         * expected it will be used to create the initial NN value, from which
         * the NNConstructor(NN) will initialize based on.
         */
        NaturalNumber testNN = this.constructorTest(this.constructorTest(0));
        NaturalNumber refNN = this.constructorRef(this.constructorRef(0));
        /*
         * Call method under test
         */
        boolean testZero = testNN.isZero();
        boolean refZero = refNN.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testZero, refZero); //because true == true
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //Below is the test for divideBy10();

    /**
     * NaturalNumber3 constructor with parameter of Integer is tested by calling
     * "divideBy10()" method. Test conducted under routine case and compared
     * against reference NN (with object type NN1L)
     */
    @Test
    public final void divideBy10FromIntRoutine() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(767598558);
        NaturalNumber sExpected = this.constructorRef(767598558);
        int divided = s.divideBy10();
        sExpected.divideBy10();

        assertEquals(divided, 8);
        assertEquals(s, sExpected);

    }

    /**
     * NaturalNumber3 constructor with parameter of Integer is tested by calling
     * "divideBy10()" method. Test conducted under challenging case and compared
     * against reference NN (with object type NN1L)
     */
    @Test
    public final void divideBy10FromIntChallenging() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(6);
        int divided = s.divideBy10();
        NaturalNumber sExpected = this.constructorRef(0);
        assertEquals(s, sExpected);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(divided, 6);
    }

    /**
     * NaturalNumber3 constructor with parameter of Integer is tested by calling
     * "divideBy10()" method. Test conducted under border case(around 0) and
     * compared against reference NN (with object type NN1L)
     */
    @Test
    public final void divideBy10FromIntBorder() {
        /*
         * Call method under test
         */
        NaturalNumber s = this.constructorTest(0);
        int divided = s.divideBy10();
        NaturalNumber sExpected = this.constructorRef(0);
        assertEquals(s, sExpected);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(divided, 0);
    }

    /**
     * NaturalNumber3 constructor with parameter of String is tested by calling
     * "divideBy10()" instance method. Test conducted around (Border) of NN, and
     * compared against reference NN (with a different object type)
     */
    @Test
    public final void testNN3DivideBy10FromStringBorder() {

        NaturalNumber testNN = this.constructorTest("0");
        NaturalNumber refNN = this.constructorRef();
        /*
         * Call method under test
         */

        int testRem = testNN.divideBy10();
        int refRem = refNN.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testRem, 0);
        assertEquals(testRem, refRem);
    }

    /**
     * NaturalNumber3 constructor with parameter of NN is tested by calling
     * "divideBy10()" instance method. Test conducted around (Border) of NN, and
     * compared against reference NN (with a different object type)
     */
    @Test
    public final void testNN3DivideBy10FromNNBorder() {
        /*
         * Since it was proven above that the NNConstructor() functions as
         * expected it will be used to create the initial NN value, from which
         * the NNConstructor(NN) will initialize based on.
         */
        NaturalNumber testNN = this.constructorTest(this.constructorTest());
        NaturalNumber refNN = this.constructorRef(this.constructorRef("0"));
        /*
         * Call method under test
         */
        int testRem = testNN.divideBy10();
        int refRem = refNN.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testRem, 0);
        assertEquals(testRem, refRem);
    }

    /**
     * NaturalNumber3 constructor with parameter of NN is tested by calling
     * "IsZero()" instance method. Test conducted around 0 (Border), and
     * compared against reference NN (with a different object type)
     */
    @Test
    public final void testNN3DivideBy10FromStringRoutine() {

        NaturalNumber testNN = this
                .constructorTest("13413510113413579890135143415");
        NaturalNumber refNN = this
                .constructorRef("13413510113413579890135143415");
        /*
         * Call method under test
         */
        int testRem = testNN.divideBy10();
        int refRem = refNN.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testRem, 5);
        assertEquals(testRem, refRem);

        testRem = testNN.divideBy10();
        refRem = refNN.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testRem, 1);
        assertEquals(testRem, refRem);
    }

    /**
     * NaturalNumber3 constructor with parameter of NN is tested by calling
     * "IsZero()" instance method. Test conducted around 0 (Border), and
     * compared against reference NN (with a different object type)
     */
    @Test
    public final void testNN3DivideBy10FromNNRoutine() {
        /*
         * Since it was proven above that the NNConstructor(String) functions as
         * expected it will be used to create the initial NN value, from which
         * the NNConstructor(NN) will initialize based on.
         */

        NaturalNumber testNN = this
                .constructorTest("1341351011341313456123414314579890135143415");
        NaturalNumber refNN = this
                .constructorRef("1341351011341313456123414314579890135143415");
        /*
         * Call method under test
         */
        int testRem = testNN.divideBy10();
        int refRem = refNN.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testRem, refRem);
        assertEquals(testRem, 5);

        testRem = testNN.divideBy10();
        refRem = refNN.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testRem, 1);
        assertEquals(testRem, refRem);

        testRem = testNN.divideBy10();
        refRem = refNN.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(testRem, 4);
        assertEquals(testRem, refRem); //Multiple calls&Checks to the same method
    }

}
