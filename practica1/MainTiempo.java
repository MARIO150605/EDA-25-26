import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;


public class MainTiempo {

    static final int Nrep = 10, Ninc = 2500, Nfin = 30000;
    static final Random RND = new Random();
    public static void main(String[] args) throws IOException{
        double pmin,t2=0,t3=0,t4=0, tTotal;
        double promedio2, promedio3, promedio4;
        int Ninicio = 5000;
        while(Ninicio<=Nfin){
            System.out.println("Entrada: "+Ninicio);
            for(int i=0;i<Nrep;i++){

                long inicio, fin;
                
                RedSocial Y = new RedSocial();
            
                List<Conexion> generador = Y.generaCaso(Ninicio, RND);
                Y.setRed(generador);
                pmin = 90;

                inicio = System.nanoTime();
                Y.creaUsuarios();
                fin = System.nanoTime();
                t2 += (fin - inicio) / 1000000000.0;

                inicio = System.nanoTime();
                Y.creaGrumos();
                fin = System.nanoTime();
                t3 += (fin - inicio) / 1000000000.0;

                inicio = System.nanoTime();
                Y.ordenaSelecciona(pmin);
                fin = System.nanoTime();
                t4 += (fin - inicio) / 1000000000.0;

                System.out.print(i+1);
            }
            
            System.out.println();

            promedio2 = t2/Nrep;
            System.out.println("Promedio de tiempo etapa 2(creacion de usuarios): "+promedio2);

            promedio3 = t3/Nrep;
            System.out.println("Promedio de tiempo etapa 3(creacion de grumos): "+promedio3);

            promedio4 = t4/Nrep;
            System.out.println("Promedio de tiempo etapa 4(ordenacion y seleccion): "+promedio4);
            
            tTotal = promedio2 + promedio3 + promedio4;
            System.out.println("Tiempo total: "+tTotal);
            Ninicio += Ninc;
        }
    }
}

