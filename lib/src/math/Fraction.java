package math;

import types.Pair;

import java.util.Objects;

public class Fraction implements Comparable<Fraction> {

    public final long x, y;
    public long nx, ny;

    public Fraction(long x, long y) {
        this.x = x;
        this.y = y;
    }

    private Fraction normalize() {
        long g = MathAlgorithms.gcd(x, y);
        return new Fraction(x / g, y / g);
    }

    public Pair asPair() {
        return new Pair(x, y);
    }

    @Override
    public int compareTo(Fraction fraction) {
        return Long.compare(nx * fraction.ny, ny * fraction.nx);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return nx == fraction.nx &&
                ny == fraction.ny;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nx, ny);
    }
}
