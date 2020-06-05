package main;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PerfectSubArray {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = in.nextInt();
        }

        Map<Integer, Integer> count = new HashMap<>();
        count.put(0, 1);

        long res = 0;

        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += A[i];
            for (int j = 0; j * j <= N * 100; j++) {
                int sq = j * j;
                res += count.getOrDefault(sum - sq, 0);
            }
            count.put(sum, count.getOrDefault(sum, 0) + 1);
        }

        out.println("Case #" + testNumber + ": " + res);
    }
}
