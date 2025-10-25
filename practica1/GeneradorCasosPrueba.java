
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Estructuras de Datos y Algoritmos
 * Universidad de Valladolid
 * 
 * Generador de casos de prueba para la práctica 2025/26
 * 
 * @author César Vaca
 */
public class GeneradorCasosPrueba {

    /**
     * Representa una conexión de amistad
     */
    public static class Conexion {

        public int usr1, usr2;

        public Conexion(int usr1, int usr2) {
            this.usr1 = usr1;
            this.usr2 = usr2;
        }

        @Override
        public int hashCode() {
            return usr1 ^ usr2;
        }

        @Override
        public boolean equals(Object obj) {
            if (getClass() != obj.getClass()) {
                return false;
            }
            Conexion otro = (Conexion) obj;
            return (usr1 == otro.usr1 && usr2 == otro.usr2) ||
                    (usr1 == otro.usr2 && usr2 == otro.usr1);
        }

        @Override
        public String toString() {
            return String.format("%d %d", usr1, usr2);
        }
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
        HashSet<Integer> husr = new HashSet<>();
        while (husr.size() < n) {
            husr.add(rnd.nextInt(90000000) + 1000000);
        }
        final Integer[] usr = husr.toArray(new Integer[0]);
        // Generar √n grumos usando rangos de índices de usuarios
        int[] inds = rnd.ints((int) Math.sqrt(n))
                .map(i -> Math.abs(i) % (n - 1) + 1)
                .sorted()
                .distinct()
                .toArray();
        inds[inds.length - 1] = n;
        // Añadir las conexiones de los grumos
        HashSet<Conexion> red = new HashSet<>();
        int i0 = 0;
        for (int i1 : inds) { // Rango [i0,i1)
            if (i1 < i0 + 2) {
                continue;
            } // No se actualiza i0 aposta.
            // Conexiones circulares
            red.addAll(IntStream.range(i0, i1 - 1)
                    .mapToObj(i -> new Conexion(usr[i], usr[i + 1]))
                    .collect(Collectors.toList()));
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

    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Numero de usuarios: ");
        int n = teclado.nextInt();
        teclado.nextLine();
        System.out.printf("Nombre del fichero (test%d.txt si pulsa enter): ", n);
        String nomfich = teclado.nextLine();
        if (nomfich.length() == 0) {
            nomfich = "test" + n + ".txt";
        }
        Random rnd = new Random(123456789); // <- Para que sea siempre igual para un mismo tamaño
        List<Conexion> red = generaCaso(n, rnd);
        Files.write(Paths.get(nomfich), red.stream().map(Conexion::toString).collect(Collectors.toList()));

        teclado.close();
    }
}
