package main;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskB {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();

        int[] armies = new int[m + 1];
        for (int i = 0; i <= m; ++i) {
            armies[i] = in.nextInt();
        }

        int res = 0;
        for (int i = 0; i < m; ++i) {
            if (Integer.bitCount(armies[m] ^ armies[i]) <= k) {
                ++res;
            }
        }

        out.println(res);
    }
}
