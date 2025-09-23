import java.util.Scanner;
import java.util.Random;

public class EDASesion1 {

    static int BLANCO = 0; // RELLENAR BLANCO CON 0
    static int NEGRO = 1; // RELLENAR NEGRO CON 1
    static int OP; // CONTADOR DE OPERACIONES

    public static void main(String[] args) {
        //
        Scanner in = new Scanner(System.in); // Scanner para pedir al usuario las filas
        int d; // filas

        int matriz[][]; // la matriz

        System.out.println("Introduce numero de filas (d): "); // pedir al usuario las d filas
        d = in.nextInt();


        matriz = casos(d); // llamo a la funcion casos

        for (int i = 0; i < d; i++) { // imprimir matriz
              for (int j = 0; j < matriz[i].length; j++) {
              System.out.print(matriz[i][j] + " ");
                }
            System.out.println("");

            }
             
            System.out.println();
            
        colorear(matriz, d); // coloreo la matriz

        for (int i = 0; i < d; i++) { // imprimir matriz
              for (int j = 0; j < matriz[i].length; j++) {
              System.out.print(matriz[i][j] + " ");
                }
            System.out.println("");

            }
             
            System.out.println();

        
        /* 
        int[] vector = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130,140,150,160,170,180,190,200 }; // vector con numero de filas
        for (int k = 0; k < vector.length - 1; k++) { // recorrer el vector
            OP=0;
            d = vector[k]; // cojo el numero de filas
            matriz = casos(d); // llamo a la funcion casos
            colorear(matriz, d); // coloreo la matriz
        */

        
            System.out.println("Numero de operaciones para " + d + " filas: " + OP); // muestro el numero de operaciones
                                                                                     // para las d filas de la matriz

        in.close();

    }

    // img es un array de dimensiones n = d x d
    public static int colorear(int[][] img, int d) {
        int col = 2;
        for (int y = 0; y < d; y++) {
            for (int x = 0; x < d; x++) {
                if (img[y][x] == BLANCO) {
                    OP++;
                    pintar(img, x, y, d - 1, col++);
                }
            }
        }
        return col;
    }

    public static void pintar(int[][] img, int x, int y, int m, int col) {
        if (img[y][x] != BLANCO) {
            return;
        }
        img[y][x] = col;
        OP++;
        if (x < m) {
            pintar(img, x + 1, y, m, col);
            OP++;
        }
        if (x > 0) {
            pintar(img, x - 1, y, m, col);
            OP++;
        }
        if (y < m) {
            pintar(img, x, y + 1, m, col);
            OP++;
        }
        if (y > 0) {
            pintar(img, x, y - 1, m, col);
            OP++;
        }
    }

    // funcion que dado el numero de filas, crea la matriz, la rellena en blanco,
    // pinta los bordes negros, y escoge un numero al azar de lineas
    // verticales y horizontales que pintar de negro. Y devuelve la matriz
    public static int[][] casos(int d) {

        Random random = new Random();
        int[][] matriz = new int[d][d];
        int lineas = random.nextInt(15);
        int linea;

        for (int i = 0; i < d; i++) { // rellenar de blanco
            for (int j = 0; j < d; j++) {
                matriz[i][j] = BLANCO;
            }
        }

        for (int j = 0; j < d; j++) { // columnas
            matriz[0][j] = NEGRO;
            matriz[d - 1][j] = NEGRO;
        }

        for (int i = 0; i < d; i++) { // filas
            matriz[i][0] = NEGRO;
            matriz[i][d - 1] = NEGRO;
        }

        // System.out.println(lineas);

        while (lineas != 0) {
            linea = random.nextInt(d - 1);

            for (int i = 0; i < d; i++) {
                matriz[linea][i] = NEGRO;
            }

            for (int j = 0; j < d; j++) {
                matriz[j][linea] = NEGRO;
            }

            lineas--;
        }

        return matriz;

    }

}