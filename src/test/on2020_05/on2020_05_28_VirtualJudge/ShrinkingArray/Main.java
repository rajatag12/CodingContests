package test.on2020_05.on2020_05_28_VirtualJudge.ShrinkingArray;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("src/test/on2020_05/on2020_05_28_VirtualJudge/ShrinkingArray/ShrinkingArray.json"))
			Assert.fail();
	}
}
