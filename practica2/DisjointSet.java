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

    private int[] padre; // padre de cada nodo
    private int[] rango; // altura de cada arbol
    private int[] tam; // tamaño del conjunto representado
    private int[] idRepresentante; // id del padre representante de cada grumo
    private int next; // siguiente pos libre en array

    public DisjointSet(int capacidad) {
        this.padre = new int[capacidad];
        this.rango = new int[capacidad];
        this.tam = new int[capacidad];
        this.idRepresentante = new int[capacidad];
        this.next = 0;
    }

    /**
     * Getter del tamaño del conjunto
     */
    public int getTam(int i) {
        int raiz = find(i);
        return tam[raiz];
    }

    /**
     * Getter de la id real del usuario representante del grumo
     */
    public int getIdRepresentante(int i) {
        int raiz = find(i);
        return idRepresentante[raiz];
    }

    /**
     * Devuelve el numero de nodos agregados en total.
     */
    public int numNodos() {
        return next;
    }

    /**
     * Busca la raiz del conjunto al que pertenece i.
     * 
     * @param i : indice del nodo
     * @return raiz (representante del conjunto)
     */
    public int find(int i) {
        if (padre[i] != i) {
            padre[i] = find(padre[i]);
        }
        return padre[i];
    }

    /**
     * Une los conjuntos a los que pertenecen los nodos iA y iB.
     * 
     * @param iA : indice nodo 1
     * @param iB : indice nodo 2
     */
    public void union(int iA, int iB) {
        int raizA = find(iA);
        int raizB = find(iB);

        if (raizA == raizB) { // comprueba si pertenecen al mismo nodo
            return;
        }

        // decidimos que arbol se une a cual mediante su rango
        if (rango[raizA] < rango[raizB]) { // A se cuelga de B
            padre[raizA] = raizB;
            tam[raizB] += tam[raizA];

        } else if (rango[raizA] > rango[raizB]) { // B se cuelga de A
            padre[raizB] = raizA;
            tam[raizA] += tam[raizB];

        } else { // caso mismo rango: elegimos B se cuelga de A
            padre[raizB] = raizA;
            tam[raizA]++;
            tam[raizA] += tam[raizB];

        }
    }

    /**
     * Agrega un nuevo usuario al conjunto asignandole un
     * indice interno consecutivo a los del array de la estructura.
     * Este se inicializa como un elemento independiente .
     * 
     * @param idUsuario : id real del usuario
     * @return indice inerno asignado al usuario
     */
    public int agregar(int idUsuario) {
        int x = next;

        padre[x] = x;
        rango[x] = 0;
        tam[x] = 1;
        idRepresentante[x] = idUsuario;

        next++;
        return x;
    }
}
