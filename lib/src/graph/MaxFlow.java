package graph;

import java.util.Arrays;
import java.util.LinkedList;

// Complexity:
//   Runtime: O(V^2 * E)
//   Space: V^2
// Implementation:
//   Edmund Karp's
// Works on:
//   Directed Graph
public class MaxFlow {
    private final long[][] f;
    private final int source;
    private final int sink;

    private long res = -1;

    public MaxFlow(Graph graph, int source, int sink) {
        this.f = graph.toAdj(true, 0);

        this.source = source;
        this.sink = sink;
    }

    public long getMaxFlow() {
        if (res > -1) {
            return res;
        }

        Bfs bfs = new Bfs(f);
        while  (true) {
            int[] path = bfs.findPath(source, sink);
            if (path == null) {
                break;
            }

            int v = sink;
            long flow = Long.MAX_VALUE;
            while (v != source) {
                int u = path[v];
                flow = Math.min(flow, f[u][v]);
                v = u;
            }

            v = sink;
            while (v != source) {
                int u = path[v];
                f[u][v] -= flow;
                f[v][u] += flow;
                v = u;
            }

            res += flow;
        }

        return res;
    }

    static class Bfs {
        int n;
        long[][] g;

        int round = 0;
        int[] visited;
        int[] parent;

        Bfs(long[][] g) {
            this.n = g.length;
            this.g = g;
            this.visited = new int[n];
            this.parent = new int[n];
        }

        void reset() {
            round++;
            Arrays.fill(parent, -1);
        }

        int[] findPath(int src, int dest) {
            reset();
            if (find(src, dest)) {
                return parent;
            }
            return null;
        }

        boolean find(int s, int d) {
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(s);
            visited[s] = round;
            parent[s] = -1;

            // Standard BFS Loop
            while (!queue.isEmpty())
            {
                int u = queue.poll();

                for (int v = 0; v < n; v++)
                {
                    if (visited[v] == round && g[u][v] > 0)
                    {
                        queue.add(v);
                        parent[v] = u;
                        visited[v] = round;
                    }
                }
            }

            return visited[d] == round;
        }
    }
}
