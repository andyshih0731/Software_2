import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;
import components.program.Program;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;

/**
 * JUnit test fixture for {@code Program}'s constructor and kernel methods.
 *
 * @author Mingzhu
 * @author Andy
 *
 */
public abstract class ProgramTest {

    /**
     * The name of a file containing a BL program. FILE_NAME_1 is a sample
     * (Routine) program
     */
    private static final String FILE_NAME_1 = "data/Test.bl";
    /**
     * The name of a file containing a BL program. FILE_NAME_2 is a sample
     * (Routine) program
     */
    private static final String FILE_NAME_2 = "data/SampleProgram1.bl";
    /**
     * FILE_NAME_BORDER has no instructions and simple if with one call in the
     * BODY.
     */
    private static final String FILE_NAME_BORDER = "data/SimpleIf.bl";
    /**
     * FILE_NAME_NEW has no instructions and no body with name "Unnamed".
     */
    private static final String FILE_NAME_NEW = "data/newProg.bl";

    /**
     * Invokes the {@code Program} constructor for the implementation under test
     * and returns the result.
     *
     * @return the new program
     * @ensures constructor = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorTest();

    /**
     * Invokes the {@code Program} constructor for the reference implementation
     * and returns the result.
     *
     * @return the new program
     * @ensures constructor = ("Unnamed", {}, compose((BLOCK, ?, ?), <>))
     */
    protected abstract Program constructorRef();

    /**
     *
     * Creates and returns a {@code Program}, of the type of the implementation
     * under test, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed to create the program
     * @return the constructed program
     * @ensures createFromFile = [the program as parsed from the file]
     */
    private Program createFromFileTest(String filename) {
        Program p = this.constructorTest();
        SimpleReader file = new SimpleReader1L(filename);
        p.parse(file);
        file.close();
        return p;
    }

    /**
     *
     * Creates and returns a {@code Program}, of the reference implementation
     * type, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed to create the program
     * @return the constructed program
     * @ensures createFromFile = [the program as parsed from the file]
     */
    private Program createFromFileRef(String filename) {
        Program p = this.constructorRef();
        SimpleReader file = new SimpleReader1L(filename);
        p.parse(file);
        file.close();
        return p;
    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructorEmpty() {
        /*
         * Setup
         */
        Program pRef = this.constructorRef();

        /*
         * The call
         */
        Program pTest = this.constructorTest();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    @Test
    public final void testConstructorNonEmpty() {
        /*
         * Setup
         */
        Program pRef = this.createFromFileRef(FILE_NAME_BORDER);

        /*
         * The call
         */
        Program pTest = this.createFromFileTest(FILE_NAME_BORDER);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    /**
     * Test name.
     */
    @Test
    public final void testNameNewProg() { //BORDER
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_NEW);
        Program pRef = this.createFromFileRef(FILE_NAME_NEW);

        /*
         * The call
         */
        String resultTest = pTest.name();
        String resultExpected = pRef.name();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("Unnamed", resultTest);
        assertEquals(resultTest, resultExpected);
    }

    @Test
    public final void testNameSimpleIfRoutine() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_BORDER);
        Program pRef = this.createFromFileRef(FILE_NAME_BORDER);

        /*
         * The call
         */
        String resultTest = pTest.name();
        String resultExpected = pRef.name();
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("SimpleIf", resultTest);
        assertEquals(resultTest, resultExpected);

    }

