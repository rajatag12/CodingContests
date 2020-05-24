package test.on2020_04.on2020_04_21_GCJRound1B.CardRanking;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("src/test/on2020_04/on2020_04_21_GCJRound1B/CardRanking/CardRanking.json"))
			Assert.fail();
	}
}
