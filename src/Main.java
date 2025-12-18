import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opc = 0;
        int dif = 1;

        while (opc != 5) {
            mostrarMenuPrincipal(dif);
            opc = leerEntero(sc);

            switch (opc) {
                case 1:
                    dif = 1;
                    menuDificultad("Fácil", 4, sc);
                    break;
                case 2:
                    dif= 2;
                    menuDificultad("Intermedio", 5, sc);
                    break;
                case 3:
                    dif = 3;
                    menuDificultad("Difícil", 6, sc);
                    break;
                case 4:
                    menuComoJugar(sc);
                    break;
                case 5:
                    System.out.println("Saliendo del juego...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    public static void mostrarMenuPrincipal(int dificultad) {
        System.out.println("--------- Puzzle Skyscraper ---------");
        System.out.println("Bienvenido al juego de Puzzle SkyScraper. Selecciona una opción:");
        System.out.println("1. Fácil");
        System.out.println("2. Intermedio");
        System.out.println("3. Difícil");
        System.out.println("4. ¿Cómo jugar?");
        System.out.println("5. Salir del juego");
    }

    public static void menuDificultad(String nombre, int size, Scanner sc) {
        System.out.println("Has seleccionado la dificultad " + nombre + ". En esta dificultad se genera una cuadrícula de " + size + "x" + size + ". Selecciona una opción:");
        System.out.println("1. Empezar");
        System.out.println("2. Cambiar dificultad");

        int opc = leerEntero(sc);

        if (opc == 1) {
            jugar(size, sc);
        }
    }

    public static void menuComoJugar(Scanner sc) {
        System.out.println("Selecciona una opción:");
        System.out.println("1. ¿Qué es Skyscraper?");
        System.out.println("2. ¿Cómo se juega?");

        int op = leerEntero(sc);

        if (op == 1) {
            System.out.println("Skyscraper es un rompecabezas lógico donde debe rellenarse una cuadrícula con números del 1 al tamaño de la cuadrícula, de manera que las filas y columnas contengan todos los números sin repetir.");
            System.out.println("Dentro de la cuadrícula, cada número representa un edificio, y este es más alto cuanto más grande sea el número en cuestión.");
            System.out.println("A los bordes de la cuadrícula, se muestran números que corresponden a pistas de visibilidad, representando cuántos edificios se ven desde ese ángulo en concreto.");
            System.out.println("Un edificio más alto tapa a otros más bajos, haciendo que no puedan verse desde el ángulo en cuestión.");
        } else if (op == 2) {
            System.out.println("Al seleccionar una dificultad, se generará un tablero, el cual tendrá una cuadrícula, pistas de visibilidad representadas en números y coordenadas representadas en letras.");
            System.out.println("Debes ingresar la coordenada de la casilla que quieres rellenar para ingresar un valor en ella.");
            System.out.println("Por ejemplo: La coordenada AE corresponde a la fila A y la columna E.");
        }
    }

    public static void jugar(int size, Scanner sc) {
        int[][] tableroUsuario = new int[size][size];
        int[][] tableroSolucion = generarSolucion(size);

        ArrayList<String> coordenadas = generarCoordenadas(size);

        int visibles[][] = generarPistas(size, tableroSolucion);

        boolean terminado = false;

        while (!terminado) {
            mostrarTablero(tableroUsuario, visibles, coordenadas, size);

            System.out.print("Ingresa una coordenada (Por ejemplo: AE) o X para terminar: ");
            String coord = sc.next().toUpperCase();

            if (coord.equals("X")) {
                System.out.println("Has salido del puzzle.");
                return;
            }

            if (!coordenadas.contains(coord)) {
                System.out.println("Coordenada inválida.");
                continue;
            }

            int col = coord.charAt(0) - 'A';
            int fil = coord.charAt(1) - 'A' - size;

            System.out.print("Introduce número (1-" + size + "): ");
            int valor = leerEntero(sc);

            if (valor < 1 || valor > size) {
                System.out.println("Valor inválido.");
                continue;
            }

            tableroUsuario[fil][col] = valor;

            if (tableroCompleto(tableroUsuario)) {
                terminado = true;
            }
        }

        if (compararTableros(tableroUsuario, tableroSolucion)) {
            System.out.println("¡Felicidades! Has resuelto el puzzle.");
        } else {
            System.out.println("Incorrecto. El puzzle no coincide con la solución.");
        }
    }

    public static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Introduce un número válido: ");
        }
        return sc.nextInt();
    }

    public static int[][] generarSolucion(int size) {
        int[][] sol = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                sol[i][j] = (j + i) % size + 1;
        return sol;
    }

    public static ArrayList<String> generarCoordenadas(int size) {
        ArrayList<String> lista = new ArrayList<>();

        for (int c = 0; c < size; c++) {
            for (int f = size; f < size * 2; f++) {
                lista.add("" + (char) ('A' + c) + (char) ('A' + f));
            }
        }
        return lista;
    }

    public static boolean tableroCompleto(int[][] tablero) {
        for (int[] fila : tablero)
            for (int v : fila)
                if (v == 0) return false;
        return true;
    }

    public static boolean compararTableros(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                if (a[i][j] != b[i][j]) return false;
        return true;
    }

    //Contar Visibles
    public static int contarVisibles(int[] linea) {
        int visibles = 0;
        int maxAltura = 0;

        for (int i = 0; i < linea.length; i++) {
            int altura = linea[i];
            if (altura > maxAltura) {
                visibles++;
                maxAltura = altura;
            }
        }
        return visibles;
    }

    //Generacion de pistas corregido
    public static int[][] generarPistas(int size, int[][] sol) {
        int[][] pistas = new int[4][size];

        for (int i = 0; i < size; i++) {
            //Pista IZQUIERDA (fila i normal)
            pistas[2][i] = contarVisibles(sol[i]);

            //Pista DERECHA (fila i al revés)
            int[] filaReversa = new int[size];
            for (int j = 0; j < size; j++) {
                filaReversa[j] = sol[i][size - 1 - j];
            }
            pistas[3][i] = contarVisibles(filaReversa);

            //Pista ARRIBA (columna i)
            int[] columna = new int[size];
            for (int j = 0; j < size; j++) {
                columna[j] = sol[j][i];
            }
            pistas[0][i] = contarVisibles(columna);

            //Pista ABAJO (columna i al revés)
            int[] columnaReversa = new int[size];
            for (int j = 0; j < size; j++) {
                columnaReversa[j] = sol[size - 1 - j][i];
            }
            pistas[1][i] = contarVisibles(columnaReversa);
        }
        return pistas;
    }

    public static void mostrarTablero(int[][] t, int[][] pistas, ArrayList<String> coords, int size) {
        System.out.print("    ");
        for (int c = 0; c < size; c++)
            System.out.print((char) ('A' + c) + " ");
        System.out.println();

        System.out.print("    ");
        for (int i = 0; i < size; i++)
            System.out.print(pistas[0][i] + " ");
        System.out.println();

        for (int f = 0; f < size; f++) {
            System.out.print((char) ('A' + size + f) + " " + pistas[2][f] + " ");
            for (int c = 0; c < size; c++) {
                if (t[f][c] == 0) System.out.print("- ");
                else System.out.print(t[f][c] + " ");
            }
            System.out.println(pistas[3][f]);
        }

        System.out.print("    ");
        for (int i = 0; i < size; i++)
            System.out.print(pistas[1][i] + " ");
        System.out.println();
    }
}//FIN