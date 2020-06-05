import java.util.*;
import java.math.*;
import static java.lang.Math.*;

public class ABBAReplace {
	
	public int countSteps(String Sprefix, int N, int seed, int threshold) {
		long MOD = 1L << 31;
		long state = seed;

		StringBuilder s = new StringBuilder(Sprefix);
		while (s.length() < N) {
			state = (state * 1103515245 + 12345) % MOD;
			if (state < threshold) {
				s.append('A');
			} else {
				s.append('B');
			}
		}

		return solve(s.toString());
	}

	private int solve(String s) {
		int firstA = s.indexOf('A');
		int lastB = s.lastIndexOf('B');
		if (firstA < 0) {
			return 0;
		}

		int a = 0;
		int steps = 0;
		for (int i = lastB-1; i >= firstA; --i) {
			if (s.charAt(i) == 'A') {
				steps++;
				a++;
			} else {
				if (a > 0) {
					a--;
				} else {
					steps++;
				}
			}
		}
		return steps;
	}
}
