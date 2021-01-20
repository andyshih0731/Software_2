import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This java program takes each individual word from a given file and counts the
 * occurrences of the words. Then, it outputs a file containing the word and the
 * occurrences in a table.
 *
 * @author Andy Shih
 *
 */
public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * Compares two string in alphabetical order. (Ignoring case)
     *
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param pos
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires <pre>
     * {@code 0 <= position < |text|}
     * </pre>
     * @ensures <pre>
     * {@code nextWordOrSeparator =
     *   text[ position .. position + |nextWordOrSeparator| )  and
     * if elements(text[ position .. position + 1 )) intersection separators = {}
     * then
     *   elements(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    elements(text[ position .. position + |nextWordOrSeparator| + 1 ))
     *      intersection separators /= {})
     * else
     *   elements(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    elements(text[ position .. position + |nextWordOrSeparator| + 1 ))
     *      is not subset of separators)}
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int pos,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= pos : "Violation of: 0 <= position";
        assert pos < text.length() : "Violation of: position < |text|";

        int i = pos;

        //if text doesn't contain any characters in {@code separators}
        if (!separators.contains(text.charAt(pos))) {

            // increase {@code i} if text doesn't contain any special characters
            while (i < text.length() && !separators.contains(text.charAt(i))) {
                i++;
            }
        } else {

            while (i < text.length() && separators.contains(text.charAt(i))) {
                i++;
            }
        }

        return text.substring(pos, i);
    }

    /**
     * Sorts words into alphabetical order.
     *
     * @param words
     *            map containing all the words and occurrences
     * @param alphabetical
     *            Comparator that compares words in alphabetical order
     * @return the alphabetically ordered queue
     * @ensures {@code words} will be in alphabetical order
     *
     */

    public static Queue<String> wordSort(Map<String, Integer> words,
            Comparator<String> alphabetical) {

        Queue<String> tempQueue = new Queue1L<>();
        Queue<String> orderedQueue = new Queue1L<>();
        Map<String, Integer> temp = new Map1L<>();

        //Take all the words out and put them in a queue and a temporary Map
        while (words.size() > 0) {

            Map.Pair<String, Integer> p = words.removeAny();
            temp.add(p.key(), p.value());
            tempQueue.enqueue(p.key());

        }

        //sort the words in alphabetical order
        tempQueue.sort(alphabetical);
        words.clear();

        //add the words back into words in alphabetical order
        while (tempQueue.length() > 0) {

            String key = tempQueue.dequeue();
            orderedQueue.enqueue(key);
            words.add(key, temp.value(key));

        }

        return orderedQueue;

    }

    /**
     * Process the given file and put all the words and occurrences in
     * {@code words).
     *
     * @param in the input text file
     *
     * @param words
     *            all the words from the input file and the occurrences of each
     *            word
     *
     * @required in.isOpen()
     * @ensures {@code words} contains all the words and occurrences of each
     *          word from {@code in}
     *
     */
    private static void process(SimpleReader in, Map<String, Integer> words) {

        int pos = 0;
        Set<Character> exclusion = new Set1L<>();
        String str = " \t/()?!.,;:~@#$%-\"";

        //Put all excluded characters into the set exclusion
        for (int i = 0; i < str.length() - 1; i++) {

            exclusion.add(str.charAt(i));
        }

        //separating the words while counting of their occurrences and put them into {@code words}
        while (!in.atEOS()) {

            pos = 0;
            String line = in.nextLine();

            while (pos < line.length()) {

                //extract a word from {@code in}
                String singleWord = nextWordOrSeparator(line, pos, exclusion);

                //if the word is not in {@code words}, add the word in {@code words}
                if (!exclusion.contains(singleWord.charAt(0))) {

                    if (!words.hasKey(singleWord)) {

                        words.add(singleWord, 1);
                    } else {

                        int occurrences = words.value(singleWord);
                        occurrences++;
                        words.replaceValue(singleWord, occurrences);

                    }

                }

                pos += singleWord.length();
            }

        }

    }

    /**
     * Outputs a table of all the words and their occurrences from {@code words}
     * into a {@code outFile}.
     *
     * @param words
     *            map containing all the words and occurrences
     * @param orderedQueue
     *            Queue that contains all the words in alphabetical order
     * @param outFile
     *            output file
     * @param inFile
     *            input file provided by the user
     * @requires inFile and outFile must be opened
     * @ensures outputs a table in file containing all the words and their
     *          occurrences.
     *
     */

    public static void makeTable(Map<String, Integer> words,
            Queue<String> orderedQueue, SimpleWriter outFile,
            SimpleReader inFile) {

        // Title
        outFile.println("<html>");
        outFile.println("<head>");
        outFile.println(
                "<title>Words Counted in " + inFile.name() + "</title>");
        outFile.println("</head>");
        outFile.println("<body>");

        //Header
        outFile.println("<h2>Words Counted in " + inFile.name() + "</h2>");
        outFile.println("<hr/>");

        //Table
        outFile.println("<table border=\"1\">");
        outFile.println("<tr>");
        outFile.println("<th>Words</th>");
        outFile.println("<th>Counts</th>");
        outFile.println("</tr>");

        //Adding the elements of the table
        while (words.size() > 0) {
            Pair<String, Integer> pair = words.remove(orderedQueue.dequeue());
            outFile.println("<tr>");
            outFile.println("<td>" + pair.key() + "</td>");
            outFile.println("<td>" + pair.value() + "</td>");
            outFile.println("</tr>");
        }
        outFile.println("</table>");
        outFile.println("</body>");
        outFile.println("</html>");

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

        Map<String, Integer> words = new Map1L<>();
        Queue<String> orderedQueue = new Queue1L<>();
        Comparator<String> alphabetical = new StringLT();

        out.println("Please enter the name of the input file: ");
        SimpleReader inFile = new SimpleReader1L(in.nextLine());

        out.println("Please enter the name of the output file");
        SimpleWriter outFile = new SimpleWriter1L(in.nextLine());

        //process all the words and occurrences of a file into {@code words}
        process(inFile, words);

        orderedQueue.clear();
        //order {@code words} into alphabetical order
        orderedQueue = wordSort(words, alphabetical);

        //make a table for the output file
        makeTable(words, orderedQueue, outFile, inFile);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
        inFile.close();
        outFile.close();
    }

}
