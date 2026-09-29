package ServidorIMC;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {

    public static void iniciar() {

        try {

            Registry registro = LocateRegistry.createRegistry(1099);

            CalculoRmiImcImplem objetoRemoto =
                    new CalculoRmiImcImplem();

            registro.rebind("CalculoIMC", objetoRemoto);

            System.out.println("=================================");
            System.out.println("       SERVIDOR RMI IMC");
            System.out.println("=================================");
            System.out.println("Servidor iniciado correctamente.");
            System.out.println("Puerto: 1099");
            System.out.println("Servicio: CalculoIMC");
            System.out.println("Esperando clientes...");

        } catch (Exception e) {

            System.err.println("Error al iniciar el servidor:");
            e.printStackTrace();
        }
    }
}