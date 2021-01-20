import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.statement.StatementKernel.Condition;
import components.statement.StatementKernel.Kind;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code Statement}'s constructor and kernel methods.
 *
 * @author Mingzhu
 * @author Andy
 *
 */
public abstract class StatementTest {

    /**
     * The name of a file containing BL Statement(s). FILE_NAME_1 is a sample
     * (Routine) statement
     */
    private static final String FILE_NAME_1 = "data/sampleStatement.bl";
    /**
     * The name of a file containing BL Statement(s). FILE_NAME_2 is a sample
     * (Routine) statement
     */
    private static final String FILE_NAME_2 = "data/smallStatement.bl";
    /**
     * The name of a file containing BL Statement(s). FILE_NAME_BORDER tests the
     * border
     */
    private static final String FILE_NAME_BORDER = "data/statementBorder.bl";

    /**
     * The name of a file containing BL Statement(s).CALL_BORDER tests the
     * border of call
     */
    private static final String CALL_BORDER = "data/oneCall.bl";

    /**
     * The name of a file containing BL Statement(s).EMPTY_FILE, as the name
     * suggests, is a file with nothing inside of it (this provides an
     * interesting BORDER testing mechanism)
     */
    private static final String EMPTY_FILE = "data/emptyStatement.bl";

    /**
     * Invokes the {@code Statement} constructor for the implementation under
     * test and returns the result.
     *
     * @return the new statement
     * @ensures constructor = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorTest();

    /**
     * Invokes the {@code Statement} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new statement
     * @ensures constructor = compose((BLOCK, ?, ?), <>)
     */
    protected abstract Statement constructorRef();

    /**
     *
     * Creates and returns a block {@code Statement}, of the type of the
     * implementation under test, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed for the sequence of
     *            statements to go in the block statement
     * @return the constructed block statement
     * @ensures <pre>
     * createFromFile = [the block statement containing the statements
     * parsed from the file]
     * </pre>
     */
    private Statement createFromFileTest(String filename) {
        Statement s = this.constructorTest();
        SimpleReader file = new SimpleReader1L(filename);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        return s;
    }

    /**
     *
     * Creates and returns a block {@code Statement}, of the reference
     * implementation type, from the file with the given name.
     *
     * @param filename
     *            the name of the file to be parsed for the sequence of
     *            statements to go in the block statement
     * @return the constructed block statement
     * @ensures <pre>
     * createFromFile = [the block statement containing the statements
     * parsed from the file]
     * </pre>
     */
    private Statement createFromFileRef(String filename) {
        Statement s = this.constructorRef();
        SimpleReader file = new SimpleReader1L(filename);
        Queue<String> tokens = Tokenizer.tokens(file);
        s.parseBlock(tokens);
        file.close();
        return s;
    }

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructorEmpty() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef();

        /*
         * The call
         */
        Statement sTest = this.constructorTest();

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testConstructorEmptyFile() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(EMPTY_FILE);
        Statement sRef = this.createFromFileRef(EMPTY_FILE);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testConstructorNonEmpty() {
        /*
         * Setup
         */
        Statement sRef = this.createFromFileRef(FILE_NAME_1);

        /*
         * The call
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test kind of a WHILE statement.
     */
    @Test
    public final void testKindWhileRoutine() { //TODO: MORE @TEST
        /*
         * TODO: Setup TODO:
         */
        final int whilePos = 3;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_1);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = sourceTest.removeFromBlock(whilePos);
        Statement sRef = sourceRef.removeFromBlock(whilePos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);

        assertEquals(kTest, Kind.WHILE);
    }

