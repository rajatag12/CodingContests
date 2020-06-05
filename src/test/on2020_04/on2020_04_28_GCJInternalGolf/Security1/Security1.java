package test.on2020_04.on2020_04_28_GCJInternalGolf.Security1;



import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Security1 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int C = in.nextInt();
        int N = in.nextInt();

        int[] x = new int[C];
        for (int i = 1; i < C; i++) {
            x[i] = in.nextInt();
        }

        int[] U = new int[N];
        int[] V = new int[N];
        for (int i = 0; i < N; i++) {
            U[i] = in.nextInt() - 1;
            V[i] = in.nextInt() - 1;
        }

        int[] visitOrder = getVisitOrder(x);
        int[] time = new int[C];
        for (int i = 1; i < C; ++i) {
            if (time[visitOrder[i]] != 0) {
                continue;
            }
            for (int j = i; j < C && x[visitOrder[j]] == x[visitOrder[i]]; ++j) {
                time[visitOrder[j]] = time[visitOrder[i-1]] + 1;
                if (x[visitOrder[j]] > 0) {
                    time[visitOrder[j]] = x[visitOrder[j]];
                }
            }
        }

        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            res[i] = Math.abs(time[U[i]] - time[V[i]]);
            res[i] = Math.max(res[i], 1);
            out.print(res[i] + " ");
        }
        out.println();
        out.flush();

        boolean ans = in.next().equals("OK");
        if (!ans) {
            System.err.println("Ouch!");
        }
    }

    private int[] getVisitOrder(int[] X) {
        int n = X.length;

        int[] res = new int[n];
        Arrays.fill(res, -1);

        res[0] = 0;
        for (int i = 1; i < n; i++) {
            int k = 0;
            for (int j = 0; j < n; j++) {
                if (X[j] == -i) {
                    res[i+k] = j;
                    k++;
                }
            }
        }

        Integer[] o = new Integer[n];
        for (int i = 0; i < n; i++) {
            o[i] = i;
        }
        Arrays.sort(o, Comparator.comparingInt(i -> X[i]));

        int j = 0;
        for (int i = 0; i < n; i++) {
            if (X[o[i]] <= 0) {
                continue;
            }
            while (res[j] != -1) j++;
            res[j] = o[i];
        }

        return res;
    }
}
