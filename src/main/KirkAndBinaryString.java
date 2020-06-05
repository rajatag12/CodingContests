package main;

import java.util.Scanner;
import java.io.PrintWriter;

public class KirkAndBinaryString {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        // 0111001100111011101000
        // 0011001100001011101000
        //
        // 0001000100001000101000

        String s = in.next();
        S = s;
        int n = s.length();

        String ans = "";
        for (int i = 0; i < n; i++) {
            if (g(i, '1') && g(i+1, '0')) {

            }
        }
    }

    private String S;
    private boolean g(int i, char c) {
        return S.charAt(i) == c;
    }
}