    @Test
    public final void testKindIfRoutine() {
        /*
         * Setup
         */
        final int Pos = 1;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_1);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = sourceTest.removeFromBlock(Pos);
        Statement sRef = sourceRef.removeFromBlock(Pos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);
        assertEquals(kTest, Kind.IF);
    }

    @Test
    public final void testKindIfBorder() { //only IF in file (nothing else)
        //Testing border here has proven useless, thus this is not repeated
        //throughout other Kind tests
        /*
         * Setup
         */
        final int Pos = 0;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement sTest = sourceTest.removeFromBlock(Pos);
        Statement sRef = sourceRef.removeFromBlock(Pos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);

        assertEquals(sRef, sTest);
        assertEquals(kTest, Kind.IF);
    }

    @Test
    public final void testKindIfElseRoutine() {
        /*
         * Setup
         */
        final int Pos = 2;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_1);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = sourceTest.removeFromBlock(Pos);
        Statement sRef = sourceRef.removeFromBlock(Pos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);
        assertEquals(kTest, Kind.IF_ELSE);
    }

    @Test
    public final void testKindIfElseRoutine2() {
        /*
         * Setup
         */
        final int Pos = 0;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_2);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_2);
        Statement sTest = sourceTest.removeFromBlock(Pos);
        Statement sRef = sourceRef.removeFromBlock(Pos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);
        assertEquals(kTest, Kind.IF_ELSE);
    }

    @Test
    public final void testKindCallBorder() {
        /*
         * Setup
         */
        final int Pos = 0;
        Statement sourceTest = this.createFromFileTest(CALL_BORDER);
        Statement sourceRef = this.createFromFileRef(CALL_BORDER);
        Statement sTest = sourceTest.removeFromBlock(Pos);
        Statement sRef = sourceRef.removeFromBlock(Pos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);
        assertEquals(kTest, Kind.CALL);
    }

    @Test
    public final void testKindCallRoutine1() {
        /*
         * Setup
         */
        final int Pos = 0;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_1);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = sourceTest.removeFromBlock(Pos);
        Statement sRef = sourceRef.removeFromBlock(Pos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);
        assertEquals(kTest, Kind.CALL);

    }

    @Test
    public final void testKindCallRoutine2() {
        /*
         * Setup
         */
        final int Pos = 4;
        Statement sourceTest = this.createFromFileTest(FILE_NAME_1);
        Statement sourceRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = sourceTest.removeFromBlock(Pos);
        Statement sRef = sourceRef.removeFromBlock(Pos);
        Kind kRef = sRef.kind();

        /*
         * The call
         */
        Kind kTest = sTest.kind();

        /*
         * Evaluation
         */
        assertEquals(kRef, kTest);
        assertEquals(sRef, sTest);
        assertEquals(kTest, Kind.CALL);

    }

    /**
     * Test addToBlock at an interior position.
     */
    @Test
    public final void testAddToBlockRoutineInterior() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = sRef.newInstance();
        Statement nestedTest = sTest.removeFromBlock(1);
        Statement nestedRef = sRef.removeFromBlock(1);
        sRef.addToBlock(2, nestedRef);

        /*
         * The call
         */
        sTest.addToBlock(2, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Adds more nested statements to one statement within file (Interior tested
     * here again). NOTE : FILE_NAME_BORDER is a file that is nearly empty.
     */
    @Test
    public final void testAddToBlockInterior2() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement sRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement addTest = this.createFromFileTest(FILE_NAME_2);
        Statement addRef = this.createFromFileRef(FILE_NAME_2);
        Statement emptyBlock = sRef.newInstance();

        sRef.addToBlock(0, addRef.removeFromBlock(0));

        /*
         * The call
         */
        sTest.addToBlock(0, addTest.removeFromBlock(0)); //NOT RESTORED

        /*
         * Evaluation
         */
        assertEquals(addRef, addTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test addToBlock at an exterior (Top_Level) position.
     */
    @Test
    public final void testAddToBlockRoutineExterior() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_2);
        Statement sRef = this.createFromFileRef(FILE_NAME_2);
        Statement addTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement addRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement emptyBlock = sRef.newInstance();

        sRef.addToBlock(0, addRef.removeFromBlock(0));

        /*
         * The call
         */
        sTest.addToBlock(0, addTest.removeFromBlock(0));

        /*
         * Evaluation
         */
        assertEquals(addRef, addTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test addToBlock at an empty file.
     */
    @Test
    public final void testAddToBlockBorder() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(EMPTY_FILE);
        Statement sRef = this.createFromFileRef(EMPTY_FILE);
        Statement addTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement addRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement emptyBlock = sRef.newInstance();

        sRef.addToBlock(0, addRef.removeFromBlock(0));

        /*
         * The call
         */
        sTest.addToBlock(0, addTest.removeFromBlock(0));

        /*
         * Evaluation
         */
        assertEquals(addRef, addTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test removeFromBlock at the front leaving a non-empty block behind.
     */
    @Test
    public final void testRemoveFromBlockFrontLeavingNonEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        Statement nestedRef = sRef.removeFromBlock(0);

        /*
         * The call
         */
        Statement nestedTest = sTest.removeFromBlock(0);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(nestedRef, nestedTest);
    }

    /**
     * Test removeFromBlock at the front leaving a empty block behind.
     */
    @Test
    public final void testRemoveFromBlockFrontLeavingEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement sRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement nestedRef1 = sRef.removeFromBlock(0);
        Statement nestedRef2 = sRef.removeFromBlock(0);
        Statement nestedRef3 = sRef.removeFromBlock(0);

        Statement emptyBlock = sRef.newInstance();

        /*
         * The call
         */
        Statement nestedTest1 = sTest.removeFromBlock(0);
        Statement nestedTest2 = sTest.removeFromBlock(0);
        Statement nestedTest3 = sTest.removeFromBlock(0);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(emptyBlock, sTest);
        assertEquals(nestedRef1, nestedTest1);
        assertEquals(nestedRef2, nestedTest2);
        assertEquals(nestedRef3, nestedTest3);

    }

    /**
     * Test removeFromBlock at the back leaving a non-empty block behind.
     */
    @Test
    public final void testRemoveFromBlockBackLeavingNonEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_2);
        Statement sRef = this.createFromFileRef(FILE_NAME_2);
        Statement nestedRef = sRef.removeFromBlock(sRef.lengthOfBlock() - 1);

        /*
         * The call
         */
        Statement nestedTest = sTest.removeFromBlock(sTest.lengthOfBlock() - 1);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(nestedRef, nestedTest);
    }

    /**
     * Test removeFromBlock at the back leaving a empty block behind.
     */
    @Test
    public final void testRemoveFromBlockBackLeavingEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement sRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement nestedRef1 = sRef.removeFromBlock(sRef.lengthOfBlock() - 1);
        Statement nestedRef2 = sRef.removeFromBlock(sRef.lengthOfBlock() - 1);
        Statement nestedRef3 = sRef.removeFromBlock(sRef.lengthOfBlock() - 1);
        Statement emptyBlock = sRef.newInstance();

        /*
         * The call
         */
        Statement nestedTest1 = sTest
                .removeFromBlock(sTest.lengthOfBlock() - 1);
        Statement nestedTest2 = sTest
                .removeFromBlock(sTest.lengthOfBlock() - 1);
        Statement nestedTest3 = sTest
                .removeFromBlock(sTest.lengthOfBlock() - 1);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(emptyBlock, sTest);
        assertEquals(nestedRef1, nestedTest1);
        assertEquals(nestedRef2, nestedTest2);
        assertEquals(nestedRef3, nestedTest3);
    }

    /**
     * Test lengthOfBlock, greater than zero.
     */
    @Test
    public final void testLengthOfBlockNonEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_1);
        Statement sRef = this.createFromFileRef(FILE_NAME_1);
        int lengthRef = sRef.lengthOfBlock();

        /*
         * The call
         */
        int lengthTest = sTest.lengthOfBlock();

        /*
         * Evaluation
         */
        assertEquals(lengthRef, lengthTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test lengthOfBlock, equal to zero.
     */
    @Test
    public final void testLengthOfBlockEmpty() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(EMPTY_FILE);
        Statement sRef = this.createFromFileRef(EMPTY_FILE);
        int lengthRef = sRef.lengthOfBlock();

        /*
         * The call
         */
        int lengthTest = sTest.lengthOfBlock();

        /*
         * Evaluation
         */
        assertEquals(lengthRef, lengthTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test assembleIf.
     */
    @Test
    public final void testAssembleIfBorder() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement blockRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(0);
        Statement sRef = blockRef.removeFromBlock(0);
        Statement nestedTest = sourceTest.newInstance();
        Condition c = sourceTest.disassembleIf(nestedTest);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIf(c, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testAssembleIfRoutine1() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(1);
        Statement sRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sourceTest.newInstance();
        Condition c = sourceTest.disassembleIf(nestedTest);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIf(c, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testAssembleIfRoutine2() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(2);
        Statement sourceRef = blockRef.removeFromBlock(2);
        Statement nestedTest1 = sourceTest.newInstance();
        Statement nestedTest2 = sourceTest.newInstance();
        Statement nestedRef1 = sourceRef.newInstance();
        Statement nestedRef2 = sourceRef.newInstance();
        Condition cTest = sourceTest.disassembleIfElse(nestedTest1,
                nestedTest2);
        Condition cRef = sourceRef.disassembleIfElse(nestedRef1, nestedRef2);
        Statement sRef = sourceRef.newInstance();
        sRef.assembleIf(cRef, nestedRef1);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIf(cTest, nestedTest1);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest1);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleIf.
     */

    @Test
    public final void testDisassembleIfBorder() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement blockRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement sTest = blockTest.removeFromBlock(0);
        Statement sRef = blockRef.removeFromBlock(0);
        Statement nestedTest = sTest.newInstance();
        Statement nestedRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIf(nestedRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleIf(nestedTest);

        /*
         * Evaluation
         */
        assertEquals(nestedRef, nestedTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    @Test
    public final void testDisassembleIfRoutine() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(1);
        Statement sRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sTest.newInstance();
        Statement nestedRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIf(nestedRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleIf(nestedTest);

        /*
         * Evaluation
         */
        assertEquals(nestedRef, nestedTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test assembleIfElse.
     */
    @Test
    public final void testAssembleIfElseBorder() {
        /*
         * Setup
         */

        Statement blockTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement blockRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(2);
        Statement sRef = blockRef.removeFromBlock(2);
        Statement thenBlockTest = sourceTest.newInstance();
        Statement elseBlockTest = sourceTest.newInstance();
        Condition cTest = sourceTest.disassembleIfElse(thenBlockTest,
                elseBlockTest);
        Statement sTest = blockTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIfElse(cTest, thenBlockTest, elseBlockTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, thenBlockTest);
        assertEquals(emptyBlock, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testAssembleIfElseRoutine1() {
        /*
         * Setup
         */
        final int ifElsePos = 2;
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(ifElsePos);
        Statement sRef = blockRef.removeFromBlock(ifElsePos);
        Statement thenBlockTest = sourceTest.newInstance();
        Statement elseBlockTest = sourceTest.newInstance();
        Condition cTest = sourceTest.disassembleIfElse(thenBlockTest,
                elseBlockTest);
        Statement sTest = blockTest.newInstance();

        /*
         * The call
         */
        sTest.assembleIfElse(cTest, thenBlockTest, elseBlockTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, thenBlockTest);
        assertEquals(emptyBlock, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testAssembleIfElseRoutine2() {
        /*
         * Setup
         */

        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(2);
        Statement sourceRef = blockRef.removeFromBlock(2);

        Statement thenBlockTest = sourceTest.newInstance();
        Statement elseBlockTest = sourceTest.newInstance();
        Statement thenBlockRef = sourceRef.newInstance();
        Statement elseBlockRef = sourceRef.newInstance();

        Condition cTest = sourceTest.disassembleIfElse(thenBlockTest,
                elseBlockTest);
        Condition cRef = sourceRef.disassembleIfElse(thenBlockRef,
                elseBlockRef);

        Statement storeTest = blockTest.removeFromBlock(1);
        Statement storeRef = blockRef.removeFromBlock(1);
        Condition s1 = storeTest.disassembleIf(storeTest.newInstance());
        Condition s2 = storeRef.disassembleIf(storeRef.newInstance());

        Statement sTest = blockTest.newInstance();
        Statement sRef = blockRef.newInstance();

        /*
         * The call
         */
        sTest.assembleIfElse(s1, thenBlockTest, elseBlockTest);
        sRef.assembleIfElse(s2, thenBlockRef, elseBlockRef);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, thenBlockTest);
        assertEquals(emptyBlock, elseBlockTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test disassembleIfElse.
     */

    @Test
    public final void testDisassembleIfElseBorder() {
        /*
         * Setup
         */

        Statement blockTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement blockRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement sTest = blockTest.removeFromBlock(2);
        Statement sRef = blockRef.removeFromBlock(2);
        Statement thenBlockTest = sTest.newInstance();
        Statement elseBlockTest = sTest.newInstance();
        Statement thenBlockRef = sRef.newInstance();
        Statement elseBlockRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIfElse(thenBlockRef, elseBlockRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleIfElse(thenBlockTest, elseBlockTest);

        /*
         * Evaluation
         */
        assertEquals(cRef, cTest);
        assertEquals(thenBlockRef, thenBlockTest);
        assertEquals(elseBlockRef, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testDisassembleIfElseRoutine() {
        /*
         * Setup
         */
        final int ifElsePos = 2;
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(ifElsePos);
        Statement sRef = blockRef.removeFromBlock(ifElsePos);
        Statement thenBlockTest = sTest.newInstance();
        Statement elseBlockTest = sTest.newInstance();
        Statement thenBlockRef = sRef.newInstance();
        Statement elseBlockRef = sRef.newInstance();
        Condition cRef = sRef.disassembleIfElse(thenBlockRef, elseBlockRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleIfElse(thenBlockTest, elseBlockTest);

        /*
         * Evaluation
         */
        assertEquals(cRef, cTest);
        assertEquals(thenBlockRef, thenBlockTest);
        assertEquals(elseBlockRef, elseBlockTest);
        assertEquals(sRef, sTest);
    }

    /**
     * Test assembleWhile.
     */
    @Test
    public final void testAssembleWhileRoutine1() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(1);
        Statement sourceRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sourceTest.newInstance();
        Statement nestedRef = sourceRef.newInstance();
        Condition cTest = sourceTest.disassembleIf(nestedTest);
        Condition cRef = sourceRef.disassembleIf(nestedRef);
        Statement sRef = sourceRef.newInstance();
        sRef.assembleWhile(cRef, nestedRef);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleWhile(cTest, nestedTest);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest);
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testAssembleWhileRoutine2() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement emptyBlock = blockRef.newInstance();
        Statement sourceTest = blockTest.removeFromBlock(2);
        Statement sourceRef = blockRef.removeFromBlock(2);
        Statement nestedTest1 = sourceTest.newInstance();
        Statement nestedTest2 = sourceTest.newInstance();
        Statement nestedRef1 = sourceRef.newInstance();
        Statement nestedRef2 = sourceRef.newInstance();
        Condition cTest = sourceTest.disassembleIfElse(nestedTest1,
                nestedTest2);
        Condition cRef = sourceRef.disassembleIfElse(nestedRef1, nestedRef2);
        Statement sRef = sourceRef.newInstance();
        sRef.assembleWhile(cRef, nestedRef1);
        Statement sTest = sourceTest.newInstance();

        /*
         * The call
         */
        sTest.assembleWhile(cTest, nestedTest1);

        /*
         * Evaluation
         */
        assertEquals(emptyBlock, nestedTest1);
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleWhile.
     */

    @Test
    public final void testDisassembleWhileBorder() {
        /*
         * Setup
         */

        Statement blockTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement blockRef = this.createFromFileRef(FILE_NAME_BORDER);
        Statement sTest = blockTest.removeFromBlock(1);
        Statement sRef = blockRef.removeFromBlock(1);
        Statement nestedTest = sTest.newInstance();
        Statement nestedRef = sRef.newInstance();
        Condition cRef = sRef.disassembleWhile(nestedRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleWhile(nestedTest);

        /*
         * Evaluation
         */
        assertEquals(nestedRef, nestedTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    @Test
    public final void testDisassembleWhileRoutine() {
        /*
         * Setup
         */

        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(3);
        Statement sRef = blockRef.removeFromBlock(3);
        Statement nestedTest = sTest.newInstance();
        Statement nestedRef = sRef.newInstance();
        Condition cRef = sRef.disassembleWhile(nestedRef);

        /*
         * The call
         */
        Condition cTest = sTest.disassembleWhile(nestedTest);

        /*
         * Evaluation
         */
        assertEquals(nestedRef, nestedTest);
        assertEquals(sRef, sTest);
        assertEquals(cRef, cTest);
    }

    /**
     * Test assembleCall.
     */
    @Test
    public final void testAssembleCallBorder() {
        /*
         * Setup
         */
        Statement sRef = this.constructorRef().newInstance();
        Statement sTest = this.constructorTest().newInstance();

        String name = "stay";
        sRef.assembleCall(name);

        /*
         * The call
         */
        sTest.assembleCall(name);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testAssembleCallRoutine1() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(CALL_BORDER);
        Statement sRef = this.createFromFileRef(CALL_BORDER);

        String name = "turnleft";
        sRef.assembleCall(name);

        /*
         * The call
         */
        sTest.assembleCall(name);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    @Test
    public final void testAssembleCallRoutine2() {
        /*
         * Setup
         */
        Statement sTest = this.createFromFileTest(FILE_NAME_BORDER);
        Statement sRef = this.createFromFileRef(FILE_NAME_BORDER);

        String name = "turnright";
        sRef.assembleCall(name);

        /*
         * The call
         */
        sTest.assembleCall(name);

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
    }

    /**
     * Test disassembleCall.
     */

    @Test
    public final void testDisassembleCallBorder() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(CALL_BORDER);
        Statement blockRef = this.createFromFileRef(CALL_BORDER);
        Statement sTest = blockTest.removeFromBlock(0);
        Statement sRef = blockRef.removeFromBlock(0);
        String nRef = sRef.disassembleCall();

        /*
         * The call
         */
        String nTest = sTest.disassembleCall();

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(nRef, nTest);
        assertEquals(nRef, "move");
    }

    @Test
    public final void testDisassembleCallRoutine1() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_1);
        Statement blockRef = this.createFromFileRef(FILE_NAME_1);
        Statement sTest = blockTest.removeFromBlock(0);
        Statement sRef = blockRef.removeFromBlock(0);
        String nRef = sRef.disassembleCall();

        /*
         * The call
         */
        String nTest = sTest.disassembleCall();

        /*
         * Evaluation
         */
        assertEquals(sRef, sTest);
        assertEquals(nRef, nTest);
        assertEquals(nRef, "move");
    }

    @Test
    public final void testDisassembleCallRoutine2() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_2);
        Statement blockRef = this.createFromFileRef(FILE_NAME_2);
        Statement s1 = blockTest.removeFromBlock(0);
        Statement s2 = blockRef.removeFromBlock(0);

        Statement callTest1 = s1.newInstance();
        Statement callTest2 = s1.newInstance();
        Statement callRef1 = s2.newInstance();
        Statement callRef2 = s2.newInstance();

        Condition cTest = s1.disassembleIfElse(callTest1, callTest2);
        Condition cRef = s2.disassembleIfElse(callRef1, callRef2);

        Statement storeTest = callTest1.removeFromBlock(2);
        String nTest = storeTest.disassembleCall();
        /*
         * The call
         */
        Statement storeRef = callRef1.removeFromBlock(2);
        String nRef = storeRef.disassembleCall();

        /*
         * Evaluation
         */
        assertEquals(s1, s2);
        assertEquals(nRef, nTest);
        assertEquals(nRef, "turnleft");
    }

    @Test
    public final void testDisassembleCallRoutine3() {
        /*
         * Setup
         */
        Statement blockTest = this.createFromFileTest(FILE_NAME_2);
        Statement blockRef = this.createFromFileRef(FILE_NAME_2);
        Statement s1 = blockTest.removeFromBlock(0);
        Statement s2 = blockRef.removeFromBlock(0);

        Statement callTest1 = s1.newInstance();
        Statement callTest2 = s1.newInstance();
        Statement callRef1 = s2.newInstance();
        Statement callRef2 = s2.newInstance();

        Condition cTest = s1.disassembleIfElse(callTest1, callTest2);
        Condition cRef = s2.disassembleIfElse(callRef1, callRef2);

        Statement storeTest = callTest2.removeFromBlock(3);
        String nTest = storeTest.disassembleCall();
        /*
         * The call
         */
        Statement storeRef = callRef2.removeFromBlock(3);
        String nRef = storeRef.disassembleCall();

        /*
         * Evaluation
         */
        assertEquals(s1, s2);
        assertEquals(nRef, nTest);
        assertEquals(nRef, "skip");
    }

}
