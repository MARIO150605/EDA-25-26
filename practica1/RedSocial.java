import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RedSocial implements IRedSocial {

    ArrayList<Conexion> red = new ArrayList<>();
    ArrayList<Integer> usr = new ArrayList<>();
    ArrayList<ArrayList<Integer>> grus = new ArrayList<>();
    ArrayList<Integer> asig = new ArrayList<>();

    ArrayList<Conexion> nuevas = new ArrayList<>(); // array para las nuevas conexiones
    ArrayList<ArrayList<Integer>> grusSeleccionados = new ArrayList<>(); // array para obtener los grumos utilizados para el porcentaje

    public int numUsuarios() {
        return usr.size();
    }

    public int numConexiones() {
        return red.size();
    }

    public int numGrumos() {
        return grus.size();
    }

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

    public void setRed(List<Conexion> red) {

        this.red = new ArrayList<>(red) ;

    }

    public void creaUsuarios() {

        int u1, u2;
        for (Conexion c : red) {

            u1 = c.getc1();
            u2 = c.getc2();

            if (!usr.contains(u1)) {
                usr.add(u1);
            }

            if (!usr.contains(u2)) {
                usr.add(u2);
            }

        }

    }

    public void creaGrumos() {

        ArrayList<Integer> grumo;

        for (Integer u : usr) {
            if (!asig.contains(u)) {
                grumo = new ArrayList<>();
                grumo.add(u);
                uber_amigos(u, red, grumo);
                grus.add(grumo);
                asig.addAll(grumo);
            }
        }

    }

    public void ordenaSelecciona(double pmin) {

        int acumulado = 0;
        double porcentaje;
        boolean superado = false;
        int u1, u2;

        grus.sort((l1, l2) -> l2.size() - l1.size()); // ordena los grumos de mayor a menor tamano

        for (ArrayList<Integer> grumo : grus) {
            if (!superado) {

                acumulado += grumo.size();
                porcentaje = (double) acumulado / numUsuarios() * 100.0;
                grusSeleccionados.add(grumo);

                if (porcentaje >= pmin) {
                    superado = true;
                }
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

    public void informe() {

        int tam,i=1;
        double porcentaje;

        if (grusSeleccionados.size() == 1) { // CASO: No se necesitan relaciones nuevas
            tam = grusSeleccionados.get(0).size();
            porcentaje = (double) tam/numUsuarios() *100;
            porcentaje = Math.round(porcentaje * 100.0) / 100.0; // redondea a dos decimales
            System.out.println("El mayor grumo contiene " + tam + " usuarios (" + porcentaje + "%)");
            System.out.println("No son necesarias nuevas relaciones de amistad");

        } else { // CASO: Se necesitan relaciones nuevas
            System.out.println("Se deben unir los " + grusSeleccionados.size() + " mayores");

            for (ArrayList<Integer> grumo : grusSeleccionados) {
                tam = grumo.size();
                porcentaje = (double) tam/numUsuarios() *100;
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

    private void uber_amigos(int usuario, ArrayList<Conexion> red, ArrayList<Integer> grumo) {

        int amigo, u1, u2;
        for (Conexion c : red) {
            amigo = -1;
            u1 = c.getc1();
            u2 = c.getc2();

            if (usuario == u1) {
                amigo = u2;
            } else if (usuario == u2) {
                amigo = u1;
            }

            if (amigo != -1 && !grumo.contains(amigo)) {
                grumo.add(amigo);
                uber_amigos(amigo, red, grumo);
            }
        }

    }

    /**
     * Genera un caso de prueba
     * @param n Tamaño (número de usuarios) del caso de prueba
     * @param rnd   Generador de números aleatorios usado
     * @return  Una lista de conexiones de amistad
     */
    public static List<Conexion> generaCaso(int n, Random rnd) {
        // Generar identificadores de usuarios
        HashSet<Integer> husr = new HashSet<>();
        while(husr.size() < n) { husr.add(rnd.nextInt(90000000)+1000000); }
        final Integer[] usr = husr.toArray(new Integer[0]);
        // Generar √n grumos usando rangos de índices de usuarios
        int[] inds = rnd.ints((int) Math.sqrt(n))
                        .map(i -> Math.abs(i) % (n-1) + 1)
                        .sorted()
                        .distinct()
                        .toArray();
        inds[inds.length-1] = n;
        // Añadir las conexiones de los grumos
        HashSet<Conexion> red = new HashSet<>();
        int i0 = 0;
        for(int i1: inds) { // Rango [i0,i1)
            // Conexiones circulares
            red.addAll(IntStream.range(i0, i1-1)
                                .mapToObj(i -> {
                return new Conexion(usr[i], usr[i+1]);
            })
                                .collect(Collectors.toList()));
            red.add(new Conexion(usr[i1-1],usr[i0]));
            // Conexiones al azar
            int ng = 2*(i1-i0);
            for(int k = 0; k < ng; k++) {
                int u1 = rnd.nextInt(i1-i0)+i0;
                int u2 = rnd.nextInt(i1-i0)+i0;
                if(u1 != u2) { red.add(new Conexion(usr[u1], usr[u2])); }
            }
            i0 = i1;
        }
        return red.stream().collect(Collectors.toList());
    } 

}
