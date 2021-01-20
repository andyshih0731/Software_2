import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;
import components.utilities.Reporter;

/**
 * This project (TagCloud) is a program that takes .txt input and generates a
 * tag cloud file ( of type .html).
 *
 * @author Andy
 * @author Mingzhu
 *
 */
public final class TagCloudGen {

    /**
     * Private class PairComparator provides a way to compare to pairs (Map.Pair
     * type) based on their values through its only method compare.
     */
    private static final class PairComparator
            implements Comparator<Map.Pair<String, Integer>> {
        /**
         * Compares two arguments {@code arg1} and {@code arg2}, and returns an
         * integer indicating their relative ordering (in regards to each
         * other).
         *
         * @param arg1
         *            first input to be compared
         * @param arg2
         *            second input to be compared
         * @return negative if arg2 comes before arg1, positive if arg2 comes
         *         after arg 1, else returns 0.
         */
        @Override
        public int compare(Map.Pair<String, Integer> arg1,
                Map.Pair<String, Integer> arg2) {
            int result = 0;
            if (arg1.value().compareTo(arg2.value()) < 0) {
                result = 1;
            } else if (arg1.value().compareTo(arg2.value()) > 0) {
                result = -1;
            }
            return result;
        }
    }

    /**
     * Private class PairComparator2 provides a way to compare to pairs
     * (Map.Pair type) based on their keys alphabetical ordering.
     */
    private static final class PairComparator2
            implements Comparator<Map.Pair<String, Integer>> {
        /**
         * Compares two arguments {@code arg1} and {@code arg2}, and returns an
         * integer indicating their relative ordering (in regards to each
         * other).
         *
         * @param arg1
         *            first input to be compared
         * @param arg2
         *            second input to be compared
         * @return negative if arg2 comes before arg1, positive if arg2 comes
         *         after arg 1, else returns 0.
         */
        @Override
        public int compare(Map.Pair<String, Integer> arg1,
                Map.Pair<String, Integer> arg2) {
            int result = 0;
            if (arg1.key().compareTo(arg2.key()) < 0) {
                result = -1;
            } else if (arg1.key().compareTo(arg2.key()) > 0) {
                result = 1;
            }
            return result;
        }
    }

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGen() {
    }

    /**
     * Counts the occurrences of words in {@code input}.
     *
     * @param input
     *            the input stream with all the input string *
     * @param separator
     *            contains all the separator characters to be considered
     * @return A Map which stores words and their occurrences
     * @requires input.isOpen
     * @ensures <pre>
     * [all occurrences are reported]
     * </pre>
     *
     */
    public static Map<String, Integer> wordCount(Set<Character> separator,
            SimpleReader input) {
        Map<String, Integer> count = new Map1L<>();
        while (!input.atEOS()) {
            String sentence = input.nextLine();
            if (!sentence.isEmpty()) {
                int position = 0;
                while (position < sentence.length()) {
                    //call the nextStringOrSeparator to get next string
                    String nextString = nextStringOrSeparator(position,
                            sentence).toLowerCase();
                    //check if what we get is a word
                    if (isWord(separator, nextString)) {
                        if (!count.hasKey(nextString)) {
                            count.add(nextString, 1);
                        } else {
                            int addValue = count.value(nextString);
                            addValue++;
                            count.replaceValue(nextString, addValue);
                        }

                    }
                    position = position + nextString.length();
                }
            }
        }
        return count;
    }

    /**
     *
     * @param resultSM
     *            will hold the pairs extracted from {@code currMap}
     * @param currMap
     *            holds the words and counts to be transferred
     * @clears currMap
     * @replaces resultSM
     * @return min value in {@code currMap}
     * @ensures resultSM.entries = #resultSM.entries * currMap
     */
    public static int sorter(SortingMachine<Map.Pair<String, Integer>> resultSM,
            Map<String, Integer> currMap) {
        int min = 0;
        if (currMap.size() > 0) {
            Map.Pair<String, Integer> temp = currMap.removeAny();
            min = temp.value();
            resultSM.add(temp);
            while (currMap.size() > 0) {
                temp = currMap.removeAny();
                resultSM.add(temp);
                if (min > temp.value()) {
                    min = temp.value();
                }
            }
        }
        resultSM.changeToExtractionMode(); //not sorted yet (SELECTION SORT ALG)
        //currMap here is cleared
        return min;
    }

