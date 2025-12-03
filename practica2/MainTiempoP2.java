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

    static int Nrep = 3; // Numero de repeticiones
    static int Ninc = 1000000; // incremento usuarios
    static int Nfin = 25000000; // maximo usuarios
    static final Random RND = new Random();

    public static void main(String[] args) throws IOException {

        double pmin = 90, tTotal, sumaTotal, promedioTotal; // promedio final por tamaño

        int Ninicio = 1000000; // tamaño inicial usuarios
        List<Conexion> generador;

        System.out.println("\nTiempos promedio de " + Nrep + " repeticiones:\n\n");
        while (Ninicio <= Nfin) {

            System.out.println("Numero de usuarios: " + Ninicio);

            sumaTotal = 0.0;

            for (int i = 0; i < Nrep; i++) {

                RedSocialP2 Y = new RedSocialP2();

                generador = Y.generaCaso(Ninicio, RND);
                Y.setRed(generador);

                long inicio = System.nanoTime();
                Y.grumosYUsuarios();
                Y.ordenaSelecciona(pmin);

                long fin = System.nanoTime();

                tTotal = (fin - inicio) / 1000000000.0;
                // System.out.println("Tiempo (" + (i+1) + "):" + tTotal); MOSTRAR TIEMPO EN
                // CADA ITERACION (SIN PROMEDIO)
                sumaTotal += tTotal;
            }

            // Promedios:
            promedioTotal = sumaTotal / Nrep;

            System.out.println("Tiempo total promedio: " + promedioTotal + "\n");

            Ninicio += Ninc;
        }
    }
}