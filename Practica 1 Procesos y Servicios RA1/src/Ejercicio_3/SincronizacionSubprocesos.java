package Ejercicio_3;

import java.io.*;//clase para creacion y manipulacion de archivos

/**
 * Clase SincronizacionSubprocesos, ejecutara dos comandos en un subproceso en los cuales independientemente
 * contaran las lineas y palabras del archivo correspondiente mediante comandos ejecutados en la powershell
 * @author GERMÁN ESCUDERO
 * @version 1
 * 22/10/24
 */
public class SincronizacionSubprocesos {

    /**
     * Metodo contadorLineas, ejecutara un comando en la powershell para contar la lineas de un archivo
     * El comando utiliza `Get-Content` y `Measure-Object -Line` para obtener el número de líneas
     * @param comando que se ejecutara en la powershell
     * @return el numero total de lineas
     * @throws IOException en caso de error en la entrada o salida del fichero
     * @throws InterruptedException en caso de que el metodo se interrumpa durante la espera
     */
    public static int contadorLineas(String comando) throws IOException, InterruptedException {
        String lineas;
        ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-Command", comando);
        Process p = pb.start();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while ((lineas = br.readLine()) != null) {
                // Si resultado.trim().matches("^\\d+$") devuelve true,
                // significa que la salida del comando es un valor numérico, que representa el conteo de líneas o palabras.
                if (lineas.trim().matches("^\\d+$")) {
                    return Integer.parseInt(lineas.trim()); // Si es un número, lo retorna
                }
            }
        }
        p.waitFor();
        System.err.println("Hubo un error en el conteo de lineas");
        return 0;
    }

    /**
     * Metodo contadorPalabras, ejecutara un comando en la powershell para contar la palabras de un archivo
     * El comando utiliza `Get-Content` y `Measure-Object -Word` para obtener el número de palabras
     * @param comando que se ejecutara en la powershell
     * @return el numero total de palabras
     * @throws IOException en caso de error en la entrada o salida del fichero
     * @throws InterruptedException en caso de que el metodo se interrumpa durante la espera
     */
    public static int contadorPalabras(String comando) throws IOException, InterruptedException {
        String palabras;
        ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-Command", comando);
        Process p = pb.start();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while ((palabras = br.readLine()) != null) {
                // Si resultado.trim().matches("^\\d+$") devuelve true,
                // significa que la salida del comando es un valor numérico, que representa el conteo de líneas o palabras.
                if (palabras.trim().matches("^\\d+$")) {
                    return Integer.parseInt(palabras.trim()); // Si es un número, lo retorna
                }
            }
        }
        p.waitFor();
        System.err.println("Hubo un error en el conteo de palabras");
        return 0;
    }

    /**
     * Metodo main de la clase SincronizacionSubproceosos, ejecutara dos comandos para obtener la cantidad
     * de lineas y palabras de un archivo mediante la powershell
     * @param args
     * @throws IOException en caso de error en la entrada o salida del fichero
     * @throws InterruptedException en caso de que el metodo se interrumpa durante la espera
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        String[] archivos = {"src\\Ejercicio_3\\archivo1.txt", "src\\Ejercicio_3\\archivo2.txt"};//windows 11

        try{
            int totalLineas = contadorLineas("Get-Content " + archivos[0] + " | Measure-Object -Line");
            int totalPalabras = contadorPalabras("Get-Content " + archivos[1] + " | Measure-Object -Word");

            if (!(totalLineas == 0)) {
                System.out.println("El archivo "+ archivos[0] + " tiene " + totalLineas + " lineas");
            } else {
                System.err.println("El archivo" + archivos[0] + "esta vacio");
            }

            if (!(totalPalabras == 0)) {
                System.out.println("El archivo "+ archivos[1] + " tiene " + totalPalabras + " palabras");
            } else {
                System.err.println("El archivo" + archivos[1] + "esta vacio");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
