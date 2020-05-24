package test.on2020_04.on2020_04_11_GCJQualRound.Task;

import java.io.PrintWriter;
import java.util.Scanner;

public class Task {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int T = in.nextInt();
        int B = in.nextInt();
        for (int i = 0; i < T; i++) {
            solve(in, out, B);
        }
    }

    private int[] a;

    public void solve(Scanner in, PrintWriter out, int B) {
        a = new int[B+1];

        int same = -1;
        int diff = -1;

        int p = 1;
        for (int q = 0; p <= B / 2; q += 2, ++p) {
            if (q > 0 && q % 10 == 0) {
                q += adjustArray(same, diff, in, out);
            }

            int left = query(p, in, out);
            int right = query(B - p + 1, in, out);
            a[p] = left;
            a[B - p + 1] = right;

            if (left == right) {
                same = p;
            } else {
                diff = p;
            }
        }

        String res = "";
        for (int b : a) {
            res += b;
        }
        out.println(res.substring(1));
        out.flush();
        in.next();
    }

    private int adjustArray(int same, int diff, Scanner in, PrintWriter out) {
        int q = 0;
        if (same > 0) {
            int b = query(same, in, out);
            q++;
            if (diff > 0) {
                int bDiff = query(diff, in, out);
                q++;
                boolean x = (b != a[same]);
                boolean y = (bDiff != a[diff]);
                if (x && y) {
                    flip();
                } else if (x) {
                    flip();
                    rev();
                } else if (y) {
                    rev();
                }
            } else {
                if (b != a[same]) {
                    flip();
                }
            }
        } else {
            int b = query(diff, in, out);
            q++;
            if (b != a[diff]) {
                flip(); // or rev, same thing
            }
        }
        if (q == 1) {
            query(1, in, out);
        }
        return 2;
    }

    private int query(int query, Scanner in, PrintWriter out) { 
        out.println(query);
        out.flush();
        return in.nextInt();
    }

    private void rev() {
        int n = a.length - 1;
        for (int i = 1; i <= n / 2; ++i) {
            int t = a[i];
            a[i] = a[n - i + 1];
            a[n - i + 1] = t;
        }
    }

    private void flip() {
        for (int i = 0; i < a.length; ++i) {
            a[i] = 1 - a[i];
        }
    }

}