    @Test
    public final void testNameSampleProgram1Routine() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);

        /*
         * The call
         */
        String resultTest = pTest.name();
        String resultExpected = pRef.name();
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("SampleProgram1", resultTest);
        assertEquals(resultTest, resultExpected);
    }

    @Test
    public final void testNameRoutine() { //FILE_NAME_1 IS UUT
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);

        /*
         * The call
         */
        String resultTest = pTest.name();
        String resultExpected = pRef.name();
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("Test", resultTest);
        assertEquals(resultTest, resultExpected);

    }

    /**
     * Test setName.
     */
    @Test
    public final void testSetNameRoutine() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        String newName = "Replacement";
        pRef.setName(newName);

        /*
         * The call
         */
        pTest.setName(newName);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
    }

    @Test
    public final void testSetNameBorder() {
        Program pTest = this.createFromFileTest(FILE_NAME_NEW);
        Program pRef = this.createFromFileRef(FILE_NAME_NEW);
        String newName = "BlackYellow";
        pRef.setName(newName);

        /*
         * The call
         */
        pTest.setName(newName);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals("BlackYellow", pTest.name()); //.name() is proven functional above
    }

    /**
     * Test newContext.
     */
    @Test
    public final void testNewContextRoutine() { //context is non-Empty
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> cRef = pRef.newContext();

        /*
         * The call
         */
        Map<String, Statement> cTest = pTest.newContext();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    @Test
    public final void testNewContextBorder() { //when file has nothing in context
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_NEW);
        Program pRef = this.createFromFileRef(FILE_NAME_NEW);
        Map<String, Statement> cRef = pRef.newContext();

        /*
         * The call
         */
        Map<String, Statement> cTest = pTest.newContext();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test swapContext.
     */
    @Test
    public final void testSwapContextRoutine() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Map<String, Statement> contextRef = pRef.newContext();
        Map<String, Statement> contextTest = pTest.newContext();
        String oneName = "one";
        pRef.swapContext(contextRef);
        Pair<String, Statement> oneRef = contextRef.remove(oneName);
        /* contextRef now has just "two" */
        pRef.swapContext(contextRef);
        /* pRef's context now has just "two" */
        contextRef.add(oneRef.key(), oneRef.value());
        /* contextRef now has just "one" */

        /* Make the reference call, replacing, in pRef, "one" with "two": */
        pRef.swapContext(contextRef);

        pTest.swapContext(contextTest);
        Pair<String, Statement> oneTest = contextTest.remove(oneName);
        /* contextTest now has just "two" */
        pTest.swapContext(contextTest);
        /* pTest's context now has just "two" */
        contextTest.add(oneTest.key(), oneTest.value());
        /* contextTest now has just "one" */

        /*
         * The call
         */
        pTest.swapContext(contextTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(contextRef, contextTest);
    }

    @Test
    public final void testSwapContextRoutine2() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);
        Map<String, Statement> contextRef = pRef.newContext();
        Map<String, Statement> contextTest = pTest.newContext();
        String oneName = "FindObstacle";
        pRef.swapContext(contextRef);
        Pair<String, Statement> oneRef = contextRef.remove(oneName);
        /* contextRef now has just "two" */
        pRef.swapContext(contextRef);
        /* pRef's context now has just "two" */
        contextRef.add(oneRef.key(), oneRef.value());
        /* contextRef now has just "one" */

        /* Make the reference call, replacing, in pRef, "one" with "two": */
        pRef.swapContext(contextRef);

        pTest.swapContext(contextTest);
        Pair<String, Statement> oneTest = contextTest.remove(oneName);
        /* contextTest now has just "two" */
        pTest.swapContext(contextTest);
        /* pTest's context now has just "two" */
        contextTest.add(oneTest.key(), oneTest.value());
        /* contextTest now has just "one" */

        /*
         * The call
         */
        pTest.swapContext(contextTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(contextRef, contextTest);
    }

    @Test
    public final void testSwapContextBorder() { //no content in context HERE
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_BORDER);
        Program pRef = this.createFromFileRef(FILE_NAME_BORDER);
        Map<String, Statement> contextRef = pRef.newContext();
        Map<String, Statement> contextTest = pTest.newContext();
        /*
         * The call
         */
        pTest.swapContext(contextTest);
        pRef.swapContext(contextRef);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(contextRef, contextTest);
    }

    /**
     * Test newBody.
     */
    @Test
    public final void testNewBodyRoutine() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bRef = pRef.newBody();

        /*
         * The call
         */
        Statement bTest = pTest.newBody();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

    @Test
    public final void testNewBodyBorder() { //this.context HERE is empty
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_BORDER);
        Program pRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement bRef = pRef.newBody();

        /*
         * The call
         */
        Statement bTest = pTest.newBody();

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bRef, bTest);
    }

    /**
     * Test swapBody.
     */
    @Test
    public final void testSwapBodyRoutine() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_1);
        Program pRef = this.createFromFileRef(FILE_NAME_1);
        Statement bodyRef = pRef.newBody();
        Statement bodyTest = pTest.newBody();
        pRef.swapBody(bodyRef);
        Statement firstRef = bodyRef.removeFromBlock(0);
        /* bodyRef now lacks the first statement */
        pRef.swapBody(bodyRef);
        /* pRef's body now lacks the first statement */
        bodyRef.addToBlock(0, firstRef);
        /* bodyRef now has just the first statement */

        /* Make the reference call, replacing, in pRef, remaining with first: */
        pRef.swapBody(bodyRef);

        pTest.swapBody(bodyTest);
        Statement firstTest = bodyTest.removeFromBlock(0);
        /* bodyTest now lacks the first statement */
        pTest.swapBody(bodyTest);
        /* pTest's body now lacks the first statement */
        bodyTest.addToBlock(0, firstTest);
        /* bodyTest now has just the first statement */

        /*
         * The call
         */
        pTest.swapBody(bodyTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bodyRef, bodyTest);
    }

    @Test
    public final void testSwapBodyRoutine2() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_2);
        Program pRef = this.createFromFileRef(FILE_NAME_2);
        Statement bodyRef = pRef.newBody();
        Statement bodyTest = pTest.newBody();
        pRef.swapBody(bodyRef);
        Statement firstRef = bodyRef.removeFromBlock(0);
        /* bodyRef now lacks the first statement */
        pRef.swapBody(bodyRef);
        /* pRef's body now lacks the first statement */
        bodyRef.addToBlock(0, firstRef);
        /* bodyRef now has just the first statement */

        /* Make the reference call, replacing, in pRef, remaining with first: */
        pRef.swapBody(bodyRef);

        pTest.swapBody(bodyTest);
        Statement firstTest = bodyTest.removeFromBlock(0);
        /* bodyTest now lacks the first statement */
        pTest.swapBody(bodyTest);
        /* pTest's body now lacks the first statement */
        bodyTest.addToBlock(0, firstTest);
        /* bodyTest now has just the first statement */

        /*
         * The call
         */
        pTest.swapBody(bodyTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bodyRef, bodyTest);
    }

    @Test
    public final void testSwapBodyBorder1() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_NEW);
        Program pRef = this.createFromFileRef(FILE_NAME_NEW);
        Statement bodyRef = pRef.newBody();
        Statement bodyTest = pTest.newBody();
        /*
         * The call
         */
        pRef.swapBody(bodyRef);
        pTest.swapBody(bodyTest);
        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bodyRef, bodyTest);
    }

    @Test
    public final void testSwapBodyBorder2() {
        /*
         * Setup
         */
        Program pTest = this.createFromFileTest(FILE_NAME_BORDER);
        Program pRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement bodyRef = pRef.newBody();
        Statement bodyTest = pTest.newBody();

        pRef.swapBody(bodyRef);
        Statement firstRef = bodyRef.removeFromBlock(0);
        /* bodyRef is empty */
        pRef.swapBody(bodyRef);
        /* both pRef's and bodyRef's body is empty */
        bodyRef.addToBlock(0, firstRef);
        /* bodyRef now has just ONE statement */

        /* Make the reference call, replacing, in pRef, remaining with first: */
        pRef.swapBody(bodyRef);

        pTest.swapBody(bodyTest);
        Statement firstTest = bodyTest.removeFromBlock(0);
        /* bodyTest is empty */
        pTest.swapBody(bodyTest);
        /* both pTest's and bodyTest's body is empty */
        bodyTest.addToBlock(0, firstTest);
        /* bodyTest now has just ONE statement */

        /*
         * The call
         */
        pTest.swapBody(bodyTest);

        /*
         * Evaluation
         */
        assertEquals(pRef, pTest);
        assertEquals(bodyRef, bodyTest);
    }
}
