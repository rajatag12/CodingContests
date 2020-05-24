package main;

public class EllysTwoRatings {
    public double getChance(int N, int A, int B) {
        double[][] dpA = chance(A, N);
        double[][] dpB = chance(B, N);
        double res = 0;
        for (int w = 1; w <= N; ++w) {
            for (int r = 1; r <= 1000; ++r) {
                res += dpA[w][r] * dpB[w][r];
            }
        }
        return res;
    }

    private double[][] chance(int start, int n) {
        double[][] dp = new double[n + 1][1001];
        dp[0][start] = 1;
        for (int w = 0; w < n; ++w) {
            for (int r = 1; r <= 1000; ++r) {
                if (dp[w][r] == 0) continue;
                int minr = Math.max(1, r - 100);
                int maxr = Math.min(1000, r + 100);
                int d = maxr - minr + 1;
                for (int nr = minr; nr <= maxr; ++nr) {
                    dp[w+1][nr] += dp[w][r] / d;
                }
            }
        }
        return dp;
    }
}
