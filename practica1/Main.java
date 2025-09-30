import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        double inicio, tiempo;
        String nomfich, extra;
        
        RedSocial Y = new RedSocial();
        Scanner in = new Scanner(System.in);

        System.out.print("Fichero principal: ");    //Solicitamos fichero principal
        nomfich = in.nextLine();
        inicio = System.nanoTime();
        Y.leeFichero(nomfich);
        tiempo = System.nanoTime();

        System.out.print("Fichero de nuevas conexiones (pulse enter si no existe): ");  //Solicitamos fichero extra
        extra = in.nextLine();
        if(!extra.equals("")){
            Y.leeFichero(extra);
        }
        System.out.println((tiempo - inicio) / 1000000000 +"s");
        System.out.println(Y.red);
        
        in.close();
    }
}
