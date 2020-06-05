package test.on2020_04.on2020_04_28_GCJInternalGolf.Security1;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("src/test/on2020_04/on2020_04_28_GCJInternalGolf/Security1/Security1.json"))
			Assert.fail();
	}
}
