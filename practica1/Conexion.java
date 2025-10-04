
public class Conexion {

    private int c1;
    private int c2;

    public Conexion(int conexion1, int conexion2) {
        this.c1 = conexion1;
        this.c2 = conexion2;
    }

    public int getc1() {
        return this.c1;
    }

    public int getc2() {
        return this.c2;
    }

    @Override
    public String toString() {
        return c1 + " " + c2;
    }
     
}
