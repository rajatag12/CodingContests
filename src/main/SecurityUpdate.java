package main;

import java.util.*;
import java.io.PrintWriter;

public class SecurityUpdate {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int C = in.nextInt();
        int D = in.nextInt();

        int[] X = new int[C];
        List<Integer> positive = new ArrayList<>();
        for (int i = 1; i < C; ++i) {
            X[i] = in.nextInt();
            if (X[i] > 0) {
                positive.add(i);
            }
        }
        positive.sort(Comparator.comparing(i -> X[i]));

        int[] U = new int[D];
        int[] V = new int[D];
        for (int i = 0; i < D; ++i) {
            U[i] = in.nextInt() - 1;
            V[i] = in.nextInt() - 1;
        }

        int[] order = new int[C];
        int px = 0;
        for (int i = 1; i < C; ) {
            // if -i exists then it should be here.
            int k = 0;
            for (int j = 1; j < C; ++j) {
                if (X[j] == -i) {
                    order[i + k] = j;
                    k++;
                }
            }

            if (k > 0) {
                i += k;
                continue;
            }

            // else find next +ve x[i] to fill
            order[i++] = positive.get(px++);
        }

        // assign time
        int[] t = new int[C];
        for (int i = 1; i < C; ++i) {
            int node = order[i];
            if (X[node] > 0) {
                t[node] = X[node];
            } else {
                t[node] = t[order[i-1]] + 1;
                int j = i + 1;
                while (j < C && X[order[j]] == X[node]) {
                    t[order[j]] = t[node];
                    j++;
                }
                i = j - 1;
            }
        }

        out.print("Case #" + testNumber + ":");
        for (int i = 0; i < D; ++i) {
            out.print(" ");
            out.print(Math.max(1, Math.abs(t[U[i]] - t[V[i]])));
        }
        out.println();
    }
}
