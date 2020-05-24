package test.on2020_04.on2020_04_10_GCJRound1A.TaskA;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("src/test/on2020_04/on2020_04_10_GCJRound1A/TaskA/TaskA.json"))
			Assert.fail();
	}
}
