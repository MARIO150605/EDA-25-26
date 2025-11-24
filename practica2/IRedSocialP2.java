import java.io.IOException;
import java.util.*;

/**
 * Interfaz de RedSocial
 * 
 * @author Mario San José de Prado (K7)
 * @author Abel López Santiago (K7)
 */
public interface IRedSocialP2 {

    public int numUsuarios();

    public int numConexiones();

    public int numGrumos();

    public void leeFichero(String nomfich) throws IOException;

    public void setRed(List<Conexion> red);

    public void ordenaSelecciona(double pmin);

    public void salvaNuevasRel(String nomfich) throws IOException;

    public void informe();

    public void grumosYUsuarios();

}
