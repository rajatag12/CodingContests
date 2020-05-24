package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Graph {
    private final int size;
    private final EdgeList[] adj;

    public Graph(int size) {
        this.size = size;
        this.adj = new EdgeList[size];
        for (int i = 0; i < size; i++) {
            adj[i] = new EdgeList();
        }
    }

    public void addEdge(int u, int v) {
        addEdge(u, v, 1);
    }

    public void addEdge(int u, int v, int w) {
        adj[u].add(new Edge(u, v, w));
    }

    public void addEdge(Edge edge) {
        adj[edge.from].add(edge);
    }

    public List<? extends Edge> getEdges(int u) {
        return adj[u].get();
    }

    public int getSize() {
        return size;
    }

    public long[][] toAdj(boolean assignZeroToSelf, long inf) {
        long[][] res = new long[size][size];
        if (inf != 0) {
            for (long[] r : res) {
                Arrays.fill(r, inf);
            }
        }
        if (assignZeroToSelf) {
            for (int i = 0; i < size; i++) {
                res[i][i] = 0;
            }
        }
        for (int i = 0; i < size; i++) {
            for (Edge e : adj[i].get()) {
                res[i][e.to] = e.w;
            }
        }
        return res;
    }

    static class Edge implements Comparable<Edge> {
        int from, to;
        long w;

        private Comparator<Edge> comparator;

        public Edge(int from, int to, int w) {
            this.from = from;
            this.to = to;
            this.w = w;
        }

        public void useComparator(Comparator<Edge> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compareTo(Edge edge) {
            if (comparator != null) {
                return comparator.compare(this, edge);
            }
            return Long.compare(w, edge.w);
        }
    }

    static class EdgeList {
        final List<Edge> edges = new ArrayList<>();
        void add(Edge e) {
            edges.add(e);
        }
        List<Edge> get() {
            return edges;
        }
    }
}
