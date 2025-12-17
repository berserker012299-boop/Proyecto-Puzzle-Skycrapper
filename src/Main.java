import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion=0;

        while (opcion!=5) {
            MenuPrincipal();

            opcion = sc.nextInt();
            switch(opcion) {
                case 1:
                    menuDificultad("FACIL", 3, sc);        //sc --> opcion de continar o retroceder al menu
                    break;
                case 2:
                    menuDificultad("MEDIO", 4, sc);
                    break;
                case 3:
                    menuDificultad("DIFICIL", 5, sc);
                    break;
                case 4:
                    menuDificultad("MUY DIFICIL",6 , sc);
                    break;
                case 5:
                    System.out.println("Gracias por jugar, vuelve pronto!");
                    break;
            }
        }
    }
    public static void MenuPrincipal() {
        System.out.println("\nBienvenido a Skycrapper Puzzle");
        System.out.println("Selecciona una opcion de dificultad:");
        System.out.println("1. Dificultad: Facil");
        System.out.println("2. Dificultad: Media");
        System.out.println("3. Dificultad: Dificil");
        System.out.println("4. Dificultad: Muy Dificil");
        System.out.println("5. Salir");
    }

    public static void menuDificultad(String nombre, int tamaño, Scanner sc) {
        System.out.println("Has seleccionado: " + nombre + " (" + tamaño + "x" + tamaño + ")");
        System.out.println("Ingresa 1 para jugar, 2 para volver");
        int option = sc.nextInt();
        if (option == 1) {
            System.out.println("Iniciando: ");
            jugar(tamaño, sc);
        }   //si opcion no es 1, no hago nada, el menu sigue mostrandose
    }

    public static void jugar(int tamaño, Scanner sc) {
        System.out.println("==== Iniciando juego " + tamaño + "x" + tamaño + "=====");
// Aquí necesitarás:
        // 1. Tablero vacío (array 2D de int)
        int[][] tablero = new int[tamaño][tamaño];  //array 2d, matriz cuadrada
        boolean jugando = true;

        while (jugando) {
            System.out.println("Tablero " + tamaño + "x" + tamaño + " creado.");
            System.out.println("Tiene " + (tamaño * tamaño) + " casillas vacías (0).");
            mostrarTablero(tablero);
            // 2. Solución generada (otro array 2D)

            System.out.println("Opciones: 1. Continuar  2. Salir");
            int op = sc.nextInt();

            if (op == 1) {

                System.out.println("(Próximo: pedir coordenada y número)");
            } else {
                jugando = false;
            }
        }
        // 3. Mostrar el tablero

        // 4. Bucle para pedir movimientos
    }

    public static int[][] generarSolucion(int tamaño) {
        int[][] sol = new int[tamaño][tamaño];

        for (int fila = 0; fila < tamaño; fila++) {
            for (int col = 0; col < tamaño; col++) {
                sol[fila][col] = (col + fila) % tamaño + 1;     //truco matemataico, latin square, cada numero aparece una vez por fila
            }
        }
        return sol;
    }
    //Sistema de coordenadas

    //generar pistas, se calculan desde la solucion

    //mostrarTabalero
    public static void mostrarTablero(int[][] tablero) {
        int tamaño = tablero.length;

        // 1. Mostrar letras de columnas (A B C D...)
        System.out.print("  "); // Espacio para los números de fila
        for (char c = 'A'; c < 'A' + tamaño; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        // 2. Mostrar cada fila con su número
        for (int fila = 0; fila < tamaño; fila++) {
            // Número de fila (1, 2, 3...)
            System.out.print((fila + 1) + " ");

            // Contenido de la fila
            for (int col = 0; col < tamaño; col++) {
                if (tablero[fila][col] == 0) {
                    System.out.print("- "); // Vacío
                } else {
                    System.out.print(tablero[fila][col] + " ");
                }
            }
            System.out.println();
        }
    }
}