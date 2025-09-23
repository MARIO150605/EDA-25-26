package practica1;

public class Conexiones {

    private int c1;
    private int c2;

    public Conexiones(int conexion1, int conexion2) {
        this.c1 = conexion1;
        this.c2 = conexion2;
    }

    public int getc1() {
        return this.c1;
    }

    public int getc2() {
        return this.c2;
    }

    public String toString() {
        return "" + c1 + " " + c2;
    }

    /*
     * public static void main(String []args){
     * 
     * Conexiones c = new Conexiones(32, 5);
     * 
     * System.out.println(c.toString());
     * System.out.println(c.getc1());
     * System.out.println(c.getc2());
     * 
     * }
     */

}
