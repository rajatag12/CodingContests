package main;

import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;

public class ShrinkingArray {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int[] A = new int[n];
        for (int i = 0; i < n; i++) {
            A[i] = in.nextInt();
        }

        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = A[i];
        }

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i < n; i++) {
                int j = i + len - 1;
                if (j >= n) continue;
                for (int k = i; k < j; ++k) {
                    if (dp[i][k] == 0 || dp[k+1][j] == 0) {
                        continue;
                    }
                    if (dp[i][k] == dp[k+1][j]) {
                        dp[i][j] = dp[i][k] + 1;
                    }
                }
            }
        }

        int[] dp2 = new int[n + 1];
        Arrays.fill(dp2, 1000);
        dp2[0] = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 1; i + j <= n; j++) {
                if (dp[i][i+j-1] > 0) {
                    dp2[i + j] = Math.min(dp2[i + j], dp2[i] + 1);
                } else {
                    dp2[i + j] = Math.min(dp2[i + j], dp2[i] + j);
                }
            }
        }

        out.println(dp2[n]);
    }
}
