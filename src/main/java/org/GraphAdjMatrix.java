package org;

public class GraphAdjMatrix implements GraphADT {
    private final int maxV;
    private final boolean[] present;
    private final int[][] mat;

    public GraphAdjMatrix(int maxVertices) {
        this.maxV = maxVertices;
        this.present = new boolean[maxVertices];
        this.mat = new int[maxVertices][maxVertices]; // 0 = no hay arista
    }


    public SetADT getVertxs() {
        // Si no tenés implementación de SetADT, podés devolver un SetADT propio simple.
        // Para no tocar tus TDAs, devuelvo null por ahora.
        return null;
    }


    public void addVertx(int v) {
        if (valid(v)) present[v] = true;
    }


    public void removeVertx(int v) {
        if (!valid(v)) return;
        present[v] = false;
        for (int i = 0; i < maxV; i++) { mat[v][i] = 0; mat[i][v] = 0; }
    }


    public void addEdge(int a, int b, int w) {
        if (valid(a) && valid(b) && present[a] && present[b] && w > 0) mat[a][b] = w;
    }


    public void removeEdge(int a, int b) {
        if (valid(a) && valid(b)) mat[a][b] = 0;
    }


    public boolean existsEdge(int a, int b) {
        return valid(a) && valid(b) && mat[a][b] > 0;
    }


    public int edgeWeight(int a, int b) {
        return valid(a) && valid(b) ? mat[a][b] : 0;
    }


    public boolean isEmpty() {
        for (int i = 0; i < maxV; i++) if (present[i]) return false;
        return true;
    }

    private boolean valid(int v) { return v >= 0 && v < maxV; }
}
