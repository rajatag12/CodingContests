package main;

import java.io.PrintWriter;
import java.util.Scanner;

public class Expogo {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int X = in.nextInt();
        int Y = in.nextInt();

        int x = Math.abs(X);
        int y = Math.abs(Y);

        StringBuilder ans = new StringBuilder();
        while (x != 0 || y != 0) {
            if (!possible(x, y)) {
                answer(testNumber, out, "IMPOSSIBLE");
                return;
            }

            if (x % 2 == 1) {
                if (possible((x-1)/2, y/2)) {
                    ans.append("E");
                    x--;
                } else {
                    ans.append("W");
                    x++;
                }
            } else {
                if (possible(x/2,(y-1)/2)) {
                    ans.append("N");
                    y--;
                } else {
                    ans.append("S");
                    y++;
                }
            }
            x /= 2;
            y /= 2;
        }

        if (X < 0) {
            flip(ans, 'E', 'W');
        }

        if ( Y < 0) {
            flip(ans, 'N', 'S');
        }

        answer(testNumber, out, ans.toString());
    }

    private void answer(int T, PrintWriter out, String answer) {
        out.println(String.format("Case #%d: %s", T, answer));
    }

    private boolean possible(int x, int y) {
        return (x == 0 && y == 0) || ((x + y) % 2 == 1);
    }

    private void flip(StringBuilder s, char a, char b) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == a) {
                s.setCharAt(i, b);
            } else if (s.charAt(i) == b) {
                s.setCharAt(i, a);
            }
        }
    }
}

