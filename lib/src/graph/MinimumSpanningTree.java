package graph;

import java.util.PriorityQueue;

public class MinimumSpanningTree {
    private final Graph graph;

    private Graph mst;
    private long edgeTotal = 0;

    public MinimumSpanningTree(Graph graph) {
        this.graph = graph;
        computeMST();
    }

    private void computeMST() {
        int n = graph.getSize();
        mst = new Graph(n);

        boolean[] in = new boolean[n];
        in[0] = true;

        int added = 1;
        PriorityQueue<Graph.Edge> q = new PriorityQueue<>(graph.getEdges(0));

        while (added < n && !q.isEmpty()) {
            Graph.Edge e = q.poll();
            if (in[e.to]) {
                continue;
            }

            in[e.to] = true;
            edgeTotal += e.w;
            added++;
            mst.addEdge(e);

            for (Graph.Edge edge : graph.getEdges(e.to)) {
                if (!in[edge.to]) {
                    q.add(edge);
                }
            }
        }
    }

    public Graph getMST() {
        return mst;
    }

    public long getTotalWeight() {
        return edgeTotal;
    }
}
