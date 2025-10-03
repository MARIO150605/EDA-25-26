import java.io.*;
import java.util.*;

public class RedSocial implements IRedSocial {

    ArrayList<Conexion> red = new ArrayList<>();
    ArrayList<Integer> usr = new ArrayList<>();
    ArrayList<ArrayList<Integer>> grus = new ArrayList<>();
    ArrayList<Integer> asig = new ArrayList<>();

    ArrayList<Conexion> nuevas = new ArrayList<>(); // array para las nuevas conexiones
    ArrayList<ArrayList<Integer>> grusSeleccionados = new ArrayList<>(); // array para obtener los grumos utilizados
                                                                         // para el porcentaje

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

        grus.sort((l1, l2) -> l2.size() - l1.size()); // ordena los grumos de mayor a menor tamaño

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
        int tam;
        double porcentaje;
        ArrayList<Integer> grumo = new ArrayList<>();

        if (grusSeleccionados.size() == 1) { // CASO: No se necesitan relaciones nuevas
            grumo = grusSeleccionados.get(0);
            tam = grumo.size();
            porcentaje = (tam * 100.0) / usr.size();
            porcentaje = Math.round(porcentaje * 100.0) / 100.0; // redondea a dos decimales
            System.out.println("El mayor grumo contiene " + tam + " usuarios (" + porcentaje + "%)");
            System.out.println("No son necesarias nuevas relaciones de amistad");

        } else { // CASO: Se necesitan relaciones nuevas
            System.out.println("Se deben unir los " + grusSeleccionados.size() + " mayores");
            for (int i = 0; i < grusSeleccionados.size(); i++) {
                grumo = grusSeleccionados.get(i);
                tam = grumo.size();
                porcentaje = (tam * 100.0) / usr.size();
                porcentaje = Math.round(porcentaje * 100.0) / 100.0; // redondea a dos decimales
                System.out.println("#" + (i + 1) + ": " + tam + " usuarios (" + porcentaje + "%)");
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

}
