package PowerMind;

import java.util.Arrays;
import java.util.Scanner;

public class PowerMind {

    // Atributo scanner para poder usarlo en varios metodos
    private static final Scanner sc = new Scanner(System.in);

    // Metodo para pedir numero entero con validacion ya que vamos a pedir muchos enteros
    public static int pedirEntero() {
        while (!sc.hasNextInt()){
            System.out.println("Please enter a number!");
            // Descartamos el input invalido
            sc.next();
        }
        return sc.nextInt();
    }

    public static void main(String[] args){
        int opcion, numPartida = 1;
        // Bucle principal
        do {
            System.out.printf("PARTIDA NUMERO %d\n\n", numPartida++);
            opcion = eleccionPrincipal();
            if (opcion==1){
                jugar(generarNum());
            }
        }while(opcion != 3);
    }



    // Metodo para mostrar el menu y nos devuelve que opcion elige el usuario
    public static int eleccionPrincipal (){
        int opcion;
        System.out.print("""
        1. Iniciar partida
        2. Ver estadísticas
        3. Finalizar juego
        
        Escoja una opcion: """);
        opcion = pedirEntero();
        // Bucle de validacion del rango de enteros
        while(opcion <1 || opcion >3){
            System.out.println("Introduce sólo un numero entre el 1-3!");
            opcion=pedirEntero();
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
                num = (int) ((Math.random() * (9 + 1)));
            } while (usado[num]);
            array[i] = num;
            // Cambiamos a true la posicion que tiene a num como indice
            // Así si vuelve a salir, el bucle se ejecutara
            usado[num] = true;
        }
        return array;
    }

    // Metodo para opcion 1. Iniciar partida
    public static int jugar (int[] correctNum){
        int [] secretNum = new int[4];

        // Bucle para rellenar array
        for (int i = 0; i < secretNum.length;i++){
            do {
                System.out.println("Introduce el número secreto o -1 para salir");
                secretNum[i] = pedirEntero();
                // Condicion para sacarnos del metodo
                if (secretNum[i] == -1){
                    // -1 indica partida abandonada, nos saca del metodo directamente
                    return -1;
                }
                if (secretNum[i] < 0 || secretNum[i]>9){
                    System.out.println("Número inválido. Solo se acepta número entre 0 y 9.");
                }
            } while (secretNum[i] <0 || secretNum[i] > 9);
        }

        //En el caso de que sea incorrecto

        // Array de caracteres para indicar si está, no está o en otra posición
        char[] SNO =  new char[4];
        boolean encontrado = false;
        for (int i = 0; i < secretNum.length; i++){
            if (secretNum[i] == correctNum [i]){
                SNO[i] = 'S';
            }
            else if (secretNum[i] != correctNum[i]){
                //Comprobamos si el numero se encuentra en alguna posicion del bucle correcto
                for (int ii = 0; ii < secretNum.length; ii++){
                    encontrado = false;
                    if (secretNum[i] == correctNum[ii]) {
                        encontrado = true;
                        break;
                    }
                }
                if (encontrado){
                    SNO[i] = 'O';
                } else {
                    SNO[i] = 'N';
                }
            }
        }

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
        System.out.println("----------------------------");
        return 1;
    }
}

