package Ejercicio_1;

import java.io.*;
import java.util.Scanner;

//encapsula la funcionalidad de ejecutar comandos del sistema operativo utilizando ProcessBuilder.
//ProcessBuilder apartado 1.2.2 pagina 24

public class EjecutarComandos {

    public static int menu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        try {
            System.out.println("Seleccione una de las tres opciones: " +
                    "\n1 para realizar un comando" +
                    "\n2 para " +
                    "\n3 para realizar un comando y enviar la salida a un archivo");
            opcion = scanner.nextInt();
            if (opcion < 1 || opcion > 3) {
                throw new Exception("Opcion no valida");
            }
            return opcion;
        } catch (Exception e1) {
            e1.printStackTrace();
            return -1;
        }


    }

    //Ejecuta el comando especificado y devuelve el código de salida del proceso
    public int ejecutarComando(String comando) throws IOException, InterruptedException {
        try {
            Process proceso1 = new ProcessBuilder("cmd", "/c",comando).start();
            int codigoSalida = proceso1.waitFor();//Introducimos la excepcion InterruptedException para el waitFor()
            return codigoSalida;
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 1;
        }

    }

    //Ejecuta el comando y proporciona una entrada estándar al proceso.
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
        }

    }

    //Ejecuta el comando y redirige la salida estándar a un archivo
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
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        EjecutarComandos ejecutar = new EjecutarComandos();

        switch (menu()){
            case 1:
                int resultado1 = ejecutar.ejecutarComando("echo Prueba primer proceso");
                if (resultado1 == 0){
                    System.out.println("Resultado: " + resultado1);
                    System.out.println("El comando se ejecuto correctamente");
                } else {
                    System.err.println("Hubo un problema en la ejecucion");
                }
                break;
            case 2:
                int resultado2 = ejecutar.ejecutarComandoEntrada("echo", "Prueba segundo proceso");
                if (resultado2 == 0){
                    System.out.println("Resultado: " + resultado2);
                    System.out.println("El comando se ejecuto correctamente");
                } else {
                    System.err.println("Hubo un problema en la ejecucion");
                }
                break;
            case 3:
                File archivoSalida = new File("src\\Ejercicio_1\\archivoSalida.txt");
                int resultado3 = ejecutar.ejecutarComandoRedireccion("echo Prueba tercer proceso", archivoSalida);
                if (resultado3 == 0){
                    System.out.println("Resultado: " + resultado3);
                    System.out.println("El comando se ejecuto correctamente");
                } else {
                    System.err.println("Hubo un problema en la ejecucion");
                }
                break;
        }

        System.out.println("Cerrando el programa");
    }
}
