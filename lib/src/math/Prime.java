package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prime {

    public static long modInverse(long a, long p) {
        return MathAlgorithms.modPower(a, p - 2, p);
    }

    public static List<Integer> sieve(int N) {
        List<Integer> primes = new ArrayList<>();
        boolean[] p = new boolean[N + 1];
        Arrays.fill(p, true);
        p[0] = p[1] = false;
        for (int i = 2; i * i <= N; ++i) {
            if (!p[i]) continue;
            primes.add(i);
            for (int j = i + i; j <= N; j += i) {
                p[j] = false;
            }
        }
        return primes;
    }
}
