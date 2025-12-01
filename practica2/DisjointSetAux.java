import java.util.*;

/**
 * Clase auxiliar para el mapeo entre los ids reales y los necesearios en
 * DisjointSet.
 * 
 * @author Mario San José de Prado (K7)
 * @author Abel López Santiago (K7)
 */
public class DisjointSetAux {
    private MiHashMap<Integer, Integer> mapeo; // nodo -> id
    private MiHashMap<Integer, Integer> mapeoInverso; // id -> nodo
    private MiHashSet<Integer> usuarios;
    private DisjointSet ds;
    private MiHashMap<Integer, ArrayList<Integer>> grumos; // raiz y grumo asociado

    /**
     * Constructor de la estructura auxiliar a partir de la lista de conexiones de
     * la red social.
     * 
     * @param conexiones <- Lista de conexiones existentes entre usuarios
     */
    public DisjointSetAux(ArrayList<Conexion> conexiones) {

        crearUsuarios(conexiones);

        mapeo = new MiHashMap<>(usuarios.size(), 2.5); // factor de carga 2.5 por defecto
        mapeoInverso = new MiHashMap<>(usuarios.size(), 2.5);

        int next = 0;
        for (Integer u : usuarios.keySet()) {
            mapeo.ins(u, next);
            mapeoInverso.ins(next, u);
            next++;
        }

        ds = new DisjointSet(usuarios.size());
        for (Conexion c : conexiones) {
            int id1, id2;
            id1 = mapeo.get(c.getc1());
            id2 = mapeo.get(c.getc2());
            ds.union(id1, id2);
        }
    }

    /**
     * Devuelve el numero total de usuarios ocupados
     * 
     * @return Numero de usuarios unicos
     */
    public int getNumUsuarios() {
        return usuarios.size();
    }

    /**
     * Devuelve el numero total de grumos detectados
     * 
     * @return Numero de grumos
     */
    public int getNumGrumos() {
        return grumos.size();
    }

    /**
     * Almacena los usuarios de la lista conexiones en la estructura interna
     * usuarios
     * 
     * @param conexiones <- Lista de conexiones entre usuarios
     */
    public void crearUsuarios(ArrayList<Conexion> conexiones) {

        double fc = 2.5; // factor de carga por defecto 2,5
        int m0 = (int) ((2.0 * conexiones.size()) / 2.5); // tamanio de tabla como máximo 2*numero de conexiones /
                                                          // factor de carga
        usuarios = new MiHashSet<>(m0, fc);
        for (Conexion c : conexiones) {
            usuarios.ins(c.getc1());
            usuarios.ins(c.getc2());
        }
    }

    /**
     * Crea todos los grumos usando la estructura DisjointSet.
     * 
     * @return
     */
    public ArrayList<ArrayList<Integer>> crearGrumos() {
        int id, raiz;
        grumos = new MiHashMap<>(usuarios.size(), 2.5); // factor de carga 2.5 por defecto

        for (Integer u : mapeo.keySet()) {
            id = mapeo.get(u);
            raiz = ds.find(id);
            if (!grumos.containsKey(raiz)) {
                grumos.ins(raiz, new ArrayList<Integer>());
            }
            grumos.get(raiz).add(u);
        }

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (ArrayList<Integer> grumo : grumos.values()) {
            res.add(grumo);
        }

        return res;
    }
}