package test.on2020_05.on2020_05_02_GCJRound1C.OversizedPancakes;



import java.util.*;
import java.io.PrintWriter;

public class OversizedPancakes {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        int D = in.nextInt();
        long[] A = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = in.nextLong();
        }
        
        int res = solveCase(N, D, A);
        out.println(String.format("Case #%d: %d", testNumber, res));
    }

    private int solve(int N, int D, long[] A) {
        Arrays.sort(A);

        Set<Fraction> targetSizes = new HashSet<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < D; j++) {
                Fraction f = new Fraction(A[i], j + 1);
                targetSizes.add(f);
            }
        }

        int res = D - 1;
        for (Fraction t : targetSizes) {
            int fulls = 0;
            long fullD = 0;
            long partialD = 0;
            for (int i = 0; i < N; ++i) {
                long d = A[i] * t.y / t.x;
                if (A[i] * t.y == t.x * d) {
                    fullD += d;
                    fulls++;
                    if (fullD >= D) {
                        if (fullD > D) --fulls;
                        res = Math.min(res, D - fulls);
                        break;
                    }
                } else {
                    partialD += d;
                }
            }
            if (fullD < D && fullD + partialD >= D) {
                res = Math.min(res, D - fulls);
            }
        }

        return res;
    }

    private int solveCase(int N, int D, long[] A) {
        Arrays.sort(A);

        List<Fraction> targetSizes = new ArrayList<>();
        Map<Fraction, int[]> targetSizeMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < D; j++) {
                Fraction f = new Fraction(A[i], j + 1);
                if (!targetSizeMap.containsKey(f)) {
                    targetSizeMap.put(f, new int[2]);
                    targetSizes.add(f);
                }
            }
        }

        Collections.sort(targetSizes);
        Fraction largestPossible = find(targetSizes, N, D, A);

        int res = D - 1;
        for (int i = 0; i < N; i++) {
            for (int j = 1; j <= D; j++) {
                Fraction f = new Fraction(A[i], j);
                if (f.compareTo(largestPossible) > 0) {
                    continue;
                }

                int[] s = targetSizeMap.get(f);
                int fullyUsed = s[0];
                int numSlices = s[1];
                if (numSlices >= D) {
                    continue;
                }

                fullyUsed++;
                numSlices += j;
                if (numSlices <= D) {
                    res = Math.min(res, D - fullyUsed);
                }
                s[0] = fullyUsed;
                s[1] = numSlices;
            }
        }

        return res;
    }

    private Fraction find(List<Fraction> targetSizes, int N, int D, long[] A) {
        int low = 0, high = targetSizes.size() - 1;
        while (low < high) {
            int mid = (low +  high) / 2;
            if (possible(targetSizes.get(mid), D, A)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        while (!possible(targetSizes.get(low), D, A)) --low;

        return targetSizes.get(low);
    }

    private boolean possible(Fraction fraction, int D, long[] A) {
        long d = 0;
        for (int i = A.length - 1; i >= 0; i--) {
            d += A[i] * fraction.y / fraction.x;
            if (d >= D) {
                return true;
            }
        }
        return false;
    }

    static class Fraction implements Comparable<Fraction> {
        long x, y;

        Fraction(long x, long y) {
            long g = gcd(x, y);
            this.x = x / g;
            this.y = y / g;
        }

        long gcd(long x, long y) {
            return y == 0 ? x : gcd(y, x % y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Fraction fraction = (Fraction) o;
            return x == fraction.x &&
                    y == fraction.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Fraction fraction) {
            return Long.compare(x * fraction.y, y * fraction.x);
        }

        @Override
        public String toString() {
            return "Fraction{" + x + "/" + y + '}';
        }
    }
}
