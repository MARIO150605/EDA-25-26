import java.util.ArrayList;

/**
 * Tabla de dispersion abiaerta que almacena pares clave-valor.
 * 
 * @author Mario San José de Prado (K7)
 * @author Abel López Santiago (K7)
 */
public class MiHashMap<K, V> {

    // Clase interna que representa un nodo de una lista simplemente enlazada
    private class Nodo<K, V> {
        K clave;
        V valor;
        Nodo<K, V> sig;

        Nodo(K clave, V valor, Nodo<K, V> sig) {
            this.clave = clave;
            this.valor = valor;
            this.sig = sig;
        }
    }

    int m; // Capacidad de la tabla
    int n; // Numero de elementos
    double maxL; // Maximo factor de carga

    // Tabla de dispersión (array de listas de pares)
    Nodo<K, V>[] tabla;

    /**
     * Constructor de un mapa con capacidad y factor de carga predefinidos
     */
    public MiHashMap() {
        this(16, 2.5);
    }

    /**
     * Constructor de un mapa con capacidad y factor de carga dados.
     * 
     * @param m0   - capacidad inicial
     * @param maxL - factor de carga maximo
     */
    public MiHashMap(int m0, double maxL) {
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
        Nodo<K, V>[] tmp = tabla;
        // Creamos una nueva tabla
        n = 0;
        m = 2 * m; // Duplicamos el tamaño
        tabla = new Nodo[m];
        for (int i = 0; i < m; i++) {
            tabla[i] = null;
        }
        // Recorremos la tabla anterior insertando elementos
        for (int i = 0; i < tmp.length; i++) {
            Nodo<K, V> nodo = tmp[i];
            while (nodo != null) {
                ins(nodo.clave, nodo.valor);
                nodo = nodo.sig;
            }
        }
    }

    /**
     * Obtiene el valor asociado a una clase
     * 
     * @param clave
     * @return valor (sino existe devuelve null)
     */
    public V get(K clave) {
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Buscar en la lista i-ésima
        Nodo<K, V> p = tabla[i];
        while (p != null && !p.clave.equals(clave)) {
            p = p.sig;
        }
        return (p == null) ? null : p.valor;
    }

    /**
     * Inserta un par clave-valor en la tabla
     * 
     * @param clave
     * @param valor
     */
    public void ins(K clave, V valor) {
        // Incrementar n y comprobar factor de carga
        n++;
        if ((1.0 * n) / m > maxL) {
            reestructurar();
        }
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Insertar al principio de la lista i-ésima
        tabla[i] = new Nodo(clave, valor, tabla[i]);
    }

    /**
     * Elimina la entrada asociada a una clave
     * 
     * @param clave clave a eliminar
     * @return true (false si no existe)
     */
    public boolean del(K clave) {
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Buscar nodo controlando elemento anterior
        Nodo<K, V> ant = null;
        Nodo<K, V> act = tabla[i];
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

    // --------- Metodos mapa -------------

    /**
     * Devuelve el numero de elementos
     * 
     * @return cantidad pares clave-valor
     */
    public int size() {
        return n;
    }

    /**
     * Conjunto de claves contenidas en el mapa
     * 
     * @return arraylist con las claves
     */
    public ArrayList<K> keySet() {
        ArrayList<K> res = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            Nodo<K, V> nodo = tabla[i];

            while (nodo != null) {
                res.add(nodo.clave);
                nodo = nodo.sig;
            }

        }

        return res;
    }

    /**
     * Conjunto de valores contenidos en el mapa
     * 
     * @return arraylist con los valores
     */
    public ArrayList<V> values() {
        ArrayList<V> res = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            Nodo<K, V> nodo = tabla[i];

            while (nodo != null) {
                res.add(nodo.valor);
                nodo = nodo.sig;
            }

        }

        return res;
    }

    /**
     * Indica si la clave esiste
     * 
     * @param clave
     * @return true (false sino existe)
     */
    public boolean containsKey(K clave) {
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Buscar en la lista i-ésima
        Nodo<K, V> p = tabla[i];
        while (p != null && !p.clave.equals(clave)) {
            p = p.sig;
        }
        return (p == null) ? false : true;
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < m; i++) {
            Nodo<K, V> p = tabla[i];

            while (p != null) {
                res += p.clave + " " + p.valor + "\n";
                p = p.sig;
            }
        }

        return res;

    }
}