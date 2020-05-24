package main;

import net.egork.chelper.tester.Verdict;

import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class TestInteractor extends TaskInteractor {

    private int MAX_QUERIES = 300;

    private Random r = new Random(System.currentTimeMillis());

    private int T = 100;
    private int A = (int) 1e9 / 2;
    private int B = (int) 1e9;
    private int M = (int) 1e9;

    private int radius, x, y;
    private int queries = 0;

    private void reset() {
        queries = 0;
        radius = A + r.nextInt(B-A);
        x = r.nextInt(M - radius) * (r.nextInt(2) == 0 ? 1 : -1);
        y = r.nextInt(M - radius) * (r.nextInt(2) == 0 ? 1 : -1);
        System.err.println(String.format("C(%d, %d), R(%d)", x, y, radius));
    }

    @Override
    protected void setInitialState(Scanner in, PrintWriter out) {
        out.println(T);
        out.println(A);
        out.println(B);
        out.flush();
        reset();
    }

    @Override
    protected String reply(String ask) {
        ++queries;
        if (queries > MAX_QUERIES) {
            return "_LIMIT";
        }
        String[] nums = ask.split("\\s");
        long x1 = Long.parseLong(nums[0]);
        long y1 = Long.parseLong(nums[1]);
        if (x1 == x && y1 == y) {
            reset();
            return "_CENTER";
        }
        return inCircle(x1, y1) ? "HIT" : "MISS";
    }

    @Override
    protected Verdict checkAnswer(String result) {
        return result.equals("CENTER") ? Verdict.OK : Verdict.WA;
    }

    private boolean inCircle(long a, long b) {
        long dx = a - x;
        long dy = b - y;
        return dx * dx + dy * dy <= (long) radius * radius;
    }
}
