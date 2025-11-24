import java.util.*;

public class DisjointSetAux {
    private Map<Integer, Integer> mapeo; // nodo -> id
    private Map<Integer, Integer> mapeoInverso; // id -> nodo
    private Set<Integer> usuarios;
    private DisjointSet ds;
    private Map<Integer, ArrayList<Integer>> grumos; // raiz y grumo asociado

    public int getNumUsuarios() {
        return usuarios.size();
    }

    public int getNumGrumos() {
        return grumos.size();
    }

    public DisjointSetAux(ArrayList<Conexion> conexiones) {
        mapeo = new HashMap<>();
        mapeoInverso = new HashMap<>();

        crearUsuarios(conexiones);

        int next = 0;
        for (Integer u : usuarios) {
            mapeo.put(u, next);
            mapeoInverso.put(next, u);
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

    public void crearUsuarios(ArrayList<Conexion> conexiones) {
        usuarios = new HashSet<>();
        for (Conexion c : conexiones) {
            usuarios.add(c.getc1());
            usuarios.add(c.getc2());
        }
    }

    public ArrayList<ArrayList<Integer>> crearGrumos() {
        int id, raiz;
        grumos = new HashMap<>();
        for (Integer u : mapeo.keySet()) {
            id = mapeo.get(u);
            raiz = ds.find(id);
            if (!grumos.containsKey(raiz)) {
                grumos.put(raiz, new ArrayList<Integer>());
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