package graph;

public class MaxFlow {
    private final Graph graph;
    private final int source;
    private final int sink;

    public MaxFlow(Graph graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;
    }

    public long getMaxFlow() {
        return 0;
    }

    static class FlowEdge {
        private final Graph.Edge e;
        private long capacity;
        private long flow;

        private FlowEdge residualEdge;
    }
}
