import org.junit.Test;
import static org.junit.Assert.*;

public class RecursiveTournamentTest {
	
	@Test(timeout=2000)
	public void test0() {
		String[] graph = new String[] {
"NYN",
"NNY",
"YNN"
};
		int k = 2;
		assertEquals(355, new RecursiveTournament().count(graph, k));
	}
	
	@Test(timeout=2000)
	public void test1() {
		String[] graph = new String[] {
"NYY",
"NNY",
"NNN"
};
		int k = 2;
		assertEquals(9, new RecursiveTournament().count(graph, k));
	}
	
	@Test(timeout=2000)
	public void test2() {
		String[] graph = new String[] {
"NYN",
"NNY",
"YNN"
};
		int k = 1;
		assertEquals(4, new RecursiveTournament().count(graph, k));
	}
	
	@Test(timeout=2000)
	public void test3() {
		String[] graph = new String[] {
"NYYYNNYNNYYNNYYYYYNNYNN",
"NNYNYYNYYNNNYNYNYNNNNYY",
"NNNNYNNNNYNYYNYYNYYNYNN",
"NYYNYNNNYNNYYYNYYYNNYYN",
"YNNNNNYYNYNNYYNNYNYNYYY",
"YNYYYNNNYNYNYNYNNYNYNYN",
"NYYYNYNNNYYNNNNYNNNNYNN",
"YNYYNYYNYYYNYNYYYNYNNYY",
"YNYNYNYNNYYYNYNYNYYYNYN",
"NYNYNYNNNNYNYYYNNYYYNYN",
"NYYYYNNNNNNNNYNYYNNYNYN",
"YYNNYYYYNYYNNYYYYNYYYNY",
"YNNNNNYNYNYYNNNNYNYNYNY",
"NYYNNYYYNNNNYNYNNNNYNYN",
"NNNYYNYNYNYNYNNYNNNYNNY",
"NYNNYYNNNYNNYYNNYNYYNYN",
"NNYNNYYNYYNNNYYNNNYNNNN",
"NYNNYNYYNNYYYYYYYNYYYNN",
"YYNYNYYNNNYNNYYNNNNNYNY",
"YYYYYNYYNNNNYNNNYNYNYNN",
"NYNNNYNYYYYNNYYYYNNNNNY",
"YNYNNNYNNNNYYNYNYYYYYNY",
"YNYYNYYNYYYNNYNYYYNYNNN"
};
		int k = 1000;
		assertEquals(71573222, new RecursiveTournament().count(graph, k));
	}
}
