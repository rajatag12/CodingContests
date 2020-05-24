package math;

import static java.util.Arrays.stream;

public class MathAlgorithms {

    public static long power(long a, long e) {
        if (e == 0) return 1;
        if (e == 1) return a;
        long e2 = power(a, e / 2);
        long res = e2 * e2;
        if (e % 2 == 1) {
            res *= a;
        }
        return res;
    }

    public static long modPower(long a, long e, long m) {
        if (e == 0) return 1;
        if (e == 1) return a % m;
        long e2 = modPower(a, e / 2, m);
        long res = (e2 * e2) % m;
        if (e % 2 == 1) {
            res *= a;
            res %= m;
        }
        return res;
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static int chineseRemainder(int[] n, int[] a) {

        int prod = stream(n).reduce(1, (i, j) -> i * j);

        int p, sm = 0;
        for (int i = 0; i < n.length; i++) {
            p = prod / n[i];
            sm += a[i] * mulInv(p, n[i]) * p;
        }
        return sm % prod;
    }

    public static int mulInv(int a, int b) {
        int b0 = b;
        int x0 = 0;
        int x1 = 1;

        if (b == 1)
            return 1;

        while (a > 1) {
            int q = a / b;
            int amb = a % b;
            a = b;
            b = amb;
            int xqx = x1 - q * x0;
            x1 = x0;
            x0 = xqx;
        }

        if (x1 < 0)
            x1 += b0;

        return x1;
    }
}
