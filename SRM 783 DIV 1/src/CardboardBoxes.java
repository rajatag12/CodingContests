import java.util.*;
import java.math.*;
import static java.lang.Math.*;

public class CardboardBoxes {
	
	public long count(long S) {
		if (S % 2 != 0) {
			return 0;
		}
		long s = S/2;
		// H*(L+W) + W*(L+W)
		// (H+W) * (L+W)
		long res = 0;
		for (long hw = 2; hw * hw <= s; ++hw) {
			if (s % hw != 0) {
				continue;
			}
			long lw = s / hw;
			res += get(lw, hw);
			if (lw != hw) {
				res += get(hw, lw);
			}
		}

		return res;
	}

	private long get(long lw, long hw) {
		long w = Math.min(hw-1, lw-1);
		return Math.min(w, lw/2);
	}
}