    /**
     * Determines if nextString has any char that is a separator.
     *
     *
     * @param nextString
     *            the input string
     * @param separators
     *            contains all the separator characters to be considered
     *
     * @requires |nextString| > 0
     * @return [immediate word's state as a word or separator]
     * @ensures <pre>
     * True if [nextString does not contain separator chars]
     * else False
     * </pre>
     */
    public static boolean isWord(Set<Character> separators, String nextString) {
        boolean word = true;
        for (int i = 0; i < nextString.length(); i++) {
            if (separators.contains(nextString.charAt(i))) {
                word = false;
            }
        }
        return word;
    }

    /**
     * Get the next word in a given sentence (word can be a separator char).
     *
     * @param position
     *            the starting index of next word or separator
     * @param remaining
     *            the input string
     * @return [upcoming word/separator starting from index]
     *
     * @requires position>=0 and position < |remaining| and |remaining| > 0
     * @ensures <pre>
     * if remaining's charAt(position) /= any separators
     * [the next entire word string is returned]
     * else
     * [remaining's charAt(position) is returned]
     * </pre>
     */
    public static String nextStringOrSeparator(int position, String remaining) {
        //create a set for further comparison
        Set<Character> separators = new Set1L<>();
        separatorsSet(separators);
        StringBuilder getString = new StringBuilder();

        //get the next separators
        if (separators.contains(remaining.charAt(position))) {
            for (int i = position; i < remaining.length()
                    && separators.contains(remaining.charAt(i)); i++) {
                getString.append(remaining.charAt(i));
            }
        } else {
            //get the next string
            for (int i = position; i < remaining.length()
                    && !separators.contains(remaining.charAt(i)); i++) {
                getString.append(remaining.charAt(i));
            }
        }
        String nextString = getString.toString();
        return nextString;
    }

    /**
     * Stores all possible separators in a set.
     *
     * @param separators
     *            the set that contains all separating characters
     * @replaces separators
     *
     * @ensures <pre>
     * [all possible separators are stored in {@code separators}]
     * </pre>
     */
    public static void separatorsSet(Set<Character> separators) {
        separators.add(',');
        separators.add('\'');
        separators.add(':');
        separators.add('\n');
        separators.add('\r');
        separators.add('\t');
        separators.add('.');
        separators.add(' ');
        separators.add('~');
        separators.add('-');
        separators.add('!');
        separators.add('@');
        separators.add('#');
        separators.add('$');
        separators.add('%');
        separators.add('^');
        separators.add('&');
        separators.add('*');
        separators.add('(');
        separators.add(')');
        separators.add('_');
        separators.add('+');
        separators.add('=');
        separators.add('{');
        separators.add('}');
        separators.add('[');
        separators.add(']');
        separators.add('|');
        separators.add(';');
        separators.add('`');
        separators.add('"');
        separators.add('/');
        separators.add('?');
        separators.add('0');
        separators.add('1');
        separators.add('2');
        separators.add('3');
        separators.add('4');
        separators.add('5');
        separators.add('6');
        separators.add('7');
        separators.add('8');
        separators.add('9');
    }

