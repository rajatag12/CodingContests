package graph;

public class UnionFind {
    private final int size;
    private final int[] id;
    private final int[] groupSize;

    private int numComponents;

    public UnionFind(int size) {
        this.size = size;
        id = new int[size];
        groupSize = new int[size];
        numComponents = size;

        for (int i = 0; i < size; i++) {
            id[i] = i;
            groupSize[i] = 1;
        }
    }

    public int root(int x) {
        int r = x;
        while (r != id[r]) {
            r = id[r];
        }

        while (id[x] != r) {
            x = id[x];
            id[x] = r;
        }

        return r;
    }

    public boolean connected(int a, int b) {
        return root(a) == root(b);
    }

    public void union(int a, int b) {
        int r1 = root(a);
        int r2 = root(b);

        if (groupSize[r1] > groupSize[r2]) {
            int t = r1;
            r1 = r2;
            r2 = t;
        }

        id[r1] = r2;
        groupSize[r1] += groupSize[r2];
        numComponents--;
    }

    public int getNumComponents() {
        return numComponents;
    }
}
