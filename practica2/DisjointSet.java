/**
 * Clase de la estructura Disjoint Set (o Union-find).
 * 
 * @author Mario San José de Prado (K7)
 * @author Abel López Santiago (K7)
 */
public class DisjointSet {
    private int[] padre;
    private int[] altura;

    /**
     * Constructor de la estructura DisjointSet a partir de n elementos.
     * 
     * @param n <- Numero de elementos
     */
    public DisjointSet(int n) {
        padre = new int[n];
        altura = new int[n];

        for (int i = 0; i < n; i++) {
            padre[i] = i;
            altura[i] = 0;

        }
    }

    /**
     * Encuentra la raiz del conjunto al que pertenece x.
     * 
     * @param x <- Elemento del cual se busca la raiz
     * @return Id de la raiz
     */
    public int find(int x) {
        if (padre[x] != x) {
            padre[x] = find(padre[x]);
        }

        return padre[x];
    }

    /**
     * Une los conjuntos a los que pertenecen los elementos x e y
     * 
     * @param x
     * @param y
     */
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

    /**
     * Determina si dos elementos pertenecen al mismo grumo.
     * 
     * @param x <- Primer elemento
     * @param y <- Segundo elemento
     * @return true si pertenecen al mismo grupo, false en caso contrario
     */
    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    /**
     * Imprime en consola el contenido de los arrays internos,
     * mostrando para cada nodo su padre y altura.
     */
    public void printPadres() {
        for (int i = 0; i < padre.length; i++) {
            System.out.println(i + " padre -> " + padre[i] + " altura: " + altura[i]);
        }
    }
}
