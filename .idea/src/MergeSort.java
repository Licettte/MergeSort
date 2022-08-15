import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class MergeSort {

    private final static String WRONG_FILE = "One or more files is damaged, missing or encrypted";

    private final static String HELP = "\nUsage: java MergeSort [options] outputFile [inputFiles]\n\n" +
            "Merge some sorted files to one sorted\n\n" +
            "Options:\n" +
            "  -a or -d     Sorting order, ascending or descending (non-required, ascending by default)\n" +
            "  -s or -i     Sorting types, string or integer (required)";

    private final static String WRONG_ARGS = "MergeSort: wrong commandline args\n\n" +
            "See \'java MergeSort --help\'";

    private final static String OK = "OK";
    private final static String WELL_SORTED = "Sorting successfully completed";

    private final static String regexDigit = "\\d+";
    private final static String regexString = "[^ ]+";

    private static String regex;

    private static Comparator<String> comparator;

    private static ArrayList<Scanner> scanners = new ArrayList<>();

    private static HashMap<Scanner, String> previousValues = new HashMap<>();

    private static FileWriter writer;

    public static void main(String[] args) {

        String argsValidate;
        if (!(argsValidate = parseArgs(args)).equals(OK)) {
            System.out.println(argsValidate);
            return;
        }

        try {
            merge();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing in file");
        }

        System.out.println(WELL_SORTED);
    }

    private static void merge() throws IOException {
        ArrayList<String> values = new ArrayList<>();
        String nextString;
        for (Scanner scanner : scanners) {
            if ((nextString = getNextValidate(scanner)) != null) {
                values.add(nextString);
            } else {
                scanner.close();
                scanners.remove(scanner);
            }
        }

        while (scanners.size() > 1) {
            int minIndex = minIndex(values);
            writeString(values.get(minIndex));

            previousValues.put(scanners.get(minIndex), values.get(minIndex));

            if ((nextString = getNextValidate(scanners.get(minIndex))) != null) {
                values.set(minIndex, nextString);
            } else {
                scanners.get(minIndex).close();
                scanners.remove(minIndex);
                values.remove(minIndex);
            }
        }

        writeString(values.get(0));

        while ((nextString = getNextValidate(scanners.get(0))) != null) {
            writeString(nextString);
        }
    }

    private static void writeString(String next) throws IOException {
        writer.write(next);
        writer.append("\n");
        writer.flush();

    }

    private static String parseArgs(String[] args) {
        if (args.length < 1) return WRONG_ARGS;
        if (args[0].equals("--help")) return HELP;
        if (args.length < 3) return WRONG_ARGS;
        if (args[0].equals("-a")) {
            if (args[1].equals("-i")) {
                regex = regexDigit;
                comparator = Comparator.comparingInt(Integer::parseInt);
                return parseFiles(args, 2);
            }
            if (args[1].equals("-s")) {
                regex = regexString;
                comparator = String::compareTo;
                return parseFiles(args, 2);
            }
        }
        if (args[0].equals("-d")) {
            if (args[1].equals("-i")) {
                regex = regexDigit;
                comparator = (o1, o2) -> Integer.parseInt(o2) - Integer.parseInt(o1);
                return parseFiles(args, 2);
            }
            if (args[1].equals("-s")) {
                regex = regexString;
                comparator = Comparator.reverseOrder();
                return parseFiles(args, 2);
            }
        }
        if (args[0].equals("-i")) {
            regex = regexDigit;
            comparator = Comparator.comparingInt(Integer::parseInt);
            return parseFiles(args, 1);
        }
        if (args[0].equals("-s")) {
            regex = regexString;
            comparator = String::compareTo;
            return parseFiles(args, 1);
        }
        return WRONG_ARGS;
    }

    private static String parseFiles(String[] args, int firstFile) {
        if (args.length < firstFile + 2) return WRONG_ARGS;
        try {
            if (!args[firstFile].matches(".+\\.txt")) return WRONG_ARGS;
            writer = new FileWriter(args[firstFile]);
            for (int i = firstFile + 1; i < args.length; i++) {
                if (!args[i].matches(".+\\.txt")) return WRONG_ARGS;
                scanners.add(new Scanner(new File(args[i])));
            }
        } catch (IOException e) {
            return WRONG_FILE;
        }
        return OK;
    }

    private static String getNextValidate(Scanner scanner) {
        String nextString;
        while (scanner.hasNext()) {
            nextString = scanner.nextLine();
            if (nextString.matches(regex) && (!previousValues.containsKey(scanner) || comparator.compare(nextString, previousValues.get(scanner)) > 0))
                return nextString;
        }
        return null;
    }

    private static int minIndex(ArrayList<String> values) {
        String min = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            if (comparator.compare(values.get(i), min) < 0) min = values.get(i);
        }
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).equals(min)) return i;
        }
        return -1;
    }
}
