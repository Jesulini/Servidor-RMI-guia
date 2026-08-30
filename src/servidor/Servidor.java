package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {

        final int PUERTO = 5000;

        System.out.println("======================================");
        System.out.println(" SERVIDOR - VELOCIDAD PROMEDIO");
        System.out.println("======================================");

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {

            System.out.println("Servidor iniciado correctamente.");
            System.out.println("Esperando conexión del cliente...");
            System.out.println("Puerto: " + PUERTO);

            // El servidor queda funcionando continuamente
            while (true) {

                // Esperar conexión de un cliente
                Socket cliente = servidor.accept();

                System.out.println("\nCliente conectado desde: "
                        + cliente.getInetAddress());

                try (
                    DataInputStream entrada = new DataInputStream(
                            cliente.getInputStream());

                    DataOutputStream salida = new DataOutputStream(
                            cliente.getOutputStream())
                ) {

                    // Recibir datos originales enviados por el cliente
                    double distancia = entrada.readDouble();
                    double tiempo = entrada.readDouble();
                    String unidadDistancia = entrada.readUTF();
                    String unidadTiempo = entrada.readUTF();

                    System.out.println("Datos recibidos:");
                    System.out.println("Distancia: " + distancia + " "
                            + unidadDistancia);
                    System.out.println("Tiempo: " + tiempo + " "
                            + unidadTiempo);

                    // Validación adicional en el servidor
                    if (distancia <= 0 || tiempo <= 0) {

                        salida.writeBoolean(false);
                        salida.writeUTF(
                            "Error: la distancia y el tiempo deben ser mayores que cero."
                        );

                    } else {

                        // =====================================
                        // EL CALCULO SE REALIZA SOLO AQUÍ
                        // =====================================
                        double velocidadPromedio = distancia / tiempo;

                        // Construir unidad de velocidad
                        String unidadVelocidad = unidadDistancia + "/"
                                + unidadTiempo;

                        String resultado = String.format(
                                "La velocidad promedio es: %.2f %s",
                                velocidadPromedio,
                                unidadVelocidad
                        );

                        System.out.println(resultado);

                        // Enviar resultado al cliente
                        salida.writeBoolean(true);
                        salida.writeUTF(resultado);
                    }

                    salida.flush();

                } catch (IOException e) {

                    System.out.println(
                        "Error durante la comunicación con el cliente: "
                        + e.getMessage()
                    );

                } finally {

                    cliente.close();
                    System.out.println("Cliente desconectado.");
                }
            }

        } catch (IOException e) {

            System.out.println(
                "Error al iniciar el servidor: " + e.getMessage()
            );
        }
    }
}