package main;

import java.util.Scanner;
import java.io.PrintWriter;

public class OverexcitedFan {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int X = in.nextInt();
        int Y = in.nextInt();
        String tour = in.next();

        int res = 50000;
        for (int i = 0; i <= tour.length(); i++) {
            if (Math.abs(X) + Math.abs(Y) <= i) {
                res = i;
                break;
            }
            if (i == tour.length()) {
                break;
            }
            if (tour.charAt(i) == 'N') {
                ++Y;
            } else if (tour.charAt(i) == 'S') {
                --Y;
            } else if (tour.charAt(i) == 'E') {
                ++X;
            } else if (tour.charAt(i) == 'W') {
                --X;
            }
        }

        out.println(String.format("Case #%d: %s", testNumber, res == 50000 ? "IMPOSSIBLE" : res));
    }
}
