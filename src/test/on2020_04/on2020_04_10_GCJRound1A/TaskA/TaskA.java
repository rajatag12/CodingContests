package test.on2020_04.on2020_04_10_GCJRound1A.TaskA;



import java.io.PrintWriter;
import java.util.Scanner;

public class TaskA {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        String[] pattern = new String[n];
        for (int i = 0; i < n; i++) {
            pattern[i] = in.next();
        }

        String prefix = "", suffix = "";
        String res = "";
        for (int i = 0; i < n; i++) {
            String[] tokens = pattern[i].split("\\*", -1);
            String left = tokens[0];
            String right = tokens[tokens.length - 1];
            if (left.length() > prefix.length()) {
                String t = prefix;
                prefix = left;
                left = t;
            }
            if (right.length() > suffix.length()) {
                String t = suffix;
                suffix = right;
                right = t;
            }

            if (!prefix.startsWith(left) || !suffix.endsWith(right)) {
                out.println(String.format("Case #%d: %s", testNumber, "*"));
                return;
            }

            for (int j = 1; j < tokens.length - 1; ++j) {
                res += tokens[j];
            }
        }
        out.println(String.format("Case #%d: %s", testNumber, prefix + res + suffix));
    }

    private boolean match(String word, String pattern) {
        // assuming only one *
        int index = pattern.indexOf('*');
        return word.startsWith(pattern.substring(0, index)) &&
                word.endsWith(pattern.substring(index + 1));
    }
}
