package types;

import math.Fraction;
import math.MathAlgorithms;

import java.util.Scanner;

public class Pair implements Comparable<Pair> {
    public long first;
    public long second;

    public Pair() {}

    public Pair(long f, long s) {
        this.first = f;
        this.second = s;
    }

    public void read(Scanner scanner) {
        first = scanner.nextLong();
        second = scanner.nextLong();
    }

    public Pair subtract(Pair p) {
        return new Pair(first - p.first, second - p.second);
    }

    public Pair add(Pair p) {
        return new Pair(first + p.first, second + p.second);
    }

    public Pair normalize() {
        long gcd = MathAlgorithms.gcd(first, second);
        return new Pair(first / gcd, second / gcd);
    }

    public Fraction asFraction() {
        return new Fraction(first, second);
    }

    @Override
    public int compareTo(Pair stPair) {
        int f = Long.compare(first, stPair.first);
        return f == 0 ? Long.compare(second, stPair.second) : f;
    }
}
