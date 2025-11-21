import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * Estructuras de Datos y Algoritmos 
 * Practica 1 
 * 
 * Mario San José de Prado (K7)
 * Abel López Santiago (K7)
 */

/**
 * Implementación de la red social.
 * - Sin lista 'asig': se sustituye por usuarioEnGrumo(u)
 * - 'contiene' reemplaza todas las comprobaciones de pertenencia y
 *    además cuenta operaciones elementales en numOperaciones.
 */
public class RedSocial implements IRedSocial {

    // Estructuras principales
    private List<Conexion> red = new ArrayList<>();
    private List<Integer> usr = new ArrayList<>();
    private List<List<Integer>> grus = new ArrayList<>();

    // Nuevas relaciones propuestas en ordenaSelecciona (etapa 5)
    private List<Conexion> nuevasRel = new ArrayList<>();

    // Contador de operaciones elementales (incrementa en 'contiene')
    private long numOperaciones = 0;

    // =================== Getters básicos ===================
    @Override
    public int numUsuarios() {
        return usr.size();
    }

    @Override
    public int numConexiones() {
        return red.size();
    }

    @Override
    public int numGrumos() {
        return grus.size();
    }

    // Para el análisis pedido
    public long getNumOperaciones() {
        return numOperaciones;
    }

    public void resetNumOperaciones() {
        numOperaciones = 0;
    }

    // =================== Entrada de datos ===================
    @Override
    public void leeFichero(String nomfich) throws IOException {
        // Formato: "a b" por línea
        List<String> lineas = Files.readAllLines(Paths.get(nomfich));
        for (String lin : lineas) {
            lin = lin.trim();
            if (lin.isEmpty()) continue;
            String[] t = lin.split("\\s+");
            if (t.length < 2) continue;
            int a = Integer.parseInt(t[0]);
            int b = Integer.parseInt(t[1]);
            red.add(new Conexion(a, b));
        }
    }

    @Override
    public void setRed(List<Conexion> red) {
        // Sustituimos completamente la red y reiniciamos estructuras derivadas
        this.red = new ArrayList<>(red);
        this.usr.clear();
        this.grus.clear();
        this.nuevasRel.clear();
    }

    // =================== Etapa 2: crear usuarios ===================
    @Override
    public void creaUsuarios() {
        usr.clear();
        for (Conexion c : red) {
            int a = c.getc1();
            int b = c.getc2();
            if (!contiene(usr, a)) usr.add(a);
            if (!contiene(usr, b)) usr.add(b);
        }
    }

    // =================== Etapa 3: crear grumos ===================
    @Override
    public void creaGrumos() {
        grus.clear();
        // Recorremos todos los usuarios. Si aún no está en ningún grumo, creamos uno
        for (int u : usr) {
            if (!usuarioEnGrumo(u)) {
                List<Integer> grumo = new ArrayList<>();
                grumo.add(u);
                // DFS clásica (uber_amigos) usando solo 'contiene' para pertenencia
                uberAmigos(u, grumo);
                grus.add(grumo);
            }
        }
    }

    // DFS: añade al 'grumo' todos los usuarios alcanzables desde 'u'
    private void uberAmigos(int u, List<Integer> grumo) {
        // Buscamos conexiones incidentes en u y expandimos
        for (Conexion c : red) {
            int v = -1;
            if (c.getc1() == u) v = c.getc2();
            else if (c.getc2() == u) v = c.getc1();

            if (v != -1) {
                if (!contiene(grumo, v)) {
                    grumo.add(v);
                    uberAmigos(v, grumo);
                }
            }
        }
    }

    /**
     * Sustituto de 'asig': devuelve true si el usuario u ya aparece en
     * alguno de los grumos (búsqueda secuencial sobre grus).
     * Usa 'contiene' para contar operaciones elementales.
     */
    private boolean usuarioEnGrumo(int u) {
        for (List<Integer> g : grus) {
            if (contiene(g, u)) return true;
        }
        return false;
    }

    /**
     * Método que sustituye a TODOS los 'contains' de listas de enteros.
     * Además de devolver si 'x' está en 'lista', incrementa numOperaciones:
     * se suma 1 por cada comparación realizada durante la búsqueda secuencial.
     */
    private boolean contiene(List<Integer> lista, int x) {
        for (int i = 0; i < lista.size(); i++) {
            numOperaciones++;                 // cuenta operación elemental
            if (lista.get(i) == x) return true;
        }
        return false;
    }

    // =================== Etapa 4: ordenar y seleccionar ===================
    @Override
    public void ordenaSelecciona(double pmin) {
        nuevasRel.clear();
        if (grus.isEmpty()) return;

        // Orden descendente por tamaño
        grus.sort((a, b) -> Integer.compare(b.size(), a.size()));

        int total = usr.size();
        int acum = 0;
        int k = 0;
        while (k < grus.size() && (acum * 100.0 / total) < pmin) {
            acum += grus.get(k).size();
            k++;
        }
        // Si hay que unir >1 grumo, generamos k-1 nuevas relaciones
        for (int i = 0; i + 1 < k; i++) {
            int u1 = (grus.get(i).size() >= 2 ? grus.get(i).get(1) : grus.get(i).get(0));
            int u2 = grus.get(i + 1).get(0);
            nuevasRel.add(new Conexion(u1, u2));
        }
    }

