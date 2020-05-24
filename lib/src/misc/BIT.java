package misc;

public class BIT {
    private final int n;
    private final long[] a;

    public BIT(int n) {
        this.n = n + 1;
        this.a = new long[n + 1];
    }

    public long getSum(int x) {
        long s = 0;
        while (x > 0) {
            s += a[x];
            x -= (x & -x);
        }
        return s;
    }

    public void update(int x, long val) {
        while (x < n) {
            a[x] += val;
            x += (x & -x);
        }
    }
}
