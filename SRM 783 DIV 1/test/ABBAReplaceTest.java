import org.junit.Test;
import static org.junit.Assert.*;

public class ABBAReplaceTest {
	
	@Test(timeout=2000)
	public void test0() {
		String Sprefix = "AABABB";
		int N = 6;
		int seed = 0;
		int threshold = 0;
		assertEquals(4, new ABBAReplace().countSteps(Sprefix, N, seed, threshold));
	}
	
	@Test(timeout=2000)
	public void test1() {
		String Sprefix = "";
		int N = 0;
		int seed = 4;
		int threshold = 7;
		assertEquals(0, new ABBAReplace().countSteps(Sprefix, N, seed, threshold));
	}
	
	@Test(timeout=2000)
	public void test2() {
		String Sprefix = "ABBABAABABBBABBBB";
		int N = 17;
		int seed = 0;
		int threshold = 0;
		assertEquals(11, new ABBAReplace().countSteps(Sprefix, N, seed, threshold));
	}
	
	@Test(timeout=2000)
	public void test3() {
		String Sprefix = "AABAA";
		int N = 17;
		int seed = 47474747;
		int threshold = 1000000000;
		assertEquals(10, new ABBAReplace().countSteps(Sprefix, N, seed, threshold));
	}
}
