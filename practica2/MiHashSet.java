// Tabla de dispersión abierta que almacena pares clave-valor
public class MiHashSet<K> {

    // Clase interna que representa un
    // nodo de una lista simplemente enlazada
    private class Nodo<K> {
        K clave;
        Nodo<K> sig;

        Nodo(K clave, Nodo<K> sig) {
            this.clave = clave;
            this.sig = sig;
        }
    }

    int m; // Capacidad de la tabla
    int n; // Número de elementos
    double maxL; // Máximo factor de carga

    // Tabla de dispersión (array de listas de pares)
    Nodo<K>[] tabla;

    // Constructor con valores por defecto
    public MiHashSet() {
        this(16, 2.5);
    }

    // Constructor: m0 - capacidad inicial
    // maxL - factor de carga máximo
    public MiHashSet(int m0, double maxL) {
        this.maxL = maxL;
        this.m = m0;
        tabla = new Nodo[m];
        for (int i = 0; i < m; i++) {
            tabla[i] = null;
        }
        this.n = 0;
    }

    // Devuelve el índice correspondiente a esa clave
    protected int indice(K c) {
        return Math.abs(c.hashCode()) % m;
    }

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

    public K get(K clave) {
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Buscar en la lista i-ésima
        Nodo<K> p = tabla[i];
        while (p != null && !p.clave.equals(clave)) {
            p = p.sig;
        }
        return (p == null) ? null : p.clave;
    }

    public void ins(K clave) {
        // Incrementar n y comprobar factor de carga
        n++;
        if ((1.0 * n) / m > maxL) {
            reestructurar();
        }
        // Aplicar función de dispersión a la clave
        int i = indice(clave);
        // Insertar al principio de la lista i-ésima
        tabla[i] = new Nodo(clave,tabla[i]);
    }

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
}
