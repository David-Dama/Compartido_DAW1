package PowerMind;

import java.util.Arrays;
import java.util.Scanner;

public class PowerMind {

    public static void main(String[] args){
        int opcion, numPartida = 1;
        int[] stats = new int[5];
        Scanner sc = new Scanner(System.in);

        // Bucle principal
        do {
            System.out.println("----------------------------");
            System.out.printf("PARTIDA NUMERO %d\n\n", numPartida);
            opcion = eleccionPrincipal(sc);
            if (opcion==1){
                iniciarPartida(generarNum(), stats, sc);
                numPartida++;
            }
            else {
                mostrarEstadistica(stats);
            }
        }while(opcion != 3);
        sc.close();
    }

    // Metodo para pedir numero entero con validacion ya que vamos a pedir muchos enteros
    public static int pedirEntero(Scanner sc) {
        while (!sc.hasNextInt()){
            System.out.println("Intoduzca un número");
            // Descartamos el input invalido
            sc.next();
        }
        return sc.nextInt();
    }

    // Metodo para mostrar el menu y nos devuelve que opcion elige el usuario
    public static int eleccionPrincipal(Scanner sc){
        int opcion;
        System.out.print("""
        1. Iniciar partida
        2. Ver estadísticas
        3. Finalizar juego
        
        Escoja una opcion: """);
        opcion = pedirEntero(sc);
        System.out.println();
        // Bucle de validacion del rango de enteros
        while(opcion <1 || opcion >3){
            System.out.println("Introduce sólo un numero entre el 1-3!");
            opcion=pedirEntero(sc);
        }
        return opcion;
    }

    // Metodo para generar el numero correcto
    public static int[] generarNum (){
        int [] array = new int[4];
        // Array de booleanos para guardar del 0-9, que se auto rellena con false
        boolean [] usado = new boolean[10];
        // Bucle rellena array
        for (int i = 0; i < array.length; i++){
            int num;
            do {
                // Genera randoms del 0 al 9
                num = (int) ((Math.random() * 10));
            } while (usado[num]);
            array[i] = num;
            // Cambiamos a true la posicion que tiene a num como indice
            // Así si vuelve a salir, el bucle se ejecutara
            usado[num] = true;
        }
        return array;
    }

    // Metodo para opcion 1. Iniciar partida
    public static void iniciarPartida(int[] correctNum, int[] resultado, Scanner sc){
        int [] secretNum = new int[4];
        int numIntentos = 1;

        while (numIntentos <= 5) {
            System.out.println("----------------------------");
            System.out.printf("Intento número %d\n", numIntentos);
            System.out.println("\nRECUERDA: Para salir debe escribir -1.");

            // Bucle para rellenar array
            for (int i = 0; i < secretNum.length; i++) {
                do {
                    System.out.printf("Introduce el número secreto de la posición %d: ", i);
                    secretNum[i] = pedirEntero(sc);
                    // Condicion para sacarnos del metodo
                    if (secretNum[i] == -1) {
                        // -1 indica partida abandonada, nos saca del metodo directamente
                        resultado[4] += 1;
                        // -1 intentos porque ha abandonado el intento actual, entonces este intento no cuenta
                        resultado[2] += numIntentos-1;
                        // salimos del metodo
                        return;
                    }
                    if (secretNum[i] < 0 || secretNum[i] > 9) {
                        System.out.println("Número inválido. Solo se acepta número entre 0 y 9.");
                    }
                } while (secretNum[i] < 0 || secretNum[i] > 9);
            }

            char [] SNO = comprobar(secretNum,correctNum);

            //Salida de datos
            System.out.println("""
                       Recuerda:
                       S == En la posición correcta
                       N == No está
                       O == En posición incorrecta
                    """);

            System.out.println(Arrays.toString(secretNum));
            System.out.println(" ↑  ↑  ↑  ↑");
            System.out.println(Arrays.toString(SNO));

            //Si encuentra el número, salimos del bucle y cambiamos el valor de la bandera para que en la siguiente
            //Condicion retorne el valor correspondiente
            if (Arrays.equals (secretNum, correctNum)){
                switch (numIntentos){
                    case 1, 2:
                        resultado[0] += 3;
                        resultado[1] += 1;
                        break;
                    case 3, 4:
                        resultado[0] += 2;
                        resultado[1] += 1;
                        break;
                    default:
                        resultado[0] += 1;
                        resultado[1] += 1;
                }
                resultado[2] += numIntentos;
                System.out.println("¡Has ganado!\n");
                return;
            }
            numIntentos++;

        }
        System.out.println("¡Has perdido! Número máximo de intentos superado.");
        resultado[3] += 1;
        resultado[2] += 5;
    }

    public static char[] comprobar (int[] secretNum, int[] correctNum){
        // Array de caracteres para indicar si está, no está o en otra posición
        char[] sno = new char[4];
        boolean encontrado = false; //Creamos la bandera

        //Hacemos un bucle for para ver que introducimos en el array de caracteres
        for (int i = 0; i < secretNum.length; i++) {
            if (secretNum[i] == correctNum[i]) { //Si el número introducido es correcto en el array a adivinar el de caracteres toma 'S'
                sno[i] = 'S';
            } else if (secretNum[i] != correctNum[i]) {
                //Comprobamos si el numero se encuentra en alguna posicion del bucle correcto
                for (int ii = 0; ii < secretNum.length; ii++) {
                    encontrado = false;
                    if (secretNum[i] == correctNum[ii]) { //Si lo encuentra decimos que lo ha encontrado para poner una 'O'
                        encontrado = true;
                        break;
                    }
                }
                //Condicional en el caso de que esté o no esté
                if (encontrado) {
                    sno[i] = 'O';
                } else {
                    sno[i] = 'N';
                }
            }
        }
        return sno;
    }

    public static void mostrarEstadistica(int[] resultado){
        System.out.printf("""
        ESTADISTICAS
        -------------------------
        Número de puntos obtenidos: %d
        Numero de partidas ganadas: %d
        Número de intentos: %d
        Número de partidas perdidas: %d
        Número de partidas abandonadas: %d
        """,
                resultado[0], resultado[1], resultado[2], resultado[3], resultado[4]);
    }
}

