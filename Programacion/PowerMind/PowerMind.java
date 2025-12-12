package PowerMind;

import java.util.Arrays;
import java.util.Scanner;

public class PowerMind {

    public static void main(String[] args) {
        /* Array para guardar las estadisticas:
        stats[0]=puntos obtenidos
        stats[1]=intentos
        stats[2]=partidas ganadas
        stats[3]=partidas perdidas
        stats[4]=partidas abandonadas
        */
        int[] stats = new int[5];
        // Declaracion inicial de parametros necesarios
        int opcion, numPartida = 1;
        // Creamos un scanner
        Scanner sc = new Scanner(System.in);

        // Bucle principal del programa
        do {
            opcion = eleccionPrincipal(sc);
            if (opcion == 1) {
                iniciarPartida(generarNum(4), stats, sc, numPartida);
                numPartida++;
            } else {
                mostrarEstadistica(stats);
            }
        } while (opcion != 3);

        // Cierre del scanner una vez finalizado el programa
        sc.close();
    }

    // Metodo para pedir numero entero con validacion ya que vamos a pedir muchos enteros
    public static int pedirEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.err.print("Introduzca solo números enteros: ");
            // Descartamos el input invalido
            sc.next();
        }
        return sc.nextInt();
    }

    // Metodo para mostrar el menu y nos devuelve que opcion elige el usuario
    public static int eleccionPrincipal(Scanner sc) {
        int opcion;
        System.out.print("""
                1. Iniciar partida
                2. Ver estadísticas
                3. Finalizar juego
                
                Escoja una opcion: """);
        opcion = pedirEntero(sc);
        System.out.println();
        // Bucle de validacion del rango de enteros
        while (opcion < 1 || opcion > 3) {
            System.err.print("Introduce sólo un numero entre el 1-3: ");
            opcion = pedirEntero(sc);
        }
        return opcion;
    }

    // Metodo para opcion 1. Iniciar partida
    public static void iniciarPartida(int[] numCorrecto, int[] stats, Scanner sc, int numPartida) {
        // Array que el usuario va a rellenar
        int[] numIntroducido = new int[4];
        // Array de pista que indicará si los numeros están en la posición correcta o no
        char[] sne = new char[4];
        int numIntentos = 1;

        System.out.println("----------------------------");
        System.out.printf("PARTIDA NUMERO %d\n\n", numPartida);

        while (numIntentos <= 5) {
            System.out.println("----------------------------");
            System.out.printf("Intento número %d\n", numIntentos);
            System.out.println("\nRECUERDA: Para salir debe escribir -1.");

            // Bucle para rellenar array
            for (int i = 0; i < numIntroducido.length; i++) {
                do {
                    System.out.printf("Introduce el número de la posición %d: ", i);
                    numIntroducido[i] = pedirEntero(sc);
                    // Condicion para sacarnos del metodo
                    if (numIntroducido[i] == -1) {
                        // -1 indica partida abandonada, nos saca del metodo directamente
                        stats[4] += 1;
                        // Intentos -1 porque ha abandonado el intento actual, entonces este intento no cuenta
                        stats[1] += numIntentos - 1;
                        // salimos del metodo
                        return;
                    }
                    // Condicion para enviar el mensage de error
                    if (numIntroducido[i] < 0 || numIntroducido[i] > 9) {
                        System.out.println("Número inválido. Solo se acepta número entre 0 y 9.");
                    }
                } while (numIntroducido[i] < 0 || numIntroducido[i] > 9);
            }

            // Llamada al metodo comprobar para que rellene el array de pistas
            sne = comprobar(numIntroducido, numCorrecto);

            mostrarEstado(numIntroducido, sne);

            // Condicion de que el jugador ha adivinado(ganado)
            if (Arrays.equals(numIntroducido, numCorrecto)) {
                // Cambiamos las estadisticas dependiendo del número de intentos
                switch (numIntentos) {
                    case 1, 2:
                        // Suma 3 puntos
                        stats[0] += 3;
                        break;
                    case 3, 4:
                        // Suma 2 puntos
                        stats[0] += 2;
                        break;
                    default:
                        // Suma 1 punto
                        stats[0] += 1;
                }
                // Suma los intentos
                stats[1] += numIntentos;
                // +1 Partida ganada
                stats[2] += 1;
                System.out.println("""
                        ----------------------------
                            (  )   (   )  )
                             ) (   )  (  (
                             ( )  (    ) )
                             _____________
                            <_____________> _____
                            |               |/ _ \\
                            |      GG       | | | |
                            |               |_| | |
                         ___|             |\\___/ /
                        /    \\___________/    \\
                        ----------------------------
                        ¡Ganaste!
                        """);

                return;
            }
            numIntentos++;

        }
        System.out.println("""
                ----------------------------
                        (  .   .  )
                      (      _      )
                     (               )
                     (      zzz      )
                      (             )
                        (         )
                ----------------------------
                Perdiste... ¡Más suerte a la próxima!
                """);

        // Suma intentos
        stats[1] += 5;
        // +1 Partida perdida
        stats[3] += 1;
    }

    // Metodo para generar el numero correcto
    public static int[] generarNum(int longitud) {
        // Array que vamos a generar
        int[] numCorrecto = new int[longitud];
        // Array de booleanos para guardar del 0-9, que se auto rellena con false
        boolean[] usado = new boolean[10];
        // Bucle rellena array
        for (int i = 0; i < numCorrecto.length; i++) {
            // Auxiliar
            int num;
            do {
                // Genera randoms del 0 al 9
                num = (int) ((Math.random() * 10));
            } while (usado[num]);
            numCorrecto[i] = num;
            // Cambiamos a true la posicion que tiene a num como indice
            // Así si vuelve a salir, el bucle se ejecutara
            usado[num] = true;
        }
        return numCorrecto;
    }

    // Metodo para comprobar el array introducido con el correcto
    public static char[] comprobar(int[] numIntroducido, int[] numCorrecto) {
        // Array de caracteres para indicar si está, no está o en otra posición
        char[] sne = new char[4];

        // Bucle for para ver que introducimos en el array de caracteres
        for (int i = 0; i < numIntroducido.length; i++) {
            // Condición de posición correcta
            if (numIntroducido[i] == numCorrecto[i]) {
                sne[i] = 'S';
            } else {
                /* Bucle para ver si existe en otra posicion
                Dentro del bucle nunca modificamos i, entonces seguimos en la misma posicion en el bucle principal
                Esto es útil ya que nos permite recorrer el número introducido en cada interación del bucle principal */
                for (int ii = 0; ii < numIntroducido.length; ii++) {
                    // Caso default, no encontrado
                    sne[i] = 'N';
                    // Condicion de encontrado en otra posicion
                    if (numIntroducido[i] == numCorrecto[ii]) {
                        // Sobreescribimos default por O, que indica que existe otra posición
                        sne[i] = 'E';
                        break;
                    }
                }
            }
        }
        return sne;
    }

    // Metodo para mostrar el mensage de estado
    public static void mostrarEstado(int[] numIntroducido, char[] sne) {
        System.out.println("""
                
                Recuerda:
                   S == En la posición correcta
                   N == No está
                   O == En posición incorrecta
                """);
        System.out.println(Arrays.toString(numIntroducido));
        System.out.println(" ↑  ↑  ↑  ↑");
        System.out.println(Arrays.toString(sne));
    }

    // Metodo que muestra las STATS
    public static void mostrarEstadistica(int[] resultado) {
        System.out.printf("""
                ESTADISTICAS
                -------------------------
                Número de puntos obtenidos: %d
                Número de intentos: %d
                Numero de partidas ganadas: %d
                Número de partidas perdidas: %d
                Número de partidas abandonadas: %d
                -------------------------
                
                """, resultado[0], resultado[1], resultado[2], resultado[3], resultado[4]);
    }
}

