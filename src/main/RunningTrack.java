package main;

import java.util.Arrays;
import java.util.Scanner;
import java.io.PrintWriter;

public class RunningTrack {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        String S = in.next();
        String T = in.next();

        int n = S.length();
        int m = T.length();

        int[][] f = new int[n + 1][m + 1];
        int[][] p = new int[m][m+1];
        for (int[] v : p) {
            Arrays.fill(v, -1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (S.charAt(i) != T.charAt(j)) {
                    continue;
                }
                int k = f[i][j] + 1;
                f[i+1][j+1] = k;
                p[j][k] = i;
            }
        }

        String rt = new StringBuilder(T).reverse().toString();
        int[][] r = new int[n + 1][m + 1];
        int[][] rp = new int[m][m+1];
        for (int[] v : rp) {
            Arrays.fill(v, -1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (S.charAt(i) != rt.charAt(j))
                    continue;
                int k = r[i][j] + 1;
                r[i+1][j+1] = k;
                rp[j][k] = i;
            }
        }

        int INF = 5000;
        int[] dp = new int[m + 1];
        int[] l = new int[m + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 0; i < m; i++) {
            if (dp[i] == INF)
                continue;
            for (int j = i; j < m; j++) {
                int rj = m - i - 1;
                int len = j - i + 1;
                if (p[j][len] < 0 && rp[rj][len] < 0) {
                    continue;
                }
                if (dp[j + 1] > dp[i] + 1) {
                    dp[j + 1] = dp[i] + 1;
                    l[j + 1] = len;
                }
            }
        }

        if (dp[m] == INF) {
            out.println(-1);
            return;
        }

        out.println(dp[m]);
        int rem = dp[m];
        int[][] cuts = new int[rem][];
        for (int i = m; i > 0; --rem) {
            int take = l[i];
            if (take == 0) {
                throw new IllegalStateException("take must be more than 0");
            }
            int p2 = p[i-1][take];
            boolean rev = false;
            if (p2 == -1) {
                rev = true;
                int ri = m - (i-take) - 1;
                p2 = rp[ri][take];
            }
            int p1 = p2 - take + 1;
            if (rev) {
                cuts[rem-1] = new int[]{p2+1, p1+1};
            } else {
                cuts[rem - 1] = new int[]{p1 + 1, p2 + 1};
            }
            i -= take;
        }
        for (int[] c : cuts) {
            out.println(c[0] + " " + c[1]);
        }
    }
}
