import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        double inicio, tiempo, porcentaje;
        String nomfich, extra;
        
        RedSocial Y = new RedSocial();
        Scanner in = new Scanner(System.in);

        System.out.println("        ANALISIS DE Y \n-------------------------------\n");
        System.out.print("Fichero principal: ");    //Solicitamos fichero principal
        nomfich = in.nextLine();
        inicio = System.nanoTime();
        Y.leeFichero("practica1/"+nomfich);
        tiempo = System.nanoTime();

        System.out.print("Fichero de nuevas conexiones (pulse enter si no existe): ");  //Solicitamos fichero extra
        extra = in.nextLine();
        if(!extra.equals("")){
            Y.leeFichero("pracica1/"+extra);
        }

        System.out.print("Porcentaje mayor tamaño grumo: ");
        porcentaje = in.nextDouble();
        System.out.println("Lectura fichero: "+(tiempo - inicio) / 1000000000);

                //...creacion lista de usuarios...

        System.out.println(Y.numUsuarios() + " usuarios, " + Y.numConexiones() + " conexiones");
        System.out.print("Existen " + Y.numGrumos() + " grumos.");



        in.close();
    }
}
