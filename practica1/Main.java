import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        long inicio, fin;
        double pmin, t1,t2,t3,t4;
        String nomfich, extra;
        RedSocial Y = new RedSocial();
        Scanner in = new Scanner(System.in);

        System.out.println("        ANALISIS DE Y \n-------------------------------\n");
        System.out.print("Fichero principal: ");    //Solicitamos fichero principal
        nomfich = in.nextLine();

        inicio = System.nanoTime();
        Y.leeFichero("practica1/"+nomfich);
        fin = System.nanoTime();
        t1 = (fin - inicio)/ 1000000000.0;

        System.out.print("Fichero de nuevas conexiones (pulse enter si no existe): ");  //Solicitamos fichero extra
        extra = in.nextLine();
        if(!extra.equals("")){
            Y.leeFichero("pracica1/"+extra);
        }

        System.out.print("Porcentaje mayor tamaño grumo: ");
        pmin = in.nextDouble();

        System.out.println("Lectura fichero: "+ t1);

                // nuevos tiempos
        inicio = System.nanoTime();
        Y.creaUsuarios();
        fin = System.nanoTime();
        t2 = (fin - inicio)/ 1000000000.0;
        System.out.println("Creacion lista usuarios: " + t2);

        inicio = System.nanoTime();
        Y.creaGrumos();
        fin = System.nanoTime();
        t3 = (fin - inicio)/ 1000000000.0;
        System.out.println("Creacion lista grumos: " + t3);

        inicio = System.nanoTime();
        Y.ordenaSelecciona(pmin);
        fin = System.nanoTime();
        t4 = (fin - inicio)/ 1000000000.0;
        System.out.println("Ordenacion y seleccion de grumos: "+ t4);

        System.out.println(Y.numUsuarios() + " usuarios, " + Y.numConexiones() + " conexiones");
        System.out.println("Existen " + Y.numGrumos() + " grumos.");


        in.close();
    }

    
}
