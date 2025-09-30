import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RedSocial implements IRedSocial {

    ArrayList<Conexion> red = new ArrayList<>();
    ArrayList<Integer> usr = new ArrayList<>();
    ArrayList<ArrayList<Integer>> grus = new ArrayList<>();
    ArrayList<Integer> asig = new ArrayList<>();


    public int numUsuarios() {
        return usr.size();
    }

    public int numConexiones() {
        return red.size();
    }

    public int numGrumos() {
        return grus.size();
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

        int u1,u2;
        for (Conexion c : red){

            u1 = c.getc1();
            u2 = c.getc2();

            if(!usr.contains(u1)){
                usr.add(u1);
            }

            if(!usr.contains(u2)){
                usr.add(u2);
            }
            
        }

    }

    public void creaGrumos() {

        ArrayList<Integer> grumo;

        for(Integer u : usr){
            if(!asig.contains(u)){
                grumo = new ArrayList<>();
                uber_amigos(u, red, grumo);
                grus.add(grumo);
                asig.addAll(grumo);
            }
        }

    }

    public void ordenaSelecciona(double pmin) {

    }

    public void salvaNuevasRel(String nomfich) throws IOException {

    }

    public void informe() {

    }

    public void uber_amigos(int usuario, ArrayList<Conexion> red, ArrayList<Integer> grumo){
        
        int amigo,u1,u2;
        for (Conexion c : red){
            amigo = -1;
            u1 = c.getc1();
            u2 = c.getc2();

            if(usuario == u1 ){
                amigo = u2;
            } else if(usuario == u2){
                amigo = u1;
            }

            if(amigo != -1 && !grumo.contains(amigo)){
                grumo.add(amigo);
                uber_amigos(amigo, red, grumo);
            }
        }

    }

}
