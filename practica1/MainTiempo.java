import java.io.IOException;
import java.util.Scanner;

public class MainTiempo {

    final int Ninicio = 0, Nrep = 0, Ninc = 0, Nfin = 0;
    public static void main(String[] args) throws IOException{
        
        long inicio, fin;
        double pmin, t1, t2, t3, t4;
        String nomfich, extra;
        RedSocial Y = new RedSocial();
        Scanner in = new Scanner(System.in);

        nomfich = in.nextLine();

        inicio = System.nanoTime();
        Y.leeFichero("practica1/" + nomfich);
        fin = System.nanoTime();
        t1 = (fin - inicio) / 1000000000.0;

        extra = in.nextLine();
        if (!extra.equals("")) {
            Y.leeFichero("practica1/" + extra);
        }

        pmin = in.nextDouble();

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


        in.close();
    }

}

