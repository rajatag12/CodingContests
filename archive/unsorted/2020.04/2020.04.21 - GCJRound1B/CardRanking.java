package main;



import java.util.*;
import java.io.PrintWriter;

public class CardRanking {
    int R, S;
    int[] cards = new int[1600];
    int[] aux = new int[1600];
    Map<String, Integer> cache = new HashMap<>();
    Map<String, Integer> seen = new HashMap<>();
    Map<String, int[]> cuts = new HashMap<>();
    String t = "";

    public void solve(int testNumber, Scanner in, PrintWriter out) {
        R = in.nextInt();
        S = in.nextInt();

        solveSmart(testNumber, out);
    }

    private void solveSmart(int testNumber, PrintWriter out) {
        for (int i = 0; i < S; i++) {
            for(int j = 1; j <= R; ++j) {
                cards[R * i + j - 1] = j;
            }
        }

        int n = R*S;
        int res = 0;
        List<Integer> cuts = new ArrayList<>();
        while (!done()) {
            // find interval ending with cards[0] and surrounded by same rank
            boolean got = false;
            int best1 = -1, best2 = -1;
            for (int i = 1; i < n - 1; i++) {
                for (int j = i; j < n - 1; ++j) {
                    int prev = cards[i - 1];
                    int next = cards[j + 1];
                    if (prev != next)
                        continue;
                    if (prev == cards[i] || cards[j] == next)
                        continue;
                    best1 = i;
                    best2 = j - i + 1;
                    if (cards[j] != cards[0])
                        continue;
                    best1 = i;
                    best2 = j - i + 1;
                    got = true;
                    break;
                }
                if (got) break;
            }
            restack(best1, best2);
            cuts.add(best1);
            cuts.add(best2);
            res++;
        }
        out.println("Case #" + testNumber + ": " + res);
        for (int i = 0; i < res; i++) {
            out.println(cuts.get(2*i) + " " + cuts.get(2 * i + 1));
        }
    }

    private void restack(int i, int j) {
        System.arraycopy(cards, i, aux, 0, j);
        System.arraycopy(cards, 0, aux, j, i);
        System.arraycopy(cards, i+j, aux, i+j, R*S-(i+j));
        swap();
    }

    private void swap() {
        int[] t = cards;
        cards = aux;
        aux = t;
    }

    private boolean done() {
        for (int i = 1; i <= R; ++i) {
            for (int j = 0; j < S; ++j) {
                if (cards[(i-1) * S + j] != i) {
                    return false;
                }
            }
        }
        return true;
    }

    private void solveStupid(int testNumber, PrintWriter out) {
        String s = "";
        for (int i = 0; i < S; i++) {
            for(int j = 1; j <= R; ++j) {
                s += j;
                cards[R * i + j - 1] = j;
            }
        }
        t = "";
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < S; j++) {
                t += (i+1);
            }
        }
        int res = dfs(s, 0);
        if (res == 100) {
            out.println("IMPOSSIBLE");
        } else {
            out.println(String.format("Case #%d: %s", testNumber, res));
        }
        System.err.println("Start: " + s);
        for (int i = 0; i < res; ++i) {
            int a = cuts.get(s)[0], b = cuts.get(s)[1];
            System.err.print(String.format("[%d, %d]", a, b));
            s = op(s, a, b);
            System.err.println(String.format(": %s", s));
        }
        System.err.println();
    }

    private int dfs(String s, int steps) {
        if (s.equals(t)) {
            return 0;
        }
        if (cache.containsKey(s)) {
            return cache.get(s);
        }
        if (seen.containsKey(s)) {
            if (seen.get(s) < steps) {
                return 100;
            }
        }
        seen.put(s, steps);

        if (steps == 28) {
            return 100;
        }

        int min = 100;
        int best1 = -1, best2 = -1;
        for (int i = 1; i < R*S-1; ++i) {
            for (int j = 1; j <= R*S-1-i; ++j) {
                String ns = op(s, i, j);
                int t = 1 + dfs(ns,steps + 1);
                if (t < min) {
                    min = t;
                    best1 = i;
                    best2 = j;
                }
            }
        }
        seen.remove(s);

        cuts.put(s, new int[] {best1, best2});
        cache.put(s, min);
        return min;
    }

    private String op(String s, int i, int j) {
        return s.substring(i, i+j) + s.substring(0, i) + s.substring(i + j);
    }
}
