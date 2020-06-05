package main;

import java.util.Scanner;
import java.io.PrintWriter;

public class ChemicalTable {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        int[] r = new int[n];
        int[] c = new int[m];
        int ans = n + m - 1;
        for (int i = 0; i < q; i++) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            if (r[x] == 0) {
                ans--;
                r[x] = 1;
            }
            if (c[y] == 0) {
                ans--;
                c[y] = 1;
            }
        }
        out.println(ans);
    }
}
