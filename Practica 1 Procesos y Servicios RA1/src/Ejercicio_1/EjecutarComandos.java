package Ejercicio_1;

import java.io.*;
import java.util.Scanner;

//encapsula la funcionalidad de ejecutar comandos del sistema operativo utilizando ProcessBuilder.
//ProcessBuilder apartado 1.2.2 pagina 24

public class EjecutarComandos {

    public static int menu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        System.out.println("Seleccione una de las tres opciones: " +
                "1 para realizar un comando" +
                "2 para " +
                "3 para ");
        opcion = scanner.nextInt();
        return opcion;
    }

    //Ejecuta el comando especificado y devuelve el código de salida del proceso
    public int ejecutarComando(String comando) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c",comando);
        Process proceso1 = pb.start();
        int codigoSalida = proceso1.waitFor();//Introducimos la excepcion InterruptedException para el waitFor()
        return codigoSalida;
    }

    //Ejecuta el comando y proporciona una entrada estándar al proceso.
    public void ejecutarComandoEntrada(String comando, String entrada) throws IOException, InterruptedException {

    }

    //Ejecuta el comando y redirige la salida estándar a un archivo
    public void ejecutarComandoRedireccion(String comando, File archivoSalida) throws IOException, InterruptedException {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        EjecutarComandos ejecutar = new EjecutarComandos();

        switch (menu()){
            case 1:
                int resultado1 = ejecutar.ejecutarComando("echo Prueba primer proceso");
                if (resultado1 == 0){
                    System.out.println("El comando se ejecuto correctamente");
                }

                break;
            case 2:

                break;
            case 3:

                break;
        }

        System.out.println("Cerrrando programa");
    }
}
