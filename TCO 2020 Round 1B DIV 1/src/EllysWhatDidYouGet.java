public class EllysWhatDidYouGet {
	
	public int getCount(int N) {
		int res = 0;
		for (int x = 1; x <= 50; ++x) {
			for (int y = 1; y <= 50; ++y) {
				for (int z = 1; z <= 50; ++z) {
					res++;
					int ans = compute(1, x, y, z);
					for (int i = 2; i <= N; ++i) {
						if (compute(i, x, y, z) != ans) {
							res--;
							break;
						}
					}
				}
			}
		}
		return res;
	}

	private int compute(int n, int x, int y, int z) {
		n *= x;
		n += y;
		n *= z;
		int sum = 0;
		while (n > 0) {
			sum += n % 10;
			n /= 10;
		}
		return sum;
	}
}
