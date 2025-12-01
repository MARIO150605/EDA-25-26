import java.util.ArrayList;

/**
 * Implementacion de un conjunto basado en tabla hash con dispersion abierta
 * 
 * @author Mario San José de Prado (K7)
 * @author Abel López Santiago (K7)
 */
public class MiHashSet<K> {

    // Clase interna que representa un nodo de una lista simplemente enlazada
    private class Nodo<K> {
        K clave;
        Nodo<K> sig;

        Nodo(K clave, Nodo<K> sig) {
            this.clave = clave;
            this.sig = sig;
        }
    }

    int m; // Capacidad de la tabla
    int n; // Numero de elementos
    double maxL; // Maximo factor de carga

    // Tabla de dispersión (array de listas de pares)
    Nodo<K>[] tabla;

    /**
     * Constructor de un mapa con capacidad y factor de carga predefinidos
     */
    public MiHashSet() {
        this(16, 2.5);
    }

    /**
     * Constructor de un mapa con capacidad y factor de carga dados.
     * 
     * @param m0   - capacidad inicial
     * @param maxL - factor de carga maximo
     */
    public MiHashSet(int m0, double maxL) {
        this.maxL = maxL;
        this.m = m0;
        tabla = new Nodo[m];
        for (int i = 0; i < m; i++) {
            tabla[i] = null;
        }
        this.n = 0;
    }

    /**
     * Devuelve el indice hash para una clave
     * 
     * @param c clave
     * @return indice de la tabla
     */
    protected int indice(K c) {
        return Math.abs(c.hashCode()) % m;
    }

    /**
     * Duplica la capacidad de la tabla y reubica los elementos
     */
    protected void reestructurar() {
        // Salvamos la tabla anterior
        Nodo<K>[] tmp = tabla;
        // Creamos una nueva tabla
        n = 0;
        m = 2 * m; // Duplicamos el tamaño
        tabla = new Nodo[m];
        for (int i = 0; i < m; i++) {
            tabla[i] = null;
        }
        // Recorremos la tabla anterior insertando elementos
        for (int i = 0; i < tmp.length; i++) {
            Nodo<K> nodo = tmp[i];
            while (nodo != null) {
                ins(nodo.clave);
                nodo = nodo.sig;
            }
        }
    }

    /**
     * Indica si una clave pertenece al conjunto
     * 
     * @param clave
     * @return true si pertene (false en caso contrario)
     */
    public boolean contains(K clave) {
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Buscar en la lista i-ésima
        Nodo<K> p = tabla[i];
        while (p != null && !p.clave.equals(clave)) {
            p = p.sig;
        }
        return (p == null) ? false : true;
    }

    /**
     * Inserta una clave en un conjunto
     * 
     * @param clave
     * @return true (si ya existe devuelve false)
     */
    public boolean ins(K clave) {
        // Incrementar n y comprobar factor de carga

        if (contains(clave)) {
            return false;
        }

        n++;
        if ((1.0 * n) / m > maxL) {
            reestructurar();
        }
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Insertar al principio de la lista i-ésima
        tabla[i] = new Nodo(clave, tabla[i]);

        return true;
    }

    /**
     * Clave a eliminar
     * 
     * @param clave
     * @return true (si no existe devuelve false)
     */
    public boolean del(K clave) {
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Buscar nodo controlando elemento anterior
        Nodo<K> ant = null;
        Nodo<K> act = tabla[i];
        while (act != null && !act.clave.equals(clave)) {
            ant = act;
            act = act.sig;
        }
        if (act == null) {
            return false;
        }
        // Comprobar caso especial borrado del primero
        if (ant == null) {
            tabla[i] = act.sig;
        } else {
            ant.sig = act.sig;
        }
        n--;
        return true;
    }

    // ------------- Metodos conjunto ------

    /**
     * Numero de elementos del conjunto
     * 
     * @return tamaño
     */
    public int size() {
        return n;
    }

    /**
     * Devuelve todas las claves del conjunto
     * 
     * @return arraylist de claves
     */
    public ArrayList<K> keySet() {
        ArrayList<K> res = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            Nodo<K> nodo = tabla[i];

            while (nodo != null) {
                res.add(nodo.clave);
                nodo = nodo.sig;
            }

        }

        return res;
    }
}
