package test.on2020_05.on2020_05_08_TopCoder_SRM__773.ChristmasSongBroadcast;



import graph.Graph;
import graph.MinimumSpanningTree;
import math.Prime;

import java.util.Arrays;

public class ChristmasSongBroadcast {
    public int minimizeCost(int T, int[] A, int[] B, String[] directCost) {
        int MOD = 1000000007;
        int N = A.length + 1;
        int[][] G = new int[N][N];
        for (int[] g : G) {
            Arrays.fill(g, MOD-1);
        }

        G[0][0] = 0;
        for (int i = 1; i < N; i++) {
            G[i][i] = 0;
            G[0][i] = B[i - 1];
            for (int j = 0; j < Math.min(B[i - 1], 100); j++) {
                int mi = (int) ((j + MOD - B[i - 1]) * Prime.modInverse(A[i - 1], MOD) % MOD);
                if (T > mi) {
                    G[0][i] = j;
                    break;
                }
            }
            for (int j = i + 1; j < N; j++) {
                G[i][j] = G[j][i] = directCost[i-1].charAt(j-1);
            }
        }

        Graph g = new Graph(N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                g.addEdge(i, j, G[i][j]);
            }
        }
        long totalWeight = new MinimumSpanningTree(g).getTotalWeight();

        return (int) totalWeight;
    }
}