    /**
     * Print the main page and head of table.
     *
     * @param output
     *            the output stream
     *
     * @param inputFile
     *            the input file name
     * @param n
     *            is the number of words (with highest counts) to be printed
     *
     * @requires output.is_open and inputFile.length() > 0
     * @updates out.content
     * @ensures <pre>
     * out.content = #out.content *  [the HTML "opening" tags]
     * [the main page is correctly printed]
     * </pre>
     */
    public static void mainPage(SimpleWriter output, String inputFile, int n) {
        output.println("<html>");
        output.println("<head>");
        output.println("<title>");
        output.println("Top " + n + " words in " + inputFile);
        output.println("</title>");
        output.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/"
                        + "web-sw2/assignments/projects/tag-cloud-generator/data/"
                        + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println("</head>");

        output.println("<body>");
        output.println("<h2>Top " + n + " words in " + inputFile + "</h2>");
        output.println("<hr>");
        output.println("<div class=\"cdiv\">");
        output.println("<p class=\"cbox\">");
    }

    /**
     * Computes and returns an integer fontSize based on parameters.
     *
     * @param count
     *            number of occurrences
     * @param max
     *            max number of occurrences of any word
     * @param min
     *            min number of occurrences of any word
     * @return Integer holding the appropriate font size based on inputted
     *         parameter values
     * @requires count, min and max must all be positive int
     * @ensures [consistency on fontSize determination of differently counted
     *          words]
     */
    public static int fontSize(int count, int max, int min) {
        int font = ((48 - 11) * (count - min) / (max - min)) + 11;
        //Tag cloud formula from agichevski.com
        return font;
    }

    /**
     * Spans or writes the output to {@code out} which comes from an assorted
     * {@code mySM2}.
     *
     * @param out
     *            is the stream to be edited
     * @param mySM2
     *            holding pairs of words and counts to be adopted into file
     * @param max
     *            max number of counts in all the words in {@code mySM2}
     * @param min
     *            number of counts in all the words in {@code mySM2}
     * @requires top <= mySM2.size and out.isOpen
     * @clears mySM2
     */
    public static void spanOutput(SimpleWriter out,
            SortingMachine<Map.Pair<String, Integer>> mySM2, int max, int min) {
        int top = mySM2.size();
        for (int i = 0; i < top; i++) {
            Map.Pair<String, Integer> temp = mySM2.removeFirst();
            out.println("<span style=\"cursor:default\" class=\"f"
                    + fontSize(temp.value(), max, min) + "\" title=\"count: "
                    + temp.value() + "\">" + temp.key() + "</span>");
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of input file:");
        String inputFile = in.nextLine();
        SimpleReader input = new SimpleReader1L(inputFile);
        out.print("Enter the name of output file:");
        String outputFile = in.nextLine();

        out.print("Enter the positive number N: ");
        Integer top = Integer.valueOf(in.nextLine());
        Reporter.assertElseFatalError(top > 0,
                "Violation of: N cannot be a negative number");

        SimpleWriter output = new SimpleWriter1L(outputFile);
        mainPage(output, inputFile, top);

        Set<Character> separators = new Set2<>();
        separatorsSet(separators);
        Map<String, Integer> words = wordCount(separators, input);
        Comparator<Map.Pair<String, Integer>> compVal = new PairComparator();
        SortingMachine<Map.Pair<String, Integer>> mySM = new SortingMachine2<>(
                compVal); //embedding SELECTION SORT (best for small-mid N values)

        final int min = sorter(mySM, words);

        Comparator<Map.Pair<String, Integer>> compStr = new PairComparator2();
        SortingMachine<Map.Pair<String, Integer>> mySM2 = new SortingMachine2<>(
                compStr); //embedding SELECTION SORT (best for small-mid N values)

        Map.Pair<String, Integer> temp = mySM.removeFirst();
        final int max = temp.value();
        mySM2.add(temp);

        for (int i = 0; i < top - 1; i++) {
            //top-1 because one line already printed in maxOfInput method
            temp = mySM.removeFirst();
            mySM2.add(temp);
        }

        //Extra
        System.out.println(mySM2);

        mySM2.changeToExtractionMode();
        spanOutput(output, mySM2, max, min);
        output.println("</p>");
        output.println("</div>");
        output.println("</body>");
        output.println("</html>");

        /*
         * Close input and output streams
         */
        in.close();
        output.close();
    }

}
