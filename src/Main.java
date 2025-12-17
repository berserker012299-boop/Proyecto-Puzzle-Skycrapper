import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opc = 0;
        int dif = 1; // Variable para almacenar la dificultad actual

        while (opc != 5) {
            mostrarMenuPrincipal(dif);
            opc = leerEntero(sc); // Lee la opción del usuario

            switch (opc) {
                case 1:
                    dif = 1; // Establece dificultad fácil
                    menuDificultad("Fácil", 4, sc); // Tamaño 4x4
                    break;
                case 2:
                    dif= 2; // Establece dificultad intermedia
                    menuDificultad("Intermedio", 5, sc); // Tamaño 5x5
                    break;
                case 3:
                    dif = 3; // Establece dificultad difícil
                    menuDificultad("Difícil", 6, sc); // Tamaño 6x6
                    break;
                case 4:
                    menuComoJugar(sc); // Muestra instrucciones del juego
                    break;
                case 5:
                    System.out.println("Saliendo del juego...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Muestra el menú principal del juego
    public static void mostrarMenuPrincipal(int dificultad) {
        System.out.println("--------- Puzzle Skyscraper ---------");
        System.out.println("Bienvenido al juego de Puzzle SkyScraper. Selecciona una opción:");
        System.out.println("1. Fácil");
        System.out.println("2. Intermedio");
        System.out.println("3. Difícil");
        System.out.println("4. ¿Cómo jugar?");
        System.out.println("5. Salir del juego");
    }

    // Menú específico para cada dificultad
    public static void menuDificultad(String nombre, int size, Scanner sc) {
        System.out.println("Has seleccionado la dificultad " + nombre + ". En esta dificultad se genera una cuadrícula de " + size + "x" + size + ". Selecciona una opción:");
        System.out.println("1. Empezar");
        System.out.println("2. Cambiar dificultad");

        int opc = leerEntero(sc); // Lee la opción del usuario

        if (opc == 1) {
            jugar(size, sc); // Inicia el juego con el tamaño especificado
        }
        // Si opc == 2, simplemente retorna al menú principal
    }

    // Muestra las instrucciones y explicación del juego
    public static void menuComoJugar(Scanner sc) {
        System.out.println("Selecciona una opción:");
        System.out.println("1. ¿Qué es Skyscraper?");
        System.out.println("2. ¿Cómo se juega?");

        int op = leerEntero(sc);

        if (op == 1) {
            // Explicación conceptual del juego Skyscraper
            System.out.println("Skyscraper es un rompecabezas lógico donde debe rellenarse una cuadrícula con números del 1 al tamaño de la cuadrícula, de manera que las filas y columnas contengan todos los números sin repetir.");
            System.out.println("Dentro de la cuadrícula, cada número representa un edificio, y este es más alto cuanto más grande sea el número en cuestión.");
            System.out.println("A los bordes de la cuadrícula, se muestran números que corresponden a pistas de visibilidad, representando cuántos edificios se ven desde ese ángulo en concreto.");
            System.out.println("Un edificio más alto tapa a otros más bajos, haciendo que no puedan verse desde el ángulo en cuestión.");
        } else if (op == 2) {
            // Instrucciones de juego prácticas
            System.out.println("Al seleccionar una dificultad, se generará un tablero, el cual tendrá una cuadrícula, pistas de visibilidad representadas en números y coordenadas representadas en letras.");
            System.out.println("Debes ingresar la coordenada de la casilla que quieres rellenar para ingresar un valor en ella.");
            System.out.println("Por ejemplo: La coordenada AE corresponde a la fila A y la columna E.");
        }
    }

    // Método principal que controla el flujo del juego
    public static void jugar(int size, Scanner sc) {
        int[][] tableroUsuario = new int[size][size]; // Tablero que el usuario va llenando
        int[][] tableroSolucion = generarSolucion(size); // Solución generada automáticamente

        ArrayList<String> coordenadas = generarCoordenadas(size); // Lista de coordenadas válidas

        int visibles[][] = generarPistas(size, tableroSolucion); // Pistas de visibilidad en los bordes

        boolean terminado = false;

        while (!terminado) {
            mostrarTablero(tableroUsuario, visibles, coordenadas, size);

            System.out.print("Ingresa una coordenada (Por ejemplo: AE) o X para terminar: ");
            String coord = sc.next().toUpperCase();

            if (coord.equals("X")) {
                System.out.println("Has salido del puzzle.");
                return; // Sale del juego
            }

            if (!coordenadas.contains(coord)) {
                System.out.println("Coordenada inválida.");
                continue; // Vuelve a pedir coordenada
            }

            // Convierte la coordenada en índices de matriz
            // Ejemplo: "AE" -> col = 0 (A), fil = 0 (E para tamaño 4)
            int col = coord.charAt(0) - 'A'; // Primera letra es columna
            int fil = coord.charAt(1) - 'A' - size; // Segunda letra es fila

            System.out.print("Introduce número (1-" + size + "): ");
            int valor = leerEntero(sc);

            if (valor < 1 || valor > size) {
                System.out.println("Valor inválido.");
                continue; // Vuelve a pedir valor
            }

            tableroUsuario[fil][col] = valor; // Asigna el valor al tablero del usuario

            if (tableroCompleto(tableroUsuario)) {
                terminado = true; // Termina si el tablero está completo
            }
        }

        // Al terminar, verifica si la solución es correcta
        if (compararTableros(tableroUsuario, tableroSolucion)) {
            System.out.println("¡Felicidades! Has resuelto el puzzle.");
        } else {
            System.out.println("Incorrecto. El puzzle no coincide con la solución.");
        }
    }

    // Método para leer enteros con validación
    public static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next(); // Descarta entrada no válida
            System.out.print("Introduce un número válido: ");
        }
        return sc.nextInt();
    }

    // Genera una solución automática para el puzzle
    // Nota: Esta implementación genera un patrón simple, no una solución aleatoria válida
    public static int[][] generarSolucion(int size) {
        int[][] sol = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                sol[i][j] = (j + i) % size + 1; // Patrón diagonal cíclico
        return sol;
    }

    // Genera la lista de coordenadas válidas para el tablero
    // Ejemplo para tamaño 4: AA, AB, AC, AD, BA, BB, BC, BD, etc.
    public static ArrayList<String> generarCoordenadas(int size) {
        ArrayList<String> lista = new ArrayList<>();

        for (int c = 0; c < size; c++) {
            for (int f = size; f < size * 2; f++) {
                // Crea coordenadas como "AE", "AF", etc.
                lista.add("" + (char) ('A' + c) + (char) ('A' + f));
            }
        }
        return lista;
    }

    // Verifica si el tablero está completamente lleno (sin ceros)
    public static boolean tableroCompleto(int[][] tablero) {
        for (int[] fila : tablero)
            for (int v : fila)
                if (v == 0) return false;
        return true;
    }

    // Compara dos tableros para ver si son iguales
    public static boolean compararTableros(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                if (a[i][j] != b[i][j]) return false;
        return true;
    }

    // IMPORTANTE: Este método no genera pistas de visibilidad reales
    // Solo copia los valores de los bordes de la solución
    // Para un juego real, necesitarías calcular cuántos edificios son visibles desde cada borde
    public static int[][] generarPistas(int size, int[][] sol) {
        int[][] pistas = new int[4][size]; // 4 lados: arriba, abajo, izquierda, derecha

        for (int i = 0; i < size; i++) {
            pistas[0][i] = sol[0][i]; // Fila superior
            pistas[1][i] = sol[size - 1][i]; // Fila inferior
            pistas[2][i] = sol[i][0]; // Columna izquierda
            pistas[3][i] = sol[i][size - 1]; // Columna derecha
        }
        return pistas;
    }

    // Muestra el tablero con formato visual
    public static void mostrarTablero(int[][] t, int[][] pistas, ArrayList<String> coords, int size) {
        // Encabezado de columnas (A, B, C, ...)
        System.out.print("    ");
        for (int c = 0; c < size; c++)
            System.out.print((char) ('A' + c) + " ");
        System.out.println();

        // Pistas superiores
        System.out.print("    ");
        for (int i = 0; i < size; i++)
            System.out.print(pistas[0][i] + " ");
        System.out.println();

        // Filas del tablero con pistas laterales
        for (int f = 0; f < size; f++) {
            // Letra de fila y pista izquierda
            System.out.print((char) ('A' + size + f) + " " + pistas[2][f] + " ");

            // Contenido de la fila
            for (int c = 0; c < size; c++) {
                if (t[f][c] == 0) System.out.print("- "); // Casilla vacía
                else System.out.print(t[f][c] + " "); // Casilla con número
            }
            System.out.println(pistas[3][f]); // Pista derecha
        }

        // Pistas inferiores
        System.out.print("    ");
        for (int i = 0; i < size; i++)
            System.out.print(pistas[1][i] + " ");
        System.out.println();
    }
}