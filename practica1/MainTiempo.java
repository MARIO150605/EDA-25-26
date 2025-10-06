import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.List;

public class MainTiempo {

    static final int Ninicio = 0, Nrep = 0, Ninc = 0, Nfin = 0;
    static final Random RND = new Random(123456789);
    public static void main(String[] args) throws IOException{
        for(int i=0;i<Nrep;i++){

            long inicio, fin;
            double pmin, t1, t2, t3, t4;
            String nomfich, extra;
            RedSocial Y = new RedSocial();

            inicio = System.nanoTime();
            List<GeneradorCasosPrueba.Conexion> generador = GeneradorCasosPrueba.generaCaso(Ninicio, RND);
            //Y.setRed(generador);
            fin = System.nanoTime();
            t1 = (fin - inicio) / 1000000000.0;



            pmin = 90;

            // nuevos tiempos
            inicio = System.nanoTime();
            Y.creaUsuarios();
            fin = System.nanoTime();
            t2 = (fin - inicio) / 1000000000.0;

            inicio = System.nanoTime();
            Y.creaGrumos();
            fin = System.nanoTime();
            t3 = (fin - inicio) / 1000000000.0;

            inicio = System.nanoTime();
            Y.ordenaSelecciona(pmin);
            fin = System.nanoTime();
            t4 = (fin - inicio) / 1000000000.0;


        }
    }
}

