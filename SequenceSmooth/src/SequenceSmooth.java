import components.sequence.Sequence;
import components.sequence.Sequence1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 *
 * @author Andy Shih
 *
 */
public final class SequenceSmooth {

    /**
     * Smooths a given {@code Sequence<Integer>}.
     *
     * @param s1
     *            the sequence to smooth
     * @return s2 sequence after the method smooth
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     */
    public static Sequence<Integer> smooth(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";

        int s1Length = s1.length();

        Sequence<Integer> s2 = new Sequence1L<>();

        for (int i = 0; i < s1Length && s1Length > 1; i++) {

            int first = s1.remove(0);
            int second = s1.remove(0);

            s1.add(0, second);
            s1.add(s1Length - 1, first);

            if (i < s1Length - 1) {

                s2.add(i, (first + second) / 2);

            }

        }

        return s2;

    }

    /**
     * Smooths a given {@code Sequence<Integer>}.
     *
     * @param s1
     *            the sequence to smooth
     * @return s2 sequence after method smooth
     * @replaces s2
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     */
    public static Sequence<Integer> smooth1(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";

        Sequence<Integer> s2 = new Sequence1L<>();

        int s1Length = s1.length();

        if (s1Length > 1) {

            int first = s1.remove(0);
            int second = s1.remove(0);

            s1.add(0, second);

            s2.transferFrom(smooth1(s1));

            s2.add(0, (first + second) / 2);

            s1.add(0, first);

        }

        return s2;

    }

    public static void main(String[] args) {

        SimpleWriter out = new SimpleWriter1L();

        Sequence<Integer> s2 = new Sequence1L<>();
        Sequence<Integer> s1 = new Sequence1L<>();

        s1.add(s1.length(), 2);
        s1.add(s1.length(), 4);
        s1.add(s1.length(), 6);
        //s1.add(s1.length(), 7);

        out.println("s1: ");
        for (int i : s1) {
            out.println(i);
        }

        out.println();

        s2.add(s2.length(), 2);
        s2.add(s2.length(), 4);
        s2.add(s2.length(), 6);

        out.println("s2: ");
        for (int i : s2) {
            out.println(i);
        }

        out.println();

        s2.transferFrom(smooth1(s1));

        out.println("s1 outcome : ");
        for (int x : s1) {
            out.print(x + " ");
        }

        out.println();

        out.println("s2 outcome: ");
        for (int y : s2) {
            out.print(y + " ");
        }

        /*
         * Close input and output streams
         */
        out.close();

    }

}