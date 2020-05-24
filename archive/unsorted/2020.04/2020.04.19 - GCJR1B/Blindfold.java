package main;



import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.function.Function;

public class Blindfold {
    private static int MAX_VAL = (int) 1e9;

    static class Point {
        long x, y;
        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private Scanner in;
    private PrintWriter out;
    boolean done = false;

    public void solve(int testNumber, Scanner in, PrintWriter out) {
        this.in = in;
        this.out = out;

        int T = in.nextInt();
        int A = in.nextInt();
        int B = in.nextInt();

        for (int i = 0; i < T; i++) {
            q = 1;
            done = false;
            System.err.println("Finding HIT point..");
            Point p = findInsidePoint();
            findCenter(p);
        }
    }

    private void findCenter(Point p) {
        long minX = -findEdge(-p.x, MAX_VAL, x -> ask(-x, p.y));
        long maxX = findEdge(p.x, MAX_VAL, x -> ask(x, p.y));
        long minY = -findEdge(-p.y, MAX_VAL, y -> ask(p.x, -y));
        long maxY = findEdge(p.y, MAX_VAL, y -> ask(p.x, y));

        long cx = (minX + maxX) / 2, cy = (minY + maxY) / 2;
        ask(cx, cy);
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1 ; dy <= 1; ++dy) {
                String reply = ask(cx + dx, cy + dy);
                if (reply.equals("CENTER")) {
                    break;
                }
            }
        }
    }

    private Point findInsidePoint() {
        String reply = "MISS";
        int x = -5, y = 17;
        while (reply.equals("MISS")) {
            x = getRandom();
            y = getRandom();
            reply = ask(x, y);
        }
        System.err.println(String.format("Point found: %d, %d", x, y));
        return new Point(x, y);
    }

    Random random = new Random(System.currentTimeMillis());
    private int getRandom() {
        return random.nextInt(MAX_VAL/4) * (random.nextInt(2) == 1 ? 1 : -1);
    }

    private long findEdge(long left, long right, Function<Long, String> query) {
        while (left < right) {
            long mid = (left + right) / 2;
            if (query.apply(mid).equals("MISS")) {
                right = mid - 1;
            } else {
                if (left == mid) {
                    break;
                }
                left = mid;
            }
        }
        return left;
    }

    int q = 1;
    private String ask(long x, long y) {
        if (done) {
            return "CENTER";
        }
//        System.err.print(String.format("[%d] Ask(%d, %d): ", q++, x, y));
        out.println(String.format("%d %d", x, y));
        out.flush();
        String reply = in.next();
//        System.err.println(reply);
        if (reply.equals("CENTER")) {
            done = true;
        }
        if (reply.equals("WRONG")) {
            System.exit(0);
        }
        return reply;
    }
}
