package main;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Solution {

  private static final int INF = 577777777;

  private static Random random = new Random(System.currentTimeMillis());

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    Scanner in = new Scanner(inputStream);
    PrintWriter out = new PrintWriter(outputStream);

    int T = 1;
    int C = 10;
    int N = 25;

    if (args.length == 1) {
      T = Integer.parseInt(args[0]);
    } else if (args.length == 2) {
      C = Integer.parseInt(args[0]);
      N = Integer.parseInt(args[1]);
    } else if (args.length == 3) {
      T = Integer.parseInt(args[0]);
      C = Integer.parseInt(args[1]);
      N = Integer.parseInt(args[2]);
    }

    // T
    out.println(T);
    boolean allOK = true;
    for (int t = 0; t < T; t++) {
      int[] x0 = new int[C - 1];
      for (int i = 0; i < C - 1; i++) {
        x0[i] = random.nextInt(2);
      }

      int[][] G = prepareGraph(C, N);
      int[] constraints = getTestData(G, x0);
      printInput(out, C, N, G, constraints);

      int[] output = readOutput(in, N);
      boolean good = checkSolution(C, G, x0, constraints, output);
      if (!good) {
        allOK = false;
      }
      out.println(good ? "OK" : "WA");
      out.flush();
    }
    System.err.println();
    System.err.println("Result: " + (allOK ? "CORRECT" : "WA"));
  }

  private static int[][] prepareGraph(int C, int N) {
    int[][] G = new int[C][C];
    for (int i = 0; i < C; i++) {
      for (int j = 0; j < C; j++) {
        if (i != j) {
          G[i][j] = INF;
        }
      }
    }

    while (N > C) {
      int u = random.nextInt(C);
      int v = random.nextInt(C);
      if (u == v || G[u][v] < INF) continue;
      int w = random.nextInt(400) + 600;
      G[u][v] = G[v][u] = w;
      N--;
    }

    boolean[][] reachable = new boolean[C][C];
    for (int i = 0; i < C; i++) {
      for (int j = 0; j < C; j++) {
        reachable[i][j] = G[i][j] < INF;
      }
    }
    for (int i = 0; i < C; i++) {
      for (int j = 0; j < C; j++) {
        for (int k = 0; k < C; k++) {
          reachable[i][j] |= reachable[i][k] && reachable[k][j];
        }
      }
    }

    for (int i = 0; i < C; i++) {
      if (!reachable[0][i]) {
        --N;
        G[0][i] = random.nextInt(600) + 400;
        G[i][0] = G[0][i];
      }
    }
    if (N < 0) {
      throw new AssertionError();
    }

    while (N > 0) {
      int u = random.nextInt(C);
      int v = random.nextInt(C);
      if (u == v || G[u][v] < INF) continue;
      int w = random.nextInt(500) + 500;
      G[u][v] = G[v][u] = w;
      --N;
    }

    return G;
  }

  private static int[] getTestData(int[][] G, int[] x0) {
    int C = G.length;

    int[] when = djkstra(G, 0);

    Integer[] visitOrder = new Integer[C];
    for (int i = 0; i < C; i++) {
      visitOrder[i] = i;
    }
    Arrays.sort(visitOrder, Comparator.comparingInt(i -> when[i]));

    int x = 0, y = 1;
    int[] after = new int[C];
    for (int i = 1; i < C; i++) {
      int u = visitOrder[i];
      int p = visitOrder[i - 1];
      if (when[p] != when[u]) {
        x += y;
        y = 1;
      } else {
        y++;
      }
      after[u] = x;
    }

    int[] X = new int[C - 1];
    for (int i = 1; i < C; i++) {
      X[i-1] = x0[i-1] == 1 ? -after[i] : when[i];
    }
    return X;
  }

  private static void printInput(PrintWriter out, int c, int n, int[][] G, int[] constraints) {
    // C N
    out.println(c + " " + n);
    // X2 .. XC
    // System.err.print("Input: ");
    for (int x : constraints) {
      // System.err.print(x + " ");
      out.print(x + " ");
    }
    // System.err.println();
    out.println();
    // Ui Vi
    int k = 0;
    for (int i = 0; i < c; i++) {
      for (int j = i+1; j < c; j++) {
        if (G[i][j] == INF) {
          continue;
        }
        out.println((i+1) + " " + (j+1));
        k++;
        if (k > n) {
          System.err.println("oops.. produced more than N edges.");
        }
      }
    }
    out.flush();
  }

  private static int[] readOutput(Scanner in, int N) {
    int[] d = new int[N];
    for (int i = 0; i < N; ++i) {
      d[i] = in.nextInt();
    }
    return d;
  }

  private static boolean checkSolution(int C, int[][] G, int[] x0, int[] constraints, int[] output) {
    int k = 0;
    for (int i = 0; i < C; i++) {
      for (int j = i+1; j < C; j++) {
        if (G[i][j] == INF) {
          continue;
        }
        G[i][j] = G[j][i] = output[k++];
      }
    }

    int[] constraints1 = getTestData(G, x0);

    boolean good = true;

    // System.err.print("Output: ");
    for (int i = 0; i < C-1; i++) {
      // System.err.print(constraints1[i] + " ");
      if (constraints[i] != constraints1[i]) {
        good = false;
      }
    }
    // System.err.println();

    System.err.print(good ? "." : "_");
    return good;
  }


  private static int[] djkstra(int[][] g, int src) {
    int n = g.length;

    int[] d = new int[n];
    Arrays.fill(d, INF);
    d[src] = 0;

    boolean[] seen = new boolean[n];
    boolean[] done = new boolean[n];

    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(i -> d[i]));
    pq.add(src);

    while (!pq.isEmpty()) {
      int u = pq.poll();
      done[u] = true;

      for (int i = 0; i < n; i++) {
        if (g[u][i] == INF || done[i]) {
          continue;
        }

        d[i] = Math.min(d[i], d[u] + g[u][i]);
        if (!seen[i]) {
          seen[i] = true;
          pq.add(i);
        }
      }
    }

    return d;
  }
}
