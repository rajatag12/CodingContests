package test.on2020_05.on2020_05_02_GCJRound1C.OverexcitedFan;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("src/test/on2020_05/on2020_05_02_GCJRound1C/OverexcitedFan/OverexcitedFan.json"))
			Assert.fail();
	}
}
