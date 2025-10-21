
/*
 * Estructuras de Datos y Algoritmos 
 * Practica 1 
 * 
 * Mario San José de Prado (K7)
 * Abel López Santiago (K7)
 */

/**
 * Clase que representa una conexion de la red social
 */
public class Conexion {

    /**
     * @param c1 conexion 1
     * @param c2 conexion 2
     */

    private int c1;
    private int c2;

    public Conexion(int conexion1, int conexion2) {
        this.c1 = conexion1;
        this.c2 = conexion2;
    }

    /**
     * Getter de la conexion 1
     * 
     * @return conexion 1
     */
    public int getc1() {
        return this.c1;
    }

    /**
     * Getter de la conexion 2
     * 
     * @return conexion 2
     */
    public int getc2() {
        return this.c2;
    }

    /**
     * ToString de la clase conexion
     * 
     * @return c1 c2
     */
    @Override
    public String toString() {
        return c1 + " " + c2;
    }

}
