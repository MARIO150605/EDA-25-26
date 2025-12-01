import java.io.IOException;
import java.util.*;

/**
 * Clase de ejecución Main para el algoritmo de la practica.
 * 
 * @author Mario San José de Prado (K7)
 * @author Abel López Santiago (K7)
 */
public class MainP2 {
    public static void main(String[] args) throws IOException {
        long inicio, fin;
        double pmin, t1, t2, t4;
        String nomfich, extra;
        RedSocialP2 Y = new RedSocialP2();
        Scanner in = new Scanner(System.in);

        System.out.println("        ANALISIS DE Y \n-------------------------------\n");
        System.out.print("Fichero principal: "); // Solicitamos fichero principal
        nomfich = in.nextLine();

        inicio = System.nanoTime();
        Y.leeFichero(nomfich);
        fin = System.nanoTime();
        t1 = (fin - inicio) / 1000000000.0; // tiempo de lectura de fichero (seg)

        System.out.print("Fichero de nuevas conexiones (pulse enter si no existe): "); // Solicitamos fichero extra
        extra = in.nextLine();
        if (!extra.equals("")) {
            Y.leeFichero(extra);
        }

        System.out.print("Porcentaje mayor tamano grumo: ");
        pmin = in.nextDouble();

        System.out.println("Lectura fichero: " + t1);

        inicio = System.nanoTime();
        Y.grumosYUsuarios();
        fin = System.nanoTime();
        t2 = (fin - inicio) / 1000000000.0; // tiempo de creacion de lista de usuarios (seg)
        System.out.println("Creacion lista usuarios y grumos: " + t2); 

        inicio = System.nanoTime();
        Y.ordenaSelecciona(pmin);
        fin = System.nanoTime();
        t4 = (fin - inicio) / 1000000000.0; // tiempo de ordenaccion y seleccion de grumos (seg)
        System.out.println("Ordenacion y seleccion de grumos: " + t4);

        System.out.println(Y.numUsuarios() + " usuarios, " + Y.numConexiones() + " conexiones");
        System.out.println("Existen " + Y.numGrumos() + " grumos.");

        Y.salvaNuevasRel("extra.txt");
        //Y.informe();

        in.close();
    }

}
