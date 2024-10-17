package Ejercicio_2;

import java.io.*;

public class ComprimirArchivos {

    public static void compresorArchivos(String[] archivosAComprimir, String tar) throws IOException {
        try {
            ProcessBuilder pb = new ProcessBuilder("tar", "-cvf", tar, "-T", "-");

            Process p = pb.start();


            try(BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
                for (String archivo : archivosAComprimir) {
                    bf.write(archivo);
                    bf.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String linea;
            System.out.println("Archivos comprimidos:");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

            BufferedReader er = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder eO = new StringBuilder();
            while ((linea = er.readLine()) != null) {
                eO.append(linea).append("\n");
            }

            int codigoSalida = p.waitFor();
            System.out.println("Resultado: " + codigoSalida);
            if (codigoSalida == 0) {
                System.out.println("Compresión exitosa en " + tar);
            } else {
                System.out.println("Error al comprimir los archivos:");
                System.out.println(eO.toString());
            }


        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void comprobacion_Creacion_Archivos(String[] archivosAComprimir) throws IOException {
        for (String archivo : archivosAComprimir) {
            File file = new File(archivo);
            if (!file.exists()) {
                try{
                    file.createNewFile();
                    System.out.println("Archivo " + archivo + " creado");
                }catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al crear el archivo " + archivo);
                }

            }else{
                System.out.println("Se ha encontrado el archivo " + archivo);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        //String tar = "Practica 1 Procesos y Servicios RA1\\src\\Ejercicio_2\\archivosComprmidos.tar";//windows 10
        String tar = "src\\Ejercicio_2\\archivosComprmidos.tar";//windows 11
        /*
        String[] archivosAComprimir  = {"Practica 1 Procesos y Servicios RA1\\src\\Ejercicio_2\\archivo1.txt",
                "Practica 1 Procesos y Servicios RA1\\src\\Ejercicio_2\\archivo2.txt"};
                windows 10
         */
        String[] archivosAComprimir  = {"src\\Ejercicio_2\\archivo1.txt", "src\\Ejercicio_2\\archivo2.txt"};//windows 11

        comprobacion_Creacion_Archivos(archivosAComprimir);
        compresorArchivos(archivosAComprimir, tar);

    }
}
