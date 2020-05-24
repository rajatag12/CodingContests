package test.on2020_04.on2020_04_24_GCJInternalRoundGolf.Security;



import java.util.*;
import java.io.PrintWriter;

public class Security {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int C = in.nextInt();
        int N = in.nextInt();

        List<Integer>[] adj = new ArrayList[C];
        for (int i = 0; i < C; i++) {
            adj[i] = new ArrayList<>();
        }

        int[] x = new int[C];
        for (int i = 1; i < C; i++) {
            x[i] = in.nextInt();
        }

        int[] U = new int[N];
        int[] V = new int[N];
        for (int i = 0; i < N; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            U[i] = u;
            V[i] = v;
            adj[u].add(v);
            adj[v].add(u);
        }

        int[][] w = new int[C][C];
        boolean[] done = new boolean[C];
        boolean[] active = new boolean[C];
        int[] afterTime = new int[C];

        done[0] = true;
        active[0] = true;
        int numActive = 1;

        int nodes = 1;
        int time = 0;
        while (numActive > 0) {
            ++time;
            int newDone = 0;
            for (int i = 0; i < C; ++i) {
                if (!active[i]) {
                    continue;
                }

                boolean allDone = true;
                for (int v : adj[i]) {
                    if (!done[v]) {
                        allDone = false;
                        break;
                    }
                }
                if (allDone) {
                    active[i] = false;
                    numActive--;
                    continue;
                }

                for (int v : adj[i]) {
                    if (done[v]) {
                        continue;
                    }
                    if ((x[v] < 0 && -x[v] == nodes)
                            || (x[v] > 0 && x[v] == time)) {
                        for (int u : adj[v]) {
                            if (!done[u]) {
                                continue;
                            }
                            w[u][v] = w[v][u] = time - afterTime[u];
                        }
                        afterTime[v] = time;
                        done[v] = true;
                        if (!active[v]) {
                            active[v] = true;
                            ++numActive;
                        }
                        newDone++;
                    }
                }
            }
            nodes += newDone;
        }

        out.print("Case #" + testNumber + ":");
        for (int i = 0; i < N; i++) {
            out.print(" " + w[U[i]][V[i]]);
        }
        out.println();
    }
}