    // =================== Etapa 5: salvar nuevas relaciones ===================
    @Override
    public void salvaNuevasRel(String nomfich) throws IOException {
        List<String> out = nuevasRel.stream()
                .map(Conexion::toString)
                .collect(Collectors.toList());
        Files.write(Paths.get(nomfich), out);
    }

    // =================== Informe simplicado ===================
    @Override
    public void informe() {
        System.out.println(numUsuarios() + " usuarios, " + numConexiones() + " conexiones");
        System.out.println("Existen " + numGrumos() + " grumos.");
        if (!nuevasRel.isEmpty()) {
            System.out.println("Nuevas relaciones de amistad:");
            for (Conexion c : nuevasRel) System.out.println(c);
        } else {
            System.out.println("No son necesarias nuevas relaciones de amistad");
        }
    }

    // ========= Generador de casos (idéntico al del enunciado, adaptado a esta clase) =========
    /**
     * Genera un caso de prueba de tamaño n (n usuarios) con ~√n grumos.
     * Usa la clase Conexion de este proyecto.
     */
    public List<Conexion> generaCaso(int n, Random rnd) {
        // Usuarios únicos aleatorios
        HashSet<Integer> husr = new HashSet<>();
        while (husr.size() < n) {
            husr.add(rnd.nextInt(90000000) + 1000000);
        }
        final Integer[] usuarios = husr.toArray(new Integer[0]);

        // √n grumos como rangos de índices
        int[] inds = rnd.ints((int) Math.sqrt(n))
                .map(i -> Math.abs(i) % (n - 1) + 1)
                .sorted()
                .distinct()
                .toArray();
        inds[inds.length - 1] = n;

        HashSet<Conexion> redSet = new HashSet<>();
        int i0 = 0;
        for (int i1 : inds) {
            if (i1 < i0 + 2) {
                continue;
            }
            // Conexiones circulares
            redSet.addAll(IntStream.range(i0, i1 - 1)
                    .mapToObj(i -> new Conexion(usuarios[i], usuarios[i + 1]))
                    .collect(Collectors.toList()));
            redSet.add(new Conexion(usuarios[i1 - 1], usuarios[i0]));
            // Conexiones aleatorias dentro del grumo
            int ng = 2 * (i1 - i0);
            for (int k = 0; k < ng; k++) {
                int u1 = rnd.nextInt(i1 - i0) + i0;
                int u2 = rnd.nextInt(i1 - i0) + i0;
                if (u1 != u2) {
                    redSet.add(new Conexion(usuarios[u1], usuarios[u2]));
                }
            }
            i0 = i1;
        }
        return new ArrayList<>(redSet);
    }
}

///MAIN
/// 
/// 
/// 
import java.io.IOException;
import java.util.List;
import java.util.Random;

/*
 * Estructuras de Datos y Algoritmos 
 * Practica 1 
 * 
 * Mario San José de Prado (K7)
 * Abel López Santiago (K7)
 */

/**
 * Main para el análisis de numOperaciones vs n usuarios.
 * Imprime líneas CSV listas para pegar en Excel:
 * n,avgNumOperaciones,avgConexiones,avgGrumos
 */
public class Main {
    // Parámetros del muestreo
    static final int N_INICIO = 100;
    static final int N_FIN    = 20000;   // hasta 20.000 como piden
    static final int N_INC    = 1000;    // paso (ajústalo si quieres más puntos)
    static final int N_REP    = 5;       // repeticiones por tamaño
    static final double PMIN  = 90.0;    // no influye apenas en tiempo/ops (enunciado)

    static final Random RND = new Random();

    public static void main(String[] args) throws IOException {
        System.out.println("n,avgNumOperaciones,avgConexiones,avgGrumos");

        for (int n = N_INICIO; n <= N_FIN; n += N_INC) {
            long sumOps = 0L;
            long sumCon = 0L;
            long sumGru = 0L;

            for (int r = 0; r < N_REP; r++) {
                RedSocial Y = new RedSocial();

                // Genera la red sintética y la carga
                List<Conexion> red = Y.generaCaso(n, RND);
                Y.setRed(red);

                // Contador a cero antes de las etapas que usan 'contiene'
                Y.resetNumOperaciones();

                // Etapa 2 y 3 (las que acumulan operaciones de 'contiene')
                Y.creaUsuarios();
                Y.creaGrumos();

                // Etapa 4 (no suma operaciones, pero la hacemos para consistencia)
                Y.ordenaSelecciona(PMIN);

                sumOps += Y.getNumOperaciones();
                sumCon += Y.numConexiones();
                sumGru += Y.numGrumos();
            }

            long avgOps = sumOps / N_REP;
            long avgCon = sumCon / N_REP;
            long avgGru = sumGru / N_REP;

            System.out.println(n + "," + avgOps + "," + avgCon + "," + avgGru);
        }
    }
}
