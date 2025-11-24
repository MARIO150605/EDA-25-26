import java.io.*;
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
 * Clase que implementa la interfaz IRedSocial
 */
public class RedSocialP2 implements IRedSocialP2 {

    ArrayList<Conexion> red = new ArrayList<>();

    ArrayList<Conexion> nuevas = new ArrayList<>(); // array para las nuevas conexiones
    ArrayList<ArrayList<Integer>> grusSeleccionados = new ArrayList<>(); // array para obtener los grumos utilizados
                                                                         // para el porcentaje
    DisjointSetAux dsa;

    /**
     * Funcion que devuelve el numero de usuarios
     * 
     * @return numero de usuarios
     */
    public int numUsuarios() {
        return dsa.getNumUsuarios();
    }

    /**
     * Funcion que devuelve el numero de conexiones
     * 
     * @return numero de conexiones
     */
    public int numConexiones() {
        return red.size();
    }

    /**
     * Funcion que devuelve el numero de grumos
     * 
     * @return numero de grumos
     */
    public int numGrumos() {
        return dsa.getNumGrumos();
    }

    /**
     * Metodo que lee el fichero de conexiones por parametro
     * e introduce las conexiones en la lista de conexiones
     * 
     * @param nomfich ruta al fichero de conexiones
     * 
     */
    public void leeFichero(String nomfich) throws IOException {

        Scanner in = new Scanner(new File(nomfich));
        int u1, u2;

        while (in.hasNextInt()) {
            u1 = in.nextInt();
            u2 = in.nextInt();

            red.add(new Conexion(u1, u2));
        }

        in.close();

    }

    /**
     * Metodo que dada una lista de conexiones externa por parametro
     * actualiza la lista de conexiones de la red
     * 
     * @param red lista de conexiones externa
     */
    public void setRed(List<Conexion> red) {

        this.red = new ArrayList<>(red);

    }

    /**
     * Metodo que ordena la lista de grumos de mayor a menor según su tamanio
     * y posteriormente mediante un bucle se suman los numeros de usuarios de cada
     * grumo hasta conseguir el porcentaje minimo por parametro.
     * Si se necesita unir mas de un grumo entonces, creamos las nuevas conexiones
     * escogiendo el segundo usuario de un grumo con el primer usuario del grumo
     * siguiente
     * 
     * @param pmin porcentaje minimo requerido
     */
    public void ordenaSelecciona(double pmin) {
        ArrayList<ArrayList<Integer>> grus = dsa.crearGrumos();
        int acumulado = 0;
        double porcentaje;
        int u1, u2;

        grus.sort((l1, l2) -> l2.size() - l1.size()); // ordena los grumos de mayor a menor tamano

        for (ArrayList<Integer> grumo : grus) {

            acumulado += grumo.size();
            porcentaje = (double) acumulado / numUsuarios() * 100.0;
            grusSeleccionados.add(grumo);

            if (porcentaje >= pmin) {
                break;
            }
        }

        if (grusSeleccionados.size() > 1) {
            for (int i = 0; i < grusSeleccionados.size() - 1; i++) {

                ArrayList<Integer> grumo1 = grusSeleccionados.get(i);
                ArrayList<Integer> grumo2 = grusSeleccionados.get(i + 1);

                u1 = grumo1.get(1);
                u2 = grumo2.get(0);

                nuevas.add(new Conexion(u1, u2));
            }
        }

    }

    /**
     * Metodo que guarda las nuevas conexiones obtenidas en el
     * fichero dado por parametro
     * 
     * @param nomfich ruta al fichero de escritura
     */
    public void salvaNuevasRel(String nomfich) throws IOException {

        PrintWriter extra;
        if (grusSeleccionados.size() > 1) {
            extra = new PrintWriter(nomfich);
            for (Conexion c : nuevas) {
                extra.println(c);
            }

            extra.close();
        }
    }

    /**
     * Metodo que genera un informe con la información obtenida de la
     * etapa de ordenacion y seleccion
     */
    public void informe() {

        int tam, i = 1;
        double porcentaje;

        if (grusSeleccionados.size() == 1) { // CASO: No se necesitan relaciones nuevas
            tam = grusSeleccionados.get(0).size();
            porcentaje = (double) tam / numUsuarios() * 100;
            porcentaje = Math.round(porcentaje * 100.0) / 100.0; // redondea a dos decimales
            System.out.println("El mayor grumo contiene " + tam + " usuarios (" + porcentaje + "%)");
            System.out.println("No son necesarias nuevas relaciones de amistad");

        } else { // CASO: Se necesitan relaciones nuevas
            System.out.println("Se deben unir los " + grusSeleccionados.size() + " mayores");

            for (ArrayList<Integer> grumo : grusSeleccionados) {
                tam = grumo.size();
                porcentaje = (double) tam / numUsuarios() * 100;
                porcentaje = Math.round(porcentaje * 100.0) / 100.0; // redondea a dos decimales
                System.out.println("#" + (i) + ": " + tam + " usuarios (" + porcentaje + "%)");
                i++;
            }

            System.out.println("Nuevas relaciones de amistad (salvadas en extra.txt)");
            for (Conexion c : nuevas) {
                System.out.println(c);
            }
        }
    }

    public void grumosYUsuarios() {
        dsa = new DisjointSetAux(red);
        dsa.crearGrumos();
    }

    public ArrayList<ArrayList<Integer>> getGrumos() {
        return dsa.crearGrumos();
    }

    /**
     * Genera un caso de prueba
     * 
     * @param n   Tamaño (número de usuarios) del caso de prueba
     * @param rnd Generador de números aleatorios usado
     * @return Una lista de conexiones de amistad
     */
    public static List<Conexion> generaCaso(int n, Random rnd) {
        // Generar identificadores de usuarios
        int[] usr = new int[n];
        for (int i = 0; i < n; i++) {
            usr[i] = i + 10000000;
        }
        // Algoritmo de Fisher-Yates (shuffle)
        for (int i = n - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int tmp = usr[j];
            usr[j] = usr[i];
            usr[i] = tmp;
        }
        // Generar √n grumos usando rangos de índices de usuarios
        int[] inds = rnd.ints((int) Math.sqrt(n))
                .map(i -> Math.abs(i) % (n - 1) + 1)
                .sorted()
                .distinct()
                .toArray();
        inds[inds.length - 1] = n;
        // Añadir las conexiones de los grumos
        // Añadir las conexiones de los grumos
        HashSet<Conexion> red = new HashSet<>();
        int i0 = 0;
        for (int i1 : inds) { // Rango [i0,i1)
            if (i1 < i0 + 2) {
                continue;
            } // No se actualiza i0 aposta.
              // Conexiones circulares
            for (int i = i0; i < i1 - 1; i++) {
                red.add(new Conexion(usr[i], usr[i + 1]));
            }
            red.add(new Conexion(usr[i1 - 1], usr[i0]));
            // Conexiones al azar
            int ng = 2 * (i1 - i0);
            for (int k = 0; k < ng; k++) {
                int u1 = rnd.nextInt(i1 - i0) + i0;
                int u2 = rnd.nextInt(i1 - i0) + i0;
                if (u1 != u2) {
                    red.add(new Conexion(usr[u1], usr[u2]));
                }
            }
            i0 = i1;
        }
        return red.stream().collect(Collectors.toList());
    }

}
