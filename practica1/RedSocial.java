import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RedSocial implements IRedSocial {

    ArrayList<Conexion> red = new ArrayList<>();

    public int numUsuarios() {
        return 5;
    }

    public int numConexiones() {
        return red.size();
    }

    public int numGrumos() {
        return 5;
    }

    public void leeFichero(String nomfich) throws IOException {

        String[] partes;
        int u1, u2;
        try (BufferedReader br = new BufferedReader(new FileReader(nomfich))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                partes = linea.split(" ");
                u1 = Integer.parseInt(partes[0]);
                u2 = Integer.parseInt(partes[1]);

                red.add(new Conexion(u1, u2));

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void setRed(List<Conexion> red) {

    }

    public void creaUsuarios() {

    }

    public void creaGrumos() {

    }

    public void ordenaSelecciona(double pmin) {

    }

    public void salvaNuevasRel(String nomfich) throws IOException {

    }

    public void informe() {

    }

}
