package main;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        Scanner in = new Scanner(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }

    static class Task {
        int n, d;
        long[] sizes;

        Random r = new Random(System.currentTimeMillis());

        public void solve(int testNumber, Scanner in, PrintWriter out) {
            n = 10; //in.nextInt();
            d = 3; //in.nextInt();
            for (int i = 0; i < 1; i++) {
                generateInput(in);
                int ans1 = solve1(n, d, sizes);
                int ans2 = solve2(n, d, sizes);
                if (ans1 != ans2) {
                    System.err.println("oops");
                }
            }
            // out.println("Case #" + testNumber + ": " + ans1);
        }

        private void getInput(Scanner in) {
            n = in.nextInt();
            d = in.nextInt();
            sizes = new long[n];
            for (int i = 0; i < n; ++i) {
                sizes[i] = in.nextLong();
            }
        }

        private void generateInput(Scanner in) {
            sizes = new long[] {6, 9, 17, 21, 27, 39, 50, 50, 50, 50};
            for (int i = 0; i < n; i++) {
               // sizes[i] = 1 + r.nextInt(60);// + 359999999900L;
            }
        }

        private int solve1(int n, int d, long[] sizes) {
            Arrays.sort(sizes);

            Map<Long, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < n; ++i) {
                put(map, sizes[i], i);
            }

            int[] indices = new int[d+1];
            long[] values = new long[d+1];
            int x = 0;

            int res = d - 1;
            for (int i = 0; i < n; ++i) {
                for (int divs = 1; divs < d; ++divs) {
                    long curSize = sizes[i];

                    int rem = d;
                    int curMin = d;

                    // check how many are divisble by curSize/divs.
                    int k = 1;
                    while (rem > 0 && k <= rem) {
                        if ((curSize * k) % divs != 0) {
                            k++;
                            continue;
                        }

                        long s = curSize * k / divs;
                        int p = getFirst(map, s);
                        if (p == -1) {
                            k++;
                            continue;
                        }

                        curMin--;
                        rem -= k;
                        indices[x] = p;
                        values[x++] = sizes[p];
                        sizes[p] = 0;
                    }

                    for (int j = n - 1; j >= 0; --j) {
                        if (rem <= 0) {
                            break;
                        }
                        rem -= sizes[j] * divs / curSize;
                    }

                    while (x > 0) {
                        --x;
                        sizes[indices[x]] = values[x];
                        put(map, values[x], indices[x]);
                    }

                    if (rem > 0) {
                        continue;
                    }

                    res = Math.min(res, curMin);
                }
            }

            return res;
        }

        private void put(Map<Long, List<Integer>> map, long s, int x) {
            List<Integer> v = map.get(s);
            if (v == null) {
                v = new ArrayList<>();
            }
            v.add(x);
            map.put(s, v);
        }

        private int getFirst(Map<Long, List<Integer>> map, long s) {
            List<Integer> v = map.get(s);
            if (v == null || v.isEmpty()) {
                return -1;
            }
            return v.remove(0);
        }

        private int solve2(int n, int d, long[] sizes) {
            Arrays.sort(sizes);

            Map<Fraction, long[]> m = new HashMap<>();
            List<Fraction> allSizes = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                for (int divs = 1; divs <= d; ++divs) {
                    Fraction f = new Fraction(sizes[i], divs);
                    if (m.containsKey(f)) {
                        continue;
                    }
                    m.put(f, new long[2]);
                    allSizes.add(f);
                }
            }

            Collections.sort(allSizes);
            int low = 0, high = allSizes.size() - 1;
            while (low < high) {
                int mid = (low + high) / 2;
                if (possible(sizes, allSizes.get(mid), d)) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            while (!possible(sizes, allSizes.get(low), d)) {
                --low;
            }
// 6 9 17 21 27 39 50 50 50 50
            int res = d - 1;
            for (int i = 0; i < n; ++i) {
                for (int divs = 1; divs < d; ++divs) {
                    Fraction f = new Fraction(sizes[i], divs);
                    if (f.compareTo(allSizes.get(low)) > 0) {
                        continue;
                    }
                    long[] v = add(m, sizes[i], divs);
                    if (v[1] <= d) {
                        res = Math.min(res, d - (int)v[0]);
                    }
                }
            }

            return res;
        }

        private boolean possible(long[] sizes, Fraction fraction, int D) {
            long count = 0;
            for (int i = sizes.length - 1; i >= 0; --i) {
                count += sizes[i] * fraction.d / fraction.n;
                if (count >= D) {
                    return true;
                }
            }
            return false;
        }

        private long[] add(Map<Fraction, long[]> map, long s, int c) {
            Fraction f = new Fraction(s, c);
            long[] v = map.get(f);
            v[0]++;
            v[1] += c;
            return v;
        }
    }

    static class Fraction implements Comparable<Fraction> {
        long n, d;

        Fraction(long n, long d) {
            long gcd = gcd(n, d);
            this.n = n / gcd;
            this.d = d / gcd;
        }

        long gcd(long n, long d) {
            return d == 0 ? n : gcd(d, n % d);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Fraction fraction = (Fraction) o;

            if (n != fraction.n) {
                return false;
            }
            return d == fraction.d;
        }

        @Override
        public int hashCode() {
            int result = (int) (n ^ (n >>> 32));
            result = 31 * result + (int) (d ^ (d >>> 32));
            return result;
        }

        @Override
        public int compareTo(Fraction o) {
            long diff = n * o.d - o.n * d;
            if (diff < 0) {
                return -1;
            } else if (diff > 0) {
                return 1;
            }
            return 0;
        }
    }
}
