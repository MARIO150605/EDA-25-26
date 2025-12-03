import java.io.IOException;
import java.util.*;
import java.util.Random;

/**
 * Clase de control de tiempos del Main para el algoritmo de la practica.
 * 
 * @author Mario San José de Prado (K7)
 * @author Abel López Santiago (K7)
 */
public class MainTiempoP2 {

    static int Nrep = 10; // Numero de repeticiones
    static int Ninc = 100; // incremento usuarios
    static int Nfin = 10000000; // maximo usuarios
    static final Random RND = new Random();

    public static void main(String[] args) throws IOException {

        double pmin = 90, tTotal, sumaTotal, promedioTotal; // promedio final por tamaño
        int nConexiones, nUsuarios, nGrumos, promConexiones, promUsuarios, promGrumos;
        int Ninicio = 100; // tamaño inicial usuarios
        List<Conexion> generador;

        System.out.println("\nTiempos promedio de " + Nrep + " repeticiones:\n\n");
        while (Ninicio <= Nfin) {

            System.out.println("Numero de usuarios: " + Ninicio);
            nConexiones = 0;
            nUsuarios = 0;
            nGrumos = 0;
            sumaTotal = 0.0;

            for (int i = 0; i < Nrep; i++) {
                System.out.print(i);
                RedSocialP2 Y = new RedSocialP2();

                generador = Y.generaCaso(Ninicio, RND);
                Y.setRed(generador);

                long inicio = System.nanoTime();
                Y.grumosYUsuarios();
                Y.ordenaSelecciona(pmin);

                long fin = System.nanoTime();

                tTotal = (fin - inicio) / 1000000000.0;
                sumaTotal += tTotal;

                nConexiones += Y.numConexiones();
                nUsuarios += Y.numUsuarios();
                nGrumos += Y.numGrumos();

            }
            System.out.println();
            // Promedios:

            promedioTotal = sumaTotal / Nrep;
            promConexiones = (int) nConexiones / Nrep;
            promUsuarios = (int) nUsuarios / Nrep;
            promGrumos = (int) nGrumos / Nrep;
            System.out.println("Usuarios: " + promUsuarios + " Conexiones:" + promConexiones);
            System.out.println("Grumos: " + promGrumos);
            System.out.println("Tiempo total promedio: " + promedioTotal + "\n");

            Ninicio += Ninc;

            if (Ninicio == 1000) { // cuando llegue a 1000 de 1000 en 1000
                Ninc = 1000;
            }

            if (Ninicio == 10000) { // cuando llegue a 10k de 10k en 10k
                Ninc = 10000;
            }

            if (Ninicio == 100000) { // cuando llegue a 100k de 100k en 100k
                Ninc = 100000;
            }

            if (Ninicio == 1000000) { // cuando llegue a 1M de 1M en 1M
                Ninc = 1000000;
            }

        }
    }
}