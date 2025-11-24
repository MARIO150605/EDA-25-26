/*
 * Estructuras de Datos y Algoritmos 
 * Practica 2
 * 
 * Mario San José de Prado (K7)
 * Abel López Santiago (K7)
 */

/**
 * Clase de la estructura Disjoint Set (o Union-find).
 */

public class DisjointSet {
    private int[] padre;
    private int[] altura;

    public DisjointSet(int n) {
        padre = new int[n];
        altura = new int[n];

        for (int i = 0; i < n; i++) {
            padre[i] = i;
            altura[i] = 0;

        }
    }

    public int find(int x) {
        if (padre[x] != x) {
            padre[x] = find(padre[x]);
        }

        return padre[x];
    }

    public void union(int x, int y) {
        int raizx = find(x);
        int raizy = find(y);

        if (raizx == raizy) {
            return;
        }

        if (altura[raizx] < altura[raizy]) {
            padre[raizx] = raizy;
        } else if (altura[raizx] > altura[raizy]) {
            padre[raizy] = raizx;
        } else {
            padre[raizy] = raizx;
            altura[raizx]++;
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public void printPadres() {
        for (int i = 0; i < padre.length; i++) {
            System.out.println(i + " padre -> " + padre[i] + " altura: " + altura[i]);
        }
    }
}
