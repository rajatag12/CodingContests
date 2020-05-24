package test.on2020_05.on2020_05_09_Single_Round_Match_785.EllysPalMul;



public class EllysPalMul {
    private long LIM = 1000000000;
    private int x;
    public int getMin(int X) {
        if (isPalindrome(X)) {
            return 1;
        }
        this.x = X;
        int m10 = 1;
        for (int l = 1; l <= 14; ++l) {
            if (l > 2 && l % 2 == 1) {
                m10 *= 10;
            }
            for (int outer = 1; outer <= 9; ++outer) {
                int res;
                if (l <= 2) {
                    res = find(l, outer);
                    if (res > 0) {
                        return res;
                    } else if (res == -2) {
                        return -1;
                    }
                } else {
                    for (int i = 0; i < m10; ++i) {
                        int half = outer * m10 + i;
                        res = find(l, half);
                        if (res > 0) {
                            return res;
                        } else if (res == -2) {
                            return -1;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private int find(int len, int half) {
        long left = half;
        if (len % 2 == 1) {
            left /= 10;
        }
        long n = half;
        while (left > 0) {
            n = n * 10 + left % 10;
            left /= 10;
        }
        if (x * LIM < n) {
            return -2;
        }
        if (n % x == 0) {
            return (int) (n / x);
        }
        return -1;
    }

    private boolean isPalindrome(long x) {
        long t = x;
        long rev = 0;
        while (t > 0) {
            rev = rev * 10 + t % 10;
            t /= 10;
        }
        return x == rev;
    }
}
