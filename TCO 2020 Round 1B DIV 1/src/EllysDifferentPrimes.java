import java.util.*;
import java.math.*;
import static java.lang.Math.*;

public class EllysDifferentPrimes {
	
	public int getClosest(int N) {
		int best = N+N;
		boolean[] p = new boolean[N+N+1];
		Arrays.fill(p, true);
		for (int i = 2; i * i <= N+N; ++i) {
			if (!p[i]) continue;
			for (int j = i * i; j <= N + N; j += i) {
				p[j] = false;
			}
		}
		for (int i = 2; i < N + N; i++) {
			if (!p[i]) continue;
			if (diff(i)) {
				if (Math.abs(best - N) > Math.abs(i - N)) {
					best = i;
				} else if (Math.abs(best - N) <= Math.abs(i - N)) {
					break;
				}
			}
		}
		return best;
	}

	private boolean diff(int p) {
		int bits = 0;
		while (p > 0) {
			int d = p % 10;
			if (((bits >> d) & 1) == 1) {
				return false;
			}
			bits |= (1 << d);
			p /= 10;
		}
		return true;
	}
}
