import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;


public class MainTiempo {

    static final int Ninicio = 5000, Nrep = 10, Ninc = 0, Nfin = 0;
    static final Random RND = new Random(123456789);
    public static void main(String[] args) throws IOException{
        double pmin,t2=0,t3=0,t4=0;
        double promedio;
        
        for(int i=0;i<Nrep;i++){

            long inicio, fin;
            
            RedSocial Y = new RedSocial();
            ArrayList<Conexion> aux = new ArrayList<>();

            inicio = System.nanoTime();
            List<GeneradorCasosPrueba.Conexion> generador = GeneradorCasosPrueba.generaCaso(Ninicio, RND);
            for(GeneradorCasosPrueba.Conexion c : generador){
                aux.add(new Conexion(c.usr1, c.usr2));
            }

            Y.setRed(aux);
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


        }

        promedio = t2/Nrep;
        System.out.println("Promedio de tiempo etapa 2(creacion de usuarios): "+promedio);

        promedio = t3/Nrep;
        System.out.println("Promedio de tiempo etapa 3(creacion de grumos): "+promedio);

        promedio = t4/Nrep;
        System.out.println("Promedio de tiempo etapa 4(ordenacion y seleccion): "+promedio);


    }
}

