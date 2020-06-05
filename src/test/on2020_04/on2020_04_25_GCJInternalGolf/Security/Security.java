package test.on2020_04.on2020_04_25_GCJInternalGolf.Security;



import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Security {

    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int C = in.nextInt();
        int D = in.nextInt();

        List<Integer> t = new ArrayList<>();
        List<Pair> a = new ArrayList<>();
        t.add(0);

        int[] time = new int[C];
        for (int i = 1; i < C; ++i) {
            int x = in.nextInt();
            if (x > 0) {
                t.add(x);
                time[i] = x;
            } else {
                a.add(new Pair(-x, i));
            }
        }

        Collections.sort(t);
        Collections.sort(a);
        for (int i = 1; !a.isEmpty(); ) {
            if (a.get(0).f != i) {
                ++i;
                continue;
            }
            Pair p = a.remove(0);
            p.f = t.get(i-1) + 1;
            t.add(i, p.f);
            time[p.s] = p.f;
        }

        int[] res = new int[D];
        for (int i = 0; i < D; ++i) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            res[i] = Math.max(1, Math.abs(time[u] - time[v]));
        }
        out.println("Case #" + testNumber + ": " + printInts(res));
    }

    private static String printInts(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int x : a) {
            sb.append(x).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    static class Pair implements Comparable<Pair> {
        int f, s;
        Pair(int f, int s) {
            this.f = f;
            this.s = s;
        }

        @Override
        public int compareTo(Pair o) {
            int t = f - o.f;
            return t == 0 ? s - o.s : t;
        }
    }

    public void solve1(int testNumber, Scanner in, PrintWriter out) {
        StringBuilder input = new StringBuilder();
        int C = in.nextInt();
        int N = in.nextInt();
        input.append(C).append(" ").append(N).append("\n");

        List<Integer>[] adj = new ArrayList[C];
        for (int i = 0; i < C; i++) {
            adj[i] = new ArrayList<>();
        }

        int[] x = new int[C];
        for (int i = 1; i < C; i++) {
            x[i] = in.nextInt();
            input.append(x[i] + " ");
        }
        input.append("\n");

        int[] U = new int[N];
        int[] V = new int[N];
        for (int i = 0; i < N; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            U[i] = u;
            V[i] = v;
            adj[u].add(v);
            adj[v].add(u);
            input.append(u+1).append(" ").append(v+1).append("\n");
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

        // out.print("Case #" + testNumber + ": ");
        StringBuilder sb = new StringBuilder();
        int min = 2000, max = 0;
        for (int i = 0; i < N; i++) {
            int latency = w[U[i]][V[i]];
            sb.append(latency).append(" ");
            min = Math.min(min, latency);
            max = Math.max(max, latency);
        }
        if (min < 1 || max > 1000000) {
            System.err.println(String.format("Min: %d, Max: %d", min, max));
            // System.err.println(input.toString());
        }
        out.println(sb.toString());
        out.flush();
    }
}
