package Ejercicio_1;

import java.io.*;//clase para creacion y manipulacion de archivos
import java.util.InputMismatchException;
import java.util.Scanner;//clase para entrada de datos por teclado para el metodo menu()

/**
 * Clase EjecutarComandos que encapsula la funcionalidad de ejecutar comandos
 * del sistema operativo utilizando ProcessBuilder.
 * Proporciona métodos para ejecutar comandos con diferentes tipos de redirección.
 * @author GERMÁN ESCUDERO RODRÍGUEZ
 * @version 1
 * 19/10/24
 */

public class EjecutarComandos {

    /**
     * Metodo menu para seleccionar que opcion de ejecucion del comando desea el usuario
     * @return opcion elegida mediante entrada por teclado
     */
    public static int menu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        while (true) {
            try {
                System.out.println("Seleccione una de las tres opciones: " +
                        "\n1 para realizar un comando" +
                        "\n2 para realizar un comando mediante el uso de un buffer" +
                        "\n3 para realizar un comando y enviar la salida a un archivo");
                opcion = sc.nextInt();

                // Validar la opción
                if (opcion < 1 || opcion > 3) {
                    throw new IllegalArgumentException("Opción no válida, debe ser entre 1 y 3.");
                }

                // Retornar la opción válida
                return opcion;

            } catch (InputMismatchException e) {
                System.err.println("Error: Debes introducir un número entero.");
                sc.next(); // Limpiar el buffer
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }//Fin metodo menu

    /**
     * Metodo ejecutarComando, ejecutara el comando  especificado y devolvera el código de salida del proceso
     * Lo ejecutara mediante el cmd y sera un comando que se cerrara tras la ejecucion
     * @param comando que se ejecutara
     * @return codigo de salida para comprobar si el comando se ha efectuado correctamente
     * @throws IOException en caso de error en la entrada o salida del fichero
     * @throws InterruptedException en caso de que el metodo se interrumpa durante la espera
     */
    public int ejecutarComando(String comando) throws IOException, InterruptedException {
        try {
            Process proceso1 = new ProcessBuilder("cmd", "/c",comando).start();
            int codigoSalida = proceso1.waitFor();//Introducimos la excepcion InterruptedException para el waitFor()
            return codigoSalida;
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 1;
        }//Fin try-catch
    }//Fin metodo ejecutarComando

    /**
     * Metodo ejecutarComandoEntrada, ejecutara el comando especificado y proporcionara una entrada estandar al proceso
     * La entrada se enviara mediante el uso de outputStream para interactuar con el proceso
     * Lo ejecutara mediante el cmd y sera un comando que se cerrara tras la ejecucion
     * @param comando que se ejecutara
     * @param entrada estandar para enviar al proceso
     * @return codigo de salida para comprobar si el comando se ha efectuado correctamente
     * @throws IOException en caso de error en la entrada o salida del fichero
     * @throws InterruptedException en caso de que el metodo se interrumpa durante la espera
     */
    public int ejecutarComandoEntrada(String comando, String entrada) throws IOException, InterruptedException {
        try {
            Process proceso2 = new ProcessBuilder("cmd", "/c",comando).start();

            OutputStream os = proceso2.getOutputStream();
            os.write(entrada.getBytes());
            os.flush(); // Vacíamos el buffered de salida
            os.close();

            int codigoSalida = proceso2.waitFor();

            return codigoSalida;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 1;
        }//Fin try-catch
    }//Fin metodo ejecutarComandoEntrada

    /**
     * Metodo ejecutarComandoRedireccion, ejecutara el comando especificado y redirigira la salida estandar a un archivo
     * La salida del proceso sera escrita en el archivo
     * Lo ejecutara mediante el cmd y sera un comando que se cerrara tras la ejecucion
     * @param comando que se ejecutara
     * @param archivoSalida donde se redirigirá la salida del proceso
     * @return codigo de salida para comprobar si el comando se ha efectuado correctamente
     * @throws IOException en caso de error en la entrada o salida del fichero
     * @throws InterruptedException en caso de que el metodo se interrumpa durante la espera
     */
    public int ejecutarComandoRedireccion(String comando, File archivoSalida) throws IOException, InterruptedException {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c",comando);
            pb.redirectOutput(archivoSalida); // Redirigir a la salida al archivo
            //Con ProcessBuilder.Redirect.appendTo(archivoSalida) podria mantener la informacion sin que se sobre escriba
            Process proceso3 = pb.start();
            int codigoSalida = proceso3.waitFor();
            return codigoSalida;
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 1;
        }//Fin try-catch
    }//Fin metodo ejecutarComandoRedireccion

    /**
     * Metodo main de la clase EjecutarComandos, tomara la opcion seleccionada del menu y ejecutara
     * el comando correspondiente
     * @param args
     * @throws IOException en caso de error en la entrada o salida del fichero
     * @throws InterruptedException en caso de que el metodo se interrumpa durante la espera
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        EjecutarComandos ejecutar = new EjecutarComandos();
        switch (menu()){
            case 1://Ejecuta un comando
                int resultado1 = ejecutar.ejecutarComando("echo Prueba primer proceso");
                System.out.println("Resultado: " + resultado1);
                if (resultado1 == 0){
                    System.out.println("El comando se ejecuto correctamente");
                } else {
                    System.err.println("Hubo un problema en la ejecucion");
                }//Fin if-else
                break;
            case 2://Ejecuta un comando con entrada estandar
                int resultado2 = ejecutar.ejecutarComandoEntrada("echo", "Prueba segundo proceso");
                System.out.println("Resultado: " + resultado2);
                if (resultado2 == 0){
                    System.out.println("El comando se ejecuto correctamente");
                } else {
                    System.err.println("Hubo un problema en la ejecucion");
                }//Fin if-else
                break;
            case 3://Ejecuta un comando y redirige la salida a un archivo

                //Rutas del archivo, diferentes segun el sistema operativo por problemas con la lectura
                //File archivoSalida = new File("Practica 1 Procesos y Servicios RA1\\src\\Ejercicio_1\\archivoSalida.txt");//windows 10
                File archivoSalida = new File("src\\Ejercicio_1\\archivoSalida.txt");//windows 11

                int resultado3 = ejecutar.ejecutarComandoRedireccion("echo Prueba tercer proceso", archivoSalida);
                System.out.println("Resultado: " + resultado3);
                if (resultado3 == 0){
                    System.out.println("El comando se ejecuto correctamente");
                } else {
                    System.err.println("Hubo un problema en la ejecucion");
                }//Fin if-else
                break;
        }//Fin swtich
        System.out.println("Cerrando el programa");
    }//Fin metodo main
}//Fin clase ejecutarComando
