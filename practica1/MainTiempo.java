import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


/*
 * Estructuras de Datos y Algoritmos 
 * Practica 1 
 * 
 * Mario San José de Prado (K7)
 * Abel López Santiago (K7)
 */

/**
 * Clase de ejecucion Main para la toma de diferente tiempos del algoritmo
 * en función de varias entradas con un incremento hasta un valor final
 */
public class MainTiempo {

    static int Nrep = 10, Ninc = 100, Nfin = 200000;
    static final Random RND = new Random();

    public static void main(String[] args) throws IOException {
        double pmin, t2, t3, t4, tTotal;
        double promedio2, promedio3, promedio4;
        int promedioConexiones, promedioGrumos, conexiones, grumos;
        int Ninicio = 100;
        List<Conexion> generador;

        while (Ninicio <= Nfin) {
            // inicializar los tiempos para cada nueva entrada
            t2 = 0;
            t3 = 0;
            t4 = 0;
            conexiones = 0;
            grumos = 0;
            System.out.println("Numero de usuarios: " + Ninicio);
            for (int i = 0; i < Nrep; i++) {

                long inicio, fin;

                RedSocialP2 Y = new RedSocialP2();

                generador = Y.generaCaso(Ninicio, RND);
                Y.setRed(generador);
                pmin = 90;

                inicio = System.nanoTime();
                Y.creaUsuarios();
                fin = System.nanoTime();
                t2 += (fin - inicio) / 1000000000.0; // tiempo de creacion de lista de usuarios

                conexiones += Y.numConexiones();

                inicio = System.nanoTime();
                Y.creaGrumos();
                fin = System.nanoTime();
                t3 += (fin - inicio) / 1000000000.0; // tiempo de creacion de lista de grumos

                grumos += Y.numGrumos();

                inicio = System.nanoTime();
                Y.ordenaSelecciona(pmin);
                fin = System.nanoTime();
                t4 += (fin - inicio) / 1000000000.0; // tiempo de ordenaccion y seleccion de grumos

                System.out.print(i + 1); // nº de ejecucion completada
            }

            System.out.println();

            promedioGrumos = grumos / Nrep;
            promedioConexiones = conexiones / Nrep;
            System.out.println("Promedio de conexiones: " + promedioConexiones + " Promedio de grumos: " + promedioGrumos);

            promedio2 = t2 / Nrep;
            System.out.println("Promedio de tiempo etapa 2(creacion de usuarios): " + promedio2);

            promedio3 = t3 / Nrep;
            System.out.println("Promedio de tiempo etapa 3(creacion de grumos): " + promedio3);

            promedio4 = t4 / Nrep;
            System.out.println("Promedio de tiempo etapa 4(ordenacion y seleccion): " + promedio4);

            tTotal = promedio2 + promedio3 + promedio4;
            System.out.println("Tiempo total: " + tTotal);

            if(Ninicio == 500){
                Ninc = 500;
            }
            if(Ninicio == 5000){
                Ninc = 5000;
            }

            if(Ninicio == 75000){
                Nrep=6;
            }

            if (Ninicio == 100000) {
                Nrep = 3; // cambio de nº repes al 100k
                Ninc = 10000; // cambio de incrementos al 100k
            }
            
            if (Ninicio == 150000){
                Ninc = 20000;
            }
            Ninicio += Ninc;
        }
    }
}
