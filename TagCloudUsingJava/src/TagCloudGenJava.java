import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This project (TagCloud) is a program that takes .txt input and generates a
 * tag cloud file ( of type .html).
 *
 * @author Andy Shih
 * @author Mingzhu Bao
 *
 */
public final class TagCloudGenJava {

    /**
     * Compare {@code Map}s in alphabetical order.
     */
    private static class StringLT
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    }

    /**
     * Compare {@code Map}s in numerical order.
     */
    private static class IntegerLT
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    }

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenJava() {
    }

    /**
     *
     * Max number of word occurrence in the given file.
     */
    private static Integer max;
    /**
     *
     * Minimum number of word occurrence in the given file.
     */
    private static Integer min;

    /**
     *
     * Definition of separators.
     */
    private static final String SEPARATORS = ":\n\r\t.-!?@#@$%^&*()+= {}[];/";

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
            BufferedReader input) {
        Map<String, Integer> count = new HashMap<>();
        String sentence = "";

        try {

            sentence = input.readLine();

            while (sentence != null) {

                if (!sentence.isEmpty()) {
                    int position = 0;
                    while (position < sentence.length()) {
                        //call the nextStringOrSeparator to get next string
                        String nextString = nextStringOrSeparator(position,
                                sentence).toLowerCase();

                        //check if what we get is a word
                        if (isWord(separator, nextString)) {
                            if (!count.containsKey(nextString)) {
                                count.put(nextString, 1);
                            } else {
                                int addValue = count.get(nextString);
                                addValue++;
                                count.put(nextString, addValue);
                            }

                        }
                        position = position + nextString.length();
                    }
                }
                sentence = input.readLine();
            }

        } catch (IOException e) {
            System.err.println("Error reading from system input");
        }

        return count;

    }

    /**
     * Sorts the words with respect to their occurrences and alphabetical order.
     *
     *
     * @param words
     *            will hold the pairs extracted from the input file
     * @param n
     *            number of words that will be displayed
     * @updates
     * @return a list of words sorted by their values
     * @ensures all words are sorted
     */
    public static List<Map.Entry<String, Integer>> sorter(
            Map<String, Integer> words, int n) {

        List<Map.Entry<String, Integer>> sortedValues = new ArrayList<>();
        List<Map.Entry<String, Integer>> sortedKeys = new ArrayList<>();

        Comparator<Map.Entry<String, Integer>> numerical = new IntegerLT();
        Comparator<Map.Entry<String, Integer>> alphabetical = new StringLT();

        //transfer all words from input to a list
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            sortedValues.add(entry);
        }

        //sort the list by their numerical values
        sortedValues.sort(numerical);

        max = 0;
        min = sortedValues.get(0).getValue();

        //remove words from the sortedValues
        while (!sortedValues.isEmpty() && sortedKeys.size() < n) {
            Map.Entry<String, Integer> temp = sortedValues.remove(0);
            sortedKeys.add(temp);

            if (sortedKeys.get(sortedKeys.size() - 1).getValue() > max) {
                max = sortedKeys.get(sortedKeys.size() - 1).getValue();
            }

            if (sortedKeys.get(sortedKeys.size() - 1).getValue() < min) {
                min = sortedKeys.get(sortedKeys.size() - 1).getValue();
            }

        }

        sortedKeys.sort(alphabetical);

        return sortedKeys;

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

        int i = position + 1;

        while (i < remaining.length()
                && ((SEPARATORS.indexOf(remaining.charAt(position))
                        * SEPARATORS.indexOf(remaining.charAt(i)) > 0))) {

            i++;

        }

        return remaining.substring(position, i);

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
    public static void mainPage(PrintWriter output, String inputFile, int n) {
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
    public static int fontSize(int count, Integer max, Integer min) {
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
     * @param allsorted
     *            holding pairs of words and counts to be adopted into file
     * @param max
     *            max number of counts in all the words in {@code mySM2}
     * @param min
     *            number of counts in all the words in {@code mySM2}
     * @requires top <= mySM2.size and out.isOpen
     * @clears mySM2
     */
    public static void spanOutput(PrintWriter out,
            TreeMap<String, Integer> allsorted, Integer max, Integer min) {
        int top = allsorted.size();

        for (int i = 0; i < top; i++) {

            out.println("<span style=\"cursor:default\" class=\"f"
                    + fontSize(allsorted.firstEntry().getValue(), max, min)
                    + "\" title=\"count: " + allsorted.firstEntry().getValue()
                    + "\">" + allsorted.firstEntry().getKey() + "</span>");

            allsorted.remove(allsorted.firstEntry().getKey());
        }

        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        BufferedReader input;
        String inputFile = "";
        PrintWriter output;

        System.out.print("Eneter the name of input file: ");

        //open file
        try {

            inputFile = in.readLine();

        } catch (IOException e) {

            System.err.println("Error opening file from input file");
            return;
        }

        //read file
        try {
            input = new BufferedReader(new FileReader(inputFile));

        } catch (IOException e) {
            System.err.println("Error reading file from input file location");
            return;
        }

        System.out.print("Enter the name of output file: ");

        String outputFile = "";

        //output name
        try {

            outputFile = in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading stream from input");

        }

        //number of words
        System.out.print("Enter the positive number N: ");
        int top = -23;
        String str = "";

        while (top < 0) {

            try {

                str = in.readLine();

            } catch (IOException e) {
                System.err.println("Error reading stream from input ");
            }

            if (str != null) {
                top = Integer.parseInt(str);
            }

            if (top < 0) {
                System.out.println("Number cannot be negative");
                System.out.print("Enter positive number of N again: ");
            }

        }

        output = new PrintWriter(
                new BufferedWriter(new FileWriter(outputFile)));

        mainPage(output, inputFile, top);

        Set<Character> separators = new HashSet<>();
        separatorsSet(separators);

        Map<String, Integer> words = wordCount(separators, input);

        //in case if input file is empty
        if (words.size() > 0) {

            List<Map.Entry<String, Integer>> sortedWords = sorter(words, top);
            TreeMap<String, Integer> allsorted = new TreeMap<String, Integer>();

            for (int i = 0; i < sortedWords.size(); i++) {
                allsorted.put(sortedWords.get(i).getKey(),
                        sortedWords.get(i).getValue());
            }

            spanOutput(output, allsorted, max, min);

        }

        /*
         * Close input and output streams
         */

        in.close();
        input.close();
        output.close();
    }

}
