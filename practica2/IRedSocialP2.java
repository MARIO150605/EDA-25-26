import java.io.IOException;
import java.util.*;

/*
 * Estructuras de Datos y Algoritmos 
 * Practica 1 
 * 
 * Mario San José de Prado (K7)
 * Abel López Santiago (K7)
 */

/**
 * Interfaz de RedSocial
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
