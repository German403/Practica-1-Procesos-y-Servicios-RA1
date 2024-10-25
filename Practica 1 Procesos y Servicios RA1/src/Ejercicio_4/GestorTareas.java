package Ejercicio_4;

//A documentar
import java.io.*;//clase para creacion y manipulacion de archivos
import java.util.InputMismatchException;
import java.util.Scanner;//clase para entrada de datos por teclado para el metodo menu()

/**
 * Clase GestorTareas
 * @author GERMÁN ESCUDERO RODRÍGUEZ
 * @version 1
 * 24/10/24
 */
public class GestorTareas {
    /**
     *
     */
    static String[] rutas = {"src\\ejercicio_4\\mesas.txt",
            "src\\ejercicio_4\\lamparas.txt",
            "src\\ejercicio_4\\alfombras.txt",
            "src\\ejercicio_4\\sofas.txt",
            "src\\ejercicio_4\\cortinas.txt"
    };

    /**
     *
     */
    public static void crearArchivos() {
        for (String archivo : rutas) {
            File file = new File(archivo);
            try{
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                } else {
                    file.createNewFile();
                }
            }catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al crear el archivo " + archivo);
            }
        }
    }

    public static String[] elegirArchivo() {
        Scanner sc = new Scanner(System.in);
        int archivosElegidos = 0;
        String archivo;
        int i = 0;
        int seleccionado = 0;
        boolean repetido = false;

        do{
            try {
                System.out.println("¿Cuántos archivos quieres comprimir?. Seleccione de 1-5");
                archivosElegidos = sc.nextInt();
                if (archivosElegidos < 1 || archivosElegidos > 5){
                    throw new IllegalArgumentException("Debe de elegir de 1 a 5 archivos");
                }
            } catch (InputMismatchException e1) {
                System.err.println("Error: Debes introducir un número entero.");
                sc.next(); // Limpiar el buffer
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }while(archivosElegidos <1 || archivosElegidos > 5);

        String[] comprobar = new String[archivosElegidos];
        String[] muebles = new String[archivosElegidos];

        try {
            while ( i < muebles.length) {
                try {
                    System.out.println("Seleccione un archivo del 1 al 5" +
                            "\nSeleccione 1 para comprimir el archivo de mesas" +
                            "\nSeleccione 2 para comprimir el archivo de lamparas" +
                            "\nSeleccione 3 para comprimir el archivo de alfombras" +
                            "\nSeleccione 4 para comprimir el archivo de sofas" +
                            "\nSeleccione 5 para comprimir el archivo de cortinas");
                    seleccionado = sc.nextInt();
                    if (seleccionado < 1 || seleccionado > 5){
                        throw new IllegalArgumentException("Debe de elegir uno de los 5 archivos");
                    }
                } catch (InputMismatchException e1) {
                    System.err.println("Error: Debes introducir un número entero.");
                    sc.next(); // Limpiar el buffer
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                }

                archivo = traerRuta(seleccionado);
                comprobar[i] = archivo;

                for (int j = 0; j < muebles.length; j++) {
                    if (muebles[j] != null && muebles[j].equals(archivo)) {
                        repetido = true;
                    }
                }

                // Verificamos si el archivo ya fue elegido
                if (repetido) {
                    System.out.println("El archivo '" + archivo + "' ya ha sido seleccionado. Por favor, elige otro.");
                    repetido = false;
                } else {
                    // Si no ha sido seleccionado antes, lo añadimos al array
                    muebles[i] = archivo;
                    //archivosElegidos++;
                    i++;
                }
            }
            // Pedimos al usuario que ingrese el nombre del archivo a comprimir
            // Tomamos la ruta del archivo


        } catch (Exception e) {
            System.out.println("Error al procesar la entrada. Inténtalo de nuevo.");
            sc.nextLine();  // Limpiar el buffer en caso de error
        }

        // Mostramos los archivos seleccionados
        System.out.println("Archivos seleccionados para comprimir:");
        for (String archivoFor : muebles) {
            System.out.println(archivoFor);
        }

        sc.close();
        return muebles;
    }

    public static String traerRuta(int opcion) {
        String archivo = "";
        try {
            if (opcion >= 1 || opcion <= 5) {
                switch (opcion) {
                    case 1:
                        archivo = rutas[0];
                        break;
                    case 2:
                        archivo = rutas[1];
                        break;
                    case 3:
                        archivo = rutas[2];
                        break;
                    case 4:
                        archivo = rutas[3];
                        break;
                    case 5:
                        archivo = rutas[4];
                        break;
                }
            }else{
                throw new IllegalArgumentException("Opcion no valida");
            }
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return archivo;
    }

    public static void compresorArchivos(String[] archivosComprimir, String tar) {
        try {
            Process p = new ProcessBuilder("tar", "-cvf", tar, "-T", "-").start();

            try(BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
                for (String archivo : archivosComprimir) {
                    bf.write(archivo);
                    bf.newLine();
                }
                bf.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }//Fin try-catch

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            System.out.println("Archivos comprimidos:");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }//Fin while
            br.close();

            BufferedReader br2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            while ((linea = br2.readLine()) != null) {
                sb.append(linea).append("\n");
            }//Fin while
            br2.close();

            int codigoSalida = p.waitFor();
            System.out.println("Resultado: " + codigoSalida);
            if (codigoSalida == 0) {
                System.out.println("Compresión exitosa en " + tar);
            } else {
                System.out.println("Error al comprimir los archivos:");
                System.out.println(sb.toString());
            }//Fin if-else
        }catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String tar = "src\\Ejercicio_4\\mueblesEmpaquetados.tar";
        crearArchivos();
        String[] archivosComprimir = elegirArchivo();
        compresorArchivos(archivosComprimir, tar);
    }
}
